Note in this implementation you can give nodes a demand, and edges a lower bound, which although it can be used does not need to be used. You can set both demands an lower bounds to zero when using these constructors!

In our adventure in changing courses to be ready for online education next year, we are also reconsidering the TA distribution.
Students have already indicated what courses they can TA and assist in, we are now trying to figure out whether we have sufficient TAs.

To that end you have been provided with the following information:
For n
TAs you get the number of hours that TA is available ai for each TA 1≤i≤n.
For m courses you get the the number of TA hours bj that the course 1≤j≤m requires.
Further more you are told which TAs are available for which courses through a large matrix ci,j where for 1≤i≤n,1≤j≤m the value ci,j is true iff TA i can assist course j.

The method you should implement is shortageOfTAs() which answers the question:
When optimally using the TAs available, how many TA-hours are we still short for all courses combined?

For this assignment you are advised to use the code made available on WebLab for this assignment specifically.

The Graph, Node, Edge and MaxFlow classes available to you are based on the extended
network flow library created in the last tutorial of the course.
You are free to make modifications to these classes, though our reference solutions to this assignment do not modify them in any way.
Functions you may require include:

    In the Node class:
        public Node(int id, int d) to construct a new Node object with id id and a demand of d. Please note that a supply is represented by a negative demand.
        public void addEdge(Node to, int lower, int upper) to construct a new edge between this and to with lower bound lower and upper bound upper.
    In the Graph class:
        public Graph(List<Node> nodes, Node source, Node sink) to construct a new graph with the nodes nodes, a source source, and a sink sink.
        public Graph(List<Node> nodes) to construct a new graph with the nodes nodes. (Use this one if you want hasCirculation() to create the source and sink for you)
        public boolean hasCirculation() to determine if the graph has a circulation of the value D where D is the sum of the demands in the graph. This method will throw an IllegalArgumentException if the amount of
    supply in the network is not exactly equal to the amount of demand.

In the MaxFlow class:

    public statitc int maximiseFlow(Graph g) to determine the maximum flow of the graph g. Note that this ignores lower bounds and supplies and demands (see hasCirculation() for using those).

### Assignment:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class TADistribution {

  /**
   * You should implement this method
   *
   * @param n the number of TAs
   * @param a the number of hours a TA is available from index _1_ to _n_. You should ignore a[0].
   * @param m the number of courses
   * @param b the number of TA hours a course requires from index _1_ to _m_. You should ignore b[0].
   * @param c a matrix indicating whether a TA is available to assist in a course. The value c[i][j] is true iff TA i can assist course j. You should ignore c[0][j] and c[i][0].
   * @return the number of hours we are short when optimally using the available TAs.
   */
  public static int shortageOfTAs(int n, int[] a, int m, int[] b, boolean[][] c) {
  // TODO
  }
}

class Graph {

  private List<Node> nodes;

  private Node source;

  private Node sink;

  public Graph(List<Node> nodes) {
    this.nodes = nodes;
    this.source = null;
    this.sink = null;
  }

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

  public boolean hasCirculation() {
    this.removeLowerBounds();
    int D = this.removeSupplyDemand();
    int x = MaxFlow.maximizeFlow(this);
    return x == D;
  }

  private void removeLowerBounds() {
    for (Node n : this.getNodes()) {
      for (Edge e : n.edges) {
        if (e.lower > 0) {
          e.capacity -= e.lower;
          e.backwards.capacity -= e.lower;
          e.backwards.flow -= e.lower;
          e.from.d += e.lower;
          e.to.d -= e.lower;
          e.lower = 0;
        }
      }
    }
  }

  private int removeSupplyDemand() {
    int Dplus = 0, Dmin = 0;
    int maxId = 0;
    for (Node n : this.getNodes()) {
      maxId = Math.max(n.id, maxId);
    }
    Node newSource = new Node(maxId + 1, 0);
    Node newSink = new Node(maxId + 2, 0);
    for (Node n : this.getNodes()) {
      if (n.d < 0) {
        newSource.addEdge(n, 0, -n.d);
        Dmin -= n.d;
      } else if (n.d > 0) {
        n.addEdge(newSink, 0, n.d);
        Dplus += n.d;
      }
      n.d = 0;
    }
    if (Dmin != Dplus) {
      throw new IllegalArgumentException("Demand and supply are not equal!");
    }
    this.nodes.add(newSource);
    this.nodes.add(newSink);
    this.source = newSource;
    this.sink = newSink;
    return Dplus;
  }
}

class Node {

  protected int id;

  protected int d;

  protected Collection<Edge> edges;

  /**
   * Create a new node
   *
   * @param id: Id for the node.
   * @param d:  demand for the node. Remember that supply is represented as a negative demand.
   */
  public Node(int id, int d) {
    this.id = id;
    this.d = d;
    this.edges = new ArrayList<Edge>();
  }

  public void addEdge(Node to, int lower, int upper) {
    Edge e = new Edge(lower, upper, this, to);
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

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getId());
    sb.append(" ");
    sb.append(this.getEdges().size());
    sb.append(":");
    for (Edge e : this.getEdges()) {
      sb.append("(");
      sb.append(e.from.getId());
      sb.append(" --[");
      sb.append(e.lower);
      sb.append(',');
      sb.append(e.capacity);
      sb.append("]-> ");
      sb.append(e.to.getId());
      sb.append(")");
    }
    return sb.toString();
  }
}

