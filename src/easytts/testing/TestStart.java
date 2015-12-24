package easytts.testing;

import java.util.Scanner;

import com.deb.lib.program.ProgramFs;

import easytts.main.Speaker;

public class TestStart {

	public static void main(String[] args) {
		System.out.println(ProgramFs.loadString(ProgramFs.getProgramFile("input.txt")));
		Speaker s = new Speaker();
		Scanner sc = new Scanner(System.in);
		//s.speak(ProgramFs.loadString(ProgramFs.getProgramFile("input.txt")), true, false);
		while(true) {
			s.speak(sc.nextLine(), true, false);
		}
	}

}
