In this assignment, you are tasked with finding the maximum flow of a graph.
Execute the Ford-Fulkerson algorithm on the given graph and return its maximum flow.

In the hidden library code, a full implementation for the following classes is given:
```java
class Graph {
    public Graph(Collection<Node> nodes, Node source, Node sink);
    public Node getSink();
    public Node getSource();
}

class Node {
    public Node(int id);
    public void addEdge(Node to, int capacity);
    public Collection<Edge> getEdges();
    public int getId();
}

class Edge {
    public Edge(int capacity, Node from, Node to);
    public Edge getBackwards(); // Returns the residual edge
    public int getCapacity();
    public int getFlow();
    public void setFlow();
    public Node getFrom();
    public Node getTo();
}
```

### Template:
```java
import java.util.*;

class Solution {

  /**
   * Find the maximum flow in the given network.
   *
   * @param g Graph representing the network.
   * @return The maximum flow of the network.
   */
  static int maxFlow(Graph g) {
  // TODO
  }
}
```

#### Library:
```java
import java.util.*;

class Graph {

  private Collection<Node> nodes;

  private Node source;

  private Node sink;

  public Graph(Collection<Node> nodes, Node source, Node sink) {
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

  public boolean equals(Object other) {
    if (other instanceof Graph) {
      Graph that = (Graph) other;
      return this.nodes.equals(that.nodes);
    }
    return false;
  }
}

class Node {

  protected int id;

  protected Collection<Edge> edges;

  public Node(int id) {
    this.id = id;
    this.edges = new ArrayList<>();
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

  public Edge(int capacity, Node from, Node to) {
    this.capacity = capacity;
    this.from = from;
    this.to = to;
    this.flow = 0;
    this.backwards = new Edge(this);
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

  public Node getTo() {
    return to;
  }

  public void setFlow(int f) {
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
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
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
    assertEquals(Integer.parseInt(WebLab.getData(fileName + ".out").trim()), Solution.maxFlow(parse(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8)))));
  }

  private static Graph parse(InputStream in) {
    Scanner sc = new Scanner(in);
    // no. of nodes
    int n = sc.nextInt();
    // no. of edges
    int m = sc.nextInt();
    // source
    int s = sc.nextInt();
    // sink
    int t = sc.nextInt();
    // Create all nodes
    ArrayList<Node> nodes = new ArrayList<>();
    for (int x = 0; x < n; x++) {
      nodes.add(new Node(x));
    }
    // Create all edges
    for (int x = 0; x < m; x++) {
      int from = sc.nextInt();
      int to = sc.nextInt();
      int cap = sc.nextInt();
      nodes.get(from).addEdge(nodes.get(to), cap);
    }
    sc.close();
    return new Graph(nodes, nodes.get(s), nodes.get(t));
  }

  /**
   * Tests a network of 4 nodes and 5 edges.
   */
  @Test(timeout = 100)
  public void n4m5() {
    ArrayList<Node> nodes = new ArrayList<>();
    for (int x = 0; x < 4; x++) nodes.add(new Node(x));
    nodes.get(0).addEdge(nodes.get(1), 10);
    nodes.get(0).addEdge(nodes.get(2), 10);
    nodes.get(1).addEdge(nodes.get(3), 10);
    nodes.get(2).addEdge(nodes.get(3), 10);
    nodes.get(1).addEdge(nodes.get(2), 2);
    Graph g = new Graph(nodes, nodes.get(0), nodes.get(3));
    assertEquals(20, Solution.maxFlow(g));
  }

  /**
   * Tests network without path from source to sink.
   */
  @Test(timeout = 100)
  public void noFlow() {
    runTestWithFile("noFlow");
  }

  /**
   * Tests network with 5 nodes and only 1 s-t path.
   */
  @Test(timeout = 100)
  public void simpleFlow() {
    runTestWithFile("simpleFlow");
  }

  /**
   * Tests network where one edge is a bottleneck.
   */
  @Test(timeout = 100)
  public void restrictedFlow() {
    runTestWithFile("restrictedFlow");
  }

  /**
   * Tests network of 9 nodes 12 edges.
   */
  @Test(timeout = 100)
  public void n9m12() {
    runTestWithFile("n9m12");
  }

  /**
   * Tests network of 300 nodes 2000 edges.
   */
  @Test(timeout = 400)
  public void n300m2000() {
    runTestWithFile("n300m2000");
  }

  /**
   * Tests network of 500 nodes 10000 edges.
   */
  @Test(timeout = 1000)
  public void n500m10000() {
    runTestWithFile("n500m10000");
  }
}
```

