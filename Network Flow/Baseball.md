The Supreme League is the highest level baseball competition. In September the best m teams play are selected to play
in the playoffs to compete for the Cup. The winner of the Cup is the team with the highest number of wins.

You are given a list of m baseball teams that are competing for the Cup. For each team i, the current number of wins,
and the number of games left to play is given, and it is already decided against whom each team will play their last games.

We now ask you to decide for team x whether that team is still able to win the Cup.

The input will look as follows: the first line will contain the number of teams, m.
The team numbers range from 1 to m. Then 2m lines follow, for each team their team number, i*, the number of wins, w_i,
and the number of games left to play, g_i, are printed on one line. The line below will contain g_i numbers
separated by spaces, which are the teams that team i still has to play against. Keep in mind that one game is displayed
in the lists of both teams.

The first team that is entered is team x, i.e. the team of which we want to find out if it can still win.

4   
4 10 2  
2 3  
1 12 2  
2 3   
2 11 3  
1 3 4  
3 11 3  
1 2 4  

### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Returns true if team x can still win the Cup.
   */
  public static boolean solve(InputStream in) {
    Scanner sc = new Scanner(in);
  }
}


```

### Library:
```java
class MaxFlow {

  private static List<Edge> findPath(Graph g, Node start, Node end) {
    Map<Node, Edge> mapPath = new HashMap<Node, Edge>();
    Queue<Node> sQueue = new LinkedList<Node>();
    Node currentNode = start;
    sQueue.add(currentNode);
    while (!sQueue.isEmpty() && currentNode != end) {
      currentNode = sQueue.remove();
      for (Edge e : currentNode.getEdges()) {
        Node to = e.getTo();
        if (to != start && mapPath.get(to) == null && e.getResidual() > 0) {
          sQueue.add(e.getTo());
          mapPath.put(to, e);
        }
      }
    }
    if (sQueue.isEmpty() && currentNode != end)
      return null;
    LinkedList<Edge> path = new LinkedList<Edge>();
    Node current = end;
    while (mapPath.get(current) != null) {
      Edge e = mapPath.get(current);
      path.addFirst(e);
      current = e.getFrom();
    }
    return path;
  }

  public static void maximizeFlow(Graph g) {
    Node sink = g.getSink();
    Node source = g.getSource();
    List<Edge> path;
    while ((path = findPath(g, source, sink)) != null) {
      int r = Integer.MAX_VALUE;
      for (Edge e : path) {
        r = Math.min(r, e.getResidual());
      }
      for (Edge e : path) {
        e.augmentFlow(r);
      }
    }
  }
}

class Graph {

  private List<Node> nodes;

  private Node source;

  private Node sink;

  public Graph(List<Node> nodes, Node source, Node sink) {
    this.nodes = nodes;
    this.source = source;
    this.sink = sink;
  }

  public Node getSink() {
    return sink;
  }

  public Node getSource() {
    return source;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public boolean equals(Object other) {
    if (other instanceof Graph) {
      Graph that = (Graph) other;
      return this.nodes.equals(that.nodes);
    }
    return false;
  }

  public void maximizeFlow() {
    MaxFlow.maximizeFlow(this);
  }
}

class Node {

  protected int id;

  protected Collection<Edge> edges;

  public Node(int id) {
    this.id = id;
    this.edges = new ArrayList<Edge>();
  }

  public void addEdge(Node to, int capacity) {
    Edge e = new Edge(capacity, this, to);
    edges.add(e);
    to.getEdges().add(e.getBackwards());
  }

  public Collection<Edge> getEdges() {
    return edges;
  }

  public int getId() {
    return id;
  }

  public boolean equals(Object other) {
    if (other instanceof Node) {
      Node that = (Node) other;
      if (id == that.getId())
        return edges.equals(that.getEdges());
    }
    return false;
  }
}

class Edge {

  protected int capacity;

  protected int flow;

  protected Node from;

  protected Node to;

  protected Edge backwards;

  private Edge(Edge e) {
    this.flow = e.getCapacity();
    this.capacity = e.getCapacity();
    this.from = e.getTo();
    this.to = e.getFrom();
    this.backwards = e;
  }

  protected Edge(int capacity, Node from, Node to) {
    this.capacity = capacity;
    this.from = from;
    this.to = to;
    this.flow = 0;
    this.backwards = new Edge(this);
  }

  public void augmentFlow(int add) {
    assert (flow + add <= capacity);
    flow += add;
    backwards.setFlow(getResidual());
  }

