### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Mason {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @return The minimum latest end time.
   */
  int solve(int n, int[] t, int[] p) {
  // TODO
  }
}
```

### Tests:
```java
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
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

    int[] t;

    int[] p;

    public ProblemInstance(int n, int[] t, int[] p) {
      this.n = n;
      this.t = t;
      this.p = p;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int[] t = new int[n + 1];
    int[] p = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = sc.nextInt();
      p[i] = sc.nextInt();
    }
    sc.close();
    return new ProblemInstance(n, t, p);
  }

  private static List<Integer> parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    List<Integer> res = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      res.add(sc.nextInt());
    }
    sc.close();
    return res;
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    List<Integer> solutions = parseOutputFile(fileName + ".out");
    assertTrue(solutions.contains(Mason.solve(S.n, S.t, S.p)));
  }

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int[] t = { 0, 1, 3 };
    int[] p = { 0, 5, 2 };
    assertEquals("Do job 1 first, then job 2", 1, Mason.solve(n, t, p));
  }

  @Test(timeout = 100)
  public void example_one_job() {
    int n = 1;
    int[] t = { 0, 10 };
    int[] p = { 0, 32 };
    assertEquals("Do job 1", 0, Mason.solve(n, t, p));
  }

  @Test(timeout = 100)
  public void only_sorting_p_will_break() {
    int n = 3;
    int[] t = { 0, 1, 5, 2 };
    int[] p = { 0, 2, 4, 6 };
    /* Only sorting p gives:
     * t = {0, 1, 5, 2}
     * p = {0, 6, 4, 2}
     * This results in: 1+5 = 6
     * Correct answer is: 2 + 5 = 7
    */
    assertEquals(7, Mason.solve(n, t, p));
  }
}

```

_______________________________________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Mason {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @return The minimum latest end time.
   */
  int solve(int n, int[] t, int[] p) {
    return solveProper(n, t, p, 2);
  // return solveOnlySortP(n,t,p);
  }

  public static int solveOnlySortP(int n, int[] t, int[] p) {
    int[] tp = new int[n];
    Integer[] pp = new Integer[n];
    for (int i = 1; i <= n; i++) {
      tp[i - 1] = t[i];
      pp[i - 1] = p[i];
    }
    Arrays.sort(pp, Collections.reverseOrder());
    int s = 0;
    for (int i = 0; i < n; i++) {
      s += tp[i];
    }
    return s - tp[n - 1];
  }

  public static int solveProper(int n, int[] t, int[] p, int sorting) {
    Job[] jobs = new Job[n];
    for (int i = 1; i <= n; i++) {
      jobs[i - 1] = new Job(t[i], p[i]);
    }
    if (sorting > 0) {
      return solveDiff(jobs, sorting);
    } else {
      return solve(jobs);
    }
  }

  public static int solve(Job[] jobs) {
    Arrays.sort(jobs);
    int s = 0;
    for (int i = 0; i < jobs.length; i++) {
      s += jobs[i].t;
    }
    return s - jobs[jobs.length - 1].t;
  }

  public static int solveDiff(Job[] jobs, int sorting) {
    Arrays.sort(jobs, new Comparator<Job>() {

      @Override
      public int compare(Job job, Job t1) {
        int x = Integer.compare(job.p, t1.p);
        if (sorting > 1 && x == 0) {
          x = -Integer.compare(job.t, t1.t);
        }
        return -1 * x;
      }
    });
    int s = 0;
    for (int i = 0; i < jobs.length; i++) {
      s += jobs[i].t;
    }
    return s - jobs[jobs.length - 1].t;
  }

  private static class Job implements Comparable<Job> {

    int t;

    int p;

    public Job(int t, int p) {
      this.t = t;
      this.p = p;
    }

    public int compareTo(Job job) {
      int x = Integer.compare(this.p, job.p);
      if (x == 0) {
        x = Integer.compare(this.t, job.t);
      }
      return -1 * x;
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/resit_1_2018_2019/greedy/data/";
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
    int[] t = new int[n + 1];
    int[] p = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = sc.nextInt();
      p[i] = sc.nextInt();
    }
    sc.close();
    StringBuilder sb = new StringBuilder();
    sb.append('3');
    sb.append('\n');
    sb.append(Mason.solveProper(n, t, p, 0));
    sb.append(' ');
    sb.append(Mason.solveProper(n, t, p, 1));
    sb.append(' ');
    sb.append(Mason.solveProper(n, t, p, 2));
    return sb.toString();
  }
}

```
