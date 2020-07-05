### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class DynamicProgramming {

  /**
   *   You should implement this method. Note that your solution should be _iterative_!
   *   @param n The number of updates to ship.
   *   @param c The cost of shipping one bundle of updates.
   *   @param costs The costs matrix of dimension (n+1)*(n+1), where costs[i][j] denotes the costs of bundling updates i,i+1,...,j; given for all 1 <= i <= j <= n
   *   @return The minimal costs of bundling.
   */
  public static int solve(int n, int c, int[][] costs) {
  // TODO
  }
}
```

### Tests:
```java
import static org.junit.Assert.assertEquals;
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

  @Test(timeout = 100)
  public void example() {
    int m = 4;
    int c = 8;
    int[][] R = { { 0, 0, 0, 0, 0 }, { 0, 0, 2, 3, 4 }, { 0, 0, 0, 2, 3 }, { 0, 0, 0, 0, 2 }, { 0, 0, 0, 0, 0 } };
    assertEquals("Right answer is to bundle all updates", 12, DynamicProgramming.solve(m, c, R));
  }

  @Test(timeout = 100)
  public void example02() {
    int m = 4;
    int c = 8;
    int[][] R = { { 0, 0, 0, 0, 0 }, { 0, 0, 2, 100, 100 }, { 0, 0, 0, 2, 100 }, { 0, 0, 0, 0, 2 }, { 0, 0, 0, 0, 0 } };
    assertEquals("Right answer is to bundle updates 1&2 and 3&4", 20, DynamicProgramming.solve(m, c, R));
  }

  private static int parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int output = sc.nextInt();
    sc.close();
    return output;
  }

  private static class ProblemInstance {

    int m;

    int c;

    int[][] R;

    public ProblemInstance(int m, int c, int[][] R) {
      this.m = m;
      this.c = c;
      this.R = R;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int m = sc.nextInt();
    int c = sc.nextInt();
    int[][] R = new int[m + 1][m + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = i; j <= m; j++) {
        R[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return new ProblemInstance(m, c, R);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    // DominatedSolutions.Solution sol = S.get(S.size()-1);
    // S.remove(S.size()-1);
    int theirSolution = DynamicProgramming.solve(S.m, S.c, S.R);
    assertEquals(parseOutputFile(fileName + ".out"), theirSolution);
  }
}
```

_______________________________________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class DynamicProgramming {

  /**
   *   You should implement this method. Note that your solution should be _iterative_!
   *   @param n The number of updates to ship.
   *   @param c The cost of shipping one bundle of updates.
   *   @param costs The costs matrix of dimension (n+1)*(n+1), where costs[i][j] denotes the costs of bundling updates i,i+1,...,j; given for all 1 <= i <= j <= n
   *   @return The minimal costs of bundling.
   */
  public static int solve(int n, int c, int[][] costs) {
    int[] mem = new int[n + 1];
    mem[0] = 0;
    for (int j = 1; j <= n; j++) {
      int min = Integer.MAX_VALUE;
      for (int i = 1; i <= j; i++) {
        min = Math.min(min, c + costs[i][j] + mem[i - 1]);
      }
      mem[j] = min;
    }
    return mem[n];
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2018_2019/dp/data/";
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
    int m = sc.nextInt();
    int c = sc.nextInt();
    int[][] R = new int[m + 1][m + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = i; j <= m; j++) {
        R[i][j] = sc.nextInt();
      }
    }
    sc.close();
    return Integer.toString(DynamicProgramming.solve(m, c, R));
  }
}
```
