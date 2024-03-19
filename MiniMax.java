package application;

import java.util.ArrayList;

public class MiniMax {
	public ArrayList<State> Successorfunction(State state) {// this method return all state that cane player play it
		ArrayList<State> possibleMoves = new ArrayList<>();
		int xMoves = 0;
		int oMoves = 0;
		String player;
		for (int i = 0; i < state.getState().length; i++) {
		    String s = state.getState()[i];
		    if (s.equals("X")) {
		        xMoves++;
		    } else if (s.equals("O")) {
		        oMoves++;
		    }
		}


		if (xMoves <= oMoves) {
			player = "X";
		} else {
			player = "O";
		}

		// Create all possible states if there are available moves
		for (int i = 0; i <= 8; i++) {
			if (!state.getStateIndex(i).equals("X") && !state.getStateIndex(i).equals("O")) {
				String[] newState = state.getState().clone();
				newState[i] = player;
				possibleMoves.add(new State(i, newState));
			}
		}
		return possibleMoves;
	}
	
	
	public int Decision(State state, char player) {//return optimal move 
		// First Player must be X
		int bestVal;
		if (player == 'X') {
		    bestVal = Integer.MIN_VALUE;
		} else {
		    bestVal = Integer.MAX_VALUE;
		}
		int bestMove = 0;
		for (State move : Successorfunction(state)) {
			int moveVal;
			if (player == 'X') {// Ai first
					moveVal = minimize(move);
				if (moveVal > bestVal) {		// Minimize if AI played 1st
					bestVal = moveVal;
					bestMove = move.getPosition();
				}
			} else {// user
				moveVal = maximize(move); // Maximize if Ai played 2nd
				if (moveVal < bestVal) {
					bestVal = moveVal;
					bestMove = move.getPosition();
				}
			}
		}

		return bestMove;
	}

	
	public int maximize(State state) {// Maximizes the score
		if (isTerminal(state)) {
			return Autilityfunction(state);
		}
		int bestScore = Integer.MIN_VALUE;
		for (State move : Successorfunction(state)) {
			bestScore = Math.max(bestScore, minimize(move));
		}
		return bestScore;
	}

	
	public int minimize(State state) {// Minimizes the score
		if (isTerminal(state)) {
			return Autilityfunction(state);
		}

		int bestScore = Integer.MAX_VALUE;
		for (State move : Successorfunction(state)) {
			bestScore = Math.min(bestScore, maximize(move));
		}
		return bestScore;
	}

	public boolean isTerminal(State state) {// this method return true if either AI or Player win || all spots are
											// reserved (draw)
		int Reservedspots = 0;
		for (int a = 0; a <=8; a++) {
			if (state.getStateIndex(a).equals("X") || state.getStateIndex(a).equals("O")) {
				Reservedspots++;
			}

			String line = checkState(state, a); // 012

			// Check for Winners
			if (line.equals("XXX")) {
				return true;
			} else if (line.equals("OOO")) {
				return true;
			}

			if (Reservedspots == 9) {
				return true;
			}
		}
		return false;
	}

	public int Autilityfunction(State state) {
		for (int a = 0; a < 8; a++) {
			String line = checkState(state, a);
			// Check for Winners
			if (line.equals("XXX")) {
				return 1;
			} else if (line.equals("OOO")) {
				return -1;
			}
		}
		return 0;
	}

	public String checkState(State state, int a) {//Check if the case is a winner
	    if (a == 0) {
	        return state.getStateIndex(0) + state.getStateIndex(1) + state.getStateIndex(2);
	    } else if (a == 1) {
	        return state.getStateIndex(3) + state.getStateIndex(4) + state.getStateIndex(5);
	    } else if (a == 2) {
	        return state.getStateIndex(6) + state.getStateIndex(7) + state.getStateIndex(8);
	    } else if (a == 3) {
	        return state.getStateIndex(0) + state.getStateIndex(3) + state.getStateIndex(6);
	    } else if (a == 4) {
	        return state.getStateIndex(1) + state.getStateIndex(4) + state.getStateIndex(7);
	    } else if (a == 5) {
	        return state.getStateIndex(2) + state.getStateIndex(5) + state.getStateIndex(8);
	    } else if (a == 6) {
	        return state.getStateIndex(0) + state.getStateIndex(4) + state.getStateIndex(8);
	    } else if (a == 7) {
	        return state.getStateIndex(2) + state.getStateIndex(4) + state.getStateIndex(6);
	    } else {
	        return "";
	    }
	}


	
}