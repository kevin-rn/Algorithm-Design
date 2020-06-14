To set up internet connections in a large area in Cittagazze, stations are planned at locations with a high population density.
These n stations are connected through pairs of quite expensive directional antennas.
The price of (a pair of) antennas depends on the required range, and for each pair of locations (u,v) these costs are given by c(u,v)>0.
The main question of this exercise is which (undirected) connections to set up such that all locations are connected and the installation costs are as low as possible.

Unfortunately, there is insufficient budget for setting up the connected network computed above at once.
The government decides to make as many connections of the optimal network as possible, given the available budget B (and to complete the network in upcoming years).
For example, if the network looks like this, a budget of 3 would select (a,b) and (d,e) and (f,g).

     1       2       3       1       2       1
(a)-----(b)-----(c)-----(d)-----(e)-----(f)-----(g)

Your task in this exercise is to read the cost of all possible connections from the input Scanner in the following format.

First read the number of locations n (2≤n≤10000), the number of connections m (1≤m≤100000), and the budget B (1≤B≤109), followed by a new line character.

Then read m lines with three values: 
the identifier of the first location u (0≤u<n),
the identifier of the second location v (0≤v<n),
and the costs of connecting these through a pair of antennas c(u,v) (1≤c(u,v)≤100000), followed by a new line.

For any pair of locations that is not listed, the costs of connecting can be assumed to be infinite.
It is guaranteed that the network can be set up using finite costs.

Your output should be a String that has two numbers, separated by a space.
The first number is the minimum total costs to connect all locations.
The second number is the number of connections that can be built using the budget.

Example input:

4 5 8
0 1 6
1 2 9
0 2 7
1 3 2
0 3 8

Example output:

15 2


### Template
```java
package weblab;

import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
  // TODO
  }
}

```

### Test
```java
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  private long time = 0;

  @Rule
  public TestName name = new TestName();

  @Before
  public void setUp() {
    time = System.currentTimeMillis();
  }

  @After
  public void tearDown() {
    System.out.println("Test '" + name.getMethodName() + "' took " + (System.currentTimeMillis() - time) + "ms");
  }

  private static void runTestWithFile(String fileName) {
    assertEquals(WebLab.getData(fileName + ".out").trim(), Solution.run(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8))).trim());
  }

  @Test(timeout = 100)
  public void budgetExample() {
    runTestWithFile("budget_example");
  }

  @Test(timeout = 100)
  public void example() {
    runTestWithFile("example");
  }

  @Test(timeout = 100)
  public void dontJustTakeSmallestEdges() {
    runTestWithFile("dont_just_take_smallest_edges");
  }

  @Test(timeout = 100)
  public void noBudget() {
    runTestWithFile("no_budget");
  }

  @Test(timeout = 100)
  public void twoNodesNoBudget() {
    runTestWithFile("two_nodes_no_budget");
  }

  @Test(timeout = 100)
  public void twoNodesInBudget() {
    runTestWithFile("two_nodes_in_budget");
  }

  @Test(timeout = 1000)
  public void test700Nodes() {
    runTestWithFile("n_700_m_35000");
  }

  @Test(timeout = 5000)
  public void test9999Nodes() {
    runTestWithFile("n_9999_m_99999");
  }

  @Test(timeout = 5000)
  public void testFullBudget() {
    runTestWithFile("n_9999_m_99999_full_budget");
  }
}

```

_____________________________________________________________________________________________
### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    int b = sc.nextInt();
    int s = 0;
    Edge[] edges = new Edge[m];
    Edge[] tree = new Edge[n - 1];
    for (int i = 0; i < m; i++) edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
    Arrays.sort(edges, Comparator.comparingInt(e -> e.l));
    UnionFind uf = new UnionFind(n);
    for (Edge e : edges) {
      if (uf.join(e.x, e.y))
        tree[s++] = e;
      if (s == n - 1)
        break;
    }
    long sum = 0;
    long amount = 0;
    for (Edge edge : tree) {
      sum += edge.l;
      if (sum <= b)
        amount++;
    }
    return sum + " " + amount;
  }

  class UnionFind {

    private int[] parent;

    private int[] rank;

    public UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) parent[i] = i;
    }

    // returns false if x and y are in same set
    private boolean join(int x, int y) {
      int xrt = find(x);
      int yrt = find(y);
      if (rank[xrt] > rank[yrt])
        parent[yrt] = xrt;
      else if (rank[xrt] < rank[yrt])
        parent[xrt] = yrt;
      else if (xrt != yrt)
        rank[parent[yrt] = xrt]++;
      return xrt != yrt;
    }

    private int find(int x) {
      return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }
  }

  private class Edge {

    // from, to and length
    int x, y, l;

    public Edge(int from, int to, int length) {
      x = from;
      y = to;
      l = length;
    }
  }
}

```


### My Solution:
```java
import java.io.*;
import java.util.*;

class Solution {
  
  class Location implements Comparable<Location> {
    int start, end, cost;
    
    public Location(int start, int end, int cost) {
      this.start = start;
      this.end = end;
      this.cost = cost;
    }
    
    @Override
    public int compareTo(Location other) {
      return Integer.compare(this.cost, other.cost);
    }
  }
  
   class UnionFind {
    private int[] parent, rank;

    public UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) parent[i] = i;
    }

    private boolean union(int x, int y) {
      int xrt = find(x);
      int yrt = find(y);
      if (rank[xrt] > rank[yrt])
        parent[yrt] = xrt;
      else if (rank[xrt] < rank[yrt])
        parent[xrt] = yrt;
      else if (xrt != yrt)
        rank[parent[yrt] = xrt]++;
      return xrt != yrt;
    }

    private int find(int x) {
      return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }
  }

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt(), m = sc.nextInt(), b = sc.nextInt(), counter=0, sum=0, amount=0;
    ArrayList<Location> list = new ArrayList<>();
    for(int i = 0; i < m; i++) list.add(new Location(sc.nextInt(), sc.nextInt(), sc.nextInt()));
    sc.close();
    Collections.sort(list);
    Location[] known = new Location[n-1];
    UnionFind uf = new UnionFind(n);
    for(Location loc : list) {
      if(uf.union(loc.start, loc.end)) known[counter++] = loc;
      if(counter == n -1) break;
    }
    for(Location loc : known) {
      sum += loc.cost;
      if(sum <= b) amount++;
    }
    return sum + " " + amount;
  }
}
```
