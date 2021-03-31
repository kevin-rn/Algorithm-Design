### Iteration > Recursion (2/9)  [Warning: Extra difficult no joke]
A Fibonacci sequence is a series of numbers for which it holds that, with the exception of the first number, each subsequent number equals the sum of its two predecessors.  
An example of the first ten numbers in the sequence would be {0, 1, 1, 2, 3, 5, 8, 13, 21, 34} (note that we start counting at zero).

A common method of finding Fibonacci numbers is by utilizing a recursive method which tries to find then numbers by recursively finding the first two values, and then summing them while backtracking.  
This does, however, require the recalculation of the full series during each step.

An implementation has been given which attempts to use memoization, or the storing of results as they are calculated.  
Implement the logic within the TODO section, utilizing the given method.

### Template:
```java
package weblab;

import java.io.*;
import java.util.*;

class Solution {

  /**
   * Returns the n'th Fibonacci number
   */
  public static int fibonacci(int n) {
    // The array in which you must implement your memoization.
    int[] fibonacci = new int[n + 1];
    // TODO fibonacci[0] = ...; // Implement the base cases here


    // TODO fibonacci[1] = ...;


    // After that, iterate through all fibonacci numbers from index 2 up to n.
    for (int i = 2; i <= n; i++) {
      // TODO fibonacci[i] = ...;

    }
    // Returning the obtained fibonacci value at index n.
    return fibonacci[n];
  }
}
```

### Test:
```java
package weblab;

import static org.junit.Assert.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    assertEquals(8, Solution.fibonacci(6));
  }

  @Test(timeout = 100)
  public void test05() {
    assertEquals(5, Solution.fibonacci(5));
  }
}

```

### Solution
```java
class Solution {

  /**
   * Returns the n'th Fibonacci number
   */
  public static int fibonacci(int n) {
    // The array in which you must implement your memoization.
    int[] fibonacci = new int[n + 1];
    // TODO fibonacci[0] = ...; // Implement the base cases here
    fibonacci[0] = 0;

    // TODO fibonacci[1] = ...;
    fibonacci[1] = 1;

    // After that, iterate through all fibonacci numbers from index 2 up to n.
    for (int i = 2; i <= n; i++) {
      // TODO fibonacci[i] = ...;
      fibonacci[i] = fibonacci[i-1] + fibonacci[i-2];
    }
    // Returning the obtained fibonacci value at index n.
    return fibonacci[n];
  }
}
```

### Alternative
```java

import java.io.*;
import java.util.*;

class Solution {

  /**
   * Returns the n'th Fibonacci number
   */
  public static int fibonacci(int n) {
    // The array in which you must implement your memoization.
    int[] fibonacci = new int[n + 1];
    fibonacci[0] = 0;

    fibonacci[1] = 1;

    // After that, iterate through all fibonacci numbers from index 2 up to n.
    for (int i = 2; i <= n; i++) {
      fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
    }
    // Returning the obtained fibonacci value at index n.
    return fibonacci[n];
  }
}
```
