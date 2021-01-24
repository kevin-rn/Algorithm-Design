Lyra is an avid fan of hot air balloon flying together with her friend Lee Scoresby. Unfortunately the
skies above Cittàgazze are very crowded this time of year, so only a limited number of flying slots are
still available and aeronauts from all over the world have applied for these slots. Due to safety regulations only one aeronaut can use each of these slots.
Lee is in the running to
win the prize of “aeronaut of the year”, awarded to the person that flies his or her balloon most often.
Unfortunately his balloon punctured yesterday, so he can no longer fly his balloon.

Lyra wants to ensure that Lee can still win the prize, by making sure none of the others fly as much as Lee has done
(thus Lee would only win if he has more points than all others, a tie is not enough).
Given the amount of flights Lee has flown l, a list of n competing aeronauts that each have already made f_i flights, m remaining
slots, and large matrix compatible indicating if aeronaut i is eligible for slot j, Lyra wants to know
which aeronaut should use what time slot so that Lee can still win the prize. To this end she does the
following:

Write the code for this algorithm. The algorithm should return a boolean indicating whether Lee is still able to win.

See below on how the graph is build:

    Create a source s and a sink t.
    Create one node d_i for every time slot 1 ≤ i ≤ m.
    Create one node a_j for every aeronaut 1 ≤ j ≤ n.
    Connect s to all d_i with a capacity of 1.
    Connect all a_j to t with a capacity of l - 1 - f_i.
    Connect all d_i to a_j with a capacity of ∞ iff team j is eligible for slot i.
    
  ### Assignment:
```java
import java.io.*;
import java.util.*;

class Aeronauts {

  /**
   * @param l the number of flights Lee has already done
   * @param n the number of competitors
   * @param m the number of open slots left
   * @param flights the number of flights each of the competitors has done. You should use flights[1] to flights[n]
   * @param compatible 2D boolean array such that slot i can be used by competitor j iff compatible[i][j] is true. Note that compatible[0][x] and compatible[x][0] should not be used.
   * @return
   */
  public static boolean solve(int l, int n, int m, int[] flights, boolean[][] compatible) {
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

### Solution:
```java
class Aeronauts {

  /**
   * @param l the number of flights Lee has already done
   * @param n the number of competitors
   * @param m the number of open slots left
   * @param flights the number of flights each of the competitors has done. You should use flights[1] to flights[n]
   * @param compatible 2D boolean array such that slot i can be used by competitor j iff compatible[i][j] is true. Note that compatible[0][x] and compatible[x][0] should not be used.
   * @return
   */
  public static boolean solve(int l, int n, int m, int[] flights, boolean[][] compatible) {
    Graph g = buildGraph(l, n, m, flights, compatible);
    return canLeeWin(g);
  }

  /**
   *     @return the graph, or null if one opponent has already flown more than Lee. NOTE: the nodes either have an id corresponding to a slot or to a flyer, depending on what the node represents. I.e. there are duplicate ids!
   */
  public static Graph buildGraph(int l, int n, int m, int[] flights, boolean[][] compatible) {
    // (Step 1) Create the network with a Source
    ArrayList<Node> nodes = new ArrayList<>();
    Node source = new Node(-1, 0);
    nodes.add(source);
    // (Step 1) Add the Sink to the network too
    Node sink = new Node(-2, 0);
    nodes.add(sink);
    // (Step 2 + 3) Add a node for each flyer and each slot
    Node[] flyers = new Node[n + 1];
    Node[] slots = new Node[m + 1];
    for (int j = 1; j <= n; j++) {
      flyers[j] = new Node(j, 0);
      nodes.add(flyers[j]);
    }
    for (int i = 1; i <= m; i++) {
      slots[i] = new Node(i, 0);
      nodes.add(slots[i]);
    }
    // (Step 4) Connect the source to all slots with a capacity of 1
    for (int i = 1; i <= m; i++) {
      source.addEdge(slots[i], 0, 1);
    }
    // (Step 5) Connect all flyers to the sink with a capacity of l - 1 - f_1
    for (int i = 1; i <= n; i++) {
      if (flights[i] >= l) {
        return null;
      }
      flyers[i].addEdge(sink, 0, l - 1 - flights[i]);
    }
    // (Step 6) Add a link from each slot to each eligible flyer with a capacity of ∞
    for (int j = 1; j <= n; j++) {
      Node flyer = flyers[j];
      for (int slot = 1; slot <= m; slot++) {
        if (compatible[slot][j]) {
          slots[slot].addEdge(flyer, 0, Integer.MAX_VALUE);
        }
      }
    }
    Graph g = new Graph(nodes, source, sink);
    return g;
  }
  
    /**
   * @param g a graph whose max flow it will compute and use to check if Lee can win.
   * @return true iff Lee can win.
   */
  public static boolean canLeeWin(Graph g) {
    if (g == null) {
      return false;
    }
    MaxFlow.maximizeFlow(g);
    // as one of the other flyers will have to win one more than him.
    for (Edge e : g.getSource().getEdges()) {
      if (e.getFlow() != e.getCapacity()) {
        return false;
      }
    }
    return true;
  }
  
  // Alternative code:
  public static boolean solveLiveSession(int l, int n, int m, int[] flights, boolean[][] compatible) {
    List<Node> nodes = new ArrayList<Node>();
    Node source = new Node(-1, 0);
    Node sink = new Node(-2, 0);
    
    nodes.add(source);
    nodes.add(sink);
    
    Node[] opponents = new Node[n+1];
    for (int i = 1; i <= n; i++) {
      opponents[i] = new Node(i, 0);
      nodes.add(opponents[i]);
    }
    
    
    Node[] timeslots = new Node[m+1];
    for (int j = 1; j <= m; j++) {
      timeslots[j] = new Node(j, 0);
      nodes.add(timeslots[j]);
      
      source.addEdge(timeslots[j], 0, 1);
      for (int i = 1; i <= n; i++) {
        if (compatible[j][i]) {
          timeslots[j].addEdge(opponents[i], 0, 1);
        }
      }
    }
    
    for (int i = 1; i <= n; i++) {
      if (flights[i] >= l) {
        return false;
      }
      opponents[i].addEdge(sink, 0, l - flights[i] - 1);
    }
    
    Graph g = new Graph(nodes, source, sink);
    MaxFlow.maximizeFlow(g);
    for (Edge e: g.getSource().getEdges()) {
      if (e.getResidual() > 0) {
        return false;
      }
    }
    return true;
  }
}
```
  
