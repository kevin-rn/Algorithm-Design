### Planning Bike Repairs (6/11)
Mike runs a bike repair service for students.  
Students can plan a time to bring in their bike on his website, where they also enter a description of what is broken.  
Mike is very good at his job and he can flawlessly predict how long each repair will cost.  
To cater the need of students to have a working bike at all time, he promises his customers to finish the repair as soon as possible after the bike is brought in.  
  
Now Mike wants to know how many of his employees to schedule for a day.  
He has the list of repairs for this day and he wants you to develop an algorithm to tell him how many people he needs.  
  
You get the list in the following format: On the first line is the amount of repairs.  
The next lines list the repairs with on each line the time the repair starts followed by the time the repair will take.  
  
Example input:  
6  
6 1  
3 5  
1 2  
6 3  
8 3  
1 4  
  
Example output:  
3  


### Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
   Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    
    int[] start= new int[n], repair = new int[n];
    int maxlength = 0;
    for(int i =0; i < n; i++) {
      start[i] = sc.nextInt();
      repair[i] = sc.nextInt();
      if(maxlength < start[i] + repair[i]) maxlength = start[i] + repair[i];
    }
    sc.close();
    int[] endtimes = new int[maxlength+1];
    for(int i = 0; i < n; i++) {
        for(int j=start[i]; j < start[i] + repair[i]; j++) {
            endtimes[j]++;
        }
    }
    Arrays.sort(endtimes);
    return Integer.toString(endtimes[maxlength]);
  
  }
}
```

### Alternative Solution
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt(), maxtime = 0;
    Repair[] rp = new Repair[n+1];
    for(int i = 0; i < n; i++) {
      rp[i] = new Repair(sc.nextInt(), sc.nextInt());
      maxtime = Math.max(maxtime, rp[i].total);
    }
    sc.close();
    int[] times = new int[maxtime+1];
    for(int i = 0; i < n; i++) {
      for(int j = rp[i].start; j < rp[i].total; j++) times[j]++;
    }
    Arrays.sort(times);
    return Integer.toString(times[maxtime]);
  }
  
  public class Repair {
    int start, duration, total;
    
    public Repair(int start, int duration) {
      this.start = start;
      this.duration = duration;
      this.total = start + duration;
    }
  }
}
```

### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    
    int n = sc.nextInt();
    int[] starts = new int[n];
    int[] ends = new int[n];
    for(int i = 0; i < n; i++) {
      starts[i] = sc.nextInt();
      ends[i] = starts[i] + sc.nextInt();
    }
    
    Arrays.sort(starts);
    Arrays.sort(ends);
    
    int i = 0;
    int j = 0;
    int cnt = 0;
    int m = 0;
    while (i < n && j < n) {
      if (starts[i] < ends[j]) {
        cnt += 1;
        m = Math.max(cnt, m);
        i++;
      } else {
        cnt -= 1;
        j++;
      }
    }
    
    
    sc.close();
    return "" + m;
  }
  
  public String solveQuadratic(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    Job[] jobs = new Job[n];
    for (int i = 0; i < n; i++) {
      jobs[i] = new Job(sc.nextInt(), sc.nextInt());
    }
    Arrays.sort(jobs);
    int depth = 0;
    for (int i = 1; i < n; i++) {
      int count = 1;
      for (int j = 0; j < i; j++) {
        if (jobs[j].s + jobs[j].l > jobs[i].s) {
          count++;
        }
      }
      if (count > depth) {
        depth = count;
      }
    }
    return Integer.toString(depth);
  }

  private class Job implements Comparable<Job> {

    // start time and length
    int s, l;

    public Job(int start, int length) {
      this.s = start;
      this.l = length;
    }

    @Override
    public int compareTo(Job j) {
      return this.s - j.s;
    }

    @Override
    public String toString() {
      return "Job: " + s + ", " + l;
    }
  }
}
```
