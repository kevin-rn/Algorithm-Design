### What's the previous one?
A weighted interval schedule is a schedule of jobs where each of the jobs has a weight attached.  
Every job ji for 0<i≤n has a start time si, an end time fi, and has a weight of vi. 
The goal is to find a schedule where all jobs are compatible, there are no two jobs that overlap, 
while maximizing the weight of the schedule.

In this exercise you are tasked to find the predecessors of jobs.

Given the following jobs:

s f v  
0 3 3  
1 4 5  
3 8 7  

We expect [??, -1, -1, 1] as our output, where ?? is ignored.

### Template:
```java
import java.util.*;

class Solution {

  public static int[] solve(int n, int[] s, int[] f, int[] v) {
  // TODO
  }
}

```

### Test:
```java
import static org.junit.Assert.*;
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

    ProblemInstance(int[][] jobs) {
      this.n = jobs.length;
      this.s = new int[this.n + 1];
      this.f = new int[this.n + 1];
      this.v = new int[this.n + 1];
      for (int i = 1; i <= this.n; i++) {
        this.s[i] = jobs[i - 1][0];
        this.f[i] = jobs[i - 1][1];
        this.v[i] = jobs[i - 1][2];
      }
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
    return new ProblemInstance(jobs);
  }

  public static int[] computeOutput(int n, int[] s, int[] f) {
    // Find the predecessor for every job. If a job j has no predecessor then p[j] = -1
    int[] p = new int[n + 1];
    Arrays.fill(p, -1);
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j < i; j++) {
        if (s[i] >= f[j]) {
          p[i] = j;
        }
      }
    }
    return p;
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance x = parseInput(WebLab.getData(fileName + ".in"));
    int[] expected = computeOutput(x.n, x.s, x.f);
    int[] actual = Solution.solve(x.n, x.s, x.f, x.v);
    actual[0] = -1;
    assertArrayEquals(expected, actual);
  }

  @Test(timeout = 100)
  public void example() {
    int[] s = { 0, 0, 1, 3 };
    int[] f = { 0, 3, 4, 8 };
    int[] v = { 0, 3, 5, 7 };
    int[] p = { 0, -1, -1, 1 };
    int[] solution = Solution.solve(3, s, f, v);
    solution[0] = 0;
    assertArrayEquals(p, solution);
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

  @Test(timeout = 500)
  public void Size1000() {
    runTestWithFile("test5");
  }

  @Test(timeout = 100)
  public void allJobsOverlap() {
    runTestWithFile("test6");
  }

  @Test(timeout = 500)
  public void boundaryChecking() {
    runTestWithFile("test7");
  }
}

```

### Solution:
````java
class Solution {

  public static int[] solve(int n, int[] s, int[] f, int[] v) {
    Pair[] startTimes = new Pair[n];
    Pair[] endTimes = new Pair[n];
    for (int i = 1; i <= n; i++) {
      startTimes[i - 1] = new Pair(i, s[i]);
      endTimes[i - 1] = new Pair(i, f[i]);
    }
    Arrays.sort(startTimes);
    Arrays.sort(endTimes);

    int j = 0;
    int[] p = new int[n + 1];
    for (int i = 0; i < n; i++) {
      if (endTimes[j].time > startTimes[i].time) {//current interval has no predecessor
        p[startTimes[i].index] = -1;
      } else {
        for (; j < n - 1; j++) {//loop until we find the biggest endtime smaller than the starttime
          if (endTimes[j + 1].time > startTimes[i].time) {
            break;
          }
        }
        p[startTimes[i].index] = endTimes[j].index;
      }
    }
    return p;
  }
  
  public static int[] solveQuadratic(int n, int[] s, int[] f, int[] v) {
    // Find the predecessor for every job. If a job j has no predecessor then p[j] = -1
    int[] p = new int[n + 1];
    Arrays.fill(p, -1);

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j < i; j++) {
        // If job i starts after job j is finished, we know job j is its predecessor
        if (s[i] >= f[j]) {
          p[i] = j;
        }
      }
    }
    return p;
  }

  private static class Pair implements Comparable<Pair> {
    int index;
    int time;

    public Pair(int index, int time) {
      this.index = index;
      this.time = time;
    }

    @Override
    public int compareTo(Pair other) {
      int result = Integer.compare(this.time, other.time);
      return result == 0 ? Integer.compare(this.index, other.index) : result;
    }
  }
}
```

### Alternative
```java
import java.util.*;

class Solution {

  public static int[] solve(int n, int[] s, int[] f, int[] v) {
    // Find the predecessor for every job. If a job j has no predecessor then p[j] = -1
    int[] p = new int[n + 1];
    Arrays.fill(p, -1);
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j < i; j++) {
        // If job i starts after job j is finished, we know job j is its predecessor
        if (s[i] >= f[j]) {
          p[i] = j;
        }
      }
    }
    return p;
  }
}
```
