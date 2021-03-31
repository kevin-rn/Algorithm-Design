You are playing catch with your friends in the park. While throwing the ball around, you notice that when the ball is passed between some pairs of friends the frequency with which the ball is dropped is higher than for some other pairs. You realise that you might be able to use your knowledge from Algorithm Design to maximise the probability that a ball reaches every one of them to prevent anyone feeling excluded.

We consider a simplified setting where you have as many balls as friends and where each ball is used only once. Given are your n friends that want to play, with all balls starting at “friend 1” (you).   
Not all friends are able to throw to one another however due to their distances, and for every pair of friends that can reach each other, their catch success rate 0≤rs≤1 is given.

We are then interested in the highest probability of receiving the ball for the friend that is the hardest to reach.

For example: if we have 3 friends with:
- a possible throw from 1 to 2 with catch rate 0.6
- a possible throw from 1 to 3 with catch rate 0.4
- a possible throw from 2 to 3 with catch rate 0.7
- a possible throw from 1 to 4 with catch rate 0.8

Throw the ball from 1 to 2 directly with probability 0.6, go from 1 via 2 to 3 with probability 0.42 and throw an additional ball directly from 1 to 4 at 0.8.
Here friend 3 is hardest to reach and has probability 0.42.

You may assume that the throwing-catching connections between friends are bidirectional and have the same catch rate independent on who throws and who catches.

```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Catch {

  int friend1;

  int friend2;

  double catchRate;

  public Catch(int friend1, int friend2, double catchRate) {
    this.friend1 = friend1;
    this.friend2 = friend2;
    this.catchRate = catchRate;
  }
}

class CatchAndThrow {

  /**
   *  You should implement this method.
   *  @param n the number of friends
   *  @param catches the throw catch combinations that are available.
   *  @return the minimal largest chance of dropping any of the balls.
   */
  public static double minimalLargestDropChance(int n, Set<Catch> catches) {
    return solveProper(n, catches);
  }

  public static double solveProper(int n, Set<Catch> catches) {
    double result = 1;
    Map<Integer, Map<Integer, Double>> map = new HashMap<>();
    for (int i = 1; i <= n; i++) {
      map.put(i, new HashMap<>());
    }
    for (Catch c : catches) {
      map.get(c.friend1).put(c.friend2, c.catchRate);
      map.get(c.friend2).put(c.friend1, c.catchRate);
    }
    PriorityQueue<Tuple> pq = new PriorityQueue<>(Comparator.comparingDouble((Tuple o) -> o.cost).reversed().thenComparingInt((Tuple o) -> o.id));
    pq.add(new Tuple(1, 1.0));
    HashMap<Integer, Double> probs = new HashMap<>();
    probs.put(1, 1.0);
    HashSet<Integer> visited = new HashSet<>();
    while (!pq.isEmpty()) {
      Tuple curNode = pq.poll();
      if (visited.contains(curNode)) {
        continue;
      }
      if (curNode.cost < result) {
        result = curNode.cost;
      }
      visited.add(curNode.id);
      if (visited.size() == n) {
        break;
      }
      for (int neigh : map.get(curNode.id).keySet()) {
        double prob = map.get(curNode.id).get(neigh);
        if (!visited.contains(neigh) && prob * curNode.cost >= probs.getOrDefault(neigh, 0.0)) {
          probs.put(neigh, prob * curNode.cost);
          pq.add(new Tuple(neigh, prob * curNode.cost));
        }
      }
    }
    if (visited.size() != n) {
      return 0;
    }
    return result;
  }

  static class Tuple {

    int id;

    double cost;

    Tuple(int id, double cost) {
      this.id = id;
      this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Tuple tuple = (Tuple) o;
      return id == tuple.id;
    }
  }

  public static void main(String[] args) throws java.io.FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_1_2020_2021/prob_mst/implementation/catching/data/secret";
    java.io.File dir = new java.io.File(dirName);
    System.out.println(dir.exists());
    for (java.io.File f : dir.listFiles()) {
      if (f.getName().endsWith("in")) {
        java.io.FileInputStream in = new java.io.FileInputStream(f);
        System.out.println(f.getAbsolutePath());
        String ans = run(in);
        System.out.println(ans);
        java.io.PrintWriter out = new java.io.PrintWriter(f.getAbsolutePath().replace(".in", ".out"));
        out.println(ans);
        out.close();
        System.out.println();
      }
    }
  }

  private static String run(java.io.FileInputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    Set<Catch> catches = new HashSet<>();
    for (int i = 1; i <= m; i++) {
      catches.add(new Catch(sc.nextInt(), sc.nextInt(), sc.nextDouble()));
    }
    sc.close();
    double res = CatchAndThrow.minimalLargestDropChance(n, catches);
    StringBuilder sb = new StringBuilder();
    sb.append(res);
    return sb.toString();
  }
}
```
