Implement the function largestSum(int[] arr), that takes as input an array of integers, find the largest sum using consecutive elements in this array using a divide and conquer approach.

As an example, given the input array [2, -3 , 2, 1], the largest consecutive sum would use the last two elements to sum to 3.

### Template
```java
class Solution {

  public static int largestSum(int[] arr) {
  // TODO
  }
}

```


### Test
```java
import static org.junit.Assert.assertEquals;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import org.junit.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class UTest {

  @Test
  public void testExampleA() {
    int[] input = new int[] { 2, -3, 2, 1 };
    assertEquals(3, Solution.largestSum(input));
  }

  @Test
  public void testExampleB() {
    int[] input = new int[] { 3, -3, -2, 42, -11, 2, 4, 4 };
    assertEquals(42, Solution.largestSum(input));
  }
}


```

### Solution
```java
class Solution {

  public static int largestSum(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }
    return largestSum(arr, 0, arr.length - 1);
  }

  public static int largestSum(int[] arr, int low, int high) {
    if (low == high) {
      return arr[low];
    }
    int mid = (low + high) / 2;
    // Find largest sum with elements from left side
    // Note that we iterate starting from mid
    int temp = 0;
    int leftSum = Integer.MIN_VALUE;
    for (int i = mid; i >= low; i--) {
      temp += arr[i];
      if (temp > leftSum) {
        leftSum = temp;
      }
    }
    // Find largest sum with elements from right side
    // Note that we iterate starting from mid
    temp = 0;
    int rightSum = Integer.MIN_VALUE;
    for (int i = mid + 1; i <= high; i++) {
      temp += arr[i];
      if (temp > rightSum) {
        rightSum = temp;
      }
    }
    // 3. The largest sequence spanning both left and right
    return Math.max(Math.max(largestSum(arr, low, mid), largestSum(arr, mid + 1, high)), leftSum + rightSum);
  }
}
```
