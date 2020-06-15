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

### Test:
```java

```


_______________________________________________________________________________________________________

### Solution:
```java


```
