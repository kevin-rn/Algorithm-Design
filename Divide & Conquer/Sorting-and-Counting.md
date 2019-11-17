### Sorting-and-Counting (5/5)
Counting the number of inversions in a list allows us to figure out how many elements are out of order in a list.  
Implement the Sort-and-Count algorithm out of the book. This algorithm calculates the number of inversions while sorting a list.  
Note that the Merge-and-Count part of this algorithm computes how different two lists are.

For example if you get the list [2, 1, 0, 8], your algorithm should print the number of inversion, which is 3 in this example.  

### Template:
```java


```

### Test:
```java
import static org.junit.Assert.assertEquals;
import org.junit.*;

public class UTest {

  @Test(timeout = 100)
  public void countInversions() {
    int[] input = { 2, 1, 0, 8 };
    assertEquals(3, Solution.countInversions(input));
  }
}
```

________________________________________________________________________________________________________________________________

### Solution:
```java
package weblab;

// import java.io.*;
import java.util.*;
class Solution {

  static int countInversions(int[] array) {
  return sort(array, 0, array.length - 1);
  }
  
  static int sort(int[] arr, int l, int r) { 
        int count = 0; 
        if (l < r) { 
            int m = (l + r) / 2; 
            count += sort(arr, l, m); 
            count += sort(arr, m + 1, r); 
            count += merge(arr, l, m, r); 
        } 
        return count; 
    } 
    
  static int merge(int[] arr, int l, int m, int r) { 
        int[] left = Arrays.copyOfRange(arr, l, m + 1); 
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l, swaps = 0; 
        while (i < left.length && j < right.length) { 
            if (left[i] <= right[j]) 
                arr[k++] = left[i++]; 
            else { 
                arr[k++] = right[j++]; 
                swaps += (m + 1) - (l + i); 
            } 
        } 
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
        return swaps; 
    } 
}
```
