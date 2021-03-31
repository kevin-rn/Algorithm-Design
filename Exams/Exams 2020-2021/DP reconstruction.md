Your friend has finally figured out how to solve that hard Dynamic Programming problem she was working on. She has found the following recursive formulation for the optimal value of the solution:

OPT(i,j)=⎧⎩⎨1OPT(i+1,j)max(OPT(i+1,j),vi⋅OPT(i+1,j−vi))if i==n+1 or j==0if vi>jelse

(If the formula is not fully readable, either zoom out the page, or open the image below in a new tab (right-click open image in new tab)! If this does not work for some reason, feel free to ask for clarification using the discussions!)

The parameter i here represents the first item we are considering in the respective subproblem. We say such an item i is included iff vi is used in the computation of the optimal solution.   
The parameter j represents a limit on what can be included. The optimal value can be found by calling OPT(1,m).

Unfortunately she had to leave to TA for ACC before she could finish the last step of the problem, so this is left for you to do: 
given n,m,v and a 2D-array mem that contains the values for all subproblems OPT(i,j) for 1≤i≤n+1,0≤j≤m, return the set of items included in the optimal solution when considering all items.

Your solution should be iterative and not recursive!

```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class RecoverYourItems {

  /**
   *  You should implement this method.
   *
   *  @param n   the number of items n.
   *  @param m   the maximum weight.
   *  @param v   the array containing the weights of the items, in index 1 through n. Note this means you should ignore v[0] and use v[1] through v[n].
   *  @param mem the memory filled by the dynamic programming algorithm using the provided recursive formulation.
   *  @return the set of indices of items that together form the optimal solution.
   */
  public static Set<Integer> solve(int n, int m, int[] v, int[][] mem) {
    return solveProper(n, m, v, mem);
  }

  public static Set<Integer> solveProper(int n, int m, int[] v, int[][] mem) {
    int x = 1;
    int y = m;
    HashSet<Integer> res = new HashSet<>();
    while (x <= n) {
      if (y >= v[x] && mem[x][y] == v[x] * mem[x + 1][y - v[x]]) {
        res.add(x);
        y -= v[x];
        x += 1;
      } else {
        y -= 0;
        x += 1;
      }
    }
    return res;
  }
}

```
