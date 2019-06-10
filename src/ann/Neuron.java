package ann;
import java.util.ArrayList;
import java.util.Random;

public class Neuron{
	private double y;
	private double u;
	private double alpha;
	private double se;
	private static Random random = new Random();
	private ArrayList<Double> weights = new ArrayList<Double>();
	private ArrayList<Double> weightsPrev = new ArrayList<Double>();
	private ArrayList<Double> weightsFromRand = new ArrayList<Double>();
	private double localX[];
	
	private double qValue = 0.0;
	
	private double qValueAvg = 0.0;
	private int iterations = 0;
	private ArrayList<Double> numbers = new ArrayList<Double>();
	
	public static Neuron mixNeurons(Neuron neuron1, Neuron neuron2) {
		Neuron result = new Neuron(neuron1.weights.size(), neuron1.alpha);
		for (int i=0;i<neuron1.weights.size();++i){
			result.weights.set(i, (neuron1.weights.get(i) + neuron2.weights.get(i))/2);
		}
		return result;
	}
	
	private double getRandomWeight() {
		return (double)((double)random.nextInt() / (double)Integer.MAX_VALUE);
	}
	private void copyWeights() {
		weightsPrev = new ArrayList<Double>(weights);
	}
	
	public Neuron(int inputNumber, double alpha) {
		localX = new double[inputNumber+1];
		for (int i=0;i<inputNumber + 1;++i){ // +1 BIAS
			weights.add(getRandomWeight());
			weightsPrev.add(0.0);
		}
		this.copyWeights();
		this.alpha = alpha;
		y = 0;
		u = 0;
		se = 0.0;
	}
	public void addRandomWeight() {
		//weightsFromRand.clear();
		//double buffor;
		/*for (Double it : weights){
			buffor = getRandomWeight() * 40;
			weightsFromRand.add(buffor);
			//System.out.println("przed: " + it);
			it += buffor;
			//System.out.println("po: " + it);
		}*/
		for (int i=0;i<weights.size();++i){
			weights.set(i, weights.get(i) + getRandomWeight()/1.0);
		}
	}
	public void subLastRandomWeight() {
		for (int i=0;i<weights.size();++i){
			weights.set(i, weights.get(i) - weightsFromRand.get(i));
		}
	}
	
	public double relu(double val) {
		if (val < 0) return 0;
		return val;
	}
	
	public double feedForward(ArrayList<Double> x) throws Exception{
		if (x.size() != (weights.size() - 1))
			throw new Exception("Nierówne wektory wagowy - wejœæ!");
		
		for (int i=0;i<x.size();++i)
			localX[i] = x.get(i);
		localX[localX.length-1] = 1.0;
		
		u = 0.0;
		for (int i=0;i<x.size();++i){
			u += weights.get(i) * x.get(i);
		}
		u += weights.get(weights.size()-1);
		
		y = 1 / (1 + Math.pow(Math.E, -alpha * u));
		//y = relu(u);
		//System.out.println("y = " + y + " " + u);
		return y;
	}
	public void setQValue(ArrayList<Double> weightsToThis, ArrayList<Double> nextQs) {
		if (weightsToThis.size() != nextQs.size()){
			System.err.println("Ró¿ne wejscia przy obliczeniu wspó³czynnika Q!");
			//System.out.println(weightsToThis.size() + " " + nextQs.size());
		}
		
		qValue = 0.0;
		for (int i=0;i<weightsToThis.size();++i){
			qValue += weightsToThis.get(i) * nextQs.get(i);
		}
		qValueAvg += qValue;
		numbers.add(qValue);
		iterations++;
		if (iterations >= 150){
			double variation = 0;
			double min = 999999.9, max = -999999.9;
			for (Double it : numbers){
				variation += Math.pow(it - (qValueAvg/iterations), 2);
				if (it < min) min = it;
				if (it > max) max = it;
			}
			variation /= iterations;
			//System.out.println("Neuron: " + qValueAvg/iterations + "\t Wariancja: " + variation + "\t\tMin: " + min + "\t Max: " + max);
			//System.out.printf("Neuron: %11.8f    Wariancja: %11.8f     Min: %11.8f    Max: %11.8f\n", qValueAvg/iterations, variation, min, max);
			numbers.clear();
			qValueAvg = 0.0;
			iterations = 0;
		}
	}
	public void setQValue(double d, double y) {
		qValue = d - y;
	}
	public void backPropagation(double learnRate, double momentumRate) {
		double delta = (qValue) * y * (1 - y);
		
		for (int i=0;i<weights.size();++i){
			double weightValue;
			double momentum = weights.get(i) - weightsPrev.get(i);
			weightsPrev.set(i, weights.get(i));
			//System.out.println(momentum);
			weightValue = weights.get(i) + learnRate * delta * localX[i] + (momentumRate * momentum);
			
			weights.set(i, weightValue);
		}
	}
	public double getSE() {
		se += Math.pow(qValue, 2)/2;
		return se;
	}
	void resetSE(){
		se = 0.0;
	}

	public ArrayList<Double> getWeights() { //combination !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ArrayList<Double> tmp = new ArrayList<Double>(weights);
		tmp.remove(tmp.size()-1);
		return tmp;
	}
	
	ArrayList<Double> getWeightsToSave(){
		return weights;
	}
	void setWeightsFromFile(ArrayList<Double> weights){
		this.weights = new ArrayList<Double>(weights);
		this.weightsPrev = new ArrayList<Double>(weights);
	}
	public double getU() {
		return u;
	}
	public double getY() {
		return y;
	}
	public double getQ() {
		return qValue;
	}
	
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Wagi t-ej iteracji: ");
		for (Double itDouble : weights)
			result.append(itDouble.toString() + " ");
		result.append("Wagi t-1-ej iteracji: ");
		for (Double itDouble : weightsPrev)
			result.append(itDouble.toString() + " ");
		return result.toString();
	}
}
