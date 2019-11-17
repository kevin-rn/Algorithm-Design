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
class Solution {
  /**
   * Takes an array and sorts it in an ascending order.
   *
   * @param arr
   *     - the array that needs to be sorted.
   */
  public void sort(int[] arr) {
   if (arr == null || arr.length <=1) return;
    sort(arr, 0, arr.length - 1);
  }
  
 static void merge(int arr[], int l, int m, int r) { 
        int n1 = m - l + 1; 
        int n2 = r - m; 
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
        int i = 0, j = 0; 
        int k = l; 
        while (i < n1 && j < n2) { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } else { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
        while (i < n1) { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
        while (j < n2) { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
    static void sort(int arr[], int l, int r) { 
        if (l < r) { 
            int m = (l+r)/2; 
            sort(arr, l, m); 
            sort(arr , m+1, r); 
            merge(arr, l, m, r); 
        } 
    } 
}
```

OR

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
    if (arr == null || arr.length <=1) return;
    mergeSort(arr, 0, arr.length-1);
  }
  
   static void merge(int arr[], int l, int m, int r) { 
        int l2 = m + 1; 
        if (arr[m] <= arr[l2]) return; 
      
        while (l <= m && l2 <= r) { 
            if (arr[l] <= arr[l2]) l++; 
            else { 
                int value = arr[l2]; 
                int index = l2; 
                while (index != l) { 
                    arr[index] = arr[index - 1]; 
                    index--; 
                } 
                arr[l] = value; 
                l++; 
                m++; 
                l2++; 
            } 
        } 
    } 
      
    static void mergeSort(int arr[], int l, int r) { 
        if (l < r) { 
            int m = l + (r - l) / 2; 
            mergeSort(arr, l, m); 
            mergeSort(arr, m + 1, r); 
            merge(arr, l, m, r); 
        } 
    } 
}
```