  public Edge getBackwards() {
    return backwards;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getFlow() {
    return flow;
  }

  public Node getFrom() {
    return from;
  }

  public int getResidual() {
    return capacity - flow;
  }

  public Node getTo() {
    return to;
  }

  private void setFlow(int f) {
    assert (f <= capacity);
    this.flow = f;
  }

  public boolean equals(Object other) {
    if (other instanceof Edge) {
      Edge that = (Edge) other;
      return this.capacity == that.capacity && this.flow == that.flow && this.from.getId() == that.getFrom().getId() && this.to.getId() == that.getTo().getId();
    }
    return false;
  }
}
```

### Test:
```java
package weblab;

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
    assertEquals(Boolean.parseBoolean(WebLab.getData(fileName + ".out").trim()), Solution.solve(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8))));
  }

  @Test(timeout = 100)
  public void testExample() {
    runTestWithFile("example");
  }

  /**
   * Same test as example, but now with 1 more win for team 4.
   */
  @Test(timeout = 100)
  public void testExampleTrue() {
    runTestWithFile("exampleTrue");
  }

  /**
   * Test case from the book, page 400
   */
  @Test(timeout = 100)
  public void testBook() {
    runTestWithFile("testBook");
  }

  /**
   * Test case from the book, page 400, interested in team NY
   */
  @Test(timeout = 100)
  public void testBookNY() {
    runTestWithFile("testBookNY");
  }

  /**
   * Test case from the book, page 400, interested in team Baltimore
   */
  @Test(timeout = 100)
  public void testBookB() {
    runTestWithFile("testBookB");
  }

  /**
   * Test case with 15 teams
   */
  @Test(timeout = 100)
  public void test15Teams() {
    runTestWithFile("test15Teams");
  }

  /**
   * Test case with 500 teams, return true
   */
  @Test(timeout = 3000)
  public void testLargeTrue() {
    runTestWithFile("testLargeTrue");
  }

  /**
   * Test case with 500 teams, return false
   */
  @Test(timeout = 6000)
  public void testLargeFalse() {
    runTestWithFile("testLargeFalse");
  }

  /**
   * Test case with 5000 teams where false can be returned without building a full graph
   */
  @Test(timeout = 500)
  public void testLargeTrivial() {
    runTestWithFile("testLargeTrivial");
  }
}

```

___________________________________________________________________________________________________________________
### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Returns true if team x can still win the Cup.
   */
  public static boolean solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int m = sc.nextInt();
    // Process team x
    int teamX = sc.nextInt();
    int winsX = sc.nextInt();
    // To maximize the chance of winning, assume team x wins all remaining games
    winsX += sc.nextInt();
    // The other teams for these games do not matter, skip to the other teams
    sc.nextLine();
    sc.nextLine();
    ArrayList<Node> nodes = new ArrayList<>();
    Node source = new Node(0);
    nodes.add(source);
    // Add a node for each team
    for (int x = 0; x < m; x++) {
      nodes.add(new Node(x + 1));
    }
    Node sink = new Node(m + 1);
    nodes.add(sink);
    // Process the remaining m-1 teams
    for (int x = 0; x < m - 1; x++) {
      int i = sc.nextInt();
      int w = sc.nextInt();
      int g = sc.nextInt();
      if (winsX < w) {
        sc.close();
        // We can be sure team x cannot win if another team has more wins than x can get
        return false;
      }
      // Make sure this team does not have more wins than team x
      nodes.get(i).addEdge(sink, winsX - w);
      // Find the number of games to play against each opponent
      HashMap<Integer, Integer> games = new HashMap<>();
      for (int y = 0; y < g; y++) {
        int t = sc.nextInt();
        // Avoid duplicates and remove matches against team x
        if (t < i && t != teamX) {
          int n = games.getOrDefault(t, 0);
          games.put(t, n + 1);
        }
      }
      // Adds a game node, edge from source and edges to teams
      for (Integer t : games.keySet()) {
        Node game = new Node(nodes.size());
        nodes.add(game);
        source.addEdge(game, games.get(t));
        game.addEdge(nodes.get(i), games.get(t));
        game.addEdge(nodes.get(t), games.get(t));
      }
    }
    sc.close();
    Graph g = new Graph(nodes, source, sink);
    g.maximizeFlow();
    // One of these teams will beat team x when the game is played, therefore team x cannot win.
    for (Edge e : source.getEdges()) {
      if (e.getFlow() != e.getCapacity()) {
        return false;
      }
    }
    return true;
  }
}
```
