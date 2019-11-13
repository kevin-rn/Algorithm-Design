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
    boolean median = false;
    if(n % 2 == 0) median = true;
    sort(list, 0, n-1);
    if(median) return (list[(n/2) - 1] + list[n/2])/2;
    return list[(n/2)];
  }
  
  public static int partition(double[] arr, int low, int high) { 
        double pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (arr[j] < pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                double temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        double temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
    
    public static void sort(double[] arr, int low, int high) { 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = partition(arr, low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            sort(arr, low, pi-1); 
            sort(arr, pi+1, high); 
        } 
  
}
}
```
