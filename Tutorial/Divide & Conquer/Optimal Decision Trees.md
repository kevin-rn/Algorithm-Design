Implement the decisionTree method, which takes an integer d and a list of samples and returns an Integer.
The list of samples is a list of Pair where the first element is an Array of 1 or 0 values representing the sample and the second element is an Integer either zero or one, which represents
the label of the sample. The integer d represents the number of allowed internal node layers in a decision tree. The output will be highest amount of correctly classified samples a decision tree
with d internal layers can have on the given list of samples. 

Your task is to report the optimal result for a binary classification tree.
A binary classification tree is a binary tree where each leaf is assigned a label (which is either zero or one) and each internal node is assigned an index.
Given an array of 0-1 values, we may use the classification tree to classify it as follows. Start with the root node and repeat the following recursive procedure:
If the node is a leaf node, return the label of the node. Otherwise, recurse on the left child node if the index-th element of A is zero, otherwise recurse on
the right node.

Pair Class
A Pair class has been provided inside of the library code.

### Template
```java
import java.util.ArrayList;
import java.util.List;

class Solution {

  public static int decisionTree(int d, List<Pair<Integer[], Integer>> samples) {
  // TODO
  }
}

```

### Library
```java
class Pair<L, R> {

  private L l;

  private R r;

  /**
   * Constructor
   * @param l left element
   * @param r right element
   */
  public Pair(L l, R r) {
    this.l = l;
    this.r = r;
  }

  /**
   * @return the left element
   */
  public L getL() {
    return l;
  }

  /**
   * @return the right element
   */
  public R getR() {
    return r;
  }

  /**
   * @param l left element
   */
  public void setL(L l) {
    this.l = l;
  }

  /**
   * @param r right element
   */
  public void setR(R r) {
    this.r = r;
  }
}
```

### Solution
```java

```
