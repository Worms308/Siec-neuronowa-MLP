package ann;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LearningSet {
	private String path;
	private String filePatern;
	ArrayList<ArrayList<Double>> learningData = new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> goodAnswers = new ArrayList<ArrayList<Double>>();
	
	public LearningSet(String path, String fileName) {
		this.path = path + "\\";
		this.filePatern = fileName;
	}
	public LearningSet(ArrayList<ArrayList<Double>> lSet, ArrayList<ArrayList<Double>> dSet) {
		learningData = lSet;
		goodAnswers = dSet;
		path = "learningData\\";
		filePatern = "dane";
	}
	
	public void addRecord(ArrayList<Double> xVec, ArrayList<Double> dVec) {
		learningData.add(xVec);
		goodAnswers.add(dVec);
	}
	private boolean loadXVector() {
		File file = new File(path + filePatern + ".xvec");
		try{
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				learningData.add(new ArrayList<Double>());
				String newData = scanner.nextLine();
				if (newData.length() > 1){
					String[] doubles = newData.split(" ");
					for (int i=0;i<doubles.length;++i){
						learningData.get(learningData.size()-1).add(Double.valueOf(doubles[i]));
					}
				}
			}
			scanner.close();
		} catch (Exception e) {
			 System.err.println("B��d podczas odczytu danych uczacych z pliku!");
			 return true;
		}
		return false;
	}
	private boolean loadDVector() {
		File file = new File(path + filePatern + ".dvec");
		try{
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				goodAnswers.add(new ArrayList<Double>());
				String newData = scanner.nextLine();
				if (newData.length() > 1){
					String[] doubles = newData.split(" ");
					for (int i=0;i<doubles.length;++i){
						goodAnswers.get(goodAnswers.size()-1).add(Double.valueOf(doubles[i]));
					}
				}
			}
			scanner.close();
		} catch (Exception e) {
			 System.err.println("B��d podczas odczytu w�a�ciwych odpowiedzi z pliku!");
			 return true;
		}
		return false;
	}
	
	private boolean saveXVector() {
		File file = new File(path + filePatern + ".xvec");
		try{
			FileWriter fileWriter = new FileWriter(file);
			for (int i=0;i<learningData.size();++i){
				for (int j=0;j<learningData.get(i).size();++j){
					fileWriter.write(learningData.get(i).get(j).toString() + " ");
				}
				fileWriter.write("\n");
			} 
			fileWriter.close();
		} catch (Exception e) {
			 System.err.println("B��d podczas zapisu danych uczacych do pliku!");
			 return true;
		}
		return false;
	}
	private boolean saveDVector() {
		File file = new File(path + filePatern + ".dvec");
		try{
			FileWriter fileWriter = new FileWriter(file);
			for (int i=0;i<goodAnswers.size();++i){
				for (int j=0;j<goodAnswers.get(i).size();++j){
					fileWriter.write(goodAnswers.get(i).get(j).toString() + " ");
				}
				fileWriter.write("\n");
			} 
			fileWriter.close();
		} catch (Exception e) {
			 System.err.println("B��d podczas zapisu w�a�ciwych odpowiedzi do pliku!");
			 return true;
		}
		return false;
	}
	public boolean saveToFile() {
		if (saveXVector())
			return true;
		if (saveDVector())
			return true;
		return false;
	}
	public boolean loadFromFile() {
		if (loadXVector())
			return true;
		if (loadDVector())
			return true;
		return false;
	}
	public ArrayList<Double> getXVector(int index) {
		return learningData.get(index);
	}
	public ArrayList<Double> getDVector(int index) {
		return goodAnswers.get(index);
	}
	public int getXSize() {
		return learningData.size();
	}
	public int getDSize() {
		return goodAnswers.size();
	}

	public void printD() {
		for (int i=0;i<goodAnswers.size();++i){
			for (int j=0;j<goodAnswers.get(i).size();++j){
				System.out.print(goodAnswers.get(i).get(j) + " ");
			}
			System.out.println("");
		}
	}
	public void printY() {
		for (int i=0;i<learningData.size();++i){
			for (int j=0;j<learningData.get(i).size();++j){
				System.out.print(learningData.get(i).get(j) + " ");
			}
			System.out.println("");
		}
	}
}
