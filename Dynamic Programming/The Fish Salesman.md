### The Fish Salesman
A fish salesman has determined there are two lucrative spots P and Q where he can set up his stand.
He has (perfectly) predicted the profits to be had during a period of n days on each spot, call them pi and qi for 1≤i≤n.
The salesman obviously wants to maximize his profit, but he cannot be in both spots on one day, so he will have to decide where he is going to be on each day.
Breaking up his stand and setting it up again in the other spot is a difficult job, however, which takes a whole day, on which there will be no profits.

As an example consider the following instance:

P  Q  
80 90  
30 60  
30 60  
70 50  
80 20  

We expect 300 as output here, representing that we set up shop at location Q on days 1 and 2 and on location P on days 4 and 5 (with day 3 being the switch day).

Give an iterative dynamic solution to find the maximum profit the salesman can earn.

### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * @param n the number of days
   * @param P the profits that can be made on day 1 through n on location P are stored in P[1] through P[n].
   * @param Q the profits that can be made on day 1 through n on location Q are stored in Q[1] through Q[n].
   * @return the maximum obtainable profit.
   */
  public static int solve(int n, int[] P, int[] Q) {
    /*
    //
    // Come up with an iterative dynamic programming solution to the salesman problem.
    // TODO mem[0][0] = ...; // Base case
    // TODO mem[1][0] = ...;
    // TODO mem[0][1] = ...;
    // TODO mem[1][1] = ...;
    */
    int[][] mem = new int[2][n + 1];
    // TODO return 0

  }
}
```

### Test:
```java
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.*;

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

    int[] P, Q;

    ProblemInstance(int n, int[] P, int[] Q) {
      this.n = n;
      this.P = P;
      this.Q = Q;
    }
  }

  private static ProblemInstance parseInput(String in) {
    // Reading the input through the use of a Scanner.
    Scanner sc = new Scanner(in);
    // Read the amount of days.
    int n = sc.nextInt();
    int[][] profit = new int[2][n + 1];
    // Read the data for every day.
    for (int i = 1; i <= n; i++) {
      profit[0][i] = sc.nextInt();
      profit[1][i] = sc.nextInt();
    }
    // Close the scanner.
    sc.close();
    return new ProblemInstance(n, profit[0], profit[1]);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance x = parseInput(WebLab.getData(fileName + ".in"));
    int expected = Integer.parseInt(WebLab.getData(fileName + ".out").trim());
    assertEquals(expected, Solution.solve(x.n, x.P, x.Q));
  }

  @Test(timeout = 100)
  public void example() {
    int n = 5;
    int[] P = { 0, 80, 30, 30, 70, 80 };
    int[] Q = { 0, 90, 60, 60, 50, 20 };
    assertEquals(300, Solution.solve(n, P, Q));
  }

  @Test(timeout = 100)
  public void twentyDays() {
    runTestWithFile("test1");
  }

  @Test(timeout = 100)
  public void fiftyDays() {
    runTestWithFile("test2");
  }

  @Test(timeout = 100)
  public void twoHundredFiftyDays() {
    runTestWithFile("test3");
  }

  @Test(timeout = 500)
  public void tenThousandDays() {
    runTestWithFile("test4");
  }
}

```

______________________________________________________________________________________________________

### Solution
```java

class Solution {

  /**
   * @param n the number of days
   * @param P the profits that can be made on day 1 through n on location P are stored in P[1] through P[n].
   * @param Q the profits that can be made on day 1 through n on location Q are stored in Q[1] through Q[n].
   * @return the maximum obtainable profit.
   */
  public static int solve(int n, int[] P, int[] Q) {
    /*
    //
    // Come up with an iterative dynamic programming solution to the salesman problem.
    // TODO mem[0][0] = ...; // Base case
    // TODO mem[1][0] = ...;
    // TODO mem[0][1] = ...;
    // TODO mem[1][1] = ...;
    */
    mem[0][0] = Integer.MIN_VALUE;
    mem[1][0] = Integer.MIN_VALUE;
    mem[0][1] = P[1];
    mem[1][1] = Q[1];
    for(int i =2; i<= n; i++) {
      mem[0][i] = Integer.max(P[i] + mem[0][i-1], mem[1][i-1]); 
      mem[1][i] = Integer.max(Q[i] + mem[1][i-1], mem[0][i-1]); 
    }
    return Integer.max(mem[0][n], mem[1][n]);
    
  }
}


```
