package game;

import java.util.ArrayList;
import java.util.Arrays;

import ann.Net;

public class Match {
	private Board board;
	private int playerOneScores;
	private int playerTwoScores;
	public Match() {
		playerOneScores = 0;
		playerTwoScores = 0;
	}

	private void doMatch(Net p1, Net p2) throws Exception {
		board = new Board();
		//System.out.println(board);
		int[] exceptions = new int[9];
		
		while (board.checkWinner() == 0){
			ArrayList<Double> answer1 = p1.feedForward(board.getState(Board.PLAYER1));
			Arrays.fill(exceptions, 0);
			while (board.markField(Board.PLAYER1, Translator.translateAnswer(answer1, exceptions)) == false){
				exceptions[Translator.translateAnswer(answer1, exceptions)] = 1;
			}
			//System.out.println(board);
			
			if (board.checkWinner() != 0) break;
			
			ArrayList<Double> answer2 = p2.feedForward(board.getState(Board.PLAYER2));
			Arrays.fill(exceptions, 0);
			while (board.markField(Board.PLAYER2, Translator.translateAnswer(answer2, exceptions)) == false){
				exceptions[Translator.translateAnswer(answer2, exceptions)] = 1;
			}
			//System.out.println(board);
		}
		if (board.checkWinner() == Board.PLAYER1) playerOneScores++;
		if (board.checkWinner() == Board.PLAYER2) playerTwoScores++;
	}
	
	public int chooseWinner(Net player1, Net player2) {
		int draw = 0;
		do {
			try {
				doMatch(player1, player2);
				draw++;
				if (draw > 5)
					return Board.PLAYER1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (playerOneScores + playerTwoScores != 3);
		return getWinner();
	}
	
	public int getWinner() {
		if (playerOneScores > playerTwoScores)
			return Board.PLAYER1;
		else
			return Board.PLAYER2;
	}
}
