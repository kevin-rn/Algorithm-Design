### Assignment:
```java
import java.io.*;
import java.util.*;

class Graph {

  private List<Node> nodes;

  private Node source;

  private Node sink;

  public Graph(List<Node> nodes) {
    this.nodes = nodes;
    this.source = null;
    this.sink = null;
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

  /**
   *  You should implement this method.
   *  @param y Require a circulation of at this value
   */
  public boolean hasCirculationOfValue(int y) {
  // TODO
  }

  /**
   *  Should remove all lower bounds on edges.
   */
  private void removeLowerBounds() {
  // TODO
  }

  /**
   *  Should remove all supply and demand from nodes.
   */
  private void removeSupplyDemand() {
  // TODO
  }
}

class Node {

  protected int id;

  protected int d;

  protected Collection<Edge> edges;

  /**
   *  Create a new node
   *  @param id: Id for the node.
   *  @param d: demand for the node. Remember that supply is represented as a negative demand.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  /**
   *   You should implement the method below. Note that you can use the graph structure below.
   *   @param n The number of students.
   *   @param m The number of tasks.
   *   @param hours is an array of size (n+1)*2 that contains the minimum hours for student i in hours[i][0] and the maximum hours for student i in hours[i][1]. You should ignore hours[0].
   *   @param work is an array of size (m+1) that contains the number of hours tasks 1 through m take. You should ignore work[0].
   *   @param skilled is an array of size (n+1)*(m+1) such that skilled[i][j] == 1 iff student i can do task j. You should ignore all skilled[i][0] and skilled[0][j].
   *   @return true iff all tasks can be completed.
   */
  public static boolean solve(int n, int m, int[][] hours, int[] work, int[][] skilled) {
    ArrayList<Node> allNodes = new ArrayList<>(n + m + 2);
    Node[] students = new Node[n + 1];
    Node[] tasks = new Node[m + 1];
    int totalWork = 0;
    for (int i = 1; i <= m; i++) {
      totalWork += work[i];
    }
    Node source = new Node(-1, -totalWork);
    allNodes.add(source);
    for (int i = 1; i <= n; i++) {
      students[i] = new Node(i, 0);
      source.addEdge(students[i], hours[i][0], hours[i][1]);
      allNodes.add(students[i]);
    }
    for (int i = 1; i <= m; i++) {
      tasks[i] = new Node(n + i, work[i]);
      allNodes.add(tasks[i]);
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (skilled[i][j] > 0) {
          students[i].addEdge(tasks[j], 0, hours[i][1]);
        }
      }
    }
    Graph g = new Graph(allNodes);
    return g.hasCirculationOfValue(totalWork);
  }

  @Test(timeout = 100)
  public void example_onestudent_onetask() {
    int n = 1;
    int m = 1;
    int[][] h = { {}, { 8, 15 } };
    int[] w = { 0, 10 };
    int[][] s = { { 0, 0 }, { 0, 1 } };
    /*
		 * This test models the situation where:
		 * Student 1 can work for 8 to 15 hours in total and has the skills to work on task 1.
		 * Task 1 takes 10 hours.
		 * This is doable by having student 1 work on task 1 for 10 hours.
		 */
    assertTrue(solve(n, m, h, w, s));
  }

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int m = 2;
    int[][] h = { {}, { 8, 15 }, { 2, 6 } };
    int[] w = { 0, 10, 8 };
    int[][] s = { { 0, 0, 0 }, { 0, 1, 1 }, { 0, 0, 1 } };
    /*
  	 * This test models the situation where:
  	 * Student 1 can work for 8 to 15 hours in total and has the skills to work on task 1 and task 2.
  	 * Student 2 can work for 2 to 6 hours in total and has the skills to work on task 2 only.
  	 * Task 1 takes 10 hours and task 2 takes 8.
  	 * This is doable by having student 1 work on task 1 for 10 hours and on task 2 for 2. Student 2 does the remaining 6 hours of work on task 2.
  	 */
    assertTrue(solve(n, m, h, w, s));
  }

  @Test(timeout = 100)
  public void example_notdoable() {
    int n = 2;
    int m = 2;
    int[][] h = { {}, { 8, 15 }, { 2, 6 } };
    int[] w = { 0, 10, 8 };
    int[][] s = { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
    /*
		 * This test models the situation where:
		 * Student 1 can work for 8 to 15 hours in total and has the skills to work on task 1 only.
		 * Student 2 can work for 2 to 6 hours in total and has the skills to work on task 2 only.
		 * Task 1 takes 10 hours and task 2 takes 8.
		 * This is not doable as we cannot do task 2.
		 */
    assertFalse(solve(n, m, h, w, s));
  }
}

```

### Solution:
```java
  /**
   *  You should implement this method.
   *  @param y Require a circulation of at this value
   */
  public boolean hasCirculationOfValue(int y) {
    this.removeLowerBounds();
    this.removeSupplyDemand();
    return y == MaxFlow.maximizeFlow(this);
  }

  /**
   *  Should remove all lower bounds on edges.
   */
  private void removeLowerBounds() {
    for(Node n:this.getNodes()) {
      for(Edge e:n.getEdges()) {
        if(e.lower > 0) {
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

  /**
   *  Should remove all supply and demand from nodes.
   */
  private void removeSupplyDemand() {
    Node source = new Node(-2, 0);
    Node sink = new Node(-1, 0);
    for(Node n : this.getNodes()) {
      if(n.d < 0) source.addEdge(n, 0, -n.d); 
      else if (n.d > 0) n.addEdge(sink, 0, n.d);
      n.d = 0;
    }
    this.nodes.add(source);
    this.nodes.add(sink);
    this.source=  source;
    this.sink = sink;
  }
```
