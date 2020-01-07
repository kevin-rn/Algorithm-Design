### Dijkstra on Steroids? 
You are playing a game and you are faced with a puzzle.  
In this puzzle, you have to ﬁnd your way past a group of towers.  
Each tower has a different height, and there is no way to get past it other than by using a ladder to climb to the top.  
(From the top of the tower, you can then jump down or climb up to the next tower, or the ground if you are at the end.)

The towers are arranged in a rectangular n x m grid and you can only move from a tower to the one north, west, south or east of you.  
If you want to go up any height different N, you will need a ladder of length N as well.  
If you want to go down, you can simply jump without the use of a ladder.  
A ladder’s cost increases linearly with its length and you need to get from the northwest tower to the southeast one.  
Of course, you want the cheapest ladder possible, which means finding the minimum length of ladder needed.  
You may assume you are dropped on top of the northwest tower at the start of the puzzle and want to get to the southeast tower at the end of the puzzle.

Given the following problem instance, of size 2 x 3:

3 5 6  
4 2 1  

we expect 1 as our output.  

Give an iterative dynamic solution to find the cheapest ladder you can use to reach the southeasternmost tower.  

NB: Please keep in mind that you can take your ladder with you.   
Thus if you go: 3 -> 4 -> 5 you still only require a ladder of length 1!

### Template:
```java
import java.util.*;

class Solution {

  public static int solve(int n, int m, int[][] graph) {
    /*
    //
    // Come up with an iterative dynamic programming solution to the ladder problem.
    // TODO mem[0] = ...; // Base case
    // TODO mem[i] = ...;
    */
    int[][] mem = new int[n][m];
    // TODO return 0;

  }
}
```

### Test:
```java

```

____________________________________________________________________________________________________

### Solution:
```java

```
