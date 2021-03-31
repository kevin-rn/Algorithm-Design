### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Scheduler {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @return The latest end time given this schedule.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I) {
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

    int[] t;

    int[] p;

    int[] S;

    int[] I;

    public ProblemInstance(int n, int[] t, int[] p, int[] S, int[] I) {
      this.n = n;
      this.t = t;
      this.p = p;
      this.S = S;
      this.I = I;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
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
    return new ProblemInstance(n, t, p, S, I);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    assertEquals(Integer.parseInt(WebLab.getData(fileName + ".out").trim()), Scheduler.solve(S.n, S.t, S.p, S.S, S.I));
  }

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int[] t = { 0, 1, 3 };
    int[] p = { 0, 5, 2 };
    int[] S = { 0, 0, 20 };
    int[] I = { 0, 2, 1 };
    /* The input represents the following:
    * Start the job with t=3 and p=2 at time 0.
    * Start the job with t=1 and p=5 at time 20.
    * Thus the last job finishes at time 26.
    */
    assertEquals("Do job 1 first, then job 2", 26, Scheduler.solve(n, t, p, S, I));
  }

  @Test(timeout = 100)
  public void example_one_job() {
    int n = 1;
    int[] t = { 0, 10 };
    int[] p = { 0, 32 };
    int[] S = { 0, 8 };
    int[] I = { 0, 1 };
    /* The input represents the following:
     * Start the job with t=10 and p=32 at time 8.
     * Thus the last job finishes at time 50.
     */
    assertEquals("Do job 1", 50, Scheduler.solve(n, t, p, S, I));
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

__________________________________________________________________________________________________________________________

### Solution:
```java
/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Scheduler {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @return The latest end time given this schedule.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I) {
    Job[] jobs = new Job[n];
    for(int i = 1; i<=n; i++) jobs[i-1] = new Job(t[i], p[i]);
    int result = 0;
    for(int i = 1; i<=n; i++) {
      Job job = jobs[I[i]-1];
      result = Math.max(result, S[i] + job.t + job.p);
    }
    return result;
  }
  
   public static class Job {
    public int t;
    public int p;
    
    public Job(int t, int p) {
      this.t=t;
      this.p=p;
    }
   }
}
```

```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Scheduler {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @return The latest end time given this schedule.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I) {
    int result = 0;
    for(int i = 1; i <= n; i++) {
      int index = I[i];
      result = Math.max(result, S[i] + t[index] + p[index]);
    }
    return result;
  }
}
```

_______________________________________________________________________________________________________________________

### Official Solution:
```java
package weblab;

import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Scheduler {

  public static /**
   *  You should implement this method.
   *  @param n the number of jobs.
   *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
   *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
   *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
   *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
   *  @return The latest end time given this schedule.
   */
  int solve(int n, int[] t, int[] p, int[] S, int[] I) {
    return solveProper(n, t, p, S, I);
  }

  public static int solveProper(int n, int[] t, int[] p, int[] S, int[] I) {
    Job[] jobs = new Job[n + 1];
    for (int i = 1; i <= n; i++) {
      jobs[i] = new Job(t[i], p[i]);
    }
    return solve(jobs, S, I);
  }

  public static int solve(Job[] jobs, int[] S, int[] I) {
    int ans = 0;
    for (int i = 1; i <= jobs.length - 1; i++) {
      Job job = jobs[I[i]];
      ans = Math.max(ans, S[i] + job.t + job.p);
    }
    return ans;
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
    String dirName = "src/main/java/adweblab/exams/resit_1_2018_2019/greedy2/data/";
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
    int[] S = new int[n + 1];
    int[] I = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = sc.nextInt();
      p[i] = sc.nextInt();
      S[i] = sc.nextInt();
      I[i] = sc.nextInt();
    }
    sc.close();
    return Integer.toString(Scheduler.solve(n, t, p, S, I));
  }
}
```
