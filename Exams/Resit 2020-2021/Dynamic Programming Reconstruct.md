Remember Max? Thanks to your help on the endterm, he finally figured out what his maximum profit will be when selling wireless chargers. 
(You can skip the rest of this paragraph if you remember.) Max is selling wireless chargers with a solar panel at the beach. 
He observes that the number of sales per day goes down over time. Fortunately he is allowed to also setup his mobile shop at another beach, and then his sales are completely up again. 
The question is how to maximize his total number of sales over a whole month.
To compute this, please consider some more details. 
Wherever he sets shop, the first day he sells 50 chargers, but every next day his sales are a fraction α0,α1<1 of the previous day. 
For example, after 3 days at beach 0 and for α0=0.7, he has sold 50+35+24.5 chargers (for this assignment you may assume he can sell partial chargers). 
On the other hand, switching to the other location costs him a full day before he can start there (and he can do this only at the start of a new day).

The formula he uses is:
OPT(t,d,b)= 0 if t = 0 else Max(Opt(50⋅α+OPT(t−1,d+1,b),OPT(t−1,0,1−b))

Here t represents the number of days Max still has left, d is the number of consecutive days Max has already spent on this beach, and b represents the beach (0 or 1).
The question Max now wants an answer to is: when should he switch beaches (this is the second option of the max in the statement).   
Max finds his optimal profit by calling max(OPT(n,0,0),OPT(n,0,1)), and the given memory array mem is already filled with all intermediate values of OPT(t,d,b).
Your solution should be iterative and not recursive and should use the memory array efficiently.

### Template:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HelpingOutMax {

  /**
   *  You should implement this method.
   *
   *  @param n   the number of days.
   *  @param a   the alpha values of the different beaches a[0] and a[1].
   *  @param mem the memory filled by the dynamic programming algorithm using the provided recursive formulation (dimensions [n+1][n+2][2])
   *  @return the set of days on which Max should switch beach
   */
  public static Set<Integer> solve(int n, double[] a, double[][][] mem) {
  // TODO
  }
}
```

### Test:
```java
import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  @Test(timeout = 100)
  public void example_one_switch() {
    int n = 3;
    double[] a = { 0.01, 0.01 };
    double[][][] mem = { // t == 0
    { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, // t == 1
    { { 50, 50 }, { 0.5, 0.5 }, { 0.005, 0.005 }, { 5e-5, 5e-5 }, { 0, 0 } }, // t == 2
    { { 50.5, 50.5 }, { 50, 50 }, { 50, 50 }, { 50, 50 }, { 0, 0 } }, // t == n == 3
    { { 100, 100 }, { 99.5, 99.5 }, { 99.5, 99.5 }, { 99.5, 99.5 }, { 0, 0 } } };
    /*
         * Only switch on day 2.
         */
    Set<Integer> ans = HelpingOutMax.solve(n, a, mem);
    assertEquals(1, ans.size());
    assertTrue(ans.contains(2));
  }

  @Test(timeout = 100)
  public void example_no_switches() {
    int n = 3;
    double[] a = { 0.01, 0.99 };
    double[][][] mem = { // t == 0
    { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, // t == 1
    { { 50, 50 }, { 0.5, 49.5 }, { 0.005, 49.005 }, { 5e-5, 48.51495 }, { 0, 0 } }, // t == 2
    { { 50.5, 99.5 }, { 50, 98.505 }, { 50, 97.51995 }, { 50, 50 }, { 0, 0 } }, // t == n == 3
    { { 100, 148.505 }, { 99.5, 147.01995 }, { 99.5, 99.005 }, { 99.5, 50.5 }, { 0, 0 } } };
    /*
         * Just stay on beach 2 forever.
         */
    Set<Integer> ans = HelpingOutMax.solve(n, a, mem);
    assertEquals(0, ans.size());
  }
}
```

### Solution:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HelpingOutMax {

  /**
   *  You should implement this method.
   *
   *  @param n   the number of days.
   *  @param a   the alpha values of the different beaches a[0] and a[1].
   *  @param mem the memory filled by the dynamic programming algorithm using the provided recursive formulation (dimensions [n+1][n+2][2])
   *  @return the set of days on which Max should switch beach
   */
  public static Set<Integer> solve(int n, double[] a, double[][][] mem) {
   Set<Integer> res = new HashSet<>();
    //traverse through whole 3d array
    for (int t = 1; t < n+1; t++) { 
      for (int d = 0; d <= n; d++){
        for (int b = 0; b < 2; b++) {
          if (t == 0) continue;
          else {
            double fract = 0;
            for (int i = 0; i < d; i++) {
                res += Math.pow(a[b], i);
            }
            
            mem[t][d][b] = Math.max((fract*50) + mem[t - 1][d + 1][b], mem[t - 1][0][1 - b]);
          }
        }
      }
    }
  // Perhaps backwards iterating through 3D memory array to receive see which days Max switches the beach would be better
  // instead of computing again.
    return res;
  }
}
```
