### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class BuildNF {

  /**
   *   You should implement the method below. Note that you can use the graph structure below.
   *   @param n The number of code changes.
   *   @param benefits An array of dimension n+1 containing the benefits of all the code changes for 1 <= i <= n
   *   @param costs An array of dimension n+1 containing the costs of all the code changes for 1 <= i <= n
   *   @param dependencies is an array of dimension (n+1)*(n+1) that contains value 1 iff code change i depends on j and 0 otherwise, for all 1 <= i,j <= n.
   *   @return
   */
  public static int solve(int n, int[] benefits, int[] costs, int[][] dependencies) {
  // TODO
  }
}

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
import static org.junit.Assert.assertEquals;
import java.util.Scanner;
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

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int[] b = { 0, 8, 2 };
    int[] c = { 0, 2, 4 };
    int[][] dep = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
    assertEquals("Should take only code change 1 as that has a net profit", 6, BuildNF.solve(n, b, c, dep));
  }

  @Test(timeout = 100)
  public void example02() {
    int n = 2;
    int[] b = { 0, 8, 2 };
    int[] c = { 0, 2, 4 };
    int[][] dep = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 0, 0 } };
    assertEquals("Code change 1 (profit 6) now requires 2 (loss 2), so we take both", 4, BuildNF.solve(n, b, c, dep));
  }

  @Test(timeout = 100)
  public void checkMissingSubtraction() {
    // If this test passes, whereas (all?) others fail, that means student forgot to subtract cap of min cut from the total profit and just returned cap of min cut.
    int n = 2;
    int[] b = { 0, 7, 5 };
    int[] c = { 0, 2, 4 };
    int[][] dep = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
    assertEquals("Total profit 6, min cut also 6", 6, BuildNF.solve(n, b, c, dep));
  }

  private static int parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int ans = sc.nextInt();
    sc.close();
    return ans;
  }

  private static class ProblemInstance {

    int n;

    int[] b;

    int[] c;

    int[][] dep;

    public ProblemInstance(int n, int[] b, int[] c, int[][] dep) {
      this.n = n;
      this.b = b;
      this.c = c;
      this.dep = dep;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int[] b = new int[n + 1];
    int[] c = new int[n + 1];
    int[][] dep = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      c[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        dep[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return new ProblemInstance(n, b, c, dep);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    // DominatedSolutions.Solution sol = S.get(S.size()-1);
    // S.remove(S.size()-1);
    int theirSolution = BuildNF.solve(S.n, S.b, S.c, S.dep);
    int ourSolutions = parseOutputFile(fileName + ".out");
    assertEquals(ourSolutions, theirSolution);
  }
}
```

_______________________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class BuildNF {

  /**
   *   You should implement the method below. Note that you can use the graph structure below.
   *   @param n The number of code changes.
   *   @param benefits An array of dimension n+1 containing the benefits of all the code changes for 1 <= i <= n
   *   @param costs An array of dimension n+1 containing the costs of all the code changes for 1 <= i <= n
   *   @param dependencies is an array of dimension (n+1)*(n+1) that contains value 1 iff code change i depends on j and 0 otherwise, for all 1 <= i,j <= n.
   *   @return
   */
  public static int solve(int n, int[] benefits, int[] costs, int[][] dependencies) {
    Node source = new Node(0);
    Node sink = new Node(n + 1);
    ArrayList<Node> nodes = new ArrayList<>(n + 2);
    nodes.add(source);
    for (int i = 1; i <= n; i++) {
      Node newNode = new Node(i);
      nodes.add(newNode);
    }
    nodes.add(sink);
    for (int i = 1; i <= n; i++) {
      source.addEdge(nodes.get(i), benefits[i]);
      nodes.get(i).addEdge(sink, costs[i]);
      for (int j = 1; j <= n; j++) {
        if (dependencies[i][j] > 0) {
          nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE / 2);
        }
      }
    }
    Graph g = new Graph(nodes, source, sink);
    MaxFlow.maximizeFlow(g);
    int f = 0;
    for (Edge e : source.getEdges()) {
      f += e.getFlow();
    }
    int maxProfit = 0;
    for (int i = 0; i < benefits.length; i++) {
      maxProfit += benefits[i];
    }
    return maxProfit - f;
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2018_2019/nf/data/";
    File dir = new File(dirName);
    System.out.println(dir.exists());
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
    int[] b = new int[n + 1];
    int[] c = new int[n + 1];
    int[][] dep = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      c[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        dep[i][j] = sc.nextInt();
      }
    }
    sc.close();
    int ans = BuildNF.solve(n, b, c, dep);
    return Integer.toString(ans);
  }
}

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
