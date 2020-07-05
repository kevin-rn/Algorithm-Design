### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class AntiqueRoadshow {

  public static /**
   *  You should implement this method.
   *  @param n the number of items.
   *  @param v an array of size n+1, containing the values v_1 through v_n. You should ignore v[0].
   *  @param skip an array of size n+1, containing the values skip(1) through skip(n). You should ignore skip[0].
   *  @return The maximum value obtainable from these jobs.
   */
  int solve(int n, int[] v, int[] skip) {
  // TODO
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

  private static class ProblemInstance {

    int n;

    int[] v;

    int[] skip;

    public ProblemInstance(int n, int[] v, int[] skip) {
      this.n = n;
      this.v = v;
      this.skip = skip;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int[] v = new int[n + 1];
    int[] skip = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      v[i] = sc.nextInt();
      skip[i] = sc.nextInt();
    }
    sc.close();
    return new ProblemInstance(n, v, skip);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    StringBuilder sb = new StringBuilder();
    sb.append(AntiqueRoadshow.solve(S.n, S.v, S.skip));
    assertEquals(WebLab.getData(fileName + ".out").trim(), sb.toString().trim());
  }

  @Test(timeout = 100)
  public void example_atmost_one() {
    int n = 4;
    int[] v = { 0, 2, 4, 8, 5 };
    int[] skip = { 0, 0, 1, 2, 3 };
    /* We can not combine any (due to the skip values), so taking only 8 is optimal
    */
    assertEquals(8, AntiqueRoadshow.solve(n, v, skip));
  }

  @Test(timeout = 100)
  public void example_combining() {
    int n = 4;
    int[] v = { 0, 2, 4, 8, 5 };
    int[] skip = { 0, 0, 1, 2, 1 };
    /* We cannot combine 2 with 4 nor 8 with any other.
     * Best option is therefore: 5 + 4 = 9.
     */
    assertEquals(9, AntiqueRoadshow.solve(n, v, skip));
  }
}
```

_______________________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class AntiqueRoadshow {

  public static /**
   *  You should implement this method.
   *  @param n the number of items.
   *  @param v an array of size n+1, containing the values v_1 through v_n. You should ignore v[0].
   *  @param skip an array of size n+1, containing the values skip(1) through skip(n). You should ignore skip[0].
   *  @return The maximum value obtainable from these jobs.
   */
  int solve(int n, int[] v, int[] skip) {
    return solveProper(n, v, skip);
  }

  public static int solveProper(int n, int[] v, int[] skip) {
    int[] mem = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      mem[i] = Math.max(mem[i - 1], mem[i - 1 - skip[i]] + v[i]);
    }
    return mem[n];
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/resit_2_2018_2019/dynamic_programming/data/";
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
    int[] v = new int[n + 1];
    int[] skip = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      v[i] = sc.nextInt();
      skip[i] = sc.nextInt();
    }
    sc.close();
    return Integer.toString(AntiqueRoadshow.solve(n, v, skip));
  }
}

```
