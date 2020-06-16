In this first implementation task of the Network Flow module you are going to build a residual graph.
We assume a network with n nodes (IDs ranging from 0 to n−1) and m

edges.

    Task 1: implement the parse method so that it builds a residual graph from textual input.

    Task 2: implement the augmentPath method so that it augments the flow in the indicated path by 1

    or throws an IllegalArgumentException when this is impossible.

NB: As you may be aware, there are multiple ways to implement the residual edges. In this exercise you will build the method as outlined in section 7.1 from the book. In later exercises you need not worry about how our library models it, as you only need to create the original graph and our library will create the residual graph from this for you.

The input you are given contains m+1

lines.

    The first line of the input contains 4 integers separated by a space: n m s t.
    n is the amount of nodes, m the amount of edges, s is the ID of the source node and t is the ID of the sink node.

    The following m lines each represent one edge and contain 4 integers: from to cap flow.
    from and to are the IDs of the starting and ending note respectively, cap is the capacity of the edge and flow indicates the current flow.

The library contains implementations for the following classes:
```java
class Graph {
    Graph(List<Node> nodes, Node source, Node sink);
    Node getSink();
    Node getSource();
    List<Node> getNodes();
}

class Node {
    Node(int id);
    int getId();
    void addEdge(Node to, int capacity, int flow, boolean backwards);
    Collection<Edge> getEdges();
}

class Edge {
    Edge(int capacity, Node from, Node to, int flow, boolean backwards);
    int getCapacity();
    int getFlow();
    Node getFrom();
    Node getTo();
    void setFlow();
    void setCapacity();
}
```

### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Parses inputstream to graph.
   */
  static Graph parse(InputStream in) {
  // TODO
  }

  /**
   * Augments the flow over the given path by 1 if possible.
   *
   * @param g    Graph to operate on.
   * @param path List of nodes to represent the path.
   * @throws IllegalArgumentException if augmenting the flow in the given path is impossible.
   */
  static void augmentPath(Graph g, List<Integer> path) throws IllegalArgumentException {
  // TODO
  }
}
```

### Library:
```java
import java.util.*;

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

  List<Node> getNodes() {
    return nodes;
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

  private int id;

  private Collection<Edge> edges;

  public Node(int id) {
    this.id = id;
    this.edges = new ArrayList<Edge>();
  }

  void addEdge(Node to, int capacity, int flow, boolean backwards) {
    Edge e = new Edge(capacity, this, to, flow, backwards);
    edges.add(e);
  }

  Collection<Edge> getEdges() {
    return edges;
  }

  int getId() {
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

  private int capacity;

  private int flow;

  private Node from;

  private Node to;

  private boolean backwards;

  protected Edge(int capacity, Node from, Node to, boolean backwards) {
    this.capacity = capacity;
    this.from = from;
    this.to = to;
    this.flow = 0;
    this.backwards = backwards;
  }

  protected Edge(int capacity, Node from, Node to, int flow, boolean backwards) {
    this.capacity = capacity;
    this.from = from;
    this.to = to;
    this.flow = flow;
    this.backwards = backwards;
  }

  int getCapacity() {
    return capacity;
  }

  int getFlow() {
    return flow;
  }

  Node getFrom() {
    return from;
  }

  Node getTo() {
    return to;
  }

  void setFlow(int f) {
    assert (f <= capacity);
    this.flow = f;
  }

  void setCapacity(int capacity) {
    assert (this.flow <= capacity);
    this.capacity = capacity;
  }

  boolean isBackwards() {
    return this.backwards;
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
      int flow = sc.nextInt();
      nodes.get(from).addEdge(nodes.get(to), cap, flow, false);
      nodes.get(to).addEdge(nodes.get(from), flow, 0, true);
    }
    return new Graph(nodes, nodes.get(s), nodes.get(t));
  }

  private ByteArrayInputStream getStream(String fileName) {
    return new ByteArrayInputStream(WebLab.getData(fileName).getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Tests the parse method on a simple network.
   */
  @Test(timeout = 100)
  public void testParser1() {
    assertEquals(parse(getStream("parser1.in")), Solution.parse(getStream("parser1.in")));
  }

  /**
   * Tests the parse method on a larger network.
   */
  @Test(timeout = 150)
  public void testParser2() {
    assertEquals(parse(getStream("parser2.in")), Solution.parse(getStream("parser2.in")));
  }

  /**
   * Tests augmenting a valid path in a small network.
   */
  @Test(timeout = 100)
  public void augment1() {
    Node node0 = new Node(0);
    Node source = new Node(1);
    Node sink = new Node(2);
    Node node3 = new Node(3);
    // Add edge between the source and node 3 with capacity 1 and no flow.
    source.addEdge(node3, 1, 0, false);
    node3.addEdge(source, 0, 0, true);
    // Add edge between node 3 and node 0 with capacity 1 and no flow.
    node3.addEdge(node0, 1, 0, false);
    node0.addEdge(node3, 0, 0, true);
    // Add edge between node 0 and the sink with capacity 1 and no flow.
    node0.addEdge(sink, 1, 0, false);
    sink.addEdge(node0, 0, 0, true);
    List<Node> nodes = new ArrayList();
    nodes.add(node0);
    nodes.add(source);
    nodes.add(sink);
    nodes.add(node3);
    Graph g = new Graph(nodes, source, sink);
    Solution.augmentPath(g, Arrays.asList(1, 3, 0, 2));
    assertEquals(parse(getStream("augment1.out")), g);
  }

  /**
   * Tests augmenting a valid path in a network of 200 nodes and 1000 edges
   */
  @Test(timeout = 100)
  public void augment2() {
    Graph g = Solution.parse(getStream("augment2.in"));
    Solution.augmentPath(g, Arrays.asList(0, 4, 5, 6, 7));
    assertEquals(parse(getStream("augment2.out")), g);
  }

  /**
   * Tests augmenting a valid path in a network of 100 nodes and 2000 edges.
   */
  @Test(timeout = 400)
  public void augment3() {
    Graph g = Solution.parse(getStream("augment3.in"));
    Solution.augmentPath(g, Arrays.asList(39, 31, 15, 72, 47));
    Solution.augmentPath(g, Arrays.asList(42, 85, 78, 53, 71));
    Solution.augmentPath(g, Arrays.asList(31, 15, 0, 92));
    assertEquals(parse(getStream("augment3.out")), g);
  }

  /**
   * Tests augmenting a path where one of the edges is at flow == cap after 1 augmentation.
   */
  @Test(timeout = 100)
  public void augmentMaxCapacity() {
    Graph g = Solution.parse(getStream("augment2.in"));
    Solution.augmentPath(g, Arrays.asList(0, 4, 6, 7));
    try {
      Solution.augmentPath(g, Arrays.asList(0, 4, 6, 7));
      fail();
    } catch (IllegalArgumentException e) {
    // Should throw an exception
    }
  }

  /**
   * Tests augmenting a path where an unused edge would be traversed backwards (residual edge with flow == cap).
   */
  @Test(timeout = 100)
  public void augmentNonExistingPath() {
    Graph g = Solution.parse(getStream("augment2.in"));
    try {
      Solution.augmentPath(g, Arrays.asList(0, 4, 2, 3, 5, 7));
      fail();
    } catch (IllegalArgumentException e) {
    // Should throw an exception
    }
  }
}
```


_______________________________________________________________________________________________________

### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Parses inputstream to graph.
   */
  static Graph parse(InputStream in) {
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
      int flow = sc.nextInt();
      nodes.get(from).addEdge(nodes.get(to), cap, flow, false);
      nodes.get(to).addEdge(nodes.get(from), flow, 0, true);
    }
    sc.close();
    return new Graph(nodes, nodes.get(s), nodes.get(t));
  }

  /**
   * Augments the flow over the given path by 1 if possible.
   *
   * @param g    Graph to operate on.
   * @param path List of nodes to represent the path.
   * @throws IllegalArgumentException if augmenting the flow in the given path is impossible.
   */
  static void augmentPath(Graph g, List<Integer> path) throws IllegalArgumentException {
    for (int x = 0; x < path.size() - 1; x++) {
      // Find edge between 2 path nodes
      Edge e = findEdge(g.getNodes().get(path.get(x)), g.getNodes().get(path.get(x + 1)));
      if (e == null)
        throw new IllegalArgumentException("Path doesn't exist.");
      if (!e.isBackwards() && e.getFlow() >= e.getCapacity())
        throw new IllegalArgumentException("Path cannot be incremented.");
      if (e.isBackwards() && e.getCapacity() == 0)
        throw new IllegalArgumentException("Path cannot be incremented.");
      if (e.isBackwards()) {
        // Backwards edges lose 1 capacity
        e.setCapacity(e.getCapacity() - 1);
      } else {
        // Regular edges gain 1 flow
        e.setFlow(e.getFlow() + 1);
      }
      // Find backwards edge
      e = findEdge(g.getNodes().get(path.get(x + 1)), g.getNodes().get(path.get(x)));
      if (e.isBackwards()) {
        // Backwards edges gain 1 capacity
        e.setCapacity(e.getCapacity() + 1);
      } else {
        // Regular edges lose 1 flow
        e.setFlow(e.getFlow() - 1);
      }
    }
  }

  private static Edge findEdge(Node from, Node to) {
    for (Edge edge : from.getEdges()) {
      if (edge.getTo().equals(to)) {
        return edge;
      }
    }
    return null;
  }
}
```

### Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Parses inputstream to graph.
   */
  static Graph parse(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt(); //nodes
    int m = sc.nextInt(); // edges
    int s = sc.nextInt(); //source 
    int t = sc.nextInt(); //sink
    ArrayList<Node> nodes = new ArrayList<>();
    for(int i = 0; i < n; i++) nodes.add(new Node(i));
    for(int i = 0; i < m; i++) {
      int from = sc.nextInt();
      int to = sc.nextInt();
      int cap = sc.nextInt();
      int flow = sc.nextInt();
      nodes.get(from).addEdge(nodes.get(to), cap, flow, false); //add flowing edge
      nodes.get(to).addEdge(nodes.get(from), flow, 0, true);   //add backwards flowing edge
    }
    sc.close();
    return new Graph(nodes, nodes.get(s), nodes.get(t));
  }

  /**
   * Augments the flow over the given path by 1 if possible.
   *
   * @param g    Graph to operate on.
   * @param path List of nodes to represent the path.
   * @throws IllegalArgumentException if augmenting the flow in the given path is impossible.
   */
  static void augmentPath(Graph g, List<Integer> path) throws IllegalArgumentException {
    for(int i = 0; i < path.size() -1; i++) {
      Edge e = findEdge(g.getNodes().get(path.get(i)), g.getNodes().get(path.get(i+1)));
      if(e == null || (!e.isBackwards() && e.getFlow() >= e.getCapacity()) || (e.isBackwards() && e.getCapacity() == 0)) throw new IllegalArgumentException();
      if(e.isBackwards()) e.setCapacity(e.getCapacity()-1);
      else e.setFlow(e.getFlow()+1);
      
      e = findEdge(g.getNodes().get(path.get(i+1)), g.getNodes().get(path.get(i)));
      if(e.isBackwards()) e.setCapacity(e.getCapacity() + 1);
      else e.setFlow(e.getFlow()-1);
    }
  }
  
  private static Edge findEdge(Node from, Node to) {
    for (Edge edge : from.getEdges()) {
      if (edge.getTo().equals(to)) return edge;
    }
    return null;
  }
}
```
