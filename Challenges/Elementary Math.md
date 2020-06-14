Ellen is teaching elementary math to her students and the time for the final exam has come. 
The exam consists of n questions. 
In each question the students have to add (+), subtract (−) or multiply (∗) a pair of numbers.

Ellen has already chosen the n pairs of numbers. 
All that remains is to decide for each pair which of the three possible operations the students should perform.
To avoid students getting bored, Ellen wants to make sure that the n correct answers to her exam are all different.
Please help Ellen finish constructing the exam by automating this task.

Input

The input consists of:
    one line with one integer n (1≤n≤2,500), the number of pairs of numbers;
    n lines each with two integers a and b (−106≤a,b≤106), a pair of numbers used.

Output

For each pair of numbers (a,b) in the same order as in the input, output a line containing a valid equation.
Each equation should consist of five parts: a, one of the three operators, b, an equals sign (=), and the result of the expression.
All the n expression results must be different.

If there are multiple valid answers you may output any of them.
If there is no valid answer, output a single line with the string “impossible” instead.
Examples

For each example, the first block is the input and the second block is the corresponding output.
Example 1

4
1 5
3 3
4 5
-1 -6

1 + 5 = 6
3 * 3 = 9
4 - 5 = -1
-1 - -6 = 5

Example 2

4
-4 2
-4 2
-4 2
-4 2

impossible

Source: NWERC 2015

### Template:
```java
package weblab;

import java.io.*;
import java.util.*;

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
import java.io.*;
import java.nio.charset.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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
    class Equation {

      int a;

      char op;

      int b;

      long ans;

      public Equation(int a, char op, int b) {
        this.a = a;
        this.op = op;
        this.b = b;
        switch(this.op) {
          case '+':
            this.ans = (long) this.a + (long) this.b;
            break;
          case '-':
            this.ans = (long) this.a - (long) this.b;
            break;
          case '*':
            this.ans = (long) this.a * (long) this.b;
            break;
        }
      }
    }
    Scanner team = new Scanner(out);
    Scanner ours = new Scanner(ans);
    Scanner input = new Scanner(new BufferedReader(new InputStreamReader(byteArrayInputStream)));
    if (!team.hasNextInt() && !ours.hasNextInt()) {
      return true;
    }
    int n = input.nextInt();
    Set<Long> answers = new HashSet<>();
    for (int i = 0; i < n; i++) {
      int input_a = input.nextInt();
      int input_b = input.nextInt();
      int team_a = team.nextInt();
      if (team_a != input_a) {
        System.err.println("Not valid a");
        return false;
      }
      char team_op = team.next().charAt(0);
      int team_b = team.nextInt();
      if (team_b != input_b) {
        System.err.println("Not valid b");
        return false;
      }
      char team_eq = team.next().charAt(0);
      if (team_eq != '=') {
        System.err.println("Not valid =");
        return false;
      }
      long team_ans = team.nextLong();
      Equation eq = new Equation(team_a, team_op, team_b);
      if (team_ans != eq.ans) {
        System.err.println("Not valid ans: " + team_a + team_op + team_b + "=" + team_ans + " our ans" + eq.ans);
        return false;
      }
      if (answers.contains(eq.ans)) {
        System.err.println("Reused ans");
        return false;
      }
      answers.add(eq.ans);
    }
    return true;
  }

  private static final int timeout = 2000;

  @Test(timeout = 18_000)
  public void specTests() {
    runTestWithFile("01_large_single_sol");
    runTestWithFile("02_large_impossible");
    runTestWithFile("03_size_1");
    runTestWithFile("04_overflow");
    runTestWithFile("05_kill_greedy");
    runTestWithFile("06_hard_single");
    runTestWithFile("07_hard_single");
    runTestWithFile("08_hard_single");
    runTestWithFile("09_hard_single");
    runTestWithFile("10_hard_single");
    runTestWithFile("11_random");
    runTestWithFile("12_random");
    //runTestWithFile("13_random");
    //runTestWithFile("14_random");
    //runTestWithFile("15_random");
    runTestWithFile("16_force_multiplication");
    runTestWithFile("17_large_all_the_same");
    runTestWithFile("18_large_three_interleaved");
    runTestWithFile("19_large_three_the_same");
    runTestWithFile("20_random_max_size_1");
    runTestWithFile("21_hard_single");
    runTestWithFile("22_hard_single");
    //runTestWithFile("23_hard_single");
    //runTestWithFile("24_hard_single");
    runTestWithFile("25_hard_single");
    //runTestWithFile("26_allunique");
  }
}
```

