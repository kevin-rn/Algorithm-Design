Misinformation is one of the most dangerous issues that Phoenix faces in his work as a lawyer.
It is paramount in his investigations and cases that he is able to distinguish fake evidence from real evidence.
In order to do so, he has asked you to divide his collected evidence into different groups based on the similarity of the evidence.

The focus for now is on written documents, which can easily be expressed with a dissimilarity score di,j. A higher score means documents are more different.
Given a set of n documents, you are tasked to split them into k>1 groups such that the smallest score between different groups is maximised (meaning that documents that are similar are in the same group).

Since these documents contain a lot of Legalese, the dissimilarity score is not perfect. Phoenix has already done a first round and selected some documents which he assures you must be in the same group as they are really similar.
Even if the dissimilarity score between them is very high, Phoenix’ opinion should overrule this score and they should still always be put in the same group.

Given these prerequisites implement the method legalisingDocuments() which returns the smallest score between any two groups in the optimally created k
groups. Return 0 if it is impossible to make k groups (e.g. because of the first round of information from Phoenix).

### Template
```java
import java.io.*;
import java.util.*;

class WrightAndCo {

  /**
   * You should implement this method.
   * @param n the number of documents to sort.
   * @param k the number of groups that they need to be sorted in.
   * @param d 2D-array with all the dissimilarity scores, d[i][j] represents the dissimilarity score of document \\(1 \leq i \leq n\\) and document \\(1 \leq j \leq n\\). You should ignore d[0][j] and d[i][0].
   * @param e 2D-array indicating which documents Phoenix has judged similar, ei][j] is true iff Phoenix has determined that a document \\(1 \leq i \leq n\\) and document \\(1 \leq j \leq n\\) must be in the same group. You should ignore e[0][j] and e[i][0].
   * @return the smallest dissimilarity score between different groups in the optimal grouping.
   */
  public static int legalisingDocuments(int n, int k, int[][] d, boolean[][] e) {
    
  }
}
```

### Solution
```java
import java.io.*;
import java.util.*;

class WrightAndCo {

	/**
	 * You should implement this method.
	 * 
	 * @param n the number of groups that they need to be sorted in.
	 * @param k the number of documents to sort.
	 * @param d 2D-array with all the dissimilarity scores, d[i][j] represents the
	 *          dissimilarity score of document \\(1 \leq i \leq n\\) and document
	 *          \\(1 \leq j \leq n\\). You should ignore d[0][j] and d[i][0].
	 * @param e 2D-array indicating which documents Phoenix has judged similar,
	 *          ei][j] is true iff Phoenix has determined that a document \\(1 \leq
	 *          i \leq n\\) and document \\(1 \leq j \leq n\\) must be in the same
	 *          group. You should ignore e[0][j] and e[i][0].
	 * @return the smallest dissimilarity score between different groups in the
	 *         optimal grouping.
	 */
	public static int legalisingDocuments(int n, int k, int[][] d, boolean[][] e) {
	  PriorityQueue<Distance> pq = new PriorityQueue<>();
	  
	  UnionFind uf = new UnionFind(k);
	  int groups = k;
	  
	  // Traversing upper triangle of dissimilarity matrix
	  for(int i = 1; i <= k; i++) {
	    for(int j = i + 1; j <= k; j++) {
	      if(e[i][j] && uf.union(i, j)) groups--;
        pq.add(new Distance(i, j, d[i][j]));
	    }
	  }
	  
	  if(groups < n) return 0;
	  
    while(groups != n) {
      Distance current = pq.poll();
      if(uf.union(current.i, current.j)) groups--;
    }
    
    return pq.poll().distance;	  
	}

}

class Distance implements Comparable<Distance> {
  public int i, j;
  public int distance;
  
  public Distance(int i, int j, int d) {
    this.i = i;
    this.j = j;
    this.distance = d;
  }
  
  @Override
  public int compareTo(Distance o) {
    return this.distance - o.distance;
  }
}

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
    
    // Adapting to the 1-indexed arrays of the solution with (i - 1) and (j - 1)
    int parent1 = find(i - 1);
    int parent2 = find(j - 1);
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
	 * 
	 * @param i index of a node
	 * @return the root of the subtree containg i.
	 */
	int find(int i) {
		parent[i] == i ? i : (parent[i] = find(parent[i]))
	}

}
```
