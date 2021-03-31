### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HighPriority {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @param x the time at which the high priority job should be started.
   *  @return The number of jobs that are initialized at or later than x.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I, int x) {
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

    int[] x;

    int[] t;

    int[] p;

    int[] S;

    int[] I;

    public ProblemInstance(int n, int[] t, int[] p, int[] S, int[] I, int[] x) {
      this.n = n;
      this.t = t;
      this.p = p;
      this.S = S;
      this.I = I;
      this.x = x;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int numTestCases = sc.nextInt();
    int n = sc.nextInt();
    int[] x = new int[numTestCases];
    for (int i = 0; i < numTestCases; i++) {
      x[i] = sc.nextInt();
    }
    int[] t = new int[n + 1];
    int[] p = new int[n + 1];
    int[] S = new int[n + 1];
    int[] I = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = sc.nextInt();
      p[i] = sc.nextInt();
      S[i] = sc.nextInt();
      I[i] = sc.nextInt();
    }
    sc.close();
    return new ProblemInstance(n, t, p, S, I, x);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < S.x.length; i++) {
      sb.append(HighPriority.solve(S.n, S.t, S.p, S.S, S.I, S.x[i]));
      sb.append('\n');
    }
    assertEquals(WebLab.getData(fileName + ".out").trim(), sb.toString().trim());
  }

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int[] t = { 0, 2, 3 };
    int[] p = { 0, 5, 2 };
    int[] S = { 0, 0, 20 };
    int[] I = { 0, 2, 1 };
    /* The input represents the following:
    * Start the job with t=3 and p=2 at time 0.
    * Start the job with t=2 and p=5 at time 20.
    */
    assertEquals("One job to postpone", 1, HighPriority.solve(n, t, p, S, I, 17));
    assertEquals("One job to postpone", 1, HighPriority.solve(n, t, p, S, I, 20));
    assertEquals("No jobs to postpone", 0, HighPriority.solve(n, t, p, S, I, 21));
    assertEquals("No jobs to postpone", 0, HighPriority.solve(n, t, p, S, I, 25));
  }

  @Test(timeout = 100)
  public void example_one_job() {
    int n = 1;
    int[] t = { 0, 10 };
    int[] p = { 0, 32 };
    int[] S = { 0, 8 };
    int[] I = { 0, 1 };
    assertEquals("One job to postpone", 1, HighPriority.solve(n, t, p, S, I, 7));
    assertEquals("No jobs to postpone", 0, HighPriority.solve(n, t, p, S, I, 10));
  }

  @Test(timeout = 100)
  public void example_three_jobs() {
    int n = 3;
    int[] t = { 0, 2, 3, 5 };
    int[] p = { 0, 5, 2, 8 };
    int[] S = { 0, 0, 3, 5 };
    int[] I = { 0, 2, 1, 3 };
    /* The input represents the following:
     * Start the job with t=3 and p=2 at time 0.
     * Start the job with t=2 and p=5 at time 3.
     * Start the job with t=5 and p=8 at time 5.
     */
    assertEquals("Two jobs to postpone", 2, HighPriority.solve(n, t, p, S, I, 2));
    assertEquals("One job to postpone", 1, HighPriority.solve(n, t, p, S, I, 4));
    assertEquals("No jobs to postpone", 0, HighPriority.solve(n, t, p, S, I, 6));
    int[] correctanswers = { 3, 2, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
    for (int x = 0; x < correctanswers.length; x++) {
      assertEquals(correctanswers[x], HighPriority.solve(n, t, p, S, I, x));
    }
  }

  @Test(timeout = 500)
  public void testOneJob() {
    runTestWithFile("oneJob");
  }

  @Test(timeout = 500)
  public void testTwoJobs() {
    runTestWithFile("twoJobs");
  }

  @Test(timeout = 500)
  public void test01() {
    runTestWithFile("n_1000_m_10");
  }

  @Test(timeout = 500)
  public void test02() {
    runTestWithFile("n_1000_m_100");
  }

  @Test(timeout = 500)
  public void test03() {
    runTestWithFile("n_1000_m_10000");
  }

  @Test(timeout = 500)
  public void test04() {
    runTestWithFile("n_1000_unitinit");
  }

  @Test(timeout = 500)
  public void test05() {
    runTestWithFile("n_1000_unitproc");
  }
}
```

____________________________________________________________________________________________________________

### Solution:
```java
/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HighPriority {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @param x the time at which the high priority job should be started.
   *  @return The number of jobs that are initialized at or later than x.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I, int x) {
    int r = n, l = 1;
    while (l < r) {
      int m = (l + r)/2;
      if (x < S[m]) r = m - 1;
      else if (x > S[m]) l = m + 1;
      else return n - m + 1;
    }
    if (S[l] >= x) return n - l + 1;
    return n - l;
  }
}
```

_______________________________________________________________________________________________________

### Official Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HighPriority {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @param x the time at which the high priority job should be started.
   *  @return The number of jobs that are initialized at or later than x.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I, int x) {
    return solveProper(n, t, p, S, I, x);
  }

  public static int solveProper(int n, int[] t, int[] p, int[] S, int[] I, int x) {
    Job[] jobs = new Job[n + 1];
    for (int i = 1; i <= n; i++) {
      jobs[i] = new Job(t[i], p[i]);
    }
    return solve(jobs, S, I, x);
  }

  public static int solve(Job[] jobs, int[] S, int[] I, int x) {
    int n = (jobs.length - 1);
    int l = 1;
    int r = jobs.length - 1;
    while (l < r) {
      int m = (l + r) / 2;
      if (x < S[m]) {
        r = m - 1;
      } else if (x > S[m]) {
        l = m + 1;
      } else {
        return n - m + 1;
      }
    }
    if (x <= S[l]) {
      return n - l + 1;
    }
    return n - l;
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
    String dirName = "src/main/java/adweblab/exams/resit_1_2018_2019/divide_and_conquer/data/";
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
    int numTestCases = sc.nextInt();
    int n = sc.nextInt();
    int[] x = new int[numTestCases];
    for (int i = 0; i < numTestCases; i++) {
      x[i] = sc.nextInt();
    }
    int[] t = new int[n + 1];
    int[] p = new int[n + 1];
    int[] S = new int[n + 1];
    int[] I = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = sc.nextInt();
      p[i] = sc.nextInt();
      S[i] = sc.nextInt();
      I[i] = sc.nextInt();
    }
    sc.close();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numTestCases; i++) {
      sb.append(HighPriority.solve(n, t, p, S, I, x[i]));
      sb.append('\n');
    }
    return sb.toString();
  }
}
```
