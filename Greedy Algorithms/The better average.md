### The better average? (1/11)
In this exercise, you will receive a list of n elements.
Your task is to sort them and return the median value.

Note that if the list has an even length, the median value might be the average of the two middle elements in the array,
as in the example below.

You are free to choose any sorting algorithm that you like, but the input can be as large as one million numbers,
so the running time of your algorithm should be O(n log n).
We recommend choosing Quicksort or Merge Sort. 

In the upcoming exercises (level 1 and up), using the Java standard library for sorting will not be a problem.
However, the purpose of this exercise is to refresh your knowledge.
This means that actually implementing a sorting algorithm will likely be a better refresher than blindly using Arrays.sort().

Example input:
4 2 1 3

Example output:
2.5      

Solution (using Quicksort):
```java
class Solution {

public static double solve(int n, double[] list) {
    sort(list, 0, n-1);
    if(n % 2 == 0) return (list[(n/2) - 1] + list[n/2])/2;
    return list[(n/2)];
  }
  
  public static int partition(double[] arr, int low, int high) { 
        double pivot = arr[high];  
        int i = (low-1); 
        for (int j=low; j<high; j++) { 
            if (arr[j] < pivot) { 
                i++; 
                double temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
        double temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
        return i+1; 
    } 
    
    public static void sort(double[] arr, int low, int high) { 
        if (low < high) { 
            int pi = partition(arr, low, high); 
            sort(arr, low, pi-1); 
            sort(arr, pi+1, high); 
        } 
    }
}
```
### Alternative

```java
import java.io.*;
import java.util.*;

class Solution {

  public static double solve(int n, double[] list) {
    // We sort the list first, so that we can easily determine the median element.
    Arrays.sort(list);
    if (n % 2 == 0) {
      // For even-sized arrays, we need to average the middle two elements.
      double sum = (list[n / 2 - 1] + list[n / 2]);
      return (sum / 2);
    } else
      return list[n / 2];
  }
}
```