___________________________________________________________________________________________________________________

### Official Solution:
```java
import java.util.*;

class Solution {

  /**
   * Find the maximum flow in the given network.
   *
   * @param g Graph representing the network.
   * @return The maximum flow of the network.
   */
  static int maxFlow(Graph g) {
    maximizeFlow(g);
    // Calculate sum of outgoing flow from source
    // Calculating the incoming flow for the sink would do the same
    int flow = 0;
    for (Edge e : g.getSource().getEdges()) {
      flow += e.getFlow();
    }
    return flow;
  }

  private static List<Edge> findPath(Node start, Node end) {
    // Stores the `edge` that we used to reach the `node`
    Map<Node, Edge> mapPath = new HashMap<>();
    Queue<Node> sQueue = new LinkedList<>();
    Node currentNode = start;
    sQueue.add(currentNode);
    // Loop while we have more nodes to traverse and haven't found the end yet
    while (!sQueue.isEmpty() && currentNode != end) {
      currentNode = sQueue.remove();
      // using edges that have a flow < capacity (i.e. we can augment the flow)
      for (Edge e : currentNode.getEdges()) {
        Node to = e.getTo();
        if (to != start && mapPath.get(to) == null && e.getCapacity() - e.getFlow() > 0) {
          sQueue.add(e.getTo());
          // Store how we got to the next node
          mapPath.put(to, e);
        }
      }
    }
    // No path found
    if (sQueue.isEmpty() && currentNode != end)
      return null;
    // Trace back path
    LinkedList<Edge> path = new LinkedList<Edge>();
    Node current = end;
    while (mapPath.get(current) != null) {
      Edge e = mapPath.get(current);
      path.addFirst(e);
      current = e.getFrom();
    }
    return path;
  }

  private static void maximizeFlow(Graph g) {
    Node sink = g.getSink();
    Node source = g.getSource();
    List<Edge> path;
    // Repeat as long as the flow can be increased
    while ((path = findPath(source, sink)) != null) {
      // Calculate the maximal flow on the path
      int r = Integer.MAX_VALUE;
      for (Edge e : path) {
        r = Math.min(r, e.getCapacity() - e.getFlow());
      }
      // Augment the flow on the path
      for (Edge e : path) {
        e.setFlow(e.getFlow() + r);
        e.getBackwards().setFlow(e.getCapacity() - e.getFlow());
      }
    }
  }
}
```

### Solution:
```java
import java.util.*;

class Solution {

  /**
   * Find the maximum flow in the given network.
   *
   * @param g Graph representing the network.
   * @return The maximum flow of the network.
   */
  static int maxFlow(Graph g) {
    Node s = g.getSource(), t = g.getSink();
    List<Edge> path;
    while((path = findPath(s, t)) != null) {
      int bottleneck = Integer.MAX_VALUE;
      for(Edge e : path) bottleneck = Integer.min(bottleneck, e.getCapacity() - e.getFlow());
      for(Edge e : path) {
        e.setFlow(e.getFlow() + bottleneck);
        e.getBackwards().setFlow(e.getCapacity() - e.getFlow());
      }
    }
    int maxFlow = 0;
    for(Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();
    return maxFlow;
  }
  
  static List<Edge> findPath(Node start, Node end) {
    Map<Node, Edge> mapPath = new HashMap<>();
    Queue<Node> sQueue = new LinkedList<>();
    Node currentNode = start;
    sQueue.add(currentNode);
    while (!sQueue.isEmpty() && currentNode != end) {
      currentNode = sQueue.remove();
      for (Edge e : currentNode.getEdges()) {
        Node to = e.getTo();
        if (to != start && mapPath.get(to) == null && e.getCapacity() - e.getFlow() > 0) {
          sQueue.add(e.getTo());
          mapPath.put(to, e);
        }
      }
    }
    if (sQueue.isEmpty() && currentNode != end)  return null;
    LinkedList<Edge> path = new LinkedList<Edge>();
    Node current = end;
    while (mapPath.get(current) != null) {
      Edge e = mapPath.get(current);
      path.addFirst(e);
      current = e.getFrom();
    }
    return path;
  }

}

```
