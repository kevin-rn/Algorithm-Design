### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class AuctionTime {

  /**
   *   You should implement this method.
   *   @param n the number of items.
   *   @param V the maximum value you can put up for auction.
   *   @param v an array of size n+1, containing the values v_1 through v_n. You should ignore v[0].
   *   @param M The memory array of dimension (n+1)*(V+1) filled based on the recursive formula.
   *   @return A list of all indices of the items that should be put up for auction, in _decreasing_ order.
   */
  public static List<Integer> solve(int n, int V, int[] v, int[][] M) {
  // TODO
  }
}


```

### Test:
```java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.*;
import java.util.Scanner;
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
    int V = 5;
    int[] v = { 0, 3, 4, 2 };
    int[][] M = { { 0, 1, 2, 3, 4, 5 }, { 0, 1, 2, 0, 1, 2 }, { 0, 1, 2, 0, 0, 2 }, { 0, 1, 0, 0, 0, 0 } };
    List<Integer> chosenElements = new LinkedList<>();
    chosenElements.add(3);
    chosenElements.add(1);
    assertEquals("Should pick the first and last item", chosenElements, AuctionTime.solve(n, V, v, M));
  }

  @Test(timeout = 100)
  public void noItemFits() {
    int n = 3;
    int V = 5;
    int[] v = { 0, 8, 8, 8 };
    int[][] M = { { 0, 1, 2, 3, 4, 5 }, { 0, 1, 2, 3, 4, 5 }, { 0, 1, 2, 3, 4, 5 }, { 0, 1, 2, 3, 4, 5 } };
    List<Integer> chosenElements = new LinkedList<>();
    assertEquals("Should pick no items", chosenElements, AuctionTime.solve(n, V, v, M));
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

    int[] c;

    public ProblemInstance(int n, int C, int[][] M, int[] c) {
      this.n = n;
      this.C = C;
      this.M = M;
      this.c = c;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int C = sc.nextInt();
    int[][] M = new int[n + 1][C + 1];
    int[] b = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= C; j++) {
        M[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return new ProblemInstance(n, C, M, b);
  }

  private static void runTestWithFile(String fileName, boolean checkOrder) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    // DominatedSolutions.Solution sol = S.get(S.size()-1);
    // S.remove(S.size()-1);
    List<Integer> theirSolution = AuctionTime.solve(S.n, S.C, S.c, S.M);
    List<Integer> ourSolution = parseOutputFile(fileName + ".out");
    int theirCost = theirSolution.stream().mapToInt(i -> S.c[i]).sum();
    assertTrue("Fits in capacity", theirCost <= S.C);
    int theirBenefit = theirSolution.stream().mapToInt(i -> S.c[i]).sum();
    int ourBenefit = ourSolution.stream().mapToInt(i -> S.c[i]).sum();
    assertEquals("Achieved maxima are equal.", ourBenefit, theirBenefit);
    if (checkOrder) {
      Iterator<Integer> listIt = theirSolution.iterator();
      if (!listIt.hasNext()) {
        return;
      }
      int item = listIt.next();
      while (listIt.hasNext()) {
        int next = listIt.next();
        assertTrue("Should be sorted", next <= item);
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
class AuctionTime {

  /**
   *   You should implement this method.
   *   @param n the number of items.
   *   @param V the maximum value you can put up for auction.
   *   @param v an array of size n+1, containing the values v_1 through v_n. You should ignore v[0].
   *   @param M The memory array of dimension (n+1)*(V+1) filled based on the recursive formula.
   *   @return A list of all indices of the items that should be put up for auction, in _decreasing_ order.
   */
  public static List<Integer> solve(int n, int V, int[] v, int[][] M) {
    // return solveLinearRecursive(n,V,v,M);
    return solveIterative(n, V, v, M);
  }

  private static List<Integer> solveIterative(int n, int V, int[] v, int[][] M) {
    LinkedList<Integer> output = new LinkedList<>();
    int i = n;
    int val = V;
    while (i > 0) {
      if (v[i] <= val && (M[i - 1][val] > (M[i - 1][val - v[i]]))) {
        output.add(i);
        val -= v[i];
        i--;
      } else {
        i--;
      }
    }
    return output;
  }

  public static List<Integer> solveLinearRecursive(int n, int V, int[] v, int[][] M) {
    LinkedList<Integer> output = new LinkedList<>();
    rec(output, M, v, n, V);
    return output;
  }

  public static List<Integer> solveLinearithmic(int n, int V, int[] v, int[][] M) {
    ArrayList<Integer> output = new ArrayList<>(n);
    int i = n;
    int val = V;
    while (i > 0) {
      if (v[i] > val || M[i - 1][val - v[i]] > M[i - 1][val]) {
        i--;
      } else {
        output.add(i);
        val -= v[i];
        i--;
      }
    }
    Collections.sort(output, Collections.reverseOrder());
    return output;
  }

  private static void rec(LinkedList<Integer> output, int[][] M, int[] v, int i, int c) {
    if (i > 0) {
      if (v[i] > c || M[i - 1][c] < M[i - 1][c - v[i]]) {
        rec(output, M, v, i - 1, c);
        return;
      } else {
        output.add(i);
        rec(output, M, v, i - 1, c - v[i]);
      }
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/resit_2_2018_2019/dp_solution/data/";
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
    int[][] mem = new int[n + 1][C + 1];
    for (int i = 1; i <= n; i++) {
      b[i] = sc.nextInt();
    }
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= C; j++) {
        mem[i][j] = sc.nextInt();
      }
    }
    sc.close();
    StringBuilder sb = new StringBuilder();
    List<Integer> sol = AuctionTime.solve(n, C, b, mem);
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
