package ann;
import java.util.ArrayList;

public class Layer{
	ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	ArrayList<Double> lastY = new ArrayList<Double>();

	
	public Layer(int neuronNumber, int inputNumber, double alpha) {
		for (int i=0;i<neuronNumber;++i){
			neurons.add(new Neuron(inputNumber, alpha));
			lastY.add(0.0);
		}
	}
	
	
	
	void setNeuron(Neuron neuron, int index){
		neurons.set(index, neuron);
	}
	
	public void addRandomWeights() {
		for (Neuron it : neurons)
			it.addRandomWeight();
	}
	public void subLAstRandomWeight() {
		for (Neuron it : neurons)
			it.subLastRandomWeight();
	}
	
	public ArrayList<Double> feedForward(ArrayList<Double> x) throws Exception {
		
		for (int i=0;i<neurons.size();++i){
			try {
				lastY.set(i, neurons.get(i).feedForward(x));
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage() + " --Neuron: " + i);
			}
		}
		
		return new ArrayList<Double>(lastY);
	}
	
	public void setQValue(ArrayList<ArrayList<Double>> weights, ArrayList<Double> nextQs) {
		for (int i=0;i<neurons.size();++i){
			ArrayList<Double> tmp = new ArrayList<Double>();
			for (int j=0;j<weights.size();++j){
				for (int k=0;k<weights.get(j).size();++k){
					if (i == k){
						tmp.add(weights.get(j).get(k));
					}
				}
			}
			neurons.get(i).setQValue(tmp, nextQs);// risk is here!!!
		}
		//System.out.println();
	}
	public void setQValueTop(ArrayList<Double> d, ArrayList<Double> y) {
		for (int i=0;i<neurons.size();++i){
			neurons.get(i).setQValue(d.get(i), y.get(i));
		}
	}
	public void backPropagation(double learnRate, double momentumRate) {
		for (int i=0;i<neurons.size();++i){
			neurons.get(i).backPropagation(learnRate, momentumRate);
		}
	}
	
	
	public double getSE() {
		double result = 0.0;
		for (Neuron itNeuron : neurons){
			result += itNeuron.getSE();
		}
		return result;
	}
	void resetSE(){
		for (Neuron itNeuron : neurons){
			itNeuron.resetSE();
		}
	}
	public ArrayList<Double> getY() {
		return new ArrayList<Double>(lastY);
	}
	public ArrayList<ArrayList<Double>> getWeights() {
		ArrayList<ArrayList<Double>> weights = new ArrayList<ArrayList<Double>>();
		for (int i=0;i<neurons.size();++i){
			weights.add(neurons.get(i).getWeights());
		}
		return weights;
	}
	public ArrayList<Double> getQs() {
		ArrayList<Double> qS = new ArrayList<Double>();
		for (int i=0;i<neurons.size();++i){
			qS.add(neurons.get(i).getQ());
		}
		return qS;
	}
	
	ArrayList<ArrayList<Double>> getWeightsToSave(){
		ArrayList<ArrayList<Double>> tmp = new ArrayList<ArrayList<Double>>();
		for (Neuron it : neurons){
			tmp.add(it.getWeightsToSave());
		}
		return tmp;
	}
	void setWeightsFromFile(ArrayList<ArrayList<Double>> weights){
		int i;
		for (i=0;i<neurons.size();++i){
			neurons.get(i).setWeightsFromFile(weights.get(i));
		}
		for (;i>0;--i){
			weights.remove(0);
		}
	}
}
