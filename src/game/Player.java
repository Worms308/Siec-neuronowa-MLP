package game;

import ann.Net;

public class Player {
	private int points;
	private Net neuralNetwork;
	
	public Player(int topology[], double alpha) {
		points = 0;
		neuralNetwork = new Net(topology, 9, alpha);
	}
	
	public Net getNetwork() {
		return neuralNetwork;
	}
	
	public void addPoint() {
		points++;
	}
	
	public int getPoints() {
		return points;
	}

}
