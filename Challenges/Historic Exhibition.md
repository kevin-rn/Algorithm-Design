### Historic Exhibition

The Benelux Artistic Pottery Consortium is preparing for an exhibit of its most
prized urns and vases at a gallery in Nijmegen.
Due to the sheer number of vases to be put on display the gallery has trouble
finding a pedestal of the right size for every single vase.  
They have pedestals available that can either be placed normally or upside down and can be characterised by the diameter of their top and bottom surface.  
Moreover, the diameter of the top and bottom varies by at most one unit length. 

For artistic reasons, it is important that the diameter of the base of a vase matches the diameter of the surface of the pedestal it is placed on. 
You have been asked to find a way to place all the vases on available pedestals.  
In order to make this work, you might need to turn some of the pedestals upside down.  
For example, Figure 1 shows a possible assignment of pedestals to vases for sample input 1.  
Assist the gallery by writing a program to compute such an assignment.

![alt text](https://github.com/K-R-N/Algorithm-Design/blob/master/Challenges/Resources/HistoricExhibition.png)

#### Input

- The first line contains two integers 0≤p, v≤104 the number of pedestals and the number of vases.

- Then follow p lines, the i-th of which contains two integers 1≤ai,bi≤104 denoting the diameters of the different sides of pedestal i.
It is given that |ai−bi|≤1.

- Then follows a single line containing v integers 1≤c1,…,cv≤104, where ci denotes the diameter of vase i.

#### Output

- Output v distinct integers 1≤x1,…,xv≤p such that vase i can stand on pedestal xi , or print impossible if no assignment of vases to pedestals exists.

If there are multiple possible solutions, you may output any one of them.

Examples
For each example, the first block is the input and the second block is the corresponding output.

Example 1

4 3  
1 2   
4 5    
2 3   
2 2  
1 2 3  

1  
4  
3  

Example 2  

2 2  
1 1  
2 3  
1 1  

impossible

Example 3 

2 3  
9 8    
4 5   
4 9 5  

impossible

Source: BAPC 2019
______________________________________________________________________________________________________________________________

### Template:
```java
package weblab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

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
import java.util.Scanner;
import java.util.concurrent.*;
import org.junit.*;

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
    if (!verifyAns(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8)), ans, out)) {
      System.out.println(fileName + ": Expected '" + ans + "', but got '" + out + "'");
      throw new fpc.WrongAnswer();
    }
  }

  private static boolean verifyAns(ByteArrayInputStream byteArrayInputStream, String ans, String out) {
    Scanner team = new Scanner(out);
    Scanner ours = new Scanner(ans);
    Scanner input = new Scanner(new BufferedReader(new InputStreamReader(byteArrayInputStream)));
    if (!team.hasNext() && !ours.hasNext()) {
      return true;
    }
    String team_first = team.next();
    if (team_first.equals("impossible")) {
      return ours.next().equals(team_first);
    }
    int n = input.nextInt();
    int k = input.nextInt();
    int[][] sheets = new int[n][2];
    for (int i = 0; i < n; i++) {
      sheets[i][0] = input.nextInt();
      sheets[i][1] = input.nextInt();
    }
    boolean[] used = new boolean[n];
    int s = Integer.parseInt(team_first);
    for (int i = 0; i < k; i++) {
      if (used[s - 1]) {
        return false;
      }
      used[s - 1] = true;
      int wanted_colour = input.nextInt();
      if (sheets[s - 1][0] != wanted_colour && sheets[s - 1][1] != wanted_colour) {
        return false;
      }
      if (i < k - 1) {
        s = team.nextInt();
      }
    }
    if (ours.next().equals("impossible")) {
      return false;
    }
    return true;
  }

  private static final int timeout = 1000;

  @Test
  public void specTests() {
    runTestWithFile("1000_1000_400");
    runTestWithFile("100_100_20");
    runTestWithFile("100_60_60");
    runTestWithFile("10_7_8");
    runTestWithFile("5_3_5");
    runTestWithFile("all_duplicate_colours2");
    runTestWithFile("all_duplicate_colours");
    runTestWithFile("all-one-color");
    runTestWithFile("all-one-color-not-enough");
    runTestWithFile("insufficient");
    runTestWithFile("minimal-2");
    runTestWithFile("minimal-3");
    runTestWithFile("minimal-4");
    runTestWithFile("minimal-impossible");
    runTestWithFile("minimal");
    runTestWithFile("need-all");
    runTestWithFile("nicky");
    runTestWithFile("one_colour");
    runTestWithFile("peaky");
    runTestWithFile("rand_10");
    runTestWithFile("rand-1");
    runTestWithFile("rand_1");
    runTestWithFile("rand_2");
    runTestWithFile("rand_3");
    runTestWithFile("rand_4");
    runTestWithFile("rand_5");
    runTestWithFile("rand_6");
    runTestWithFile("rand_7");
    runTestWithFile("rand_8");
    runTestWithFile("rand_9");
    runTestWithFile("reverse_nicky");
    runTestWithFile("simple_wave_possible");
    runTestWithFile("simple_wave_reverse_possible");
    runTestWithFile("small_1");
    runTestWithFile("smallest-impossible");
    runTestWithFile("threerow");
  }
}

```



__________________________________________________________________________________________________________________________

### Solution:
```java
package weblab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner sc, PrintStream out) {
    int C = 10000;
    int n = sc.nextInt();
    int k = sc.nextInt();
    /*ArrayList<Stack<Integer>> cc = new ArrayList<Stack<Integer>>();
        ArrayList<Stack<Integer>> cc = new ArrayList<Stack<Integer>>();*/
    Stack<Integer>[] cc = new Stack[C + 1];
    Stack<Integer>[] cc2 = new Stack[C + 1];
    Stack<Integer>[] needed = new Stack[C + 1];
    for (int i = 0; i <= C; i++) {
      cc[i] = new Stack<Integer>();
      cc2[i] = new Stack<Integer>();
      needed[i] = new Stack<Integer>();
    }
    for (int i = 1; i <= n; i++) {
      int a = sc.nextInt();
      int b = sc.nextInt();
      if (a == b) {
        cc[a].push(i);
      } else if (a > b) {
        cc2[b].push(i);
      } else {
        cc2[a].push(i);
      }
    }
    int[] ans = new int[k];
    int maxi = -1;
    for (int i = 0; i < k; i++) {
      int c = sc.nextInt();
      needed[c].push(i);
      if (i > maxi)
        maxi = i;
    }
    for (int c = 1; c <= C; c++) {
      while (!needed[c].empty()) {
        if (!cc[c].empty()) {
          ans[needed[c].pop()] = cc[c].pop();
        } else if (!cc2[c - 1].empty()) {
          ans[needed[c].pop()] = cc2[c - 1].pop();
        } else if (!cc2[c].empty()) {
          ans[needed[c].pop()] = cc2[c].pop();
        } else {
          out.println("impossible");
          return;
        }
      }
    }
    for (int i = 0; i < k; i++) {
      out.println(ans[i]);
    }
  }
}
```
