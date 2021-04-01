The government is testing what kinds of events can be organised now that the vaccination campaign is in full swing. To that end they have selected n events that they would like to run experiments with. Experimentation requires data collection, and so for every event they want to get at least k measurements.
Fortunately there are plenty of companies interested in helping out! These m
companies all have a number of sensors available to help measure the data. Each sensor can be used for at most 1 event due to hygiene constraints, belongs to a company (as indicated by the array t of length z), and not all sensors are suitable for all events (as indicated by the matrix c of dimension n×z).

Finally, the government wants to make sure the companies are not faking the data. To this end every event should have at least 2 different companies providing sensors.

The question you should now answer is, given n,m,z,k,t,c is it possible to organise all events?

For this assignment you are advised to use the code made available on WebLab for this assignment specifically.
The Graph, Node, Edge and MaxFlow classes available to you are based on the extended network flow library created in the sessions of the course. You are free to make modifications to these classes, though our reference solutions to this assignment do not modify them in any way.

Functions you may require include:
In the Node class:

    public Node(int id, int d) to construct a new Node object with id id and a demand of d. Please note that a supply is represented by a negative demand.
    public void addEdge(Node to, int lower, int upper) to construct a new edge between this and to with lower bound lower and upper bound upper.

In the Graph class:

    public Graph(List<Node> nodes, Node source, Node sink) to construct a new graph with the nodes nodes, a source source, and a sink sink (use this one if you want to use maximizeFlow.)
    public Graph(List<Node> nodes) to construct a new graph with the nodes nodes. (Use this one if you want hasCirculation() to create the source and sink for you.)
    public boolean hasCirculation() to determine if the graph has a circulation of the value D where D is the sum of the demands in the graph. This method will throw an IllegalArgumentException if the amount of
    supply in the network is not exactly equal to the amount of demand.

In the MaxFlow class:

    public static int maximizeFlow(Graph g) to determine the maximum flow of the graph g. Note that this ignores lower bounds and supplies and demands (see hasCirculation() for using those).

