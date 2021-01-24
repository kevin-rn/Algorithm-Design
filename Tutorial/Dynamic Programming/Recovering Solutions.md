We have a path graph where every node has a certain weight. We want to select nodes such that there is at most a gap of 2 between selected nodes, while minimizing the total weight and including the last node.

Now recover the solution, i.e., a list of indices of the selected nodes, given an array containing the memoization values and the node weights. For example, the node weights are:

7   
1 18 18 1 18 18 1   

And the optimal value and memoization values are:   

3   
mem: [0, 1, 18, 18, 2, 20, 20, 3]   

Then we expect the following solution:   

[1, 4, 7]  
 

### Assignment:
```java
import java.util.*;

class Solution {


  /**
   * Implement this method to recover the solution (indices of nodes) from the memoization values
   * @param n The number of nodes
   * @param nodes the different weights. You should use nodes[1] to nodes[n]
   * @param result the optimal value
   * @param mem the memoization values
   * @return the list of indices
  **/
  public static List<Integer> solve(int n, int[] nodes, int result, int[] mem) {
  // TODO
  }
}

```

### Test:
```java
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.junit.*;

public class UTest {

  public static int[] getResultArray(int n, int[] nodes) {
    if (n <= 2) {
      return nodes;
    }
    int[] mem = new int[n + 1];
    mem[0] = 0;
    mem[1] = nodes[1];
    mem[2] = nodes[2];
    for (int i = 3; i <= n; i++) {
      // Find node of max distance 3, that minimizes total weight.
      int result = nodes[i] + Integer.min(Integer.min(mem[i - 1], mem[i - 2]), mem[i - 3]);
      mem[i] = result;
    }
    return mem;
  }

  @Test(timeout = 100)
  public void example() {
    int n = 7;
    int[] nodes = { 0, 1, 18, 18, 1, 18, 18, 1 };
    int[] mem = { 0, 1, 18, 18, 2, 20, 20, 3 };
    int optValue = 3;
    List<Integer> result = Arrays.asList(1, 4, 7);
    assertEquals(result, Solution.solve(n, nodes, optValue, mem));
  }
}
```

### Solution:
```java
import java.util.LinkedList;
import java.util.List;

class Solution {

  
  /**
   * Implement this method to recover the solution (indices of nodes) from the memoization values
   * @param n The number of nodes
   * @param nodes the different weights. You should use nodes[1] to nodes[n]
   * @param result the optimal value
   * @param mem the memoization values
   * @return the list of indices
   **/
   public static List<Integer> solve(int n, int[] nodes, int result, int[] mem) {
    // Since we find the solution in reversed order, using a list allows us to prepend in O(1)
    LinkedList<Integer> solution = new LinkedList<>();
    int currentResult = result;
    for (int i = n; i >= 1; i--) {
      if (mem[i] == currentResult) {
        solution.addFirst(i);
        currentResult -= nodes[i];
      }
    }
    return solution;
  }
}
```
