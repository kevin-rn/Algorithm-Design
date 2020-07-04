// Test:
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int[] b = { 0, 8, 2 };
    int[] c = { 0, 2, 4 };
    int[][] dep = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
    Set<Integer> answer = TheGreedyProjectManager.outputSelection(n, b, c, dep);
    assertEquals("Should take only project 1 as that has a net profit: Size", 1, answer.size());
    assertTrue("Should take only project 1 as that has a net profit: Project 1", answer.contains(1));
  }

  @Test(timeout = 100)
  public void example02() {
    int n = 2;
    int[] b = { 0, 8, 2 };
    int[] c = { 0, 2, 4 };
    int[][] dep = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 0, 0 } };
    Set<Integer> answer = TheGreedyProjectManager.outputSelection(n, b, c, dep);
    assertEquals("Project 1 (profit 6) now requires 2 (loss 2), so we take both", 2, answer.size());
    assertTrue("Project 1 should be included", answer.contains(1));
    assertTrue("Project 2 should be included", answer.contains(2));
  }
  
  @Test(timeout = 100)
	public void no_dep_one_good_one_bad() {
		int n = 2;
		int[] b = { 0, 8, 0};
		int[] c = { 0, 0, 4};
		int[][] dep = {
				{0 , 0, 0},
				{0 , 0, 0},
				{0 , 0, 0}
		};
		Set<Integer> answer = TheGreedyProjectManager.outputSelection(n, b, c, dep);
		assertEquals("Project 1 (profit 8) and project 2 (loss 4), so we take only 1", 1, answer.size());
		assertTrue("Project 1 should be included", answer.contains(1));
		assertFalse("Project 2 should not be included", answer.contains(2));
	}

  private static Set<Integer> parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int num = sc.nextInt();
    Set<Integer> res = new HashSet<>();
    for (int i = 0; i < num; i++) {
      res.add(sc.nextInt());
    }
    sc.close();
    return res;
  }

  private static class ProblemInstance {

    int n;

    int[] b;

    int[] c;

    int[][] dep;

    public ProblemInstance(int n, int[] b, int[] c, int[][] dep) {
      this.n = n;
      this.b = b;
      this.c = c;
      this.dep = dep;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int[] b = new int[n + 1];
    int[] c = new int[n + 1];
    int[][] dep = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      c[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        dep[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return new ProblemInstance(n, b, c, dep);
  }

	private static void runTestWithFile(String fileName) {
		ProblemInstance S = parseInputFile(fileName+".in");
		//DominatedSolutions.Solution sol = S.get(S.size()-1);
		//S.remove(S.size()-1);

		Set<Integer> theirSolution = TheGreedyProjectManager.outputSelection(S.n,S.b,S.c, S.dep);
		Set<Integer> ourSolution = parseOutputFile(fileName+".out");
		int ourCost = 0;
		int theirCost = 0;
		for (int i = 1; i <= S.n; i++) {
			if (theirSolution.contains(i)) {
				theirCost += S.b[i] - S.c[i];
				for (int j = 1; j <= S.n; j++) {
					assertTrue("Check if deps are included: " + i + "," + j, S.dep[i][j] <= 0 || theirSolution.contains(j));
				}
			}
			if (ourSolution.contains(i)) {
				ourCost += S.b[i] - S.c[i];
			}
		}
		assertEquals(ourCost,theirCost);
	}
}

// _______________________________________________________________________________________________________________________
// Solution:
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class TheGreedyProjectManager {

  /**
   *   You should implement the method below.
   *   @param n The number of projects
   *   @param benefits An array of dimension n+1 containing the benefits of all the projects for 1 <= i <= n
   *   @param costs An array of dimension n+1 containing the costs of all the projects for 1 <= i <= n
   *   @param dependencies is an array of dimension (n+1)*(n+1) that contains value 1 iff project i depends on j and 0 otherwise, for all 1 <= i,j <= n.
   *   @return the set of project ids that are selected. Note that the ids of `Node`s in the graph correspond to the ids of the projects they represent.
   */
  public static Set<Integer> outputSelection(int n, int[] benefits, int[] costs, int[][] dependencies) {
    Graph g = buildGraph(n, benefits, costs, dependencies);
    MaxFlow.maximizeFlow(g);
    {
      return bfs(g);
    }
  }

  private static Set<Integer> bfs(Graph g) {
    Queue<Node> q = new LinkedList<>();
    q.add(g.getSource());
    Set<Integer> res = new HashSet<>();
    while (!q.isEmpty()) {
      Node cur = q.poll();
      for (Edge e : cur.getEdges()) {
        if (e.getFlow() < e.getCapacity() && !res.contains(e.to.getId()) && e.to.getId() > 0) {
          q.add(e.to);
          res.add(e.to.getId());
        }
      }
    }
    return res;
  }

  private static Graph buildGraph(int n, int[] benefits, int[] costs, int[][] dependencies) {
    Node source = new Node(0);
    Node sink = new Node(n + 1);
    ArrayList<Node> nodes = new ArrayList<>(n + 2);
    nodes.add(source);
    for (int i = 1; i <= n; i++) {
      Node newNode = new Node(i);
      nodes.add(newNode);
    }
    nodes.add(sink);
    for (int i = 1; i <= n; i++) {
      source.addEdge(nodes.get(i), benefits[i]);
      nodes.get(i).addEdge(sink, costs[i]);
      for (int j = 1; j <= n; j++) {
        if (dependencies[i][j] > 0) {
          nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE / 2);
        }
      }
    }
    return new Graph(nodes, source, sink);
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2019_2020/projects/data/secret/";
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
    int[] b = new int[n + 1];
    int[] c = new int[n + 1];
    int[][] dep = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      c[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        dep[i][j] = sc.nextInt();
      }
    }
    sc.close();
    Set<Integer> ans = TheGreedyProjectManager.outputSelection(n, b, c, dep);
    StringBuilder sb = new StringBuilder();
    sb.append(ans.size());
    sb.append('\n');
    for (Integer i : ans) {
      sb.append(i);
      sb.append('\n');
    }
    return sb.toString();
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

  public void maximizeFlow() {
    MaxFlow.maximizeFlow(this);
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
