### Union-by-Rank (9/11 inside job)
The Union-Find data structure allows us to maintain disjoint sets.  
One way to implement the Union-Find data structure is to use Union-by-Rank.  
This data structure can be implemented by using two arrays (root and rank).  
The data structure for this implementation uses pointers.  
Each node has an associated pointer to the name of the set that contains this node.  
The Union-by-Rank data structure has been partly implemented.  
Implement the missing find and union methods.


### Tests:
```java
import static org.junit.Assert.*;
//import java.util.Arrays;
import java.util.Random;
import org.junit.*;
//import org.junit.rules.TestName;

public class UTest {

  @Test
  public void example() {
    UnionFind uf = new UnionFind(10);
    assertEquals(9, uf.find(9));
    assertTrue(uf.union(1, 2));
    assertTrue(uf.union(2, 3));
    assertTrue(uf.union(0, 1));
    assertTrue(uf.union(3, 4));
    // Test that joining any combination will have no effect, as they are already joined
    for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) assertFalse("union(" + i + "," + j + ")", uf.union(i, j));
    // Test whether all first five entries have the same root
    for (int i = 0; i < 4; i++) assertEquals("find(" + i + ")", uf.find(i), uf.find(i + 1));
    // Test whether all last five entries have themselves as root
    for (int i = 5; i < 10; i++) assertEquals("find(" + i + ")", i, uf.find(i));
  }

  @Test
  public void rankNoUnionTest() {
    UnionFind uf = new UnionFind(10);
    // Test whether all entries have themselves as root
    for (int i = 0; i < 10; i++) assertEquals("find(" + i + ")", i, uf.find(i));
  }

  @Test
  public void findWithUnionTest() {
    UnionFind uf = new UnionFind(10);
    assertTrue(uf.union(4, 2));
    assertFalse(uf.union(2, 4));
    assertEquals(uf.find(2), uf.find(4));
  }

  @Test(timeout = 100)
  public void fastEnough() {
    int n = 1_000_000;
    UnionFind uf = new UnionFind(n);
    Random rng = new Random(1234);
    for (int i = 0; i < n / 100; i++) {
      int a = rng.nextInt(n);
      int b = rng.nextInt(n);
      uf.union(a, b);
    }
  }
}
```

### Solution:
```java
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
    int x = find(i); 
    int y = find(j); 
    if(x == y) return false;
    if (rank[x] < rank[y]) 
        parent[x] = y; 
    else if (rank[y] < rank[x]) 
        parent[y] = x; 
    else { 
        parent[x] = y; 
        rank[y]++; 
    } 
    return true;
  }

  /**
   * NB: this function should also do path compression
   * @param i index of a node
   * @return the root of the subtree containg i.
   */
  int find(int i) {
   if (parent[i] != i) 
    parent[i] = find(parent[i]); 
    return parent[i]; 
  }

  // Return the rank of the trees
  public int[] getRank() {
    return rank;
  }

  // Return the parent of the trees
  public int[] getParent() {
    return parent;
  }
}
```

### Alternative

```java
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
}
```
