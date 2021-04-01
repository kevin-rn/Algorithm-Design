You are playing a fun boardgame inspired by games like Catan and Carcasssone with some of your friends through an online boardgame platform.   
You take turns placing directed streets, and at the end of the game the player that managed to make the shortest route from a start s to an end t wins the game.   
In the event of a tie however, the person that created most shortest routes from s to t wins.
In the game streets have a weight w which is either positive or negative, but it should be noted that the rules guarantee every cycle has a net strict positive weight.
Given a set of placed streets, you should implement the tie-break (method solve) and return the total number of shortest paths from s to t.

### Template:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class TieBreak {

  /**
   * You should implement this method.
   *
   * @param n the number of unique endpoints.
   * @param m the number of edges.
   * @param E a set of directed streets, you may assume the endpoints are labelled 1 <= label <= n.
   * @param s the start point (1 <= s <= n).
   * @param t the end point (1 <= t <= n).
   * @return the number of shortest paths from s to t.
   */
  public static int solve(int n, int m, Set<Streets> E, int s, int t) {
  // TODO
  }
}

class Streets {

  protected int weight;

  protected int from;

  protected int to;

  protected Streets(int from, int to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Streets edge = (Streets) o;
    return weight == edge.weight && from == edge.from && to == edge.to;
  }

  @Override
  public int hashCode() {
    return Objects.hash(weight, from, to);
  }
}

```

### Test:
```java
import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 100)
  public void example_one_road() {
    int n = 2;
    int m = 1;
    int s = 1;
    int t = 2;
    Set<Streets> streets = new HashSet<>();
    streets.add(new Streets(1, 2, 10));
    /*
         * Only one road so only one shortest path
         */
    assertEquals(1, TieBreak.solve(n, m, streets, s, t));
  }

  @Test(timeout = 100)
  public void example_two_routes() {
    int n = 4;
    int m = 4;
    int s = 1;
    int t = 4;
    Set<Streets> streets = new HashSet<>();
    streets.add(new Streets(1, 2, 10));
    streets.add(new Streets(1, 3, 1));
    streets.add(new Streets(2, 4, 1));
    streets.add(new Streets(3, 4, 10));
    /*
         * There are two shortest path of weight 11.
         */
    assertEquals(2, TieBreak.solve(n, m, streets, s, t));
  }

  @Test(timeout = 200)
  public void example_with_a_negative_edge() {
    int n = 4;
    int m = 4;
    int s = 1;
    int t = 4;
    Set<Streets> streets = new HashSet<>();
    streets.add(new Streets(1, 2, 10));
    streets.add(new Streets(1, 3, -4));
    streets.add(new Streets(2, 4, -4));
    streets.add(new Streets(3, 4, 10));
    /*
         * There are two shortest paths of weight 6.
         */
    assertEquals(2, TieBreak.solve(n, m, streets, s, t));
  }
}

```

### Solution:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class TieBreak {

  /**
   * You should implement this method.
   *
   * @param n the number of unique endpoints.
   * @param m the number of edges.
   * @param E a set of directed streets, you may assume the endpoints are labelled 1 <= label <= n.
   * @param s the start point (1 <= s <= n).
   * @param t the end point (1 <= t <= n).
   * @return the number of shortest paths from s to t.
   */
  public static int solve(int n, int m, Set<Streets> E, int s, int t) {
    // Use Bellman-ford algorithm to count the number of shortest paths
    // TODO: check if dijkstra would be better
    int[] distances = new int[n + 1];
	  for(int i = 1; i <= n; i++) {
	    distances[i] = Integer.MAX_VALUE / 2;
	  }
  	distances[1] = 0;
  	
  	// Loop through each street between s to t
	  for(int i = s; i < t; i++) {
		  for(Streets r : E) {
			  if(distances[r.getFrom()] + r.getWeight() < distances[r.getTo()]) {
				  distances[r.getTo()] = distances[r.getFrom()] + r.getWeight();
			  }
		  }
	  }
	  
	  //
	  int shortestpathcount = 0;
	  for(int i = s; i <= t; i++) {
	    shortestpathcount += distances[i];
	  }
	  for(Streets r : E) {
		  if(distances[r.getFrom()] + r.getWeight() < distances[r.getTo()]) {
			  distances[r.getTo()] = distances[r.getFrom()] + r.getWeight();
		  }
	  }
	  
	  return shortestpathcount;
  }
}

class Streets {

  protected int weight;

  protected int from;

  protected int to;

  protected Streets(int from, int to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Streets edge = (Streets) o;
    return weight == edge.weight && from == edge.from && to == edge.to;
  }

  @Override
  public int hashCode() {
    return Objects.hash(weight, from, to);
  }
}


```
