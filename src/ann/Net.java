package ann;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Net implements Cloneable{
	ArrayList<Layer> layers = new ArrayList<Layer>();
	ArrayList<ArrayList<Double>> lastY = new ArrayList<ArrayList<Double>>();
	ArrayList<Double> RMSE = new ArrayList<Double>();
	private int neuronNumber;
	private int growingTime = 20;
	
	public Net(int topology[], int inputNumber, double alpha) {
		if (topology.length > 0){
			layers.add(new Layer(topology[0], inputNumber, alpha));
			lastY.add(new ArrayList<Double>());
			neuronNumber += topology[0];
		}
		for (int i=1;i<topology.length;++i){
			neuronNumber += topology[i];
			layers.add(new Layer(topology[i], topology[i-1], alpha));
			lastY.add(new ArrayList<Double>());
		}
	}
	
	public static Net mixNets(Net net1, Net net2) {
		try {
			Net result = (Net) net1.clone();
			
			return result;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addRandomWeightsWithChecks(LearningSet lSet, int index) {
		try {
			feedForward(lSet.getXVector(index));
			double prevSE = getLastLayerSE();
			for (Layer it : layers)
				it.addRandomWeights();
			feedForward(lSet.getXVector(index));
			if (prevSE < getLastLayerSE()){
				for (Layer it : layers)
					it.subLAstRandomWeight();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addRandomWeights() {
		for (Layer it : layers)
			it.addRandomWeights();
	}
	
	public long learnNet(LearningSet lSet, LearningSet TSet, double maxRMSE, double learnRate, double momentum, int maxEpoch) throws Exception {
		RMSE.clear();
		int iterations = 1;
		long epoch = 1;
		double rmse = 1.0;
		double prevRmse = 1.0;
		int rmseIsGrowing = 0;
		int epochSize = lSet.getXSize();
		int iterModuloSize;
		Randomizer randomizer = new Randomizer(0, epochSize);
		
		Scanner scanner = new Scanner(System.in);
		
		try {
			while(rmse > maxRMSE){
				if (epoch >= maxEpoch+1 && maxEpoch > 0) break;
				iterModuloSize = iterations%epochSize;
				//System.out.println("ewe");
				this.feedForward(lSet.getXVector(randomizer.getIndex(iterModuloSize)));
				this.getLastLayerSE();
				this.backPropagation(lSet.getDVector(randomizer.getIndex(iterModuloSize)), learnRate, momentum);
				
				if (iterations%epochSize == 0){
					//System.out.printf("RMSE: %.8f\n",rmse);
					//System.out.println("Epoch: " + epoch);
					randomizer = new Randomizer(0, epochSize);
					//scanner.nextLine();
				}
				
				if (iterModuloSize == 0){
					prevRmse = rmse;
					rmse = Math.sqrt(this.getLastLayerSE()/epochSize);
					this.resetNeuronsSE();
					RMSE.add(rmse);
					if (prevRmse < rmse + (0.0001*rmse)){
						++rmseIsGrowing;
						if (rmseIsGrowing >= growingTime){
							this.addRandomWeights();
							rmseIsGrowing = 0;
							//throw new Exception("Learning stoped at " + rmse);
						}
					} else
						rmseIsGrowing = 0;
					++epoch;
				}
				++iterations;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		return epoch-1;
	}
	public ArrayList<Double> getLastRMSE() {
		return RMSE;
	}
	public void resetNeuronsSE() {
		for (Layer it : layers)
			it.resetSE();
	}
	
	public ArrayList<Double> feedForward(ArrayList<Double> x) throws Exception {
		lastY.set(0, layers.get(0).feedForward(x)); // first layer
		
		for (int i=1;i<layers.size();++i){
			lastY.set(i, layers.get(i).feedForward(lastY.get(i-1)));
		}
		return lastY.get(lastY.size()-1);
	}
	
	private void setQAll(ArrayList<Double> d) {
		layers.get(layers.size()-1).setQValueTop(d, lastY.get(lastY.size()-1));
		
		for (int i=layers.size()-1-1;i>-1;--i){
			layers.get(i).setQValue(layers.get(i+1).getWeights(), layers.get(i+1).getQs());
		}
	}
	public void backPropagation(ArrayList<Double> d, double learnRate, double momentumRate) {
		this.setQAll(d);
		for (int i=0;i<layers.size();++i){
			layers.get(i).backPropagation(learnRate, momentumRate);
		}
	}
	public double getRMSE() {
		double result = 0.0;
		for (int i=0;i<layers.size();++i){
			result += layers.get(i).getSE();
		}
		return result/neuronNumber;
	}
	public double getLastLayerSE() {
		return layers.get(layers.size()-1).getSE();
	}
	
	public int getEfficiency(LearningSet learningSet) {
		int errors = 0;
		ArrayList<Double> netAnswer;
		ArrayList<Double> roundNetAnswer;
		ArrayList<Double> goodAnswer;
		for (int i=0;i<learningSet.getXSize();++i){
			try {
				netAnswer = this.feedForward(learningSet.getXVector(i));
				roundNetAnswer = new ArrayList<Double>();
				goodAnswer = learningSet.getDVector(i);
				for (int j=0;j<netAnswer.size();++j){
					roundNetAnswer.add((double) Math.round(netAnswer.get(j)));
				}
				
				if (!goodAnswer.equals(roundNetAnswer))
					errors++;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return errors;
	}

	public void saveKnowledge(String filename) {
		File file = new File(filename);
		try {
			FileWriter fileWriter = new FileWriter(file);
			ArrayList<ArrayList<Double>> toSave = new ArrayList<ArrayList<Double>>();
			int layerID = 1;
			for (Layer it : layers){
				fileWriter.write("   ---   Warstwa nr " + layerID + "   ---\n");
				toSave = it.getWeightsToSave();
				for (int i=0;i<toSave.size();++i){
					for (int j=0;j<toSave.get(i).size();++j){
						fileWriter.write(toSave.get(i).get(j).toString() + " ");
					}
					fileWriter.write("\n");
				}
				fileWriter.write(" \n");
				++layerID;
			}
			fileWriter.write(" ");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadKnowledge(String filename) {
		File file = new File(filename);
		try {
			Scanner scanner = new Scanner(file);
			ArrayList<ArrayList<Double>> toNeurons = new ArrayList<ArrayList<Double>>();
			ArrayList<Double> oneData = new ArrayList<Double>();
			String oneLine;
			String[] splitedLine;
			while (scanner.hasNextLine()){
				oneLine = scanner.nextLine();
				
				if (oneLine.startsWith(" ")) continue;
				
				splitedLine = oneLine.split(" ");
				for (int i=0;i<splitedLine.length;++i){
					oneData.add(Double.valueOf(splitedLine[i]));
				}
				toNeurons.add(new ArrayList<Double>(oneData));
				oneData.clear();
			}

			scanner.close();
			for (Layer it : layers)
				it.setWeightsFromFile(toNeurons);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
