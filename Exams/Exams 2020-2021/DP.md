The lecturers of AD wish to have an efficient plan for the construction of exam questions for n future exams.   
We know that each exam 1≤i≤n requires exactly qi questions.    
Of course, all questions need to be unique, so we know exactly how many questions to produce. Questions are only produced right before an exam.
In the planning of constructing exam questions we therefore are concerned only with two forms of overhead:

The overhead of the lecturers getting into “the zone” for creating exam questions.    
The cost of getting into the zone is z minutes (regardless of how many questions you then write).

Additionally, for any question that was made and is not selected for use directly in the upcoming exam but saved for a future exam, there is a cost for refreshing our memory about the question.    
This takes r minutes of overhead per question every time we postpone using the question.  

Finally, there is a hard limit on how many questions we can keep in reserve.   
We simply cannot store more than L questions. (We also do not want to make too many questions, so after n exams no questions should be left.)

Implement an iterative dynamic programming solution in O(nL) space that returns the minimum amount of overhead in minutes to supply the questions for all of the n exams.  
As a hint, here is part of a recursive mathematical solution for this problem, with some missing parts indicated by … . (You may use this one, but other equivalent correct solutions exist!)
OPT(i,j)=⎧⎩⎨⎪⎪⎪⎪0∞OPT(i+1,j−qi)+……if i>n and j==0if i>n and j>0if qi≤jelse 

The optimal solution is obtained by calling OPT(0,…).

```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class ExamDatabase {

  /**
   * You should implement this method.
   *
   * @param n the number of exams we need to create.
   * @param z the overhead of getting into the zone in minutes, regardless of how many questions need to be created.
   * @param r the overhead to recall a question in minutes, measured _per_ question.
   * @param L the maximum number of questions we can keep in reserves.
   * @param q the number of questions we need per exam from index _1_ to _n_, you should ignore q[0].
   * @return the minimal overhead in minutes.
   */
  public static int minimiseOverhead(int n, int z, int r, int L, int[] q) {
    return solveProper(n, q, z, r, L);
  }

  public static int solveProper(int t, int[] p, int c, int f, int s) {
    int[][] mem = new int[t + 2][s + 1];
    for (int i = t + 1; i >= 0; i--) {
      for (int j = 0; j <= s; j++) {
        if (i == t + 1 && j == 0) {
          mem[i][j] = 0;
        } else if (i == t + 1 && j > 0) {
          mem[i][j] = Integer.MAX_VALUE / 2;
        } else if (j >= p[i]) {
          mem[i][j] = mem[i + 1][j - p[i]] + f * (j - p[i]);
        } else {
          mem[i][j] = Integer.MAX_VALUE / 2;
          for (int new_total = p[i]; new_total <= s + p[i]; new_total++) {
            mem[i][j] = Math.min(mem[i][j], mem[i + 1][new_total - p[i]] + c + f * (new_total - p[i]));
          }
        }
      }
    }
    return mem[0][0];
  }

  public static void main(String[] args) throws java.io.FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2020_2021/dp/implementation/unused_questions/data/secret";
    java.io.File dir = new java.io.File(dirName);
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

  public static String run(java.io.InputStream in) {
    Scanner sc = new Scanner(in);
    int t = sc.nextInt();
    int c = sc.nextInt();
    int f = sc.nextInt();
    int s = sc.nextInt();
    int[] p = new int[t + 1];
    for (int i = 1; i <= t; i++) {
      p[i] = sc.nextInt();
    }
    sc.close();
    return Integer.toString(ExamDatabase.minimiseOverhead(t, c, f, s, p));
  }
}

```
