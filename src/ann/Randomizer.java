package ann;

import java.util.Random;

public class Randomizer {

	private int used[];
	
	public Randomizer(int start, int stop) {
		this.used = new int[stop - start];
		for (int i=0;i<stop-start;++i){
			used[i] = -1;
		}
		Random random = new Random();
		for (int i=0;i<stop-start;++i){
			int index = random.nextInt(stop-start - 1);
			while(used[index] != -1){
				if (index < stop - start - 1) ++index;
				else index = 0;
			}
			used[index] = i;
		}
	}
	
	public int getIndex(int value) {
		return used[value];
	}
	

}
