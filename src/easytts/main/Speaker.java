package easytts.main;

import java.io.File;
import java.io.IOException;

import com.deb.lib.program.Logger;
import com.deb.lib.program.ProgramFs;

public class Speaker {
	
	static final int delay = 50;
	File batch;
	
	public Speaker() {
		batch = ProgramFs.getProgramFile("Speaker.bat");
	}
	
	public Speaker(File f) {
		batch = f;
	}
	
	public boolean speak(String message, boolean pause, boolean log) {
		//NOTE: Add code for determining validity of file
		if(checkFillBat()) {
			Thread t = new Thread(new Runnable(){
				@Override
				public void run() {
					String command = "cmd /c start /b /w /d\"";
					command += batch.getParent();
					command += "\" ";
					command += batch.getName();
					command += " \"";
					command += removeDangerous(message);
					command += "\"";
					Process p;
					try {
						if(log) Logger.uLogger.log("Speaking", command);
						p = Runtime.getRuntime().exec(command);
						if(pause) {
							while(p.isAlive()) {
								try {
									Thread.sleep(delay);
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
						Thread.sleep(delay);
					} catch (InterruptedException e) {  }
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	String removeDangerous(String in) {
		String out = in;
		
		//NOTE: Add anything
		
		return out;
	}
	
	boolean checkFillBat() {
		if(!batch.isFile()) {
			return ProgramFs.saveString(batch, batchString);
		}
		return true;
	}
	
	String batchString = "title Talk\ncolor A\n:st\ncls\nif exist Talk_.vbs del Talk_.vbs\ncopy NUL Talk_.vbs\ncls\nping localhost -n 2 > nul\necho strText = (\"%~1\")> \"Talk_.vbs\"\necho Set objvoice = CreateObject(\"SAPI.SpVoice\")>> \"Talk_.vbs\"\necho ObjVoice.Speak strText>> \"Talk_.vbs\"\ncls\nstart /B /w Talk_.vbs\nping localhost -n 3 > nul\ndel Talk_.vbs\nexit";
}
