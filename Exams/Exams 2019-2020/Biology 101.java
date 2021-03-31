// Test:
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

  private static class ProblemInstance {

    int n;

    char[] b;

    boolean[][] p;

    public ProblemInstance(int n, char[] b, boolean[][] p) {
      this.n = n;
      this.b = b;
      this.p = p;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    char[] b = sc.next().toCharArray();
    boolean[][] p = new boolean[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      int cnt = sc.nextInt();
      while (cnt-- > 0) {
        p[i][sc.nextInt()] = true;
      }
    }
    sc.close();
    return new ProblemInstance(n, b, p);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    StringBuilder sb = new StringBuilder();
    sb.append(Biology101.solve(S.n, S.b, S.p));
    assertEquals(WebLab.getData(fileName + ".out").trim(), sb.toString().trim());
  }

  @Test(timeout = 100)
  public void example_no_compat() {
    int n = 8;
    char[] b = "\0AUCGAUCG".toCharArray();
    boolean[][] p = new boolean[n + 1][n + 1];
    p[1][3] = true;
    /* We can not combine any (due to the p values), so the largest set will have 0 elements.
    * Note that the 1-3 pairing doesn't work as 1 and 3 are less than 4 apart.
    */
    assertEquals(0, Biology101.solve(n, b, p));
  }

  @Test(timeout = 100)
  public void example_one_compat() {
    int n = 8;
    char[] b = "\0AUCGAUCG".toCharArray();
    boolean[][] p = new boolean[n + 1][n + 1];
    p[1][6] = true;
    /* We can only combine 1 with 6 which should get us a set of size 1.
     */
    assertEquals(1, Biology101.solve(n, b, p));
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
class Biology101 {

  public static /**
   *  You should implement this method.
   *  @param n the number of bases.
   *  @param b an array of size n+1, containing the bases b_1 through b_n. NOTE that you should use b[1] through b[n]
   *  @param p an array of size n+1 by n+1, containing the validity for pair (i,j). A pair (i,j) is valid iff p[i][j] == true. Note that you should use p[1][1] through p[n][n].
   *  @return The size of the largest set of base pairs.
   */
  int solve(int n, char[] b, boolean[][] p) {
    // return solveProper2(n, b, p);
    return solveProper(n, b, p);
  }

  public static int solveProper(int n, char[] b, boolean[][] p) {
    int[][] mem = new int[n + 1][n + 1];
    for (int j = 1; j <= n; j++) {
      for (int i = 1; i <= j - 1; i++) {
        int othermax = -1;
        for (int t = i; t < j - 4; t++) {
          if (p[t][j]) {
            othermax = Math.max(othermax, 1 + mem[i][t - 1] + mem[t + 1][j - 1]);
          }
        }
        mem[i][j] = Math.max(mem[i][j - 1], othermax);
      }
    }
    return mem[1][n];
  }

  public static int solveProper2(int n, char[] b, boolean[][] p) {
    int[][] mem = new int[n + 1][n + 1];
    for (int k = 1; k < n; k++) {
      for (int i = 1; i <= n - k; i++) {
        int j = i + k;
        int othermax = -1;
        for (int t = i; t < j - 4; t++) {
          if (p[t][j]) {
            othermax = Math.max(othermax, 1 + mem[i][t - 1] + mem[t + 1][j - 1]);
          }
        }
        mem[i][j] = Math.max(mem[i][j - 1], othermax);
      }
    }
    return mem[1][n];
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2019_2020/rna/data/secret";
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
    String s = sc.next();
    char[] b = new char[n + 1];
    for (int i = 0; i < s.length(); i++) {
      b[i + 1] = s.charAt(i);
    }
    boolean[][] p = new boolean[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      int cnt = sc.nextInt();
      while (cnt-- > 0) {
        p[i][sc.nextInt()] = true;
      }
    }
    sc.close();
    return Integer.toString(Biology101.solve(n, b, p));
  }
}
