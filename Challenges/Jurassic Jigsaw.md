### Jurassic Jigsaw
See Jurassic Jigsaw pdf in folder Resources

### Template:
```java
package weblab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner in, PrintStream out) {
  // TODO
  }
}


```

### Test:
```java
package weblab;

import static org.junit.Assert.assertEquals;
import java.io.*;
import java.nio.charset.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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
    if (!verifyAns(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8)), ans, out)) {
      System.out.println(fileName + ": Expected '" + ans + "', but got '" + out + "'");
      throw new fpc.WrongAnswer();
    }
  }

  private static boolean verifyAns(ByteArrayInputStream byteArrayInputStream, String ans, String out) {
    class UnionFind {

      private int[] parent;

      private int[] rank;

      // Union Find structure implemented with two arrays for Union by Rank
      public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) parent[i] = i;
      }

      /**
       * Merge two subtrees if they have a different parent, input is array indices
       * @param i a node in the first subtree
       * @param j a node in the second subtree
       * @return true iff i and j had different parents.
       */
      boolean union(int i, int j) {
        int parent1 = find(i);
        int parent2 = find(j);
        if (parent2 == parent1)
          return false;
        if (rank[parent1] > rank[parent2]) {
          parent[parent2] = parent1;
        } else if (rank[parent2] > rank[parent1]) {
          parent[parent1] = parent2;
        } else {
          parent[parent2] = parent1;
          rank[parent1]++;
        }
        return true;
      }

      /**
       * NB: this function should also do path compression
       * @param i index of a node
       * @return the root of the subtree containg i.
       */
      int find(int i) {
        int parent = this.parent[i];
        if (i == parent) {
          return i;
        }
        return this.parent[i] = find(parent);
      }

      // Return the rank of the trees
      public int[] getRank() {
        return rank;
      }

      // Return the parent of the trees
      public int[] getParent() {
        return parent;
      }

      public int count() {
        Set<Integer> parents = new HashSet<>();
        for (int i = 0; i < this.parent.length; i++) {
          this.find(i);
          parents.add(this.parent[i]);
        }
        return parents.size();
      }
    }
    // 1) Check the team printed the right weight.
    // 2) Check that the team output is a tree.
    // 3) Check that its weight equals the answer weight.
    Scanner team = new Scanner(out);
    Scanner ours = new Scanner(ans);
    Scanner input = new Scanner(new BufferedReader(new InputStreamReader(byteArrayInputStream)));
    int n = input.nextInt();
    int k = input.nextInt();
    String[] dna = new String[n];
    for (int i = 0; i < n; i++) {
      dna[i] = input.next("[a-zA-Z]+");
    }
    // 1) right weight?
    int answer = team.nextInt();
    if (answer != ours.nextInt()) {
      return false;
    }
    // 2) tree?
    int weight = 0;
    UnionFind uf = new UnionFind(n);
    for (int i = 0; i < n - 1; ++i) {
      int u = team.nextInt();
      int v = team.nextInt();
      uf.union(u, v);
      for (int j = 0; j < k; ++j) if (dna[u].charAt(j) != dna[v].charAt(j))
        ++weight;
    }
    if (uf.count() > 1 || weight != answer) {
      System.out.println("Invalid: " + uf.count() + " " + weight + " " + answer);
      return false;
    }
    return true;
  }

  private static final int timeout = 1000;

  @Test
  public void specTests() {
    runTestWithFile("01-minimal");
    runTestWithFile("02-minimal");
    runTestWithFile("03-identical");
    runTestWithFile("04-disjoint");
    runTestWithFile("05-path");
    runTestWithFile("06-path-dist-2");
    runTestWithFile("07-cycle");
    runTestWithFile("08-star");
    runTestWithFile("09-small");
    runTestWithFile("11-max-c2");
    runTestWithFile("12-max-c3");
    runTestWithFile("13-max-c4");
    runTestWithFile("14-k9-max");
    runTestWithFile("15-k8-max");
    runTestWithFile("16-k7-max");
    runTestWithFile("17-k6-max");
    runTestWithFile("18-k5-max");
    runTestWithFile("19-k4-max");
    runTestWithFile("20-k3-max");
    runTestWithFile("21-k2-max");
    runTestWithFile("22-k1-c1");
    runTestWithFile("23-k1-c2");
    runTestWithFile("24-k2-c2");
    runTestWithFile("25-k2-c3");
    runTestWithFile("26-k2-c4");
    runTestWithFile("27-k3-c2");
    runTestWithFile("28-k3-c3");
    runTestWithFile("29-k3-c4");
    runTestWithFile("30-k4-c2");
    runTestWithFile("31-k4-c3");
    runTestWithFile("32-k4-c4");
    runTestWithFile("33-path-d1");
    runTestWithFile("34-path-d2");
    runTestWithFile("35-path-d3");
    runTestWithFile("36-path-d4");
    runTestWithFile("37-path-d5");
    runTestWithFile("38-path-d6");
    runTestWithFile("39-path-d7");
    runTestWithFile("40-path-d8");
    runTestWithFile("41-path-d9");
    runTestWithFile("42-path-d10");
    runTestWithFile("43-star-d1");
    runTestWithFile("44-star-d2");
    runTestWithFile("45-star-d3");
    runTestWithFile("46-star-d4");
    runTestWithFile("47-star-d5");
    runTestWithFile("48-star-d6");
    runTestWithFile("49-star-d7");
    runTestWithFile("50-star-d8");
    runTestWithFile("51-star-d9");
    runTestWithFile("52-star-d10");
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

______________________________________________________________________________________________________________________________

### Solution
```java
package weblab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner in, PrintStream out) {
    int n = in.nextInt();
    int k = in.nextInt();
    String[] vertex = new String[n];
    for (int i = 0; i < n; i++) {
      vertex[i] = in.next("[a-zA-Z]+");
    }
    in.close();
    // create edges
    Edge[] edges = new Edge[n * (n - 1) / 2];
    int idx = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        edges[idx] = new Edge(i, j, vertex[i], vertex[j]);
        idx += 1;
      }
    }
    // sorting following the hash (O(nlogn) basic sorting from java)
    Arrays.sort(edges, Edge::compareWeight);
    int cost = 0;
    int select = 0;
    idx = 0;
    Edge[] selected = new Edge[n - 1];
    UF uf = new UF(n);
    while (select < n - 1) {
      if (uf.connectIfNot(edges[idx].vertex1, edges[idx].vertex2)) {
        selected[select] = edges[idx];
        cost += edges[idx].weight;
        select += 1;
      }
      idx += 1;
    }
    // Arrays.sort(selected, Edge::compareVertex);
    // output MST
    out.println(cost);
    for (int i = 0; i < n - 1; i++) out.println(selected[i].vertex1 + " " + selected[i].vertex2);
  // TODO
  }

  public static class Edge {

    public int vertex1, vertex2, weight;

    public Edge(int v1, int v2, String s1, String s2) {
      this.vertex1 = v1;
      this.vertex2 = v2;
      this.weight = 0;
      for (int i = s1.length() - 1; i >= 0; i--) {
        if (s1.charAt(i) != s2.charAt(i))
          this.weight += 1;
      }
    }

    public static int compareWeight(Edge e1, Edge e2) {
      return e1.weight - e2.weight;
    }

    public static int compareVertex(Edge e1, Edge e2) {
      if (e1.vertex1 != e2.vertex2)
        return e1.vertex1 - e2.vertex1;
      else
        return e1.vertex2 - e2.vertex2;
    }
  }

  public static class UF {

    private int[] id;

    public UF(int n) {
      id = new int[n];
      for (int i = 0; i < n; i++) id[i] = i;
    }

    public boolean connectIfNot(int p, int q) {
      int pid = id[p];
      int qid = id[q];
      if (pid != qid) {
        for (int i = 0; i < id.length; i++) {
          if (id[i] == pid)
            id[i] = qid;
        }
        return true;
      } else
        return false;
    }
  }
}
```
