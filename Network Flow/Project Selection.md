Led by Oliver Stone the camel club has played a part in many important events over the years. Despite Oliver’s insistence on keeping a low profile, the club has recently been discovered by Sean & Michelle who have managed to convince Oliver to start taking cases with them. The camel club now features a large variety in skills among its members and thus when new cases come in, they need to closely assess who takes on what jobs.

Every member of the club has a certain set of skills and every job requires a certain set of skills. For instance Caleb is a literature expert, whereas Oliver is an investigator and literature expert. Their latest job which involved some literature research was done by Caleb as a result, whereas Oliver tackled the job before that required both investigation and literature research. Now that Sean & Michelle are involved, the camel club can finally leave their regular employment behind them and focus
full time on doing the different jobs. Every member still has other obligations however (Caleb still wants to visit the library often for instance), so every member has a limited number of hours they can work on jobs. Furthermore Sean has worked hard to ensure that every job has a clear description of how many hours a certain job will take.

To help them with the future assignment of jobs to individual camel club members, you have been asked to devise an algorithm that computes what jobs should be attributed to what person such that the camel club can do the largest number of cases. As the members of the camel club also have regular employment, they can only work on one job.

Now that every member can potentially take on multiple jobs, we ask ourselves a different question. We now ask: is it
possible for the camel club to complete all jobs?

The first line contains the amount of members,, n and the amount of jobs, m. These are positive integers >=1. Following this, there is a line for each member. Each of these lines has a single word (the name of the member), a number representing the amount of time this member has available t_i >= 0, followed by a positive integer s_i followed by that number of single words (the names of the skills the member has). After that there is a line for each job. Again each of these lines begin with a single word description of the job, a number indicating how much time is required for this job p_i >= 0, and then have a number q_j >= 0 followed by that number of single words (the names of the skills the jobs requires).

An example is input:

2 3
Oliver 5 2 investigation interviewing
Caleb 8 2 interviewing lit
hire_member 1 1 interviewing
interview_author 3 2 interviewing lit
solve_crime 4 1 investigation

Which should output:
true

In the library a full implementation for the following classes is given:
```java
class Graph { 
    public Graph(Collection<Node> nodes, Node source, Node sink);
    public Node getSink();
    public Node getSource();
    public void maximizeFlow(); // Executes the Ford-Fulkerson algorithm to maximize the flow
}

class Node {
    public Node(String id);
    public void addEdge(Node to, int capacity);
    public Collection<Edge> getEdges();
    public String getId();
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

  public boolean solve(InputStream in) {
  // TODO
  }
}
```


### Library:
```java
import java.util.*;

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

  static void maximizeFlow(Graph g) {
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

  public Collection<Node> getNodes() {
    return nodes;
  }

  public void maximizeFlow() {
    MaxFlow.maximizeFlow(this);
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

  protected String id;

  protected Collection<Edge> edges;

  public Node(String id) {
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

  public String getId() {
    return id;
  }

  public boolean equals(Object other) {
    if (other instanceof Node) {
      Node that = (Node) other;
      if (id.equals(that.getId()))
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
      return this.capacity == that.capacity && this.flow == that.flow && this.from.getId().equals(that.getFrom().getId()) && this.to.getId().equals(that.getTo().getId());
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

  long time = 0;

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

  private static void runTestWithFile(String fileName) {
    assertEquals(Boolean.parseBoolean(WebLab.getData(fileName + ".out").trim()), new Solution().solve(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8))));
  }

  @Test(timeout = 100)
  public void example01() {
    runTestWithFile("example");
  }

  @Test(timeout = 250)
  public void test01() {
    runTestWithFile("1");
  }

  @Test(timeout = 100)
  public void test02() {
    runTestWithFile("2");
  }

  @Test(timeout = 500)
  public void test03() {
    runTestWithFile("3");
  }

  @Test(timeout = 1000)
  public void test04() {
    runTestWithFile("4");
  }

  @Test(timeout = 250)
  public void test5() {
    runTestWithFile("5");
  }

  @Test(timeout = 250)
  public void test06() {
    runTestWithFile("6");
  }

  @Test(timeout = 250)
  public void test07() {
    runTestWithFile("7");
  }

  @Test(timeout = 250)
  public void test08() {
    runTestWithFile("8");
  }

  @Test(timeout = 250)
  public void test09() {
    runTestWithFile("9");
  }

  @Test(timeout = 7000)
  public void test_500_500_big_one() {
    runTestWithFile("the_big_one");
  }

  @Test(timeout = 250)
  public void test_one_member_one_skill() {
    runTestWithFile("one_member");
  }

  @Test(timeout = 250)
  public void test_one_member_can_do_all() {
    runTestWithFile("one_member_can_do_all");
  }

  @Test(timeout = 250)
  public void test_one_job_one_skill() {
    runTestWithFile("one_job");
  }

  @Test(timeout = 250)
  public void test_one_job_many_skills() {
    runTestWithFile("one_job_many_skills");
  }

  @Test(timeout = 250)
  public void test_one_member_many_skills() {
    runTestWithFile("one_member_many_skills");
  }
}
```

______________________________________________________________________________________________________________


### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  final int PARSING_SCANNER = 0;

  final int PARSING_BUFFEREDREADER = 1;

  int numMembers;

  int numJobs;

  int[] memberTimes;

  int[] jobTimes;

  Set[] memberSkills;

  Set[] jobSkills;

  public boolean solve(InputStream in) {
    // Used for testing scanner vs bufferedReader
    int parseMethod = PARSING_SCANNER;
    try {
      return solve(in, parseMethod);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean solve(InputStream in, int parseMethod) throws IOException {
    parse(in, parseMethod);
    Graph g = buildGraph();
    return solveLoop(g);
  }

  private void parse(InputStream in, int parseMethod) throws IOException {
    switch(parseMethod) {
      case PARSING_SCANNER:
        Scanner sc = new Scanner(in);
        parse(sc);
        break;
      case PARSING_BUFFEREDREADER:
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        parse(bf);
        break;
    }
  }

  public void parse(Scanner sc) {
    this.numMembers = sc.nextInt();
    this.numJobs = sc.nextInt();
    this.memberSkills = new Set[this.numMembers];
    this.memberTimes = new int[this.numMembers];
    // Parse each member
    for (int member = 0; member < numMembers; member++) {
      this.memberSkills[member] = new HashSet();
      sc.next();
      this.memberTimes[member] = sc.nextInt();
      int numSkills = sc.nextInt();
      // Parse the skills for this member
      for (int skill = 0; skill < numSkills; skill++) {
        this.memberSkills[member].add(sc.next());
      }
    }
    this.jobSkills = new Set[this.numJobs];
    this.jobTimes = new int[this.numJobs];
    // Parse all jobs
    for (int job = 0; job < numJobs; job++) {
      this.jobSkills[job] = new HashSet();
      sc.next();
      this.jobTimes[job] = sc.nextInt();
      int numSkills = sc.nextInt();
      // Parse all skills for this job
      for (int skill = 0; skill < numSkills; skill++) {
        this.jobSkills[job].add(sc.next());
      }
    }
  }

  public void parse(BufferedReader bf) throws IOException {
    String line = bf.readLine();
    String[] split = line.split(" ");
    this.numMembers = Integer.parseInt(split[0]);
    this.numJobs = Integer.parseInt(split[1]);
    // Parse each member
    this.memberSkills = new Set[this.numMembers];
    for (int member = 0; member < numMembers; member++) {
      this.memberSkills[member] = new HashSet();
      split = bf.readLine().split(" ");
      this.memberTimes[member] = Integer.parseInt(split[1]);
      // Parse the skills for this member
      int numSkills = Integer.parseInt(split[2]);
      for (int skill = 0; skill < numSkills; skill++) {
        this.memberSkills[member].add(split[skill + 3]);
      }
    }
    // Parse all jobs
    this.jobSkills = new Set[this.numJobs];
    for (int job = 0; job < numJobs; job++) {
      this.jobSkills[job] = new HashSet();
      split = bf.readLine().split(" ");
      this.jobTimes[job] = Integer.parseInt(split[1]);
      int numSkills = Integer.parseInt(split[2]);
      // Parse all skills for this job
      for (int skill = 0; skill < numSkills; skill++) {
        this.jobSkills[job].add(split[skill + 3]);
      }
    }
  }

  private Graph buildGraph() {
    Node source = new Node("s");
    Node sink = new Node("t");
    // Create nodes for each member and connect to source with the max time as capacity
    Node[] memberNodes = new Node[this.numMembers];
    for (int m = 0; m < this.numMembers; m++) {
      memberNodes[m] = new Node("m" + m);
      source.addEdge(memberNodes[m], memberTimes[m]);
    }
    // Create nodes for each job and connect to sink with the time per job
    Node[] jobNodes = new Node[this.numJobs];
    for (int j = 0; j < this.numJobs; j++) {
      jobNodes[j] = new Node("j" + j);
      jobNodes[j].addEdge(sink, jobTimes[j]);
    }
    // Connect each member to each compatible job with a very large capacity
    for (int i = 0; i < this.numMembers; i++) {
      for (int j = 0; j < this.numJobs; j++) {
        if (compatible(this.memberSkills[i], this.jobSkills[j])) {
          memberNodes[i].addEdge(jobNodes[j], Integer.MAX_VALUE / 10);
        }
      }
    }
    // Collect all nodes for the graph and build the graph
    Collection<Node> nodes = new ArrayList<>(2 + this.numMembers + this.numJobs);
    nodes.add(source);
    nodes.add(sink);
    for (int i = 0; i < this.numMembers; i++) {
      nodes.add(memberNodes[i]);
    }
    for (int i = 0; i < this.numJobs; i++) {
      nodes.add(jobNodes[i]);
    }
    Graph g = new Graph(nodes, source, sink);
    return g;
  }

  private // Returns true when the member and job are compatible, false otherwise.
  boolean compatible(Set memberSkill, Set jobSkill) {
    for (Object skill : jobSkill) {
      if (!memberSkill.contains(skill)) {
        return false;
      }
    }
    return true;
  }

  public boolean solveLoop(Graph g) {
    g.maximizeFlow();
    // Compute flow
    int flow = 0;
    for (Edge e : g.getSource().getEdges()) {
      flow += e.getFlow();
    }
    // Compute total job times
    int sumTimes = 0;
    for (int i = 0; i < this.numJobs; i++) {
      sumTimes += this.jobTimes[i];
    }
    // Total flow should be equal to total job times, otherwise not all jobs are completed
    return flow == sumTimes;
  }
}
```

### Solution:
```java

```
