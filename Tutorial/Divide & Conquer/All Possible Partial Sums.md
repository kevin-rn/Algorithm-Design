Implement the function partialSums(Integer[] arr), which takes as input an array of integers and returns a set of all possible sums of the subsets.

For this exercise you should use a divide and conquer approach instead of brute forcing.

An example input would be the array [1, 2], resulting in the output set {0, 1, 2, 3}.

### Template
```java
import java.util.*;

class Solution {
  
  /**
     * Computes all possible partial sums given an array of integers.
     * 
     * @param arr - all values in the input set
     * @return set of sums
     */
  public static Set<Integer> partialSums(Integer[] arr) {
    // TODO
  }
}

```

### Official Solution
```java
import java.util.*;

class Solution {

  public static Set<Integer> partialSums(Integer[] arr) {
    if (arr.length == 0) {
      return new HashSet<>(Collections.singletonList(0));
    }
    return partialSums(arr, 0, arr.length - 1);
  }

  public static Set<Integer> partialSums(Integer[] arr, int low, int high) {
    if (low == high) {
      return new HashSet<>(Arrays.asList(arr[low], 0));
    }
    int mid = (low + high) / 2;
    Set<Integer> leftSums = partialSums(arr, low, mid);
    Set<Integer> rightSums = partialSums(arr, mid + 1, high);
    Set<Integer> combinedSums = new HashSet<>();
    combinedSums.addAll(leftSums);
    combinedSums.addAll(rightSums);
    for (Integer a : leftSums) {
      for (Integer b : rightSums) {
        combinedSums.add(a + b);
      }
    }
    return combinedSums;
  }
}
```

### Lecture Solution
```java
import java.util.*;

class Solution {
  
  /**
     * Computes all possible partial sums given an array of integers.
     * 
     * @param arr - all values in the input set
     * @return set of sums
     */
  public static Set<Integer> partialSums(Integer[] arr) {
    if(arr.length == 0) return new HashSet<>(Collections.singletonList(0));
    return compute(arr, 0, arr.length);
  }
  
  private static Set<Integer> compute(Integer[] arr, int start, int end) {
    HashSet<Integer> result = new HashSet<Integer>();
    if (start + 1== end) {
      result.add(0);
      result.add(arr[start]);
      return result;
    }
    int middle = (start+end)/2;
    Set<Integer> first = compute(arr, start, middle), second = compute(arr, middle, end);
    for(Integer i : first) {
      for(Integer j : second) {
        result.add(i+j);
      }
    }
    return result;
  }
}
```
