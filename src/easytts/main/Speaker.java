package easytts.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.deb.lib.program.Logger;
import com.deb.lib.program.ProgramFs;

public class Speaker {
	
	static final int loopDelay = 50;
	File batch;
	ArrayList<ReplacementIndex> rIs = new ArrayList<>();
	
	public Speaker() {
		this(ProgramFs.getProgramFile("Speaker.bat"), null, true, null);//NOTE: Use full constructor
	}
	
	public Speaker(File f, ReplacementIndex preBase, boolean useBase, ReplacementIndex postBase) {
		batch = f;
		if(preBase != null) this.rIs.add(preBase);
		if(useBase) this.rIs.add(this.getBase());
		if(postBase != null) this.rIs.add(postBase);
	}
	
	public boolean speak(String message, boolean pause, boolean log) {
		if(checkFillBat()) {
			Thread t = new Thread(new Runnable(){
				@Override
				public void run() {
					String command = "cmd /c start /b /w /d\"";
					command += batch.getParent();
					command += "\" ";
					command += batch.getName();
					command += " \"";
					command += replaceAll(message);
					command += "\"";
					Process p;
					try {
						if(log) Logger.uLogger.log("Speaking", command);
						p = Runtime.getRuntime().exec(command);
						if(pause) {
							while(p.isAlive()) {
								try {
									Thread.sleep(loopDelay);
								} catch (InterruptedException e) {  }
							}
						}
					} catch (IOException e1) {  }
					
					
				}
			});
			
			t.start();
			
			if(pause) {
				while(t.isAlive()) {
					try {
						Thread.sleep(loopDelay);
					} catch (InterruptedException e) {  }
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	String replaceAll(String in) {
		String out = in;
		
		for(int i = 0; i < this.rIs.size(); i++) {
			out = this.rIs.get(i).replace(out);
		}
		
		return out;
	}
	
	boolean checkFillBat() {
		if(!batch.isFile()) {
			return ProgramFs.saveString(batch, batchString);
		}
		return true;
	}
	
	ReplacementIndex getBase() {
		return new ReplacementIndex()
				.add("\t", " ")
				.add("\b", " ")
				.add("\n", " ")
				.add("\r", " ")
				.add("\f", " ")
				.add("\'", " ")
				.add("\"", " ")
				.add("\\", " back slash ")
				.add("-", " ")
				.add("/", " forward slash ")
				;
	}
	
	String batchString = "title Talk\ncolor A\n:st\ncls\nif exist Talk_.vbs del Talk_.vbs\ncopy NUL Talk_.vbs\ncls\nping localhost -n 2 > nul\necho strText = (\"%~1\")> \"Talk_.vbs\"\necho Set objvoice = CreateObject(\"SAPI.SpVoice\")>> \"Talk_.vbs\"\necho ObjVoice.Speak strText>> \"Talk_.vbs\"\ncls\nstart /B /w Talk_.vbs\nping localhost -n 3 > nul\ndel Talk_.vbs\nexit";
}
