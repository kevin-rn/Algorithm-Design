### Getting out the fastest (4/11)

A maze is represented by a directed (and weighted, but you only need that for the next problem) graph G = (V,E), 
where V denotes the set containing n vertices and E the set containing m directed edges.  
Each vertex represents an intersection or end point in the maze and the edges represent paths between them.  
A directed edge is used for (downhill) tunnels and holes that you can jump into, but where it is impossible to get back.  
Because of this, it may become impossible to reach the exit.  
  
Some edges take longer than others, which is expressed in their weight.  
Additionally the sheer number of options you can chose from in every vertex overwhelms you quite a bit, so every vertex takes 1 
time step per outgoing edge (because you have to find out what is the correct one).  
  
Design and implement an algorithm that determines the path from s to t that takes the least amount of time (which is the sum of lengths of all edges plus for all vertices (except t) the number of outgoing edges).   
Let the algorithm just print the total time of this path. Aim for the most efficient algorithm you can think of. Extremely slow implementations will not be accepted.  

The input is structured the same as for Greedy task 0C: “Can we get out?”:  

The first line of the input contains four integers separated by a space: n, m, s and t.  
The integers n and m are defined as above and you may assume the vertices are labelled 1 up to and including n.   
E.g. if n equals 6, the vertices are labelled 1,2,3,4,5 and 6. The integers s and t are the numbers of the vertices representing your starting point and the exit of the maze, respectively.  
The next m lines are the directed edges. Each line contains three integers: the two numbers of the vertices associated with this edge and the length (i.e., positive weight) of the edge. 
For example, a line containing the integers 3, 6 and 9 indicates that there is an edge from vertex 3 to vertex 6 with length 9.  
The output should be a single line with the time spent on the shortest path if one exists, or -1 otherwise.  

Example Input:  
7 7 1 5  
1 2 2  
2 3 100  
3 4 10  
4 5 10  
2 6 10  
6 7 10  
7 4 80  

Example Output:  
118  
      
Used Dijkstra algorithm for shortest path (https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/)      
    
### Slow Solution:
```java
import java.io.*;
import java.util.*;

class Solution {
  
  class Node implements Comparator<Node> { 
    public int start; 
    public int cost; 
  
    public Node() {} 
  
    public Node(int start, int cost) { 
        this.start = start; 
        this.cost = cost; 
    } 
  
    @Override
    public int compare(Node start1, Node start2) { 
        if (start1.cost < start2.cost) return -1; 
        if (start1.cost > start2.cost) return 1; 
        return 0; 
    } 
} 
  
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
    int t = sc.nextInt();
    if(s > n || t > n) { 
      sc.close();
      return "-1";
    }
    ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<HashMap<Integer, Integer>>();
    Integer[] dist = new Integer[n+1];
    for (int i = 0; i <= n; i++) {
      nodes.add(new HashMap<>());
      dist[i] = Integer.MAX_VALUE;
    }
    for (int i = 0; i < m; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      int cost = sc.nextInt();
      nodes.get(u).put(v, cost);
    }
    sc.close();
    Set<Integer> settled = new HashSet<Integer>(); 
    PriorityQueue<Node> pq = new PriorityQueue<Node>(n, new Node()); 
    pq.add(new Node(s,0));
    settled.add(s);
    dist[s] = 0;
    while(!pq.isEmpty()) {
      int u = pq.remove().start;
      if(!settled.contains(u)) settled.add(u);
      if(u==t) return dist[u].toString();
      int edist = 0, newdist = 0; 
      for (Integer i : nodes.get(u).keySet()) {
         Node v = new Node(i, nodes.get(u).get(i));
         if (!settled.contains(v.start)) {
            edist = v.cost; 
            newdist = dist[u] + edist + nodes.get(u).size(); 
            if (newdist < dist[v.start]) {
              dist[v.start] = newdist; 
            }
            pq.add(new Node(v.start, dist[v.start])); 
          } 
        }
      }
    return "-1";
  }
}
```
  
    
      
     
     
### Gotta go fast Solution:
```java
import java.io.*;
import java.util.*;

class Solution {
  
  class Node implements Comparator<Node> { 
    public int start; 
    public int cost; 
  
    public Node() {} 
  
    public Node(int start, int cost) { 
        this.start = start; 
        this.cost = cost; 
    } 
  
    @Override
    public int compare(Node start1, Node start2) { 
        if (start1.cost < start2.cost) return -1; 
        if (start1.cost > start2.cost) return 1; 
        return 0; 
    } 
} 
  
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
    int t = sc.nextInt();
    if(s > n || t > n) { 
      sc.close();
      return "-1";
    }
    ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<HashMap<Integer, Integer>>();
    Integer[] dist = new Integer[n+1];
    for (int i = 0; i <= n; i++) {
      nodes.add(new HashMap<>());
      dist[i] = Integer.MAX_VALUE;
    }
    for (int i = 0; i < m; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      int cost = sc.nextInt();
      nodes.get(u).put(v, cost);
    }
    sc.close();
    Set<Integer> settled = new HashSet<Integer>(); 
    PriorityQueue<Node> pq = new PriorityQueue<Node>(n, new Node()); 
    pq.add(new Node(s,0));
    settled.add(s);
    dist[s] = 0;
    while(!pq.isEmpty()) {
      int u = pq.remove().start;
      if(u==t) return dist[u].toString();
      if(!settled.contains(u)) settled.add(u);
      for (Map.Entry<Integer, Integer> en : nodes.get(u).entrySet()) {
          if(dist[u] + en.getValue() + nodes.get(u).size() < dist[en.getKey()]) {
            dist[en.getKey()] = dist[u] + en.getValue() + nodes.get(u).size();
            pq.add(new Node(en.getKey(), dist[en.getKey()])); 
          }
        }
      }
    return "-1";
  }
}
```
