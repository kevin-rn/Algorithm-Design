### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class FindSolution {

  /**
   *   You should implement this method.
   *   @param n The number of updates
   *   @param C the (monthly) cost budget
   *   @param benefits An array of dimension n+1 containing the benefits of all the code changes for 1 <= i <= n
   *   @param costs An array of dimension n+1 containing the costs of all the code changes for 1 <= i <= n
   *   @param M The memory array of dimension (n+1)*(C+1) filled based on the recursive formula.
   *   @return A list of all indices of the updates that should be included, in _increasing_ order.
   */
  public static List<Integer> solve(int n, int C, int[] benefits, int[] costs, int[][] M) {
  // TODO
  }
}
```

### Test:
```java
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.*;
import org.junit.*;
import org.junit.rules.TestName;

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
    int n = 3;
    int C = 5;
    int[] b = { 0, 8, 7, 2 };
    int[] c = { 0, 3, 3, 2 };
    int[][] M = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 8, 8, 8 }, { 0, 0, 0, 8, 8, 8 }, { 0, 0, 2, 8, 8, 10 } };
    List<Integer> chosenElements = new LinkedList<>();
    chosenElements.add(1);
    chosenElements.add(3);
    assertEquals("Should pick the first and last update", chosenElements, FindSolution.solve(n, C, b, c, M));
  }

  @Test(timeout = 100)
  public void noItemFits() {
    int n = 3;
    int C = 5;
    int[] b = { 0, 8, 7, 2 };
    int[] c = { 0, 8, 8, 8 };
    int[][] M = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };
    List<Integer> chosenElements = new LinkedList<>();
    assertEquals("Should pick no items", chosenElements, FindSolution.solve(n, C, b, c, M));
  }

  private static List<Integer> parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    List<Integer> output = new LinkedList<>();
    int size = sc.nextInt();
    while (size-- > 0) {
      ((LinkedList<Integer>) output).addLast(sc.nextInt());
    }
    sc.close();
    return output;
  }

  private static class ProblemInstance {

    int n, C;

    int[][] M;

    int[] b;

    int[] c;

    public ProblemInstance(int n, int C, int[][] M, int[] b, int[] c) {
      this.n = n;
      this.C = C;
      this.M = M;
      this.b = b;
      this.c = c;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int C = sc.nextInt();
    int[][] M = new int[n + 1][C + 1];
    int[] b = new int[n + 1];
    int[] c = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      c[i] = sc.nextInt();
    }
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= C; j++) {
        M[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return new ProblemInstance(n, C, M, b, c);
  }

  private static void runTestWithFile(String fileName, boolean checkOrder) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    // DominatedSolutions.Solution sol = S.get(S.size()-1);
    // S.remove(S.size()-1);
    List<Integer> theirSolution = FindSolution.solve(S.n, S.C, S.b, S.c, S.M);
    List<Integer> ourSolution = parseOutputFile(fileName + ".out");
    int theirCost = theirSolution.stream().mapToInt(i -> S.c[i]).sum();
    assertTrue("Fits in capacity", theirCost <= S.C);
    int theirBenefit = theirSolution.stream().mapToInt(i -> S.b[i]).sum();
    int ourBenefit = ourSolution.stream().mapToInt(i -> S.b[i]).sum();
    assertEquals("Benefits are equal.", ourBenefit, theirBenefit);
    if (checkOrder) {
      Iterator<Integer> listIt = theirSolution.iterator();
      if (!listIt.hasNext()) {
        return;
      }
      int item = listIt.next();
      while (listIt.hasNext()) {
        int next = listIt.next();
        assertTrue("Should be sorted", next >= item);
        item = next;
      }
    }
  }
}
```

_______________________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class FindSolution {

  /**
   *   You should implement this method.
   *   @param n The number of updates
   *   @param C the (monthly) cost budget
   *   @param benefits An array of dimension n+1 containing the benefits of all the code changes for 1 <= i <= n
   *   @param costs An array of dimension n+1 containing the costs of all the code changes for 1 <= i <= n
   *   @param M The memory array of dimension (n+1)*(C+1) filled based on the recursive formula.
   *   @return A list of all indices of the updates that should be included, in _increasing_ order.
   */
  public static List<Integer> solve(int n, int C, int[] benefits, int[] costs, int[][] M) {
    // return solveYorick(n,C,benefits,costs,M);
    return solveIterative(n, C, benefits, costs, M);
  }

  private static List<Integer> solveYorick(int n, int c, int[] benefits, int[] costs, int[][] M) {
    LinkedList<Integer> output = new LinkedList<>();
    int i = n;
    while (i > 0) {
      if (costs[i] <= c && (M[i][c] == (benefits[i] + M[i - 1][c - costs[i]]))) {
        output.add(i);
        c -= costs[i];
        i--;
      } else {
        i--;
      }
    }
    LinkedList<Integer> real_output = new LinkedList<>();
    while (!output.isEmpty()) {
      real_output.addFirst(output.removeFirst());
    }
    return real_output;
  }

  public static List<Integer> solveLinear(int n, int C, int[] b, int[] c, int[][] M) {
    LinkedList<Integer> output = new LinkedList<>();
    rec(output, M, c, n, C);
    return output;
  }

  public static List<Integer> solveLinearithmic(int n, int C, int[] b, int[] costs, int[][] M) {
    ArrayList<Integer> output = new ArrayList<>(n);
    int i = n;
    int c = C;
    while (i > 0) {
      if (M[i][c] == M[i - 1][c]) {
        i--;
      } else {
        output.add(i);
        c -= costs[i];
        i--;
      }
    }
    Collections.sort(output);
    return output;
  }

  public static List<Integer> solveIterative(int n, int C, int[] b, int[] costs, int[][] M) {
    LinkedList<Integer> output = new LinkedList<>();
    int i = n;
    int c = C;
    while (i > 0) {
      if (M[i][c] == M[i - 1][c]) {
        i--;
      } else {
        output.addFirst(i);
        c -= costs[i];
        i--;
      }
    }
    return output;
  }

  private static void rec(LinkedList<Integer> output, int[][] M, int[] costs, int i, int c) {
    if (i > 0) {
      if (M[i][c] == M[i - 1][c]) {
        rec(output, M, costs, i - 1, c);
        return;
      } else {
        rec(output, M, costs, i - 1, c - costs[i]);
        output.add(i);
      }
    }
  }

  private static void rec(ArrayList<Integer> output, int[][] M, int[] costs, int i, int c) {
    if (i > 0) {
      if (M[i][c] == M[i - 1][c]) {
        rec(output, M, costs, i - 1, c);
        return;
      } else {
        rec(output, M, costs, i - 1, c - costs[i]);
        output.add(i);
      }
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2018_2019/dp_solution/data/";
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
    int C = sc.nextInt();
    int[] b = new int[n + 1];
    int[] c = new int[n + 1];
    int[][] mem = new int[n + 1][C + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      c[i] = sc.nextInt();
    }
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= C; j++) {
        mem[i][j] = sc.nextInt();
      }
    }
    sc.close();
    StringBuilder sb = new StringBuilder();
    List<Integer> sol = FindSolution.solve(n, C, b, c, mem);
    sb.append(sol.size());
    sb.append('\n');
    for (Integer i : sol) {
      sb.append(i);
      sb.append(' ');
    }
    return sb.toString();
  }
}
```
