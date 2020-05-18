package csp;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A variable in a CSP that takes on an integer value between 1 and 9.
 * The value of the variable may be specified by the original problem.
 */
public class Variable {
  private static final int[] digits = {1, 2, 3, 4, 5, 6, 7, 8, 9};
  HashSet<Integer> domain;
  int row;
  int col;
  boolean fixed = false;

  /////////////////////////////////////////////
  // Constructors
  public Variable(int row, int col, HashSet domain, boolean fixed) {
    this.row = row;
    this.col = col;
    this.domain = domain;
    this.fixed = fixed;
  }

  public Variable(int row, int col, int value, boolean fixed) {
    this.row = row;
    this.col = col;
    this.fixed = fixed;
    domain = new HashSet();

    if (fixed) {
      domain.add(value);
    } else {
      populateDomain(domain);
    }
  }

  /////////////////////////////////////////////
  // Getters
  public boolean isFixed() {
    return fixed;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getValue() {
    assert(domainSize() == 1);
    return domain.iterator().next();
  }

  public HashSet getDomain() {
    return domain;
  }

  /////////////////////////////////////////////
  // Setters
  public Variable setValue(int i) {
    return new Variable(this.row, this.col, i, this.fixed);
  }

  /////////////////////////////////////////////
  // Interactors
  public int domainSize() {
    return domain.size();
  }

  @Override
  public Variable clone() {
    return new Variable(this.row, this.col, this.domain, this.fixed);
  }

  public boolean isSet() {
    return domainSize() == 1;
  }

  public boolean isDomainEmpty() {
    return domainSize() == 0;
  }

  public void removeFromDomain(int i) {
    domain.remove(i);
  }

  public void removeFromDomain(ArrayList<Integer> vals) {
    for (int i : vals) {
      domain.remove(i);
    }
  }

  /*------------------------------------------------*/
  private void populateDomain(HashSet domain) {
    for (int i : digits) {
        domain.add(i);
      }
  }
}
