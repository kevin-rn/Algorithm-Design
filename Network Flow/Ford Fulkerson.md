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

```

___________________________________________________________________________________________________________________

### Official Solution:
```java

```

### Solution:
```java

```
