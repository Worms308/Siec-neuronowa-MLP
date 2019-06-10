package rawdata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Divider {
	private ArrayList<Row> data = new ArrayList<Row>();

	public Divider(String filename) {
		File file = new File(filename);
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				data.add(new Row(scanner.nextLine()));
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeDFile(String filename) {
		File file = new File(filename);
		try {
			FileWriter fileWriter = new FileWriter(file);
			for (int i=0;i<data.size();++i){
				fileWriter.write(data.get(i).getLast()+"\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeXFile(String filename) {
		File file = new File(filename);
		try {
			FileWriter fileWriter = new FileWriter(file);
			for (int i=0;i<data.size();++i){
				fileWriter.write(data.get(i).getData()+"\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main() {
		Divider divider = new Divider("rawData\\raw.txt");
		divider.makeDFile("rawData\\data.dvec");
		divider.makeXFile("rawData\\data.xvec");
	}
	
}
