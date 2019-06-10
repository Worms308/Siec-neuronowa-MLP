import java.util.ArrayList;

import ann.LearningSet;
import ann.Net;

public class Main {
	

	public static void main(String[] args) throws Exception {
		//Divider.main();
		int[] topology1 = {2,2,1};
		Net net = new Net(topology1, 2, 1.0);
		//net.loadKnowledge("weights\\breast.knw");

		double learnRate = 0.01;
		double momentum = 0.5;

		LearningSet learningSet = new LearningSet("learningData", "xor");
		LearningSet testSet = new LearningSet("learningData", "xor");

		learningSet.loadFromFile();	
		testSet.loadFromFile();
		
		System.out.println("rozmiar: " + learningSet.getXVector(0).size());
		//System.out.println("rozmiar: " + testSet.getXSize());
		
		long start, stop;
		start = System.nanoTime();
		System.out.println(net.learnNet(learningSet, testSet, 0.000005 , learnRate, momentum, 1000000));
		stop = System.nanoTime();
		System.out.printf("Czas: %2.8f", (float)(stop-start)/1000000);
		//net.saveKnowledge("weights\\breast.knw");
		
		
		//System.out.println("Learning set errors: " + net.getEfficiency(learningSet));
		//System.out.printf("Wydajnosc:          %.5f %c\n", ((double)learningSet.getXSize() - net.getEfficiency(learningSet))/learningSet.getXSize() * 100.0, '%');
		
		//System.out.println("Testing set errors: " + net.getEfficiency(testSet));
		//System.out.printf("Wydajnosc:          %.5f %c\n", ((double)testSet.getXSize() - net.getEfficiency(testSet))/testSet.getXSize() * 100.0, '%');
		
		/*for(int i=0;i<learningSet.getXSize();++i)
			if (net.feedForward(learningSet.getXVector(i)).get(0) > 0.5)
				//System.out.println("Id: " + (i+1) + " \tPoprawna odpowiedz: " + learningSet.getDVector(i).get(0));
				System.out.println("Answer: " + net.feedForward(learningSet.getXVector(i)).get(0));*/
		
		
		/*for(int i=0;i<learningSet.getXSize();++i)
			System.out.println(net.feedForward(learningSet.getXVector(i)) + " \tindex: " + (i+1));*/
		/*int errors = 0;
		double goodAnswer;
		double netAnswer;
		double roundNetAnswer;
		for (int i=0;i<learningSet.getXSize();++i){
			try {
				netAnswer = net.feedForward(learningSet.getXVector(i)).get(0);
				goodAnswer = learningSet.getDVector(i).get(0);
				roundNetAnswer = (netAnswer > 0.13)?1.0:0.0;
				
				if (goodAnswer != roundNetAnswer){
					errors++;
					//System.out.println("id: " + (i+1));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bledy: " + errors);*/
		
		
		/*
		System.out.println("Dokladnosc: " + (double)(learningSet.getDSize() - errors)/learningSet.getDSize() * 100.0);
		System.out.println("Ilosc bledow: " + errors);*/
	}
}