### Template:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class FieldLabs {

  /**
   * You should implement this method
   *
   * @param n the number of events
   * @param m the number of companies
   * @param z the total number of sensors
   * @param k the number of measurements required per event.
   * @param t the company a sensor belongs to. Sensor 1 <= j <= z belongs to company 1 <= t[j] <= m. You should ignore t[0].
   * @param c the compatibility of events and sensors. Event 1 <= i <= n can use sensor 1 <= j <= z iff c[i][j] = true. You should ignore c[0][x] and c[x][0].
   * @return true iff we can organise all events with this set of sensors.
   */
  public static boolean organisingEvents(int n, int m, int z, int k, int[] t, boolean[][] c) {
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
import org.junit.*;

public class UTest {

  @Test()
  public void exampleOneEvent() {
    int n = 1;
    int m = 3;
    int z = 3;
    int k = 2;
    int[] t = { 0, 1, 2, 3 };
    boolean[][] c = new boolean[n + 1][z + 1];
    c[1][1] = true;
    c[1][2] = true;
    c[1][3] = true;
    /**
     * This test models the situation where:
     * Event 1 can be done by all sensors, which all belong to different companies.
     * This is doable, for example by having sensor 1 and 2 measure at event 1.
     */
    Assert.assertTrue(FieldLabs.organisingEvents(n, m, z, k, t, c));
  }

  @Test()
  public void exampleOneEventLimit() {
    int n = 1;
    int m = 1;
    int z = 3;
    int k = 3;
    int[] t = { 0, 1, 1, 1 };
    boolean[][] c = new boolean[n + 1][z + 1];
    c[1][1] = true;
    c[1][2] = true;
    c[1][3] = true;
    /**
     * This test models the situation where:
     * Event 1 can be done by all sensors, which all belong to _the same_ company.
     * This is not doable, as we need at least two different companies to monitor events.
     */
    Assert.assertFalse(FieldLabs.organisingEvents(n, m, z, k, t, c));
  }

  @Test()
  public void exampleTwoEventTooFewSensors() {
    int n = 2;
    int m = 2;
    int z = 3;
    int k = 2;
    int[] t = { 0, 1, 2, 2 };
    boolean[][] c = new boolean[n + 1][z + 1];
    c[1][1] = true;
    c[1][2] = true;
    c[1][3] = true;
    c[2][1] = true;
    c[2][2] = true;
    c[2][3] = true;
    /**
     * This test models the situation where:
     * Both events can be done by all sensors, but there are only 3 sensors and we need 2 per event.
     * This is not doable.
     */
    Assert.assertFalse(FieldLabs.organisingEvents(n, m, z, k, t, c));
  }

  @Test()
  public void exampleTwoEventTooFewCompanies() {
    int n = 2;
    int m = 2;
    int z = 4;
    int k = 2;
    int[] t = { 0, 1, 1, 2, 2 };
    boolean[][] c = new boolean[n + 1][z + 1];
    c[1][1] = true;
    c[1][2] = true;
    c[2][3] = true;
    c[2][4] = true;
    /**
     * This test models the situation where:
     * Event 1 can be done by sensors 1 and 2.
     * Event 2 can be done by sensors 3 and 4.
     * This is not doable, as each event is done by a single company!
     */
    Assert.assertFalse(FieldLabs.organisingEvents(n, m, z, k, t, c));
  }

  @Test()
  public void exampleTwoEventDoable() {
    int n = 2;
    int m = 2;
    int z = 4;
    int k = 2;
    int[] t = { 0, 1, 2, 2, 1 };
    boolean[][] c = new boolean[n + 1][z + 1];
    c[1][1] = true;
    c[1][2] = true;
    c[2][3] = true;
    c[2][4] = true;
    /**
     * This test models the situation where:
     * Event 1 can be done by sensors 1 and 2.
     * Event 2 can be done by sensors 3 and 4.
     * This is doable, as each event can be covered by 2 sensors from two different companies.
     */
    Assert.assertTrue(FieldLabs.organisingEvents(n, m, z, k, t, c));
  }
}


```

### Solution:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class FieldLabs {

  /**
   * You should implement this method
   *
   * @param n the number of events
   * @param m the number of companies
   * @param z the total number of sensors
   * @param k the number of measurements required per event.
   * @param t the company a sensor belongs to. Sensor 1 <= j <= z belongs to company 1 <= t[j] <= m. You should ignore t[0].
   * @param c the compatibility of events and sensors. Event 1 <= i <= n can use sensor 1 <= j <= z iff c[i][j] = true. You should ignore c[0][x] and c[x][0].
   * @return true iff we can organise all events with this set of sensors.
   */
  public static boolean organisingEvents(int n, int m, int z, int k, int[] t, boolean[][] c) {
    List<Node> nodes = new ArrayList<>();
    // Create source
    Node source = new Node(-1, -m);
    nodes.add(source);
    
    // Create list of all events as nodes and add edges from source to the event nodes.
    Node[] events = new Node[n+1];
    for(int i=1; i<=n; i++) {
      events[i] = new Node(i, 0);
      // Lower bound k as every event wants at least k measurements.
      source.addEdge(events[i], k, (Integer.MAX_VALUE/2));
      nodes.add(events[i]);
    }
    
    // Create nodes for compatible sensors with the events
    Node[][] events_sensors = new Node[n+1][z+1];
    for(int i = 1; i <= n; i++) {
      for(int j = 1; j<= z; j++) {
        // check if event is compatible with the censor 
        if(c[i][j]) {
          events_sensors[i][j] = new Node(j, 0);
          nodes.add(events_sensors[i][j]);
          // Lower bound 2 as at least 2 companies should provide sensors
          events[i].addEdge(events_sensors[i][j], -2, (Integer.MAX_VALUE/2));
        }
      }
    }
    // Create nodes for the companies
    Node[] companies = new Node[m+1];
    for(int com = 1; com <= m; com++) {
      companies[com] = new Node(com, 1);
      nodes.add(companies[com]);
      
      // Create edges between the companies and the compatible sensors with the events
      for(int i = 1; i<= n; i++) {
        for(int j = 1; j<=z; j++) {
          // Check if the sensor belongs to the current company and if the sensor is available
          if(t[j] == com && events_sensors[i][j] != null) {
            events_sensors[i][j].addEdge(companies[com], 0, 1);
          }
        }
      }
    }
    
    // Create graph and call circulation on it
    Graph g = new Graph(nodes);
    return g.hasCirculation();
  }
}
```
