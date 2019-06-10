package game;

import java.util.ArrayList;

public class Translator {

	private Translator() {
		
	}

	public static int translateAnswer(ArrayList<Double> answer, int[] exceptions) {
		int result = 0;
		do {
			result++;
		} while (exceptions[result] != 0);
		
		for (int i=0;i<answer.size();++i){
			if (answer.get(i) > answer.get(result) && exceptions[i] == 0)
				result = i;
		}
		return result;
	}
}
