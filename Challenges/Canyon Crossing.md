### Canyon Crossing
The Bridge And Passageway Creators are responsible for making new paths through the
local mountains. They have approved your
plan to build a new route through your favorite canyon. You feverishly start
working on this beautiful new path, when you realize you failed to take into
account the flow of a nearby river: the canyon is flooded! Apparently
this happens once every blue moon, making some parts of the path inaccessible.
Because of this, you want to build a path such that the lowest point on the path
is as high as possible. You quickly return to the village and
use all of your money to buy rope bridges. You plan to use these to circumvent
the lowest parts of the canyon.

Your map of the canyon consists of a rectangular grid of cells, each containing
a number giving the height of the terrain at that cell. The path will go
from the south side of the canyon (bottom on your map) to the north side (top of
your map), moving through a connected sequence of cells. Two cells are
considered connected if and only if they share an edge. In particular, two
diagonally touching cells are not considered to be connected. This means that
for any cell not on the edge of the map, there are 4 other cells connected to
it. The left of figure 1 contains the map for the first sample input.
Figure 1: fig1.pdf

The path through the canyon can start on any of the bottom cells of the grid,
and end on any of the cells in the top tow, like the two paths on the right in
Figure 1.
![images](https://github.com/K-R-N/Algorithm-Design/blob/master/Challenges/Resources/fig1.jpg)
The lowest height is given by the lowest
height of any of the cells the paths goes through. Each bridge can be used
to cross exactly one cell. This cell is then not taken into account
when calculating the minimal height of the path. Note that is allowed to chain
multiple bridges to use them to cross multiple cells,

Given the map of the canyon and the number of bridges available, find
the lowest height of an optimal path.

Figure 2: 
![images](https://github.com/K-R-N/Algorithm-Design/blob/master/Challenges/Resources/fig2.jpg)

#### Input

- A single line containing three integers: 1≤R≤1000 and 1≤C≤1000, the size of the map, and 0≤K≤R−1, the number of bridges you can build.
- This is followed by R lines each containing C integers. The j-th integer on the i-th line corresponds to the height 0≤Hi,j≤109 of the canyon at point (i,j). The first line corresponds to the northern edge of the canyon, the last line to the southern edge.

#### Output

Output a single integer, the lowest height of the optimal path.
#### Examples  

For each example, the first block is the input and the second block is the corresponding output.  
Example 1  

5 3 1  
1 1 3  
3 3 3  
0 0 0  
2 2 1  
1 2 1  

2  

Example 2  

5 3 3  
2 1 1  
2 1 1  
1 1 1  
1 1 2  
1 1 2  

2  

Example 3

3 2 2  
1 1  
4 4  
1 2  
 
4  

Source: DAPC 2019

______________________________________________________________________________________________________________________________

### Template:
```java
package weblab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Scanner;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner sc, PrintStream out) {
  // TODO
  }
}


```

### Test:
```java
package weblab;

import java.io.*;
import java.nio.charset.*;
import java.util.concurrent.*;
import org.junit.*;

public class UTest {

  public static void runTestWithFile(String fileName) {
    ByteArrayInputStream inStream = new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8));
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    try {
      long start = System.currentTimeMillis();
      Executors.newSingleThreadExecutor().submit(() -> {
        Solution.run(inStream, new PrintStream(outStream));
        try {
          outStream.flush();
        } catch (IOException e) {
          throw new fpc.RunError();
        }
      }).get(timeout, // throws the exception if one occurred during the invocation
      TimeUnit.MILLISECONDS);
      System.out.println(fileName + ": Took " + (System.currentTimeMillis() - start) + " ms");
    } catch (TimeoutException e) {
      System.out.println(fileName + ": Timeout after " + timeout + " ms");
      throw new fpc.TimeLimit();
    } catch (ExecutionException e) {
      System.out.println(fileName + ": " + e.getCause());
      throw new fpc.RunError();
    } catch (Throwable e) {
      System.out.println(fileName + ": " + e);
      throw new fpc.RunError();
    }
    String ans = WebLab.getData(fileName + ".ans").trim();
    String out = outStream.toString().trim();
    if (!ans.equals(out)) {
      System.out.println(fileName + ": Expected '" + ans + "', but got '" + out + "'");
      throw new fpc.WrongAnswer();
    }
  }

  private static final int timeout = 8000;

  @Test
  public void specTests() {
    runTestWithFile("1_1");
    runTestWithFile("1_2");
    runTestWithFile("1_col_0");
    runTestWithFile("1_col_500");
    runTestWithFile("1_col_999");
    runTestWithFile("1_row");
    runTestWithFile("2_1_bridge");
    runTestWithFile("2_1");
    runTestWithFile("bridge_bottom");
    runTestWithFile("bridge_corner_A");
    runTestWithFile("bridge_corner_B");
    runTestWithFile("bridge_corner_C");
    runTestWithFile("bridge_corner_D");
    runTestWithFile("bridge_diag_A");
    runTestWithFile("bridge_diag_B");
    runTestWithFile("bridge_diag_C");
    runTestWithFile("bridge_diag_D");
    runTestWithFile("bridge_down");
    runTestWithFile("bridge_left");
    runTestWithFile("bridge_right");
    runTestWithFile("bridge_top");
    runTestWithFile("bridge_up");
    runTestWithFile("continuous_bridge");
    runTestWithFile("directiontest");
    // runTestWithFile("fingers1000x1000");
    runTestWithFile("gap_bottom");
    runTestWithFile("gap_diag_A");
    runTestWithFile("gap_diag_B");
    runTestWithFile("gap_diag_C");
    runTestWithFile("gap_diag_D");
    runTestWithFile("gap_down");
    runTestWithFile("gap_left");
    runTestWithFile("gap_right");
    runTestWithFile("gap_top");
    runTestWithFile("gap_up");
    // runTestWithFile("grate1000x1000");
    runTestWithFile("plateau1000x1000");
    runTestWithFile("plateau1000x1000K1");
    runTestWithFile("plateau1000x1000K500");
    // runTestWithFile("plateau1000x1000K999");
    runTestWithFile("plateau1000x100");
    runTestWithFile("plateau1000x100K1");
    runTestWithFile("plateau1000x100K500");
    //runTestWithFile("plateau1000x100K999");
    runTestWithFile("plateau100x100");
    runTestWithFile("plateau100x100K1");
    runTestWithFile("plateau100x100K50");
    runTestWithFile("plateau100x100K99");
    // runTestWithFile("plateauhigh1000x1000");
    runTestWithFile("separated_bridges");
    runTestWithFile("separated_edge_bridges");
    runTestWithFile("snake1000x97");
    runTestWithFile("snake1000x997");
    runTestWithFile("snake100x97");
    runTestWithFile("spiral1");
    runTestWithFile("spiral2");
    runTestWithFile("staircase1000x1000");
    runTestWithFile("staircase1000x1000K1");
    //runTestWithFile("staircase1000x1000K500");
    //runTestWithFile("staircase1000x1000K999");
    runTestWithFile("staircase1000x100");
    runTestWithFile("staircase1000x100K1");
    //runTestWithFile("staircase1000x100K500");
    // runTestWithFile("staircase1000x100K999");
    runTestWithFile("staircase100x100");
    runTestWithFile("staircase100x100K1");
    runTestWithFile("staircase100x100K50");
    runTestWithFile("staircase100x100K99");
  }
}

class fpc {

  static class RunError extends RuntimeException {

    private static final long serialVersionUID = 42;
  }

  static class TimeLimit extends RuntimeException {

    private static final long serialVersionUID = 42;
  }

  static class WrongAnswer extends RuntimeException {

    private static final long serialVersionUID = 42;
  }
}

```

_______________________________________________________________________________________________________________________________

### Solution:
```java
package weblab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Scanner;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  private static boolean isPossible(int R, int C, int K, int H, int[][] grid) {
    int[] dx = { 1, 0, -1, 0 };
    int[] dy = { 0, 1, 0, -1 };
    boolean[][] done = new boolean[R][C];
    ArrayDeque<Integer> work = new ArrayDeque<Integer>();
    ArrayDeque<Integer> nextWork = new ArrayDeque<Integer>();
    for (int i = 0; i <= K; i++) {
      if (i == 0) {
        // setup
        for (int j = 0; j < C; j++) {
          if (grid[i][j] >= H)
            work.add(j);
          else
            nextWork.add(j);
          done[0][j] = true;
        }
      }
      while (!work.isEmpty()) {
        int pos = work.remove();
        int x = pos / C;
        int y = pos % C;
        if (x == R - 1)
          return true;
        for (int j = 0; j < 4; j++) {
          if (x + dx[j] < 0 || x + dx[j] >= R || y + dy[j] < 0 || y + dy[j] >= C)
            continue;
          if (done[x + dx[j]][y + dy[j]])
            continue;
          if (grid[x + dx[j]][y + dy[j]] >= H)
            work.add((x + dx[j]) * C + y + dy[j]);
          else
            nextWork.add((x + dx[j]) * C + y + dy[j]);
          done[x + dx[j]][y + dy[j]] = true;
        }
      }
      ArrayDeque<Integer> t = work;
      work = nextWork;
      nextWork = t;
    }
    return false;
  }

  public void solve(Scanner sc, PrintStream out) {
    int R = sc.nextInt();
    int C = sc.nextInt();
    int K = sc.nextInt();
    int[][] grid = new int[R][C];
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        grid[i][j] = sc.nextInt();
      }
    }
    int low = 0;
    int high = 1000000001;
    while (high - low > 1) {
      int mid = (high + low) / 2;
      if (isPossible(R, C, K, mid, grid))
        low = mid;
      else
        high = mid;
    }
    out.println(low);
  }
}

```
