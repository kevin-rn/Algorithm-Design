Give a greedy algorithm that for a series of n jobs with run times t1 through tn, creates a schedule for a machine such that the average finishing time is minimised.
Determine also the end times f1,f2,…fn in the algorithm.

### Template:
```java
class Solution {

  public static /**
   * @param n the number of jobs
   * @param runtimes the rutimes of all jobs 1 through n. Note that runtimes[0] should be ignored!
   * @return an array with the end times of the jobs. Also here, do not use [0].
   */
  int[] minAvgEndtimes(int n, int[] runtimes) {
    int[] endtimes = new int[n+1];
    // TODO
    
    return endtimes;
  }
}
```


### Solution:
```java
import java.util.Arrays; 

class Solution {

  public static /**
   * @param n the number of jobs
   * @param runtimes the rutimes of all jobs 1 through n. Note that runtimes[0] should be ignored!
   * @return an array with the end times of the jobs. Also here, do not use [0].
   */
  int[] minAvgEndtimes(int n, int[] runtimes) {
    int[] endtimes = new int[n+1];
    if (n == 0)
      return endtimes;
    runtimes[0] = 0;
    endtimes[0] = 0;
    Arrays.sort(runtimes);
    for (int i = 1; i <= n; i++) {
      endtimes[i] = endtimes[i-1] + runtimes[i];
    }
    return endtimes;
  }
}
```
