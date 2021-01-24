You are playing a fun boardgame inspired by games like Catan and Carcasssone with some of your friends through an online boardgame platform.
You take turns placing directed roads, but do not get the points for a road, until the road turns into a directed loop.
Since the objective is to score the least possible points your friends have decided that they want to prevent their roads from becoming loops at all costs.

You have noticed one crucial thing that your friends have not however.
Some of the roads you can choose to place have a negative number of points!
Thus if you can find a loop that has a net negative number of points, you are much more likely to win the game!

Given a set of roads that you can place, find out if such a net negative loop exists. As you can see in the Solution template, roads are represented by directed edges between nodes in a graph.

### Assignment:
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
   * @param n the number of intersections.
   * @param m the number of edges.
   * @param E a set of directed Edges, you may assume the endpoints are labelled 1 <= label <= n.
   * @return true iff there is a negative net loop in the set of roads.
   */
  public static boolean solve(int n, int m, Set<Road> E) {
  // TODO
  }
}

class Road {

  protected int points;

  protected int from;

  protected int to;

  protected Road(int from, int to, int points) {
    this.from = from;
    this.to = to;
    this.points = points;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public int getPoints() {
    return points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Road edge = (Road) o;
    return points == edge.points && from == edge.from && to == edge.to;
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
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(1, 2, 10));
    /*
     * Only one road this cannot create a loop.
     */
    assertFalse(WinningTheGame.solve(n, m, roads));
  }

  @Test(timeout = 100)
  public void example_simple_positive_loop() {
    int n = 2;
    int m = 2;
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(1, 2, 10));
    roads.add(new Road(2, 1, 1));
    /*
     * There is a loop, but it is positive
     */
    assertFalse(WinningTheGame.solve(n, m, roads));
  }

  @Test(timeout = 100)
  public void example_simple_negative_loop() {
    int n = 2;
    int m = 2;
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(1, 2, -10));
    roads.add(new Road(2, 1, 1));
    /*
     * There is a loop, with net negative cost -9
     */
    assertTrue(WinningTheGame.solve(n, m, roads));
  }

  @Test(timeout = 100)
  public void example_simple_negative_loop_three_nodes() {
    int n = 3;
    int m = 3;
    Set<Road> roads = new HashSet<>();
    roads.add(new Road(1, 2, -3));
    roads.add(new Road(2, 3, 1));
    roads.add(new Road(3, 1, 1));
    /*
     * There is a loop, with net negative cost -1
     */
    assertTrue(WinningTheGame.solve(n, m, roads));
  }
}
```

### Solution:
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
   * @param n the number of intersections.
   * @param m the number of edges.
   * @param E a set of directed Edges, you may assume the endpoints are labelled 1 <= label <= n.
   * @return true iff there is a negative net loop in the set of roads.
   */
  public static boolean solve(int n, int m, Set<Road> E) {
    return solveProper(n, E);
  }

  public static boolean solveProper(int n, Set<Road> E) {
    final int t = 0;
    int[] mem = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      mem[i] = Integer.MAX_VALUE / 2;
    }
    mem[t] = 0;
    for (int i = 1; i <= n; i++) {
      E.add(new Road(i, t, 0));
    }
    boolean update = false;
    for (int counter = 0; counter <= n; counter++) {
      // Do n+1 updates!
      update = false;
      for (Road e : E) {
        int cost = e.points + mem[e.to];
        if (cost < mem[e.from]) {
          update = true;
        }
        mem[e.from] = Math.min(cost, mem[e.from]);
      }
    }
    return update;
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/resit_2019_2020/dp/boardgame/data/secret";
    File dir = new File(dirName);
    for (File f : dir.listFiles()) {
      if (f.getName().endsWith("in")) {
        FileInputStream in = new FileInputStream(f);
        System.out.println(f.getAbsolutePath());
        String ans = run(in);
        System.out.println(ans);
        PrintWriter out = new PrintWriter(f.getAbsolutePath().replace(".in", ".out"));
        out.println(ans);
        out.close();
        System.out.println();
      }
    }
  }

  public static String run(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    Set<Road> E = new HashSet<>();
    for (int i = 0; i < m; i++) {
      E.add(new Road(sc.nextInt(), sc.nextInt(), sc.nextInt()));
    }
    sc.close();
    return Boolean.toString(WinningTheGame.solve(n, m, E));
  }
}

class Road {

  protected int points;

  protected int from;

  protected int to;

  protected Road(int from, int to, int points) {
    this.from = from;
    this.to = to;
    this.points = points;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public int getPoints() {
    return points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Road edge = (Road) o;
    return points == edge.points && from == edge.from && to == edge.to;
  }

  @Override
  public int hashCode() {
    return Objects.hash(points, from, to);
  }
}

```
