Foodtrucks used to be a booming business, at least before lockdown. But with lockdown going on, some lecturers have decided that perhaps preparing food every day is at least more fun than preparing all of these exam exercises day in day out. So after lockdown is over, perhaps you can visit “Stefan’s Salad Shop”, “Mathijs’s Macaroni Mall” or “Emir’s Enchilladas Emporium”. Some lecturers may even want to make more than one type of food, or work together on a concept. For example “Stefan and Mathijs’s Sandwiches and Muffins”.
Despite leaning their names to their brands, the lecturers have too little money to buy one food truck each. As a result they all have to share one foodtruck where they can change the brand.

You are given n
lecturers and t days that the foodtruck needs to be staffed. Furthermore we are making plans for a total of m

different brands. Allocate lectures to the days under the following conditions:

    Each lecturer is only willing to work on the same brand for at most 4 days.
    Each lecturer i is only willing to work at most fi days in total.
Each lecturer wants to work only on a subset of brands Di.
Finally market research has shown that some foods only sell well on some days. Mj
tells you which days a brand j can be used on.

Implement the function servingFoodAndChangingLives() to return true iff there is a way to staff the foodtruck with a lecturer on each of the t

days meeting these conditions.

For this assignment you are advised to use the code made available on WebLab for this assignment specifically.

The Graph, Node, Edge and MaxFlow classes available to you are based on the extended network flow library created in the last tutorial of the course.
You are free to make modifications to these classes, though our reference solutions to this assignment do not modify them in any way.

Functions you may require include:

In the Node class:

      public Node(int id, int d) to construct a new Node object with id id and a demand of d. Please note that a supply is represented by a negative demand.
      public void addEdge(Node to, int lower, int upper) to construct a new edge between this and to with lower bound lower and upper bound upper.
      
In the Graph class:

        public Graph(List<Node> nodes, Node source, Node sink) to construct a new graph with the nodes nodes, a source source, and a sink sink.
        public Graph(List<Node> nodes) to construct a new graph with the nodes nodes. (Use this one if you want hasCirculation() to create the source and sink for you.)
        public boolean hasCirculation() to determine if the graph has a circulation of the value D
        where D is the sum of the demands in the graph. This method will throw an IllegalArgumentException if the amount ofsupply in the network is not exactly equal to the amount of demand.

In the MaxFlow class:

    public static int maximizeFlow(Graph g) to determine the maximum flow of the graph g. Note that this ignores lower bounds and supplies and demands (see hasCirculation() for using those).

```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class NoMoreExams {

  /**
   * You should implement this method
   *
   * @param n the number of lecturers
   * @param t the number of days
   * @param m the number of brands
   * @param f the number of days a lecturer is willing to work from index _1_ to _n_. You should ignore p[0].
   * @param D the set of brands a lecturer is able to handle from index _1_ to _n_. You should ignore D[0].
   * @param M the set of days on which a brand can sell from index _1_ to _m_. You should ignore M[0].
   * @return true iff CH can deliver all the books, remembering that CH members can do at most 3 deliveries per day.
   */
  public static boolean servingFoodAndChangingLives(int n, int t, int m, int[] f, Set<Integer>[] D, Set<Integer>[] M) {
    return solveProper(n, t, m, f, D, M);
  }

  private static boolean solveProper(int n, int t, int m, int[] f, Set<Integer>[] D, Set<Integer>[] M) {
    List<Node> nodes = new ArrayList<>();
    // Alternative is to not use a supply, but to check later that the max flow = t*m
    Node s = new Node(-1, -t);
    nodes.add(s);
    Node[] lecturers = new Node[n + 1];
    for (int i = 1; i <= n; i++) {
      lecturers[i] = new Node(i, 0);
      s.addEdge(lecturers[i], 0, f[i]);
      nodes.add(lecturers[i]);
    }
    Node[][] lecturer_brand = new Node[n + 1][m + 1];
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (D[i].contains(j)) {
          lecturer_brand[i][j] = new Node(j, 0);
          nodes.add(lecturer_brand[i][j]);
          // At most 4 days per brand!
          lecturers[i].addEdge(lecturer_brand[i][j], 0, 4);
        }
      }
    }
    Node[] days = new Node[t + 1];
    for (int z = 1; z <= t; z++) {
      // Alternative is to not use a demand, but to check later that the max flow = m
      days[z] = new Node(z, 1);
      nodes.add(days[z]);
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          if (M[j].contains(z) && lecturer_brand[i][j] != null) {
            lecturer_brand[i][j].addEdge(days[z], 0, 1);
          }
        }
      }
    }
    Graph g = new Graph(nodes);
    return g.hasCirculation();
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2020_2021/nf/implementation/chefs/data/secret/";
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
    int t = sc.nextInt();
    int m = sc.nextInt();
    int[] a = new int[n + 1];
    Set<Integer>[] D = new Set[n + 1];
    Set<Integer>[] M = new Set[m + 1];
    for (int i = 1; i <= n; i++) {
      a[i] = sc.nextInt();
    }
    for (int j = 1; j <= n; j++) {
      D[j] = new HashSet<>();
      int num = sc.nextInt();
      while (num-- > 0) {
        D[j].add(sc.nextInt());
      }
    }
    for (int i = 1; i <= m; i++) {
      M[i] = new HashSet<>();
      int num = sc.nextInt();
      while (num-- > 0) {
        M[i].add(sc.nextInt());
      }
    }
    sc.close();
    boolean ans = servingFoodAndChangingLives(n, t, m, a, D, M);
    return Boolean.toString(ans);
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
