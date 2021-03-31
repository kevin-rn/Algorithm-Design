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
    /* Read the first line */
    int n = sc.nextInt();
    int m = sc.nextInt();
    int s = sc.nextInt();
    int t = sc.nextInt();
    /* Create the nodes */
    Map<Integer, Node> nodes = new HashMap<>();
    for (int i = 1; i <= n; i++) {
      nodes.put(i, new Node());
    }
    /* Add all the edges */
    for (int i = 1; i <= m; i++) {
      int from = sc.nextInt();
      int to = sc.nextInt();
      sc.nextInt();
      nodes.get(from).outgoingEdges.add(nodes.get(to));
    }
    sc.close();
    /* For BFS we use a queue to ensure the FCFS ordering */
    Queue<Node> q = new LinkedList<>();
    /* The first node is visited immediately */
    nodes.get(s).marked = true;
    q.add(nodes.get(s));
    /* Continue until there is nothing else to explore */
    while (!q.isEmpty()) {
      Node node = q.remove();
      if (node == nodes.get(t)) {
        /* We found the node, so it must be reachable */
        return "yes";
      }
      for (Node to : node.outgoingEdges) {
        /* Add all the not yet marked nodes to the queue so that we explore them eventually */
        if (!to.marked) {
          to.marked = true;
          q.add(to);
        }
      }
    }
    /* We were unable to find it. */
    return "no";
  }
}
