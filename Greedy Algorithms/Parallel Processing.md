We have m processors and n jobs. Each job i in {1,…,n} has a processing time of exactly 1 hour.  
Furthermore, each job i has an integer deadline di in hours.  
The aim is to find a start time si and processor pi for each job such that no jobs are run at the same processor at the same time and such that the maximum lateness over all jobs is minimized.  
The lateness is the time a job finishes compared to its deadline, defined here by si + 1 - di.  
The objective thus is to minimize maxi{si+1−di}.
Create a greedy algorithm to determine a schedule that has the smallest maximum lateness. 
Given 3 processors and the following 5 deadlines:

3, 1, 1, 1, 2

your algorithm should return the minimized maximum lateness, in this case: 

1.

### Template:
```java
import java.util.*;

class Solution {

  public static /**
   * @param n the number of jobs
   * @param m the number of processors
   * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
   * @return the minimised maximum lateness.
   */
  int solve(int n, int m, int[] deadlines) {
  // TODO
  }
}
```

### Test:
```java
import static org.junit.Assert.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 50)
  public void example() {
    int n = 5;
    int m = 2;
    int[] deadlines = { 0, 3, 1, 1, 1, 2 };
    assertEquals(1, Solution.solve(n, m, deadlines));
  }
}
```

_____________________________________________________________________________________________________________________________

### Solution:
```java
import java.util.*;

class Solution {

  public static /**
   * @param n the number of jobs
   * @param m the number of processors
   * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
   * @return the minimised maximum lateness.
   */
  int solve(int n, int m, int[] deadlines) {
    Arrays.sort(deadlines);
    int[] end = new int[n+1];
    for(int i =1; i <= n; i++) {
      if(i % m != 0) { end[i]++; }
      end[i] += (i/m) - deadlines[i];
    }
    Arrays.sort(end);
    return end[n];
  }
}
```

### Alternative
```java

`import java.util.*;

class Solution {

  public static /**
   * @param n the number of jobs
   * @param m the number of processors
   * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
   * @return the minimised maximum lateness.
   */
  int solve(int n, int m, int[] deadlines) {
    int maxLateness = maxLatenessSorting(n, m, deadlines);
    return maxLateness;
  }

  public static int maxLatenessPriorityQueue(int n, int m, int[] deadlines) {
    PriorityQueue<Job> queue = new PriorityQueue<>();
    for (int i = 1; i <= n; i++) {
      queue.add(new Job(deadlines[i]));
    }
    int maxLateness = Integer.MIN_VALUE;
    int processorIndex = 1;
    int t = 0;
    while (!queue.isEmpty()) {
      Job job = queue.poll();
      int lateness = t + 1 - job.deadline;
      if (maxLateness < lateness) {
        maxLateness = lateness;
      }
      if (processorIndex + 1 <= m) {
        processorIndex = processorIndex + 1;
      } else {
        t = t + 1;
        processorIndex = 1;
      }
    }
    return maxLateness;
  }

  public static int inefficientSorting(int n, int m, int[] deadlines) {
    ArrayList<Job> jobList = new ArrayList<>(n);
    for (int i = 1; i <= n; i++) {
      jobList.add(new Job(deadlines[i]));
    }
    for (int i = 0; i < n; i++) {
      for (int j = 1; j < (n - i); j++) {
        if (jobList.get(j - 1).deadline > jobList.get(j).deadline) {
          Job temp = jobList.get(j - 1);
          jobList.set(j - 1, jobList.get(j));
          jobList.set(j, temp);
        }
      }
    }
    int maxLateness = Integer.MIN_VALUE;
    int processorIndex = 1;
    int t = 0;
    for (int i = 0; i < jobList.size(); i++) {
      Job job = jobList.get(i);
      int lateness = t + 1 - job.deadline;
      if (maxLateness < lateness) {
        maxLateness = lateness;
      }
      if (processorIndex + 1 <= m) {
        processorIndex = processorIndex + 1;
      } else {
        t = t + 1;
        processorIndex = 1;
      }
    }
    return maxLateness;
  }

  public static int maxLatenessSorting(int n, int m, int[] deadlines) {
    ArrayList<Job> jobList = new ArrayList<>(n);
    for (int i = 1; i <= n; i++) {
      jobList.add(new Job(deadlines[i]));
    }
    Collections.sort(jobList);
    int maxLateness = Integer.MIN_VALUE;
    int processorIndex = 1;
    int t = 0;
    for (int i = 0; i < jobList.size(); i++) {
      Job job = jobList.get(i);
      int lateness = t + 1 - job.deadline;
      if (maxLateness < lateness) {
        maxLateness = lateness;
      }
      if (processorIndex + 1 <= m) {
        processorIndex = processorIndex + 1;
      } else {
        t = t + 1;
        processorIndex = 1;
      }
    }
    return maxLateness;
  }

  static class Job implements Comparable<Job> {

    public int deadline;

    public Job(int deadline) {
      this.deadline = deadline;
    }

    @Override
    public int compareTo(Job otherJob) {
      return deadline - otherJob.deadline;
    }
  }
}
```
