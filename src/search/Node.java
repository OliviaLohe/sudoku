package search;

import csp.*;
import java.util.HashSet;

/**
 * I highly recommend writing a Node class for your BackTracking search
 */
public class Node {
  public static final int GRID_SIZE = 9;
	public Variable[][] state;

  /////////////////////////////////////////////
  // Constructors
  public Node(Variable[][] state) {
    this.state = copyBoard(state);
  }

  public Node(int[][] state) {
    this.state = generateVariables(state);
  }

  /////////////////////////////////////////////
  // Getters
  public int[][] getBoard() {
    int[][] board = new int[GRID_SIZE][GRID_SIZE];
    for (int i = 0; i < GRID_SIZE; i++) {
      for (int j = 0 ; j < GRID_SIZE ; j++) {
        board[i][j] = state[i][j].getValue();
      }
    }
    return board;
  }

  /////////////////////////////////////////////
  // Interactors

  public Variable leastRemainingValue() {
    Variable LRV = state[0][0];
    int smallestDomainSize = Integer.MAX_VALUE;

    for (int i = 0 ; i < GRID_SIZE ; i++) {
      for (int j = 0 ; j < GRID_SIZE ; j++) {
        Variable check = state[i][j];
        int checkSize = check.domainSize();
        if (!check.isSet() && checkSize < smallestDomainSize) {
          smallestDomainSize = checkSize;
          LRV = check;
        }
      }
    }
    return LRV;
  }

  public Node playMove(int row, int col, int value) {
    Variable[][] newBoard = copyBoard(state);
    //for (int i = 0 ; i < GRID_SIZE ; i++) {
      //for (int j = 0 ; j < GRID_SIZE ; j++) {
        //if (i == row && j == col) {
          Variable curr = new Variable(row, col, value, false);
          newBoard[row][col] = curr;
        //}
      //}
    //}

    return new Node(newBoard);
  }

  public boolean isGoal() {
    for (int i = 0 ; i < GRID_SIZE ; i++) {
      for (int j = 0 ; j < GRID_SIZE ; j++) {
        if (!state[i][j].isSet()) {
          return false;
        }
      }
    }
    return true;
  }

  /*--------------------------------------------------------------*/

  private Variable[][] generateVariables(int[][] board) {
    Variable[][] state = new Variable[GRID_SIZE][GRID_SIZE];
    for (int i = 0 ; i < GRID_SIZE ; i++) {
      for (int j = 0 ; j < GRID_SIZE ; j++) {
        int value = board[i][j];
        state[i][j] = new Variable(i, j, value, (value > 0));
      }
    }
    return state;
  }

  /**
  * makes a copy of the rack, to prevent players from accessing it directly
  * @author alchambers
  */
  private static Variable[][] copyBoard(Variable[][] variables) {
    Variable[][] copy = new Variable[GRID_SIZE][GRID_SIZE];
    for (int i = 0; i < GRID_SIZE; i++) {
      for (int j = 0 ; j < GRID_SIZE ; j++) {
        copy[i][j] = variables[i][j].clone();
      }
    }
    return copy;
  }
}
