// Test:
import static org.junit.Assert.*;
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

  private static class ProblemInstance {

    int n, m, g;

    Node[] V;

    Set<Edge> E;

    public ProblemInstance(int n, int m, int g, Node[] v, Set<Edge> e) {
      this.n = n;
      this.m = m;
      this.g = g;
      this.V = v;
      this.E = e;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int m = sc.nextInt();
    int g = sc.nextInt();
    Node[] V = new Node[n + 1];
    Set<Edge> E = new HashSet<>();
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    for (int i = 0; i < m; i++) {
      E.add(new Edge(V[sc.nextInt()], V[sc.nextInt()], sc.nextInt()));
    }
    sc.close();
    return new ProblemInstance(n, m, g, V, E);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    StringBuilder sb = new StringBuilder();
    sb.append(TheQuestForTheHolyGrail.solve(S.n, S.m, S.g, S.V, S.E));
    assertEquals(WebLab.getData(fileName + ".out").trim(), sb.toString().trim());
  }

  @Test(timeout = 100)
  public void example_one_edge() {
    int n = 2;
    int m = 1;
    int g = 2;
    Node[] V = new Node[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    Set<Edge> E = new HashSet<>();
    E.add(new Edge(V[1], V[2], 8));
    /* Only one edge to go, so might as well use the team!
    * Costs are therefore 8/2 = 4.
    */
    assertEquals(4, TheQuestForTheHolyGrail.solve(n, m, g, V, E), 1e-4);
  }
  
  @Test(timeout = 100)
  public void path_length_n() {
    int n = 200;
    int m = 1;
    int g = 200;
    Node[] V = new Node[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    Set<Edge> E = new HashSet<>();
    for (int i = 1; i < n; i++) {
      E.add(new Edge(V[i], V[i+1], 1)); 
    }
    
    /* Only one path, so need to go through n times, we can use the cost once.
    */
    assertEquals(n-1 - 0.5, TheQuestForTheHolyGrail.solve(n, m, g, V, E), 1e-4);
  }
  

  @Test(timeout = 100)
  public void example_two_edges() {
    int n = 3;
    int m = 2;
    int g = 3;
    Node[] V = new Node[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    Set<Edge> E = new HashSet<>();
    E.add(new Edge(V[1], V[2], 8));
    E.add(new Edge(V[2], V[3], 3));
    /* Only two edge to go, so use it on the most expensive one.
     * Costs are therefore 8/2 + 3 = 7.
     */
    assertEquals(7, TheQuestForTheHolyGrail.solve(n, m, g, V, E), 1e-4);
  }

  @Test(timeout = 100)
  public void example_two_paths() {
    int n = 4;
    int m = 4;
    int g = 3;
    Node[] V = new Node[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    Set<Edge> E = new HashSet<>();
    E.add(new Edge(V[1], V[2], 8));
    E.add(new Edge(V[2], V[3], 3));
    E.add(new Edge(V[1], V[4], 3));
    E.add(new Edge(V[4], V[3], 6));
    /* Two paths to chose from, best option is 3 + 6/3
     */
    assertEquals(6, TheQuestForTheHolyGrail.solve(n, m, g, V, E), 1e-4);
  }

  @Test(timeout = 100)
  public void example_dijkstra_wouldnt_work() {
    int n = 4;
    int m = 4;
    int g = 3;
    Node[] V = new Node[n + 1];
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    Set<Edge> E = new HashSet<>();
    E.add(new Edge(V[1], V[2], 5));
    E.add(new Edge(V[2], V[3], 0));
    E.add(new Edge(V[1], V[4], 2));
    E.add(new Edge(V[4], V[3], 2));
    /* Two paths to chose from:
     * One is 5 + 0, with the bonus: 2.5 + 0 = 2.5
     * One is 2 + 2, with the bonus: 2 + 1 = 3
     */
    assertEquals(2.5, TheQuestForTheHolyGrail.solve(n, m, g, V, E), 1e-4);
  }
}

// _______________________________________________________________________________________________________________________
// Solution:
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class TheQuestForTheHolyGrail {

  public static /**
   *  You should implement this method.
   *  @param n the number of nodes.
   *  @param m the number of edges.
   *  @param g the index of the holy grail in V.
   *  @param V a list of Nodes, where V[1] is the current state and V[g] is the holy grail. You should not use V[0].
   *  @param E a set of Edges
   *  @return The size of the largest set of base pairs.
   */
  double solve(int n, int m, int g, Node[] V, Set<Edge> E) {
    return solveProper(n, m, g, V, E);
  }

  public static double solveProper(int n, int m, int g, Node[] V, Set<Edge> E) {
    double[][] mem = new double[n + 1][2];
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j < 2; j++) {
        mem[i][j] = Integer.MAX_VALUE;
      }
    }
    mem[1][1] = mem[1][0] = 0;
    for (int counter = 1; counter <= n; counter++) {
      for (Edge e : E) {
        mem[e.to.getId()][0] = Math.min(mem[e.to.getId()][0], e.cost + mem[e.from.getId()][0]);
        mem[e.to.getId()][1] = Math.min(mem[e.to.getId()][1], Math.min(mem[e.from.getId()][0] + e.cost * 0.5, mem[e.from.getId()][1] + e.cost));
      }
    }
    if (mem[g][1] == Integer.MAX_VALUE) {
      return -1;
    }
    return mem[g][1];
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2019_2020/shortest_path/data/secret";
    File dir = new File(dirName);
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
    int m = sc.nextInt();
    int g = sc.nextInt();
    Node[] V = new Node[n + 1];
    Set<Edge> E = new HashSet<>();
    for (int i = 1; i <= n; i++) {
      V[i] = new Node(i);
    }
    for (int i = 0; i < m; i++) {
      E.add(new Edge(V[sc.nextInt()], V[sc.nextInt()], sc.nextInt()));
    }
    sc.close();
    return Double.toString(TheQuestForTheHolyGrail.solve(n, m, g, V, E));
  }
}

class Node {

  protected int id;

  /**
   *  Create a new node
   *  @param id: Id for the node.
   */
  public Node(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public boolean equals(Object other) {
    if (other instanceof Node) {
      Node that = (Node) other;
      return this.id == that.id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

class Edge {

  protected int cost;

  protected Node from;

  protected Node to;

  protected Edge(Node from, Node to, int cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }

  public Node getFrom() {
    return from;
  }

  public Node getTo() {
    return to;
  }

  public int getCost() {
    return cost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Edge edge = (Edge) o;
    return cost == edge.cost && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cost, from, to);
  }
}
