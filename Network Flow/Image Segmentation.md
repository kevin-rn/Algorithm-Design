Cameras on the highway are there to detect whether a car is exceeding the speed limit. This is done by recording the speed of the passing cars and taking a picture of each car which exceeds the speed limit.
Of all the cars that exceed the speed limit, the picture of the car and the speed are given to the police. To be able to tell who the owner of the car is, the license plate needs to be determined.
The first step in this process is image segmentation to determine which pixels in the picture belong to the license plate (foreground). The other pixels are labeled background.

The police obviously want a reliable suggestion, so each outcome of the image segmentation is rated with a score and higher scores correspond to better segmentation.
All pixels have a likelihood to belong either to the foreground or background, for pixel i, these are integers f_i and b_i respectively, where 0 <= f_i, b_i <= 10
So, for each pixel which has a higher likelihood to be in the foreground than in the background, we want to assign that pixel the label of foreground and the other way around.
Furthermore, for each pair of neighboring pixels (i, j), where i is in the foreground and j is in the background, we count a penalty of 10, denoted by p_i,j.
For pixel pairs where both pixels are foreground or both pixels are background, the penalty is 0.
The final score for the image segmentation which gives as outcome the set of foreground pixels F and background pixels B is the minimum total penalty for all pairs (i,j) of neighboring nodes.

Your job is now to label each pixel in the picture as belonging either to the background or the foreground and maximize the score.
You can regard the pixels as a set of nodes, and all nodes are connected to their neighboring nodes by edges. Each node can have at most 4 neighbours (imagine a grid).

Your input will be as follows. The first line contains the number of pixels, n >= 1, followed by the number of undirected edges, m >= 0. All pixels are indicated by their integer id 1<=i<=n.
Then for each pixel a line follows with the pixel id, followed by the likelihood that the from pixel belongs to the foreground f_i and the likelihood that the pixel belongs to the background b_i.
Then for each edge a line follows with the from and to pixel.
Your algorithm should output the score of the segmentation.

An example output is given below:

2 1
1 9 1
2 8 2
1 2

The answer should be:

3

In the library a full implementation for the following classes is given:
```java
class Graph { 
    public Graph(Collection<Node> nodes, Node source, Node sink);
    public Node getSink();
    public Node getSource();
    public void maximizeFlow(); // Executes the Ford-Fulkerson algorithm to maximize the flow
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
    public void augmentFlow(int add); // Adds the given flow and removes this amount from the residual edge
    public Node getFrom();
    public Node getTo();
}
```

### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  public int solve(InputStream in) {
  // TODO
  }
}


```

### Library:
```java
import java.util.*;

class MaxFlow {

  public static List<Edge> findPath(Graph g, Node start, Node end) {
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

  public InputStream getInputStream(String id) {
    String str = WebLab.getData(id);
    return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
  }

  private static void runTestWithFile(String fileName) {
    long expected = Integer.parseInt(WebLab.getData(fileName + ".out").trim());
    long actual = new Solution().solve(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8)));
    assertEquals(expected, actual);
  }

  @Test(timeout = 100)
  public void example() {
    runTestWithFile("example");
  }

  @Test(timeout = 100)
  public void testOnePixel() {
    runTestWithFile("onePixel");
  }

  @Test(timeout = 100)
  public void testTwoPixels() {
    runTestWithFile("twoPixels");
  }

  @Test(timeout = 100)
  public void testTwoPixelsForeground() {
    runTestWithFile("twoPixelsForeground");
  }

  @Test(timeout = 500)
  public void test50x8() {
    runTestWithFile("50x8");
  }

  @Test(timeout = 2000)
  public void test10x150() {
    runTestWithFile("10x150");
  }
}
```

____________________________________________________________________________________________________________

### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  public int solve(InputStream in) {
    Graph g = parse(in);
    MaxFlow.maximizeFlow(g);
    int sum = 0;
    for (Edge e : g.getSource().getEdges()) {
      sum += e.getFlow();
    }
    return sum;
  }

  public Graph parse(InputStream in) {
    Scanner sc = new Scanner(in);
    ArrayList<Node> nodes = new ArrayList<>();
    // no. of nodes
    int n = sc.nextInt();
    // no. of edges
    int m = sc.nextInt();
    // source
    int s = 0;
    // sink
    int t = n + 1;
    Node source = new Node(s);
    Node sink = new Node(t);
    // Create nodes list and add source
    nodes.add(source);
    for (int x = 0; x < n; x++) {
      int id = sc.nextInt();
      int f_i = sc.nextInt();
      int b_i = sc.nextInt();
      Node node = new Node(id);
      nodes.add(node);
      source.addEdge(node, f_i);
      node.addEdge(sink, b_i);
    }
    nodes.add(sink);
    // Create all edges
    for (int x = 0; x < m; x++) {
      int from = sc.nextInt();
      int to = sc.nextInt();
      // Initialize capacity with maximum penalty
      nodes.get(from).addEdge(nodes.get(to), 10);
      nodes.get(to).addEdge(nodes.get(from), 10);
    }
    return new Graph(nodes, source, sink);
  }
}
```

### Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  public int solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt(); // number of pixels
    int m = sc.nextInt(); // number of undirected edges
    ArrayList<Node> nodes = new ArrayList<>();
    Node source = new Node(0), sink = new Node(n+1);
    nodes.add(source);
    for(int i = 1; i<=n; i++) {
      Node pixel_i = new Node(sc.nextInt());
      nodes.add(pixel_i);
      source.addEdge(pixel_i, sc.nextInt());
      pixel_i.addEdge(sink, sc.nextInt());
    }
    nodes.add(sink);
    for(int i = 0; i < m; i++) {
      Node from = nodes.get(sc.nextInt()), to = nodes.get(sc.nextInt());
      from.addEdge(to, 10);
      to.addEdge(from, 10);
    }
    sc.close();
    Graph g = new Graph(nodes, source, sink);
    MaxFlow.maximizeFlow(g);
    int score = 0;
    for(Edge e : g.getSource().getEdges()) score += e.getFlow();
    return score;
  }
}
```
