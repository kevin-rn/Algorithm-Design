Ninjas are evaluating the suitability of a row of trees for training, with heights hi for 1≤i≤n. From tree i a ninja is only allowed to directly jump to the tree i+1.   
Going backwards is for ninja-poseurs (in plainer English: losers).    
For example, to get to tree i+2 from tree i, the ninja must first jump from tree i to tree i+1, and then from tree i+1 to tree i+2, but a ninja is not allowed to go from tree i to tree i−1.

A training session consists of tree steps:
1. starting the session by climbing one of the trees,
2. jumping to another tree (which may involve jumping to other intermediate trees), and
3. ending the session by climbing down.

Due to the philosophy of the clan, ninjas highly value jumping upwards but punish jumping downwards. More precisely, the value of a jump J(i,j)
from tree i to tree i+1

is given as:

J(i,j)=hj−hi if hj≥hi
J(i,j)=−(hj−hi)2 if hj<hi.

The training session value is expressed as the sum of the values of each jump in the training session.    
For example, for the training session with tree heights [5,10,12,10], the value is 5+2−(22)=3, whereas omitting the last tree yields a greater value of 7.

Given n heights h1,…,hn, return the maximum possible training session value achievable.

    For 1 point, implement a brute-force O(n2)

solution to this problem.
For an additional 2.5 points, implement a more efficient Divide & Conquer solution to this problem.

```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class NinjaTraining {

  /**
   * Jump evaluation as given in the assignment description.
   * There should be no need to modify this function!
   */
  public static int evaluateJump(int height1, int height2) {
    if (height1 <= height2) {
      return height2 - height1;
    } else {
      int diff = height1 - height2;
      return -(diff * diff);
    }
  }

  /**
   * You should implement this method.
   *
   * @param n the number of trees
   * @param h the height of the trees h_1 through h_n. Note you should only use entries h[1] up to and including h[n].
   * @return the score of the best possible training.
   */
  public static int findBestTrainingBruteForce(int n, int[] h) {
    return FindBestTrainingBruteForce(h, 1, n + 1);
  }

  public static int FindBestTrainingBruteForce(int[] heights, int index_start, int index_end) {
    int best_value = 0;
    for (int i = index_start; i < index_end; i++) {
      int sum = 0;
      for (int j = i+1; j < index_end; j++) {
        int current_value = evaluateJump(heights[j-1], heights[j]);
        sum += current_value;
        if (sum > best_value) {
          best_value = sum;
        }
      }
    }
    return best_value;
  }

  /**
   * You should implement this method.
   *
   * @param n the number of trees
   * @param h the height of the trees h_1 through h_n. Note you should only use entries h[1] up to and including h[n].
   * @return the score of the best possible training.
   */
  public static int findBestTrainingDivideAndConquer(int n, int[] h) {
    return FindBestTrainingDivideAndConquer(h, 1, n + 1);
  }

  public static int FindBestTrainingDivideAndConquer(int[] heights, int index_start, int index_end) {
    // base case of size two
    if (index_end - index_start == 2) {
      return Math.max(0, evaluateJump(heights[index_start], heights[index_start + 1]));
    }
    // cases where the array is of length 1 or 0
    if (index_start + 1 >= index_end) {
      return 0;
    }
    // divide into two parts and solve independently
    int index_middle = (index_start + index_end) / 2;
    int best_left_half = FindBestTrainingDivideAndConquer(heights, index_start, index_middle);
    int best_right_half = FindBestTrainingDivideAndConquer(heights, index_middle, index_end);
    // now compute the best training option that intersects the left and right part
    // compute the best starting point from the left, assuming that index_middle is the end point
    int left_start_value = evaluateJump(heights[index_middle - 1], heights[index_middle]);
    int best_left_start_value = left_start_value;
    for (int i = index_middle - 2; i >= index_start; i--) {
      left_start_value = left_start_value + evaluateJump(heights[i], heights[i + 1]);
      if (left_start_value > best_left_start_value) {
        best_left_start_value = left_start_value;
      }
    }
    // compute the best end point from the right, assuming that index_middle-1 is the starting point
    int right_start_value = 0;
    int best_right_start_value = 0;
    for (int i = index_middle + 1; i < index_end; i++) {
      right_start_value = right_start_value + evaluateJump(heights[i - 1], heights[i]);
      if (right_start_value > best_right_start_value) {
        best_right_start_value = right_start_value;
      }
    }
    int global_best = Math.max(best_left_half, best_right_half);
    global_best = Math.max(global_best, best_left_start_value + best_right_start_value);
    return global_best;
  }

  public static int EvaluateTraining(int[] heights, int index_start, int index_end) {
    int value = 0;
    for (int i = index_start; i < index_end - 1; i++) {
      value += evaluateJump(heights[i], heights[i + 1]);
    }
    return value;
  }

  public static void main(String[] args) throws java.io.FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_1_2020_2021/dc/implementation/ninjas/data/secret";
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
    int[] h = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      h[i] = sc.nextInt();
    }
    sc.close();
    int res = NinjaTraining.findBestTrainingDivideAndConquer(n, h);
    StringBuilder sb = new StringBuilder();
    sb.append(res);
    return sb.toString();
  }
}
```
