You are the superintendent on the construction site of the new Echo building. The entire process goes according to plan, until some day new corona virus measures are announced. All of a sudden you can no longer allow more than one building company on the site at the same time. Unfortunately, multiple jobs still need to be done, each by a different contracting company. You may assume these jobs are all independent and can be done in any order. The contractors can’t do anything whilst another one is working, but they demand to be compensated for this delay, and so you need to pay them for any hours they are waiting as well. You should ensure the least amount of money possible is spent on these jobs. (NOTE: The javadoc might refer to something else. This has been fixed upon resetting the assignment.)

Given a set of n jobs to be completed, with ri≥0 for all 1≤i≤n representing the rate (money per hour) a job requires and di>0 for all 1≤i≤n the duration of a job.   
E.g. if a job takes 8 hours to do, and costs you 400 euros an hour, the minimum amount required to pay for it is 3200. If it is started later, e.g. after another job at time 7, then it finishes at time 15 and thus requires 6000 euros.

Return the minimum amount of money required to finish all n jobs. See the test tab for an example input and answer. 

```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class ConstructionTime {

  /**
   *  You should implement this method.
   *  @param n the number of jobs
   *  @param r the amount of money per hour for a job d_1 through d_n. Note you should only use entries d[1] up to and including d[n].
   *  @param d the duration of a job r_1 through r_n in hours. Note you should only use entries r[1] up to and including r[n].
   *  @return the smallest amount of man-hours for finishing all n jobs.
   */
  public static int constructionJobs(int n, int[] r, int[] d) {
    return solveProper(n, d, r, 0);
  }

  public static int solveProper(int n, int[] t, int[] p, int sortingMethod) {
    ConstructionJob[] exercises = new ConstructionJob[n];
    for (int i = 1; i <= n; i++) {
      exercises[i - 1] = new ConstructionJob(t[i], p[i]);
    }
    switch(sortingMethod) {
      case 0:
        Arrays.sort(exercises, (o1, o2) -> Double.compare(o2.p * 1.0 / o2.t, o1.p * 1.0 / o1.t));
        break;
      case 1:
        Arrays.sort(exercises, (o1, o2) -> Double.compare(o2.p, o1.p));
        break;
      case 2:
        Arrays.sort(exercises, Comparator.comparingDouble(o -> o.t));
        break;
    }
    int result = 0;
    int time = 0;
    for (int i = 0; i < n; i++) {
      time += exercises[i].t;
      result += exercises[i].p * time;
      if (result < 0) {
        throw new Error("Too large number");
      }
    }
    return result;
  }

  public static class ConstructionJob {

    int t;

    int p;

    public ConstructionJob(int t, int p) {
      this.t = t;
      this.p = p;
    }
  }

  public static void main(String[] args) throws java.io.FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_1_2020_2021/basic_greedy/implementation/base/data/secret";
    java.io.File dir = new java.io.File(dirName);
    System.out.println(dir.exists());
    for (java.io.File f : dir.listFiles()) {
      if (f.getName().endsWith("in")) {
        java.io.FileInputStream in = new java.io.FileInputStream(f);
        System.out.println(f.getAbsolutePath());
        String ans = run(in);
        System.out.println(ans);
        java.io.PrintWriter out = new java.io.PrintWriter(f.getAbsolutePath().replace(".in", ".out"));
        out.println(ans);
        out.close();
        System.out.println();
      }
    }
  }

  private static String run(java.io.FileInputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int[] t = new int[n + 1];
    int[] p = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = sc.nextInt();
      p[i] = sc.nextInt();
    }
    sc.close();
    int res = ConstructionTime.constructionJobs(n, t, p);
    StringBuilder sb = new StringBuilder();
    sb.append(res);
    return sb.toString();
  }
}
```


#### Alternative
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class ConstructionTime {

  /**
   *  You should implement this method.
   *  @param n the number of jobs
   *  @param r the amount of money per hour for a job d_1 through d_n. Note you should only use entries d[1] up to and including d[n].
   *  @param d the duration of a job r_1 through r_n in hours. Note you should only use entries r[1] up to and including r[n].
   *  @return the smallest amount of man-hours for finishing all n jobs.
   */
  public static int constructionJobs(int n, int[] r, int[] d) {
    // Create job objects
    Job[] jobs = new Job[n];
    for(int i = 1; i <= n; i++) jobs[i-1] = new Job(r[i], d[i]);
    // Sort on the ratio 
    Arrays.sort(jobs);
    int money = 0, finishtime = 0;
    for(Job j : jobs) {
      money += j.money*(finishtime + j.duration);
      finishtime += j.duration;
    }
    return money;
  }
}

// Helperclass used to keep track of the different jobs
class Job implements Comparable<Job> {
  int money, duration;
  
  public Job(int money, int duration) {
    this.money = money;
    this.duration = duration;
  }
  
  @Override
  public int compareTo(Job other) {
    double job1 = (double) this.money/ (double) this.duration;
    double job2 = (double) other.money/ (double) other.duration;
    if(job1 < job2) return 1;
    else if(job1 > job2) return -1;
    else return 0;
  }
  
}

```
