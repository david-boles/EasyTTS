package easytts.main;

import java.util.ArrayList;

public class ReplacementIndex {
	ArrayList<CharSequence> target = new ArrayList<>();
	ArrayList<CharSequence> replacement = new ArrayList<>();
	
	ReplacementIndex add(CharSequence target, CharSequence replacement) {
		this.target.add(target);
		this.replacement.add(replacement);
		return this;
	}
	
	String replace(CharSequence in) {
		String out = in.toString();
		for(int i = 0; i < this.target.size(); i++) {
			out.replace(this.target.get(i), this.replacement.get(i));
		}
		return out;
	}
}
