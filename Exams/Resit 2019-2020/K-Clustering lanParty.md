In these uncertain times you have taken to playing online boardgames with your friends and family.
After playing for a few weeks however, the group of people interested in joining your Friday Games sessions has grown quite large.
The group is so large, that the speed and fun of the game is suffering.
It turns out that this speed is determined by the player who has the poorest network connection.
Obviously, you need to split up to play the games in smaller groups.
You decide to do this using a peer-to-peer implementation instead of relying on the server.

The quality of a game using the peer-to-peer clients depends on the network delay (ping time) between the players in the game as follows: the highest quality is obtained if all clients are (indirectly) connected to each other while minimising the sum of the network delays of the used connections.
To make matters worse for your planning, some people have already decided that no matter what, they want to be directly connected.

You have access to n premium accounts for the website and so want to create a total of n groups.
You are given the group of k≥n people, the ping times between them, and the connections that you have to use as these players want to be in the same group no matter what.
Implement the function lanParty that given this information computes the (minimum) sum of the ping times of the extra connections you need to make to create n connected groups.

### Template:
```java
import java.io.;
import java.util.;

class Solution {

  /**
   * You should implement this method.
   * @param n the number of groups that need to be created.
   * @param k the total number of friends.
   * @param c 2D-array with all the costs of connecting people, g[i][j] represents the costs of connecting friend \(1 \leq i \leq n\) with friend \(1 \leq j \leq n\). You should ignore g[0][j] and g[i][0].
   * @param e 2D-array with all connections that already exist, g[i][j] is true iff there is already a direct connection between friend \(1 \leq i \leq n\) and friend \(1 \leq j \leq n\). You should ignore e[0][j] and e[i][0].
   * @return the costs of the connections that still need to be made.
   */
  public static int lanParty(int n, int k, int[][] c, boolean[][] e) {
  // TODO
  }
}
```


### Test:
```java
import static org.junit.Assert.assertEquals;
import java.util.*;
import org.junit.*;
import org.junit.rules.TestName;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    int n = 1;
    int k = 4;
    int[][] c = { { 0, 0, 0, 0, 0 }, { 0, 0, 1, 100, 100 }, { 0, 1, 0, 1, 100 }, { 0, 100, 1, 0, 1 }, { 0, 100, 100, 1, 0 } };
    // All false, nobody is connnected yet.
    boolean[][] e = new boolean[k + 1][k + 1];
    // Best solution is to connect 1 to 2, 2 to 3, and 3 to 4, this means three connections of cost 1.
    assertEquals(3, Solution.lanParty(n, k, c, e));
  }

  @Test(timeout = 100)
  public void keepGivenConnections() {
    int n = 1;
    int k = 4;
    int[][] c = { { 0, 0, 0, 0, 0 }, { 0, 0, 1, 100, 100 }, { 0, 1, 0, 1, 100 }, { 0, 100, 1, 0, 1 }, { 0, 100, 100, 1, 0 } };
    // All false, nobody is connnected yet.
    boolean[][] e = new boolean[k + 1][k + 1];
    // But now we connect 2 and 4
    e[2][4] = true;
    // Best solution is to connect 1 to 2, and 3 to 4, this means two connections of cost 1 (since 2 and 4 are already connected).
    assertEquals(2, Solution.lanParty(n, k, c, e));
  }
}

```


### Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  /**
   * You should implement this method.
   * @param n the number of groups that need to be created.
   * @param k the total number of friends.
   * @param c 2D-array with all the costs of connecting people, g[i][j] represents the costs of connecting friend \\(1 \leq i \leq n\\) with friend \\(1 \leq j \leq n\\). You should ignore g[0][j] and g[i][0].
   * @param e 2D-array with all connections that already exist, g[i][j] is true iff there is already a _direct_ connection between friend \\(1 \leq i \leq n\\) and friend \\(1 \leq j \leq n\\). You should ignore e[0][j] and e[i][0].
   * @return the costs of the connections that still need to be made.
   */
  public static int lanParty(int n, int k, int[][] c, boolean[][] e) {
    UnionFind uf = new UnionFind(k); 
    
    // List is used together with sort but PriorityQueue would also work.
    ArrayList<Cost> cost = new ArrayList<>();
    
    // Keep count of how many groups there are
    int groups = k;
    
    // Create connection of every existing one or else add possible connection to list
    for(int i = 1; i <= k; i++) {
      for(int j = i + 1; j <= k; j++) {
        if(e[i][j] && uf.union(i-1, j-1)) groups--;
        else cost.add(new Cost(i, j, c[i][j]));
      }
    }
    
    // Sort list so lowest cost go first
		Collections.sort(cost);
		
		// Create remaining connections until the number of groups has been created
		int minimumSum = 0;
		for(int i = 0; i < cost.size() && groups > n; i++) {
			Cost current = cost.get(i);
			if(uf.union(current.i - 1, current.j - 1)) {
				minimumSum += current.c;
				groups--;
			}
		}
		
		return minimumSum;
  }
  
}

// Helperclass for sorting.
class Cost implements Comparable<Cost> {
  int i, j, c;
  public Cost(int i, int j, int c) {
    this.i = i;
    this.j = j;
    this.c = c;
  }
  
  @Override
  public int compareTo(Cost other) {
    return Integer.compare(this.c, other.c);
  }
}

// Union-find to cluster groups together.
class UnionFind {
  int[] parent;
  int[] rank;
  
  public UnionFind(int size) {
    this.parent = new int[size];
    this.rank = new int[size];
    for(int i = 0; i < size; i++) parent[i] = i;
  }
  
  public boolean union(int i, int j) {
    int x = find(i);
    int y = find(j);
    if(x == y) return false;
    if(rank[x] < rank[y]) parent[x] = y;
    else if(rank[x] > rank[y]) parent[y] = x;
    else {
      parent[x] = y;
      rank[y]++;
    }
    return true;
  }
  
  private int find(int i) {
    return parent[i] == i ? i : (parent[i] = find(parent[i]));
  }
}
```

