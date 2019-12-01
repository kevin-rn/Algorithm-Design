```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Solution {

  /**
   * Optimizes the provided Phorest to be as close to an MST as possible.
   * @param n the number of nodes in the network
   * @param g all edges in the full graph
   * @param p the edges in the Phorest
   * @return total edge weight of optimized Phorest
   */
  public static String run(int n, Edge[] g, Edge[] p) {
    return new Solution().solve(n, g, p);
  }

  public String solve(int nodes, Edge[] graph, Edge[] phorest) {
    // Create the Phorest
    Edge[] tree = new Edge[nodes - 1];
    UnionFind uf = new UnionFind(nodes);
    int p = phorest.length;
    for (int i = 0; i < p; i++) {
      tree[i] = phorest[i];
      uf.join(tree[i].x, tree[i].y);
    }
    // Optimize the Phorest
    for (Edge e : graph) {
      if (uf.join(e.x, e.y)) {
        tree[p] = e;
        ++p;
      }
      if (p == nodes - 1)
        break;
    }
    // Optimize the Phorest
    // Return optimized phorest total edge weight
    int result = 0;
    for (Edge e : tree) {
      result += e.l;
    }
    return Integer.toString(result);
  // Return optimized phorest total edge weight
  }

  static Edge[] makeGraph(Scanner sc) {
    int m = sc.nextInt();
    Edge[] edges = new Edge[m];
    for (int i = 0; i < m; i++) edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
    Arrays.sort(edges, Comparator.comparingInt(e -> e.l));
    return edges;
  }

  class UnionFind {

    private int[] parent;

    private int[] rank;

    public UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) parent[i] = i;
    }

    // returns false if x and y are in same set
    private boolean join(int x, int y) {
      int xrt = find(x);
      int yrt = find(y);
      if (rank[xrt] > rank[yrt])
        parent[yrt] = xrt;
      else if (rank[xrt] < rank[yrt])
        parent[xrt] = yrt;
      else if (xrt != yrt)
        rank[parent[yrt] = xrt]++;
      return xrt != yrt;
    }

    private int find(int x) {
      return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }
  }

}
class Edge {

    // from, to and length
    int x, y, l;

    public Edge(int from, int to, int length) {
      x = from;
      y = to;
      l = length;
    }
  }
```
