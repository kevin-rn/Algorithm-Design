package weblab;

import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class ChingChingMap {

  /**
   * You should implement this method.
   *
   * @param n the number of nodes.
   * @param m the number of edges.
   * @param V the array of towns at indices 1 <= i <= n.
   * @param E a set of Edges
   * @return true iff there is a cycle that allows you to get infinite amounts of money.
   */
  public static boolean solve(int n, int m, Town[] V, Set<Road> E) {
	  int[] distances = new int[n + 1];
	  
	  for(int i = 1; i <= n; i++) {
		  distances[i] = Integer.MAX_VALUE / 2;
	  }
	  
	  
	  distances[1] = 0;
	  
	  for(int i = 0; i < n - 1; i++) {
		  for(Road r : E) {
			  if(distances[r.getFrom().id] + r.getCost() < distances[r.getTo().id]) {
				  distances[r.getTo().id] = distances[r.getFrom().id] + r.getCost();
			  }
		  }
	  }
	  
	  int checkForNegativeCycleSum1 = 0;
	  
	  for(int i = 1; i <= n; i++) {
		  checkForNegativeCycleSum1 += distances[i];
	  }
	  
	  for(Road r : E) {
		  if(distances[r.getFrom().id] + r.getCost() < distances[r.getTo().id]) {
			  distances[r.getTo().id] = distances[r.getFrom().id] + r.getCost();
		  }
	  }
	  
	  int checkForNegativeCycleSum2 = 0;
	  
	  for(int i = 1; i <= n; i++) {
		  checkForNegativeCycleSum2 += distances[i];
	  }
	  
	  return checkForNegativeCycleSum1 != checkForNegativeCycleSum2;
  }
}

class Town {

  protected int id;

  public Town(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Town))
      return false;
    Town that = (Town) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

class Road {

  protected int cost;

  protected Town from;

  protected Town to;

  protected Road(Town from, Town to, int cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }

  public Town getFrom() {
    return from;
  }

  public Town getTo() {
    return to;
  }

  public int getCost() {
    return cost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Road))
      return false;
    Road road = (Road) o;
    return cost == road.cost && Objects.equals(from, road.from) && Objects.equals(to, road.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cost, from, to);
  }
}

