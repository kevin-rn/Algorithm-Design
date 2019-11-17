Implement the search method which takes a BinaryTree and Integer as input and tries to find the specified element in the tree.

This search has to be done in a depth first manner, without using additional data structures.
Public Library classes

We have already provided several classes that might be useful for you in the Library tab.
You cannot edit these classes, but you can use them in your implementation.

### Library:
```java
package weblab;

class BinaryTree {

  private int key;

  private BinaryTree left, right;

  /**
   * Simple constructor.
   *
   * @param key
   *     to set as key.
   */
  public BinaryTree(int key) {
    this.key = key;
  }

  /**
   * Extended constructor.
   *
   * @param key
   *     to set as key.
   * @param left
   *     to set as left child.
   * @param right
   *     to set as right child.
   */
  public BinaryTree(int key, BinaryTree left, BinaryTree right) {
    this.key = key;
    setLeft(left);
    setRight(right);
  }

  public int getKey() {
    return key;
  }

  /**
   * @return the left child.
   */
  public BinaryTree getLeft() {
    return left;
  }

  /**
   * @return the right child.
   */
  public BinaryTree getRight() {
    return right;
  }

  public boolean hasLeft() {
    return left != null;
  }

  public boolean hasRight() {
    return right != null;
  }

  /**
   * @param left
   *     to set
   */
  public void setLeft(BinaryTree left) {
    this.left = left;
  }

  /**
   * @param right
   *     to set
   */
  public void setRight(BinaryTree right) {
    this.right = right;
  }
}
```

### Test:
```java
package weblab;

import static org.junit.Assert.*;
//import java.util.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 200)
  public void example() {
    Solution s = new Solution();
    BinaryTree tree = new BinaryTree(42, new BinaryTree(1337), new BinaryTree(39));
    assertTrue(s.search(tree, 42));
    assertFalse(s.search(tree, 100));
  }
}
```

### Template:
```java
package weblab;

class Solution {

  /**
   * Recursively searches for the element.
   * Returns true if element can be found, else false.
   *
   * @param tree
   *     - tree that you need to look in.
   * @param element
   *     - the element that you are looking for.
   * @return true if found, else false.
   */
  public boolean search(BinaryTree tree, int element) {
  // TODO
  }
}
```

### Solution:
```java
package weblab;

class Solution {

  /**
   * Recursively searches for the element.
   * Returns true if element can be found, else false.
   *
   * @param tree
   *     - tree that you need to look in.
   * @param element
   *     - the element that you are looking for.
   * @return true if found, else false.
   */
  public boolean search(BinaryTree tree, int element) {
  if(tree == null) return false;
  if(tree.getKey() == element) return true;
  boolean left = false, right = false;
  if(tree.hasLeft()) left = search(tree.getLeft(), element);
  if(tree.hasRight()) right = search(tree.getRight(), element);
  return left || right;
  }
}
```
