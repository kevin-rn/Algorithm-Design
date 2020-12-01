You’re designing the irrigation in a rural area. The possible canals and ditches are given by a weighted graph G=(V,E), where V denotes a set containing n vertices (locations), E a set containing m edges between these, and the weights represent the lengths of these edges.  
Complete the algorithm that selects those canals and ditches that facilitate irrigation of all vertices while having to dig the least. Aim for the most efficient algorithm you can think of.  

The input is structured the same as for Greedy task 0C: “Can we get out?”:  
The first line of the input contains three integers separated by a space: n, m, and s. The integers n and m are defined as above and you may assume the vertices are labelled 1 up to and including n. E.g. if n equals 6, the vertices are labelled 1,2,3,4,5 and 6. The integer s is the number of the vertex representing the main water source (a connection to a river).
The next m lines are the edges. Each line contains three integers: the two numbers of the vertices associated with this edge and the length (i.e., positive weight) of the edge. For example, a line containing the integers 3, 6 and 9 indicates that there is an edge between vertex 3 and vertex 6 with length 9.  
The output should be a single line with the total length needed to irrigate all vertices.  

Example Input:  

7 7 1  
1 2 2  
2 3 100  
3 4 10  
4 5 10  
2 6 10  
6 7 10  
7 4 80  
  
Example Output:  
  
122  


### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    /*
     * We already parse the input for you and should not need to make changes to this part of the code.
     * You are free to change this input format however.
     */
    int n = sc.nextInt();
    int m = sc.nextInt();
    int s = sc.nextInt();
    ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      nodes.add(new HashMap<>());
    }
    for (int i = 0; i < m; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      int cost = sc.nextInt();
      nodes.get(u).put(v, cost);
      nodes.get(v).put(u, cost);
    }
    if (n <= 1) {
      return "0";
    }
    return solve(nodes, s);
  }

  public String solve(ArrayList<HashMap<Integer, Integer>> nodes, int s ) {
    boolean[] visited = new boolean[nodes.size()];
    int[] distances = new int[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      distances[i] = Integer.MAX_VALUE / 2;
    }
    PriorityQueue<Tuple> q = new PriorityQueue<>();
    q.add(new Tuple(s, 0));
    distances[s] = 0;
    int c = 0;
    while (!q.isEmpty()) {
      Tuple curTuple = q.poll();
      if (visited[curTuple.id]) {
        continue;
      }
      visited[curTuple.id] = true;
      // todo
      for (int neighbour : nodes.get(curTuple.id).keySet()) {
        // todo
      }
    }
    return Integer.toString(c);
  }

  class Tuple implements Comparable<Tuple> {

    int id;

    int cost;

    Tuple(int id, int cost) {
      this.id = id;
      this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Tuple tuple = (Tuple) o;
      return id == tuple.id;
    }

    @Override
    public int hashCode() {
      return id;
    }

    @Override
    public int compareTo(Tuple o) {
      int res = Integer.signum(this.cost - o.cost);
      if (res == 0) {
        return Integer.signum(this.id - o.id);
      }
      return res;
    }
  }
}

```

#### Solution:
```java

```
