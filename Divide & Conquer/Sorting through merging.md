### Sorting through merging (2/5)

In this exercise you will have to implement an in-place version of Mergesort. The method sort takes an array of integers as input and sorts the provided array.

You can find more information on Mergesort in chapter 5.1 of your book (Algorithm Design, Kleinberg, Tardos).

### Template:
```java
package weblab;

class Solution {

  /**
   * Takes an array and sorts it in an ascending order.
   *
   * @param arr
   *     - the array that needs to be sorted.
   */
  public void sort(int[] arr) {
  // TODO
  }
}
```

### Test:
```java
package weblab;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    int[] input = { 4, 2, 5, 1, 3 };
    new Solution().sort(input);
    assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, input);
  }
}
```

___________________________________________________________________________________________________________________________

### Solution:
```java

```
