package csp;

import java.util.ArrayList;
import csp.BinaryDiffConstraint;

/**
 * This class should contain a list of all 1,944 constraints
 */
public class Constraints {
	private ArrayList<BinaryDiffConstraint> constraints = new ArrayList<>();
	private static int length;

	/**
   * Creates a new Node object from an initial state
   * 
   * @param s the initial state of the rack being created
   */
	public Constraints(int[][] board) {
		length = board.length;
	}


	public ArrayList<BinaryDiffConstraint> generateConstraints() {
		for (int i = 0 ; i < length ; i++) {
			for (int j = 0 ; j < length ; j++) {
				addRowConstraints(i, j);
				addColConstraints(i, j);
				addHouseConstraints(i, j);
			}
		}
		return constraints;
	}

	/**
   * Creates a new Node object from an initial state
   * 
   * @param s the initial state of the rack being created
   */
	private void addRowConstraints(int row, int col) {
		for (int i = 0 ; i < length ; i++) {
			if (col != i) {
				constraints.add(new BinaryDiffConstraint(row, col, row, i));
			}
		}
	}

	/**
   * Creates a new Node object from an initial state
   * 
   * @param s the initial state of the rack being created
   */
	private void addColConstraints(int row, int col) {
		for (int i = 0 ; i < length ; i++) {
			if (row != i) {
				constraints.add(new BinaryDiffConstraint(row, col, i, col));
			}
		}
	}

	/**
   * Creates a new Node object from an initial state
   * 
   * @param s the initial state of the rack being created
   */
	private void addHouseConstraints(int row, int col) {
		int adjRow = (row/3) * 3;
		int adjCol = (col/3) * 3;

		for (int i = adjRow ; i < adjRow+3 ; i++) {
			for (int j = adjCol ; j < adjCol+3 ; j++) {
				if (row != i || col != j) {
					constraints.add(new BinaryDiffConstraint(row, col, i, j));
				}
			}
		}
	}
}
