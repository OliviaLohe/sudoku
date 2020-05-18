package search;

import csp.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Solves a sudoku puzzle 
 */
public class Solver {
  static ArrayList<BinaryDiffConstraint> constraints;
	
	public static int[][] solve(int[][] original){
		Node assignment = new Node(original);
    Constraints c = new Constraints(original);
    constraints = c.generateConstraints();

    // if (!ac3(assignment)) { return null; }
    Node result = backtracking(assignment);

		return result.getBoard();
	}

  public static boolean ac3(Node state) {
    Queue<BinaryDiffConstraint> queue = new LinkedList<>();

    for (BinaryDiffConstraint c : constraints) {
        queue.add(c);
    }

    while (!queue.isEmpty()) {
        BinaryDiffConstraint curr = queue.poll();

        Variable xi = state.state[curr.row1][curr.col1];
        Variable xj = state.state[curr.row2][curr.col2];

        if (!xi.isFixed()) {
          if (removeValues(xi, xj)) { // something was modified
              if (xi.isDomainEmpty()) { return false; } // There is no way to satisfy this CSP

              // Modifying the domain of Xi necessitates re-queueing all incoming arcs
              for (BinaryDiffConstraint add : findIncoming(constraints, xi)) {
                queue.add(add);
              }
          }
        }
    }
    return true;
  }

  public static Node backtracking(Node assignment) {
    if (assignment.isGoal()) {
      return assignment;
    }
    Variable unassigned = assignment.leastRemainingValue();
    HashSet<Integer> domain = unassigned.getDomain();


    for (int value : domain) {
      
      // generate the child board if we made this move
      Node child = assignment.playMove(unassigned.getRow(), unassigned.getCol(), value);

      if (ac3(child)) { 
        // the board is consistent, recurse
        Node result = backtracking(child);
        System.out.println("through backtracking");

        if (result != null) { return result; } // found a solution
      }
    }

    // none of the options worked, failure
    return null;
  }

  /*--------------------------------------------------------------*/

  // //Makes X arc-consistent with respect to Y
  private static boolean removeValues(Variable xi, Variable xj) {
    ArrayList<Integer> removeVals = new ArrayList<>();
    HashSet<Integer> dx = xi.getDomain();
    HashSet<Integer> dy = xj.getDomain();
    boolean modified = false;

    for (int p : dx) {
      //Try to find a value in DY that satisfies the constraint
      boolean found = false;

      for (int q : dy) {
          if (p != q) {
              found = true;
              break;
          }
      }
    
      if (!found) {
        removeVals.add(p);
        modified = true;
      }
    }
    xi.removeFromDomain(removeVals);

    return modified;
  }

  private static ArrayList<BinaryDiffConstraint> findIncoming(ArrayList<BinaryDiffConstraint> constraints, Variable xi) {
    ArrayList<BinaryDiffConstraint> incoming = new ArrayList<>();
    int row = xi.getRow();
    int col = xi.getCol();

    for (BinaryDiffConstraint val : constraints) {
      if (val.row2 == row && val.col2 == col) {
        incoming.add(val);
      }
    }

    return incoming;
  }
}
