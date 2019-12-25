### We'll do it wrong just once (1/9)

Let G be an undirected graph with n nodes. A subset of the nodes is called an independent set if no
two of them are joined by an edge. Finding an independent set of maximum size (or weight) is generally difficult,
but it can be done efficiently in special cases.

Call a graph G a path if its nodes can be written as v1,v2,…,vn, with an edge between vi and vj if
and only if |i−j|=1. Each node vi has a positive integer weight wi.

Given the following problem instance, with a path of nodes with weights:

2 1 6 8 9

We expect 17 as our output.

We have already provided a skeleton implementation, it is up to you to complete it.

### Template:
```java
class Solution {

  /*
	 * Note that entry node[0] should be avoided, as nodes are labelled node[1] through node[n].
	 */
  public static int weight(int n, int[] nodes) {
    int[] mem = new int[n + 1];
    mem[0] = 0;
    mem[1] = nodes[1];
    for (int i = 2; i <= n; i++) {
      // TODO

    }
    return mem[n];
  }
}
```

### Test:
```java
import static org.junit.Assert.*;
import java.util.Scanner;
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

  private static void runTestWithFile(String fileName) {
    ProblemInstance x = parseInput(WebLab.getData(fileName + ".in"));
    int expected = Integer.parseInt(WebLab.getData(fileName + ".out").trim());
    assertEquals(expected, Solution.weight(x.graph.length - 1, x.graph));
  }

  private static ProblemInstance parseInput(String in) {
    // Reading the input through the use of a Scanner.
    Scanner sc = new Scanner(in);
    // Read the amount of nodes.
    int n = sc.nextInt();
    int[] graph = new int[n + 1];
    // Read the weight of every node.
    for (int i = 1; i <= n; i++) {
      graph[i] = sc.nextInt();
    }
    // Close the scanner.
    sc.close();
    return new ProblemInstance(graph);
  }

  private static class ProblemInstance {

    int[] graph;

    public ProblemInstance(int[] graph) {
      this.graph = graph;
    }
  }

  @Test(timeout = 100)
  public void example() {
    int n = 5;
    int[] nodes = { 0, 2, 1, 6, 8, 9 };
    assertEquals(17, Solution.weight(n, nodes));
  }

  @Test(timeout = 100)
  public void singleInput() {
    runTestWithFile("test1");
  }

  @Test(timeout = 100)
  public void doubleInput1() {
    runTestWithFile("test2");
  }

  @Test(timeout = 100)
  public void doubleInput2() {
    runTestWithFile("test3");
  }

  @Test(timeout = 100)
  public void tripleInput1() {
    runTestWithFile("test4");
  }

  @Test(timeout = 100)
  public void tripleInput2() {
    runTestWithFile("test5");
  }

  @Test(timeout = 800)
  public void twoThousandNodes() {
    runTestWithFile("test6");
  }

  @Test(timeout = 800)
  public void fiveThousandNodes() {
    runTestWithFile("test7");
  }
}

```


### Solution:
```java
package weblab;

class Solution {

  /*
	 * Note that entry node[0] should be avoided, as nodes are labelled node[1] through node[n].
	 */
  public static int weight(int n, int[] nodes) {
    int[] mem = new int[n + 1];
    mem[0] = 0;
    mem[1] = nodes[1];
    for (int i = 2; i <= n; i++) {
      {
        // Find which node would get you a higher total weight for the current node.
        int result = Integer.max(mem[i - 1], mem[i - 2] + nodes[i]);
        mem[i] = result;
      }
    }
    return mem[n];
  }
}

```