_____________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner sc, PrintStream out) {
    // Read input
    int n = sc.nextInt();
    long[] a = new long[n];
    long[] b = new long[n];
    for (int i = 0; i < n; i++) {
      a[i] = sc.nextLong();
      b[i] = sc.nextLong();
    }
    // Compute possible answers
    int numAns = 0;
    HashSet<Long>[] anss = new HashSet[n];
    Map<Long, Integer> idx = new HashMap<Long, Integer>();
    for (int i = 0; i < n; i++) {
      anss[i] = new HashSet<Long>();
      anss[i].add(a[i] + b[i]);
      anss[i].add(a[i] - b[i]);
      anss[i].add(a[i] * b[i]);
      for (long ans : anss[i]) if (!idx.containsKey(ans))
        idx.put(ans, numAns++);
    }
    // Create matching problem using generic max flow code
    MaxFlow mf = new MaxFlow(n + numAns);
    for (int i = 0; i < n; i++) {
      mf.Add(mf.Source, i, 1);
      for (long ans : anss[i]) mf.Add(i, n + idx.get(ans), 1);
    }
    for (int i = 0; i < numAns; i++) mf.Add(n + i, mf.Sink, 1);
    // Is the problem solvable?
    if (mf.NetworkFlow() != n) {
      out.print("IMPOSSIBLE");
      return;
    }
    // Solvable, construct the output
    for (int i = 0; i < n; i++) {
      long res = -1;
      char op = '#';
      if (mf.Flow[i][n + idx.get(a[i] + b[i])] == 1) {
        op = '+';
        res = a[i] + b[i];
      } else if (mf.Flow[i][n + idx.get(a[i] - b[i])] == 1) {
        op = '-';
        res = a[i] - b[i];
      } else if (mf.Flow[i][n + idx.get(a[i] * b[i])] == 1) {
        op = '*';
        res = a[i] * b[i];
      } else {
        throw new Error("The impossible happened.");
      }
      out.println(String.format(" %d %c %d = %d", a[i], op, b[i], res));
    }
  }
}

class MaxFlow {

  public int Nodes;

  public int Source;

  public int Sink;

  public int[][] Capacity;

  LinkedList<Integer>[] Neighbours;

  boolean[][] NeighbourAdded;

  public int[][] Flow;

  public MaxFlow(int Nodes) {
    this.Nodes = Nodes + 2;
    this.Source = Nodes;
    this.Sink = Nodes + 1;
    Neighbours = new LinkedList[this.Nodes];
    for (int i = 0; i < this.Nodes; i++) Neighbours[i] = new LinkedList<Integer>();
    Capacity = new int[this.Nodes][this.Nodes];
    NeighbourAdded = new boolean[this.Nodes][this.Nodes];
  }

  public void Add(int From, int To, int Cap) {
    Capacity[From][To] += Cap;
    if (!NeighbourAdded[From][To]) {
      NeighbourAdded[From][To] = true;
      NeighbourAdded[To][From] = true;
      Neighbours[From].addLast(To);
      Neighbours[To].addLast(From);
    }
  }

  public int NetworkFlow() {
    Flow = new int[Nodes][Nodes];
    int[] parent = new int[Nodes];
    int[] cap = new int[Nodes];
    int total = 0;
    while (true) {
      for (int i = 0; i < Nodes; i++) parent[i] = -1;
      parent[this.Source] = -2;
      cap[this.Source] = Integer.MAX_VALUE;
      Queue<Integer> Q = new LinkedList<Integer>();
      Q.offer(this.Source);
      while (!Q.isEmpty() && parent[this.Sink] == -1) {
        int act = Q.poll();
        for (int next : Neighbours[act]) {
          if (parent[next] == -1 && Capacity[act][next] > Flow[act][next]) {
            parent[next] = act;
            cap[next] = Math.min(cap[act], Capacity[act][next] - Flow[act][next]);
            Q.offer(next);
          }
        }
      }
      if (parent[this.Sink] == -1)
        return total;
      total += cap[this.Sink];
      int j = this.Sink;
      while (j != this.Source) {
        Flow[parent[j]][j] += cap[this.Sink];
        Flow[j][parent[j]] -= cap[this.Sink];
        j = parent[j];
      }
    }
  }
}
```
