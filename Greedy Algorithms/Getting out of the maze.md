### Getting out of the maze (2/11)
A maze is represented by a directed (and weighted, but you only need that for the next problem) graph G=(V,E), where V denotes the set containing n vertices and E the set containing m directed edges.
Each vertex represents an intersection or end point in the maze and the edges represent paths between them.
A directed edge is used for (downhill) tunnels and holes that you can jump into, but where it is impossible to get back.
Because of this, it may become impossible to reach the exit.

Design and implement an algorithm that determines whether it is possible to get from a given point s to the exit of the maze t.
Your algorithm should print yes or no and run in O(n+m) time.

Some exercises (like this one) require you to think about how to store your input data in an efficient format. In some of these exercises we will therefore ask you to read the data from files yourself. 
Below we give some explanation as to how reading such files works in WebLab.

##### Reading input and writing output

The input for this exercise will be read from a file that is stored on WebLab
and passed to your solve method as an InputStream.
You can wrap this stream in a Scanner to make reading easier.
This has already been done in the solution template on the right.
The output should be returned as a String.

##### Description of input and output

The first line of the input contains four integers separated by a space: n, m, s and t. The integers n and m are defined as above and you may assume the vertices are labelled 1 up to and including n. E.g. if n equals 6, the vertices are labelled 1,2,3,4,5 and 6. The integers s and t are the numbers of the vertices representing your starting point and the exit of the maze, respectively.
The next m lines are the directed edges. Each line contains three integers: the two numbers of the vertices associated with this edge and the length (i.e., positive weight) of the edge. For example, a line containing the integers 3, 6 and 9 indicates that there is an edge from vertex 3 to vertex 6 with length 9.

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
yes


```java
package weblab;

import java.io.*;
import java.util.*;

class Node {

  List<Node> outgoingEdges;

  boolean marked;

  public Node() {
    this.outgoingEdges = new ArrayList<>();
    this.marked = false;
  }
}

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    int s = sc.nextInt()-1;
    int t = sc.nextInt()-1;
    Node[] nodes = new Node[n];
    for (int i = 0; i < n; i++) {
        nodes[i] = new Node();
      }
    for (int i = 0; i < m; i++) {
      int u = sc.nextInt()-1;
      int v = sc.nextInt()-1;
      sc.nextInt();
      nodes[u].outgoingEdges.add(nodes[v]);
      }
    sc.close();
    bfs(nodes[s]);
    if(nodes[t].marked == true) {
      return "yes";
    }
    else {
      return "no";
    }
  }

  public static void bfs(Node current) {
      Queue<Node> q = new ArrayDeque<>();
      current.marked = true;
      q.add(current);
      
      while (!q.isEmpty()) {
        Node node = q.remove();
        
        for (Node n : node.outgoingEdges) {
          if (!n.marked) {
            n.marked = true;
            q.add(n);
          }
        }
        
      }
    }
}

```