class Edge {

  protected int lower;

  protected int capacity;

  protected int flow;

  protected Node from;

  protected Node to;

  protected Edge backwards;

  private Edge(Edge e) {
    this.lower = 0;
    this.flow = e.getCapacity();
    this.capacity = e.getCapacity();
    this.from = e.getTo();
    this.to = e.getFrom();
    this.backwards = e;
  }

  protected Edge(int lower, int capacity, Node from, Node to) {
    this.lower = lower;
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

  public static int maximizeFlow(Graph g) {
    int f = 0;
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
      f += r;
    }
    return f;
  }
}

```

### Test:
```java
import java.util.Scanner;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  @Test(timeout = 100)
  public void exampleTwoOfEach() {
    int n = 2;
    int m = 2;
    int[] available = { 0, 75, 75 };
    int[] need = { 0, 100, 50 };
    boolean[][] match = new boolean[n + 1][m + 1];
    match[1][1] = true;
    match[1][2] = true;
    match[2][1] = true;
    match[2][2] = true;
    /*
         * This test models the situation where:
         * Course 1 requires 100 TA hours and course 2 requires 50
         * Both TAs have 75 hours available.
         * Both TAs can work both courses.
         * So we are not short any hours.
         */
    Assert.assertEquals(0, TADistribution.shortageOfTAs(n, available, m, need, match));
  }

  @Test(timeout = 100)
  public void exampleShortage() {
    int n = 2;
    int m = 2;
    int[] available = { 0, 250, 25 };
    int[] need = { 0, 100, 50 };
    boolean[][] match = new boolean[n + 1][m + 1];
    match[1][1] = true;
    match[2][1] = true;
    match[2][2] = true;
    /*
         * This test models the situation where:
         * Course 1 requires 100 TA hours and course 2 requires 50
         * TA 1 can work on course 1 for 250 hours.
         * TA 2 can work on both courses for 25 hours.
         * So we are short a total of 25 hours for course 2 by 25 hours when optimally assigning our current TAs.
         */
    Assert.assertEquals(25, TADistribution.shortageOfTAs(n, available, m, need, match));
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
class TADistribution {

  /**
   * You should implement this method
   *
   * @param n the number of TAs
   * @param a the number of hours a TA is available from index _1_ to _n_. You should ignore a[0].
   * @param m the number of available TAs
   * @param b the number of TA hours a course requires from index _1_ to _m_. You should ignore b[0].
   * @param c a matrix indicating whether a TA is available to assist in a course. The value c[i][j] is true iff TA i can assist course j. You should ignore c[0][j] and c[i][0].
   * @return the number of hours we are short when optimally using the available TAs.
   */
  public static int shortageOfTAs(int n, int[] a, int m, int[] b, boolean[][] c) {
    return solveProper(m, b, n, a, c);
  }

  private static int solveProper(int numCourses, int[] need, int numStudents, int[] avail, boolean[][] match) {
    int totalReq = 0;
    for (int i = 1; i <= numCourses; i++) {
      totalReq += need[i];
    }
    List<Node> nodes = new ArrayList<>();
    Node s = new Node(-1, 0);
    Node t = new Node(-2, 0);
    nodes.add(s);
    nodes.add(t);
    Node[] students = new Node[numStudents + 1];
    for (int j = 1; j <= numStudents; j++) {
      students[j] = new Node(j, 0);
      nodes.add(students[j]);
      s.addEdge(students[j], 0, avail[j]);
    }
    Node[] courses = new Node[numCourses + 1];
    for (int i = 1; i <= numCourses; i++) {
      courses[i] = new Node(numStudents + i, 0);
      nodes.add(courses[i]);
      courses[i].addEdge(t, 0, need[i]);
    }
    for (int i = 1; i <= numCourses; i++) {
      for (int j = 1; j <= numStudents; j++) {
        if (match[j][i]) {
          // Change compared to other variant
          students[j].addEdge(courses[i], 0, Integer.MAX_VALUE / 2);
        }
      }
    }
```
### Solution 2: 
```java
 public static int shortageOfTAs(int n, int[] a, int m, int[] b, boolean[][] c) {
    List<Node> nodes = new ArrayList<>();
    Node[] tas = new Node[n+1], courses = new Node[m+1];
    Node source = new Node(-2, 0), sink = new Node(-1, 0);
    nodes.add(source);
    for(int i = 1; i <= n; i++) {
      tas[i] = new Node(i, 0);
      nodes.add(tas[i]);
      source.addEdge(tas[i], 0, a[i]);
    }
    for(int j = 1; j <= m; j++) {
      courses[j] = new Node(j+n, 0);
      nodes.add(courses[j]);
      courses[j].addEdge(sink, 0, b[j]);
    }
    for(int i = 1; i <= n; i++) {
      for(int j = 1; j <= m; j++) {
        if(c[i][j]) tas[i].addEdge(courses[j], 0, Integer.MAX_VALUE/2);
      }
    }
    nodes.add(sink);
    Graph g = new Graph(nodes, source, sink);
    MaxFlow.maximizeFlow(g);
    int result = 0;
    for(int j = 1; j <= m; j++) {
      for(Edge e : courses[j].getEdges()) {
        if(e.to == sink) {
          result += e.getResidual();
        }
      }
    }
    return result;
  }

```


