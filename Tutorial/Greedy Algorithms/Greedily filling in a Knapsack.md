```java
import java.util.*;

class Solution {

  /**
   * Return the minimum number of items we need to get to the weight we want to get to.
   * @param n the number of item categories
   * @param w the weight we want to achieve with as few items as possible.
   * @param num the number of items in each category c_1 through c_n stored in num[1] through num[n] (NOTE: you should ignore num[0]!)
   * @param weight the weight of items in each category c_1 through c_n stored in weight[1] through weight[n] (NOTE: you should ignore weight[0]!)
   * @return minimum number of items needed to get to the required weight
   */
  public static int run(int n, int w, int[] num, int[] weight) {
    return new Solution().solve(n, w, num, weight);
  }

  public int solve(int n, int w, int[] num, int[] weight) {
    Category[] c = new Category[n];
    for(int i = 0; i < n; i++) {
       c[i] = new Category(num[i+1], weight[i+1]);
    }
    Arrays.sort(c);
    int amount = 0;
    for(int i =0; i <n; i++) {
      if(w <= 0) break;
      int number = Math.min(c[i].num, w/c[i].weight);
      w -= number * c[i].weight;
      amount += number;
   }
   return amount;
  }
  
  /**
   * Class Category made to sort easily.
   */
  public class Category implements Comparable<Category> {
    int num;
    int weight;
    
    public Category(int num, int weight) {
      this.weight = weight;
      this.num = num;
    }
    
    @Override
    public int compareTo(Category other) {
      return other.weight - this.weight;
    }
  }
}
```
