### Sorting-and-Counting (5/5)
Counting the number of inversions in a list allows us to figure out how many elements are out of order in a list.  
Implement the Sort-and-Count algorithm out of the book. This algorithm calculates the number of inversions while sorting a list.  
Note that the Merge-and-Count part of this algorithm computes how different two lists are.

For example if you get the list [2, 1, 0, 8], your algorithm should print the number of inversion, which is 3 in this example.  

### Template:
```java
import java.io.*;

class Solution {

  static int countInversions(int[] array) {
  // TODO
  }
}


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

### Official Solution:
```java
import java.io.*;

class Solution {

  static int countInversions(int[] array) {
    return sortAndCount(array, 0, array.length - 1);
  }

  public static // Sort input array using recursion and return number of inversions
  int sortAndCount(int[] array, int left, int right) {
    int inversions = 0;
    int mid;
    if (right > left) {
      // Divide array into two parts and do mergeAndCount sort on both parts
      mid = (right + left) / 2;
      // Inversions are the sum of left-part inversions, right-part inversions,
      // and inversions during merging
      inversions = sortAndCount(array, left, mid);
      inversions += sortAndCount(array, mid + 1, right);
      inversions += mergeAndCount(array, left, mid + 1, right);
    }
    return inversions;
  }

  public static // Merge two sorted arrays and return number of inversions that occurred.
  int mergeAndCount(int[] array, int left, int mid, int right) {
    int inversions = 0;
    int[] temp = new int[array.length];
    // i is index for left subarray
    int i = left;
    // j is index for right subarray
    int j = mid;
    // k is index for merged subarrays
    int k = left;
    while ((i <= mid - 1) && (j <= right)) {
      if (array[i] <= array[j]) {
        temp[k++] = array[i++];
      } else {
        temp[k++] = array[j++];
        inversions = inversions + (mid - i);
      }
    }
    // Copy remaining elements of left subarray to temp
    while (i <= mid - 1) {
      temp[k++] = array[i++];
    }
    // Copy remaining elements of right subarray to temp
    while (j <= right) {
      temp[k++] = array[j++];
    }
    // Copy back merged elements to original array.
    for (i = left; i <= right; i++) {
      array[i] = temp[i];
    }
    return inversions;
  }
}

```

### My Solution:
```java

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

### Alternative Solution:
```java
import java.io.*;

class Solution {

  static int countInversions(int[] array) {
    return countsort(array, 0, array.length-1);
  }
  
  static int countsort(int[] array, int l, int r) {
    int inversions = 0;
    if (r > l) {
      inversions = countsort(array, l, (r+l)/2);
      inversions += countsort(array, (r+l)/2+1, r);
      inversions += countmerge(array, l, (r+l)/2+1, r);
    }
    return inversions;
  }
  
  public static int countmerge(int[] array, int l, int m, int r) {
    int[] temp = new int[array.length];
    int i = l, j = m, k = l, inversions = 0;
    while ((i <= m - 1) && (j <= r)) {
      if (array[i] <= array[j]) temp[k++] = array[i++];
      else {
        temp[k++] = array[j++];
        inversions = inversions + (m - i);
      }
    }
    while (i <= m - 1) temp[k++] = array[i++];
    while (j <= r) temp[k++] = array[j++];
    for (i = l; i <= r; i++) array[i] = temp[i];
    return inversions;
  }
}
```
