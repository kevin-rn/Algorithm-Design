### Choosing between work
A weighted interval schedule is a schedule of jobs where each of the jobs has a weight attached.  
Every job ji for 0<i≤n has a start time si, an end time fi, and has a weight of vi.  
The goal is to find a schedule where all jobs are compatible, there are no two jobs that overlap, while maximizing the weight of the schedule.

You will be implementing an iterative dynamic programming solution to this problem.  
This implementation should use memoization (storing previously calculated values) to minimize the runtime of the algorithm.  
A basic outline has already been given to you. You can find the predecessor of j by using p[j].

Given the following schedule eight jobs:

s f v  
0 3 3   
1 4 5  
3 8 7  


We expect 10 as our output, as we will execute jobs 1 and 3 for total value of 10, rather than just job two with a total value of 5.

### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Come up with an iterative dynamic programming solution to the weighted interval scheduling problem.
   * NB: You may assume the jobs are sorted by ascending finishing time.
   * @param n the number of jobs
   * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
   * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
   * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
   * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
   * @return the weight of the maximum weight schedule.
   */
  public static int solve(int n, int[] s, int[] f, int[] v, int[] p) {
    int[] mem = new int[n + 1];
    // TODO

    // Returning the obtained obtained value at index n.
    return mem[n];
  }
}

```

### Test:
```java
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
import java.util.Arrays;
import java.util.Comparator;
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

    int[] s;

    int[] f;

    int[] v;

    int[] p;

    ProblemInstance(int[][] jobs, int[] p) {
      this.n = jobs.length;
      this.s = new int[this.n + 1];
      this.f = new int[this.n + 1];
      this.v = new int[this.n + 1];
      for (int i = 1; i <= this.n; i++) {
        this.s[i] = jobs[i - 1][0];
        this.f[i] = jobs[i - 1][1];
        this.v[i] = jobs[i - 1][2];
      }
      this.p = p;
    }
  }

  private static ProblemInstance parseInput(String in) {
    // Reading the input through the use of a Scanner.
    Scanner sc = new Scanner(in);
    // Read the amount of jobs.
    int n = sc.nextInt();
    int[][] jobs = new int[n][3];
    // Read the data for every job.
    for (int i = 0; i < n; i++) {
      jobs[i][0] = sc.nextInt();
      jobs[i][1] = sc.nextInt();
      jobs[i][2] = sc.nextInt();
    }
    // Close the scanner.
    sc.close();
    // Sort the jobs on ascending End time order.
    Arrays.sort(jobs, Comparator.comparingInt((int[] o) -> o[1]));
    // Find the predecessor for every job. If a job j has no predecessor then p[j] = -1
    int[] p = new int[n + 1];
    Arrays.fill(p, -1);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if (jobs[i][0] >= jobs[j][1]) {
          p[i + 1] = j + 1;
        }
      }
    }
    return new ProblemInstance(jobs, p);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance x = parseInput(WebLab.getData(fileName + ".in"));
    int expected = Integer.parseInt(WebLab.getData(fileName + ".out").trim());
    assertEquals(expected, Solution.solve(x.n, x.s, x.f, x.v, x.p));
  }

  @Test(timeout = 100)
  public void example() {
    int[] s = { 0, 0, 1, 3 };
    int[] f = { 0, 3, 4, 8 };
    int[] v = { 0, 3, 5, 7 };
    int[] p = { 0, -1, -1, 1 };
    assertEquals(10, Solution.solve(3, s, f, v, p));
  }

  @Test(timeout = 100)
  public void exampleFile() {
    runTestWithFile("example");
  }

  @Test(timeout = 100)
  public void singleInput() {
    runTestWithFile("test1");
  }

  @Test(timeout = 100)
  public void SlidesExample() {
    runTestWithFile("test2");
  }

  @Test(timeout = 100)
  public void Size30() {
    runTestWithFile("test3");
  }

  @Test(timeout = 100)
  public void Size100() {
    runTestWithFile("test4");
  }

  @Test(timeout = 100)
  public void Size1000() {
    runTestWithFile("test5");
  }

  @Test(timeout = 100)
  public void allJobsOverlap() {
    runTestWithFile("test6");
  }

  @Test(timeout = 100)
  public void boundaryChecking() {
    runTestWithFile("test7");
  }
}

```

_____________________________________________________________________________________________________________
### Solution:
```java
class Solution {

  /**
   * Come up with an iterative dynamic programming solution to the weighted interval scheduling problem.
   * NB: You may assume the jobs are sorted by ascending finishing time.
   * @param n the number of jobs
   * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
   * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
   * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
   * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
   * @return the weight of the maximum weight schedule.
   */
  public static int solve(int n, int[] s, int[] f, int[] v, int[] p) {
    int[] mem = new int[n + 1];
    mem[0] = 0;
    for(int i=1; i<=n; i++) {
      int job = p[i] < 0 ? 0 : mem[p[i]];
      if(mem[i-1] < v[i] + job) mem[i] = v[i] + job;
      else mem[i] = mem[i-1];
    }
    // Returning the obtained obtained value at index n.
    return mem[n];
  }
}


```

### Alternative Formatting:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * Come up with an iterative dynamic programming solution to the weighted interval scheduling problem.
   * NB: You may assume the jobs are sorted by ascending finishing time.
   * @param n the number of jobs
   * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
   * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
   * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
   * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
   * @return the weight of the maximum weight schedule.
   */
  public static int solve(int n, int[] s, int[] f, int[] v, int[] p) {
    int[] mem = new int[n + 1];
    // TODO
    mem[0] = 0;
    for(int i = 1; i <=n; i++) {
      int pred = p[i] < 0 ? 0 : mem[p[i]];
      mem[i] = (mem[i-1] < v[i] + pred) ? v[i] + pred : mem[i-1];
    }
    // Returning the obtained obtained value at index n.
    return mem[n];
  }
}
```

```java

import java.io.*;
import java.util.*;

class Solution {

  /**
   * Come up with an iterative dynamic programming solution to the weighted interval scheduling problem.
   * NB: You may assume the jobs are sorted by ascending finishing time.
   * @param n the number of jobs
   * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
   * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
   * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
   * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
   * @return the weight of the maximum weight schedule.
   */
  public static int solve(int n, int[] s, int[] f, int[] v, int[] p) {
    int[] mem = new int[n + 1];
    // TODO mem[0] = ...; // Base case
    // TODO mem[i] = ...;
    {
      // Base case
      mem[0] = 0;
      for (int j = 1; j <= n; j++) {
        // Check to ensure that there is a previous job.
        int compatible_job = p[j] < 0 ? 0 : mem[p[j]];
        // or include this job v[j] and get the optimal value for the previous compatible job.
        if (mem[j - 1] < v[j] + compatible_job) {
          mem[j] = v[j] + compatible_job;
        } else {
          mem[j] = mem[j - 1];
        }
      }
    }
    // Returning the obtained obtained value at index n.
    return mem[n];
  }
}
```
