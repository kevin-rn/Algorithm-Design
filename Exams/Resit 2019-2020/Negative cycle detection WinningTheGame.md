You are playing a fun boardgame inspired by games like Catan and Carcasssone with some of your friends through an online boardgame platform.
You take turns placing directed roads between crossroads, but do not get the points for a road, until the road turns into a directed loop.
Since the objective is to score the least possible points your friends have decided that they want to prevent their roads from becoming loops at all costs.

You have noticed one crucial thing that your friends have not however.
Some of the roads you can choose to place have a negative number of points!
Thus if you can find a loop that has a net negative number of points, you are much more likely to win the game!

Given a set of roads that you can place, find out if such a net negative loop exists. As you can see in the Solution template, a Road object has a from and a to field representing the direction from one crossroad to another.

### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class WinningTheGame {

  /**
   * You should implement this method.
   *
   * @param n the number of Crossroads.
   * @param m the number of Roads.
   * @param V the array of length n+1 with Crossroads at indices 1 <= i <= n.
   * @param E a set of Roads (each from one Crossroad to another)
   * @return true iff there is a negative net loop in the set of roads.
   */
  public static boolean solve(int n, int m, Crossroads[] V, Set<Road> E) {
  // TODO
  }
}

class Crossroads {

  protected int id;

  public Crossroads(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Crossroads))
      return false;
    Crossroads that = (Crossroads) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

class Road {

  protected int points;

  protected Crossroads from;

  protected Crossroads to;

  protected Road(Crossroads from, Crossroads to, int points) {
    this.from = from;
    this.to = to;
    this.points = points;
  }

  public Crossroads getFrom() {
    return from;
  }

  public Crossroads getTo() {
    return to;
  }

  public int getPoints() {
    return points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Road))
      return false;
    Road road = (Road) o;
    return points == road.points && Objects.equals(from, road.from) && Objects.equals(to, road.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(points, from, to);
  }
}


```


### Test:
```java
import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  @Test(timeout = 100)
  public void example_one_road() {
    int n = 2;
    int m = 1;
    Crossroads[] V = new Crossroads[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Crossroads(i);
    }
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(V[1], V[2], 10));
    /*
         * Only one road this cannot create a loop.
         */
    assertFalse(WinningTheGame.solve(n, m, V, roads));
  }

  @Test(timeout = 100)
  public void example_simple_positive_loop() {
    int n = 2;
    int m = 1;
    Crossroads[] V = new Crossroads[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Crossroads(i);
    }
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(V[1], V[2], 10));
    roads.add(new Road(V[2], V[1], 1));
    /*
         * There is a loop, but it is positive
         */
    assertFalse(WinningTheGame.solve(n, m, V, roads));
  }

  @Test(timeout = 100)
  public void example_simple_negative_loop() {
    int n = 2;
    int m = 1;
    Crossroads[] V = new Crossroads[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Crossroads(i);
    }
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(V[1], V[2], -10));
    roads.add(new Road(V[2], V[1], 1));
    /*
         * There is a loop, with net negative cost -9
         */
    assertTrue(WinningTheGame.solve(n, m, V, roads));
  }

  @Test(timeout = 100)
  public void example_simple_negative_loop_three_nodes() {
    int n = 3;
    int m = 1;
    Crossroads[] V = new Crossroads[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Crossroads(i);
    }
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(V[1], V[2], -3));
    roads.add(new Road(V[2], V[3], 1));
    roads.add(new Road(V[3], V[1], 1));
    /*
         * There is a loop, with net negative cost -1
         */
    assertTrue(WinningTheGame.solve(n, m, V, roads));
  }
}

```


### Solution:
```java

class WinningTheGame {

  /**
   * You should implement this method.
   *
   * @param n the number of Crossroads.
   * @param m the number of Roads.
   * @param V the array of length n+1 with Crossroads at indices 1 <= i <= n.
   * @param E a set of Roads (each from one Crossroad to another)
   * @return true iff there is a negative net loop in the set of roads.
   */
  public static boolean solve(int n, int m, Crossroads[] V, Set<Road> E) {
    int[] distances = new int[n + 1];
	  for(int i = 1; i <= n; i++) {
	    distances[i] = Integer.MAX_VALUE / 2;
	  }
	  distances[1] = 0;
	  
	  for(int i = 0; i < n - 1; i++) {
		  for(Road r : E) {
			  if(distances[r.getFrom().id] + r.getPoints() < distances[r.getTo().id]) {
				  distances[r.getTo().id] = distances[r.getFrom().id] + r.getPoints();
			  }
		  }
	  }
	  
	  int checkForNegativeCycleSum1 = 0;
	  
	  for(int i = 1; i <= n; i++) {
	    checkForNegativeCycleSum1 += distances[i];
	  }
	  for(Road r : E) {
		  if(distances[r.getFrom().id] + r.getPoints() < distances[r.getTo().id]) {
			  distances[r.getTo().id] = distances[r.getFrom().id] + r.getPoints();
		  }
	  }
	  
	  int checkForNegativeCycleSum2 = 0;
	  for(int i = 1; i <= n; i++) {
		  checkForNegativeCycleSum2 += distances[i];
	  }
	  
	  return checkForNegativeCycleSum1 != checkForNegativeCycleSum2;
  }
}
```
