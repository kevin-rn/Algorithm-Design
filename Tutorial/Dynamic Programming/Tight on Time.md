You need to finish n assignments. Every assignment is graded on a scale of 1 to g>1. More hours spent on an assignment typically leads to a higher grade.
Your goal is to maximise your average grade, but you have only H hours available. For every assignment you made a function: spending 0≤hi≤H hours on assignment i will give you a grade of fi(hi).
The functions are monotonically increasing, that is fi(h′)≤fi(h) for any h′≤h.
Determine how many (integer) hours you should spend on each assignment such that your average grade is highest.   

Give a recursive function that computes the maximum obtainable average.  
Hint: maximising the average is the same as maximising the sum of the grades!  

### Assignment:
```java

class Solution {

    /**
     * Implement this method
     *
     * @param n - the number of assignments
     * @param h - the number of hours you can spend
     * @param f - the function in the form of a (n + 1) x (h + 1) matrix.
     *          Index 0 of the number of assignments should be ignored.
     *          Index 0 of the number of hours spend is the minimum grade for this assignment.
     * @return the max grade achievable
     */
    public static int maxGrade(int n, int h, int[][] f) {
        // TODO
    }
}

```

### Test:
```java
import static org.junit.Assert.assertEquals;

import org.junit.*;

public class UTest {

    @Test
    public void testExampleA() {
        int n = 4;
        int h = 6;
        int[][] f = {{0, 0, 0, 0, 0, 0, 0}, {1, 1, 2, 3, 4, 4, 4}, {1, 6, 6, 6, 6, 6, 6}, {1, 2, 2, 2, 2, 2, 2}, {1, 1, 2, 3, 4, 7, 10}};
        // Spend 1 hour on the second assignment and 5 hours on the last assignment
        // Including the base grade for the remaining two assignments
        // 6 + 7 + 2 = 15
        assertEquals(15, Solution.maxGrade(n, h, f));
    }

    @Test
    public void testExampleB() {
        int n = 3;
        int h = 7;
        int[][] f = {{0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 4, 6, 9, 9, 10, 10}, {1, 1, 6, 6, 6, 6, 6, 6}, {1, 3, 3, 3, 3, 3, 3, 3}};
        // Spend 4 hours on the first assignment, 2 hours on the second assignment, and 1 hour on the last assignment
        // 9 + 6 + 3 = 18
        assertEquals(18, Solution.maxGrade(n, h, f));
    }

    @Test
    public void testOneAssignment() {
        int n = 1;
        int h = 5;
        int[][] f = {{0, 0, 0, 0, 0, 0}, {1, 2, 4, 6, 8, 10}};
        // Only one assignment to choose from, spend max hours on it
        assertEquals(10, Solution.maxGrade(n, h, f));
    }

    @Test
    public void testNoTimeLeft() {
        int n = 2;
        int h = 1;
        int[][] f = {{0, 0}, {1, 2}, {1, 4}};
        // Only one hour to spare, pick the second assignment with max grade
        assertEquals(5, Solution.maxGrade(n, h, f));
    }

    @Test
    public void testNoTimeAtAll() {
        int n = 2;
        int h = 0;
        int[][] f = {{0, 0}, {1, 2}, {1, 4}};
        // Only get base grades from the assignments
        assertEquals(2, Solution.maxGrade(n, h, f));
    }

    @Test
    public void testWorkOnEveryAssignment() {
        int n = 8;
        int h = 8;
        int[][] f = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 7, 7, 7, 7, 7, 7, 7, 7}, {1, 3, 3, 3, 3, 3, 3, 3, 3}, {1, 2, 2, 2, 2, 2, 2, 2, 2}, {1, 4, 4, 4, 4, 4, 4, 4, 4}, {1, 5, 5, 5, 5, 5, 5, 5, 5}, {1, 10, 10, 10, 10, 10, 10, 10, 10}, {1, 7, 7, 7, 7, 7, 7, 7, 7}, {1, 4, 4, 4, 4, 4, 4, 4, 4}};
        // Spend exactly 1 hour per assignment
        assertEquals(42, Solution.maxGrade(n, h, f));
    }
}
```

### Solution:
```java
class Solution {

    /**
     * Implement this method
     *
     * @param n - the number of assignments
     * @param h - the number of hours you can spend
     * @param f - the function in the form of a (n + 1) x (h + 1) matrix.
     *          Index 0 of the number of assignments should be ignored.
     *          Index 0 of the number of hours spend is the minimum grade for this assignment.
     * @return the max grade achievable
     */
    public static int maxGrade(int n, int h, int[][] f) {
        int[][] mem = new int[n + 1][h + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= h; j++) {
                if (i == 0) {
                    mem[i][j] = 0;
                    continue;
                }
                if (j == 0) {
                    mem[i][j] = f[i][j] + mem[i - 1][j];
                    continue;
                }
                int max = Integer.MIN_VALUE;
                for (int k = 0; k <= j; k++) {
                    max = Math.max(max, f[i][k] + mem[i - 1][j - k]);
                }
                mem[i][j] = max;
            }
        }
        return mem[n][h];
    }
}
```

### Solution 2:
```java
class Solution {

    /**
     * Implement this method
     *
     * @param n - the number of assignments
     * @param h - the number of hours you can spend
     * @param f - the function in the form of a (n + 1) x (h + 1) matrix.
     *          Index 0 of the number of assignments should be ignored.
     *          Index 0 of the number of hours spend is the minimum grade for this assignment.
     * @return the max grade achievable
     */
    int[][] mem;
    public static int maxGrade(int n, int h, int[][] f) {
        mem = new int[n+1][h+];
        for (int i = 0; i <=n; i++) {
            for (int j = 0; j <= h; j++) {
                mem[i][j] = -1;
            }
        }
        return OPT(n,h,f);
    }

    public static int OPT(int i, int j, int[][] f) {
        if (mem[i][j] != -1) {
            return mem[i][j];
        }
        if (i == 0) {
            mem[i][j] = 0;
        } else if (j == 0) {
            mem[i][j] = f[i][j] + OPT(i-1, j, f);
        } else {
            int max = Integer.MIN_VALUE;
            for (int k = 0; k <= j; k++) {
                max = Math.max(max, f[i][k] + OPT(i - 1, j - k));
            }
            mem[i][j] = max;
        }
        return mem[i][j];
    }
}
```
