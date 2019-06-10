package game;

import java.util.ArrayList;

public class Board {
	private final int SIZE = 3;
	private int board[][] = new int[SIZE][SIZE];
	public static final int PLAYER1 =  1;
	public static final int PLAYER2 = -1;
	
	public Board() {
		for (int i=0;i<SIZE;++i)
			for (int j=0;j<SIZE;++j)
				board[i][j] = 0;
	}
	
	public boolean markField(int playerNum, int fieldNum) {
		if (board[fieldNum%SIZE][fieldNum/SIZE] == 0){
			board[fieldNum%SIZE][fieldNum/SIZE]= playerNum;
			return true;
		}else
			return false;
	}
	
	public int checkWinner() {
		if (board[0][0] != 0 && board[0][0] == board[1][0] && board[1][0] == board[2][0]) return board[0][0];
		if (board[0][1] != 0 && board[0][1] == board[1][1] && board[1][1] == board[2][1]) return board[0][1];
		if (board[0][2] != 0 && board[0][2] == board[1][2] && board[1][2] == board[2][2]) return board[0][2];
		
		if (board[0][0] != 0 && board[0][0] == board[0][1] && board[0][1] == board[0][2]) return board[0][0];
		if (board[1][0] != 0 && board[1][0] == board[1][1] && board[1][1] == board[1][2]) return board[1][0];
		if (board[2][0] != 0 && board[2][0] == board[2][1] && board[2][1] == board[2][2]) return board[2][0];

		if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0];
		if (board[2][0] != 0 && board[2][0] == board[1][1] && board[1][1] == board[0][2]) return board[2][0];
		
		int fullMap = 0;
		for (int i=0;i<SIZE;++i)
			for (int j=0;j<SIZE;++j)
				if (board[i][j] != 0)
					fullMap++;
		if (fullMap == 9)
			return 2;
				
		return 0;
	}
	
	public ArrayList<Double> getState(int playerNum) {
		ArrayList<Double> state = new ArrayList<Double>();
		if (playerNum == PLAYER1){
			for (int i=0;i<SIZE;++i)
				for (int j=0;j<SIZE;++j)
					state.add((double)board[i][j]);
		} else {
			for (int i=0;i<SIZE;++i)
				for (int j=0;j<SIZE;++j)
					state.add((double)-board[i][j]);
		}
		return state;
	}

	@Override
	public String toString(){
		String result = new String();
		for (int i=0;i<SIZE;++i){
			for (int j=0;j<SIZE;++j){
				if (board[i][j] == PLAYER1) result += "X";
				else if (board[i][j] == PLAYER2) result += "O";
				else result += " ";
				if (j == 0 || j == 1) result += "|";
				else result += "\n";
			}
			if (i == 0 || i == 1)
				result += "-+-+-\n";
		}
		return result;
	}
}
