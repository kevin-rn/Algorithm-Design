// Test:
import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  private long time = 0;

  @Rule
  public TestName name = new TestName();

  @Before
  public void setUp() {
    time = System.currentTimeMillis();
  }

  @After
  public void tearDown() {
    System.out.println("Test '" + name.getMethodName() + "' took " + (System.currentTimeMillis() - time) + "ms");
  }

  public static String refEncoding(Node node) {
    String result = "";
    while (node.parent != null) {
      if (node.parent.getLeftChild() == node) {
        result = "0" + result;
      } else {
        result = "1" + result;
      }
      node = node.parent;
    }
    return result;
  }

  public static Node refSolution(int n, char[] characters, double[] frequencies) {
    PriorityQueue<Node> nodes = new PriorityQueue<>(new RefNodeComperator());
    for (int i = 1; i <= n; i++) {
      nodes.add(new Node(characters[i], frequencies[i]));
    }
    while (true) {
      Node smallestFrequency = nodes.poll();
      if (nodes.isEmpty()) {
        return smallestFrequency;
      } else {
        Node secondSmallest = nodes.poll();
        Node newNode = combineNodes(secondSmallest, smallestFrequency);
        smallestFrequency.setParent(newNode);
        secondSmallest.setParent(newNode);
        nodes.offer(newNode);
      }
    }
  }

  private static Node combineNodes(Node child1, Node child2) {
    Node newNode = new Node((char) 0, child1.frequency + child2.frequency);
    newNode.leftChild = child1;
    newNode.rightChild = child2;
    newNode.parent = null;
    return newNode;
  }

  static class RefNodeComperator implements Comparator<Node> {

    @Override
    public int compare(Node node, Node node2) {
      return Double.compare(node.frequency, node2.frequency);
    }
  }

  private static class ProblemInstance {

    int n;

    char[] c;

    double[] f;

    public ProblemInstance(int n, char[] t, double[] p) {
      this.n = n;
      this.c = t;
      this.f = p;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    char[] t = new char[n + 1];
    double[] p = new double[n + 1];
    for (int i = 1; i <= n; i++) {
      t[i] = (char) sc.nextInt();
      p[i] = sc.nextDouble();
    }
    sc.close();
    return new ProblemInstance(n, t, p);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    Node ourSolution = refSolution(S.n, S.c, S.f);
    Node theirSolution = Huffman.buildHuffman(S.n, S.c, S.f);
    assertEquals(getValue(ourSolution, 0, S.c, S.f), getValue(theirSolution, 0, S.c, S.f), 1e-5);
    List<Node> ourNodes = BFS(ourSolution);
    List<Node> theirNodes = BFS(theirSolution);
    assertEquals(ourNodes.size(), theirNodes.size());
    Set<Character> ourChars = new HashSet<>();
    for (Node node : ourNodes) {
      ourChars.add(node.symbol);
    }
    Set<Character> theirChars = new HashSet<>();
    for (Node node : theirNodes) {
      theirChars.add(node.symbol);
    }
    assertEquals(ourChars, theirChars);
  }

  public static double getFreq(char c, char[] chars, double[] freq) {
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == c) {
        return freq[i];
      }
    }
    return -1;
  }

  public static double getValue(Node root, int depth, char[] chars, double[] freq) {
    if (root.leftChild == null && root.rightChild == null) {
      return getFreq(root.symbol, chars, freq) * depth;
    }
    double leftValue = 0;
    double rightValue = 0;
    if (root.leftChild != null) {
      leftValue = getValue(root.leftChild, depth + 1, chars, freq);
    }
    if (root.rightChild != null) {
      rightValue = getValue(root.rightChild, depth + 1, chars, freq);
    }
    return leftValue + rightValue;
  }

  public static List<Node> BFS(Node root) {
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    LinkedList<Node> result = new LinkedList<>();
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      if (node.leftChild == null && node.rightChild == null) {
        result.add(node);
      }
      if (node.leftChild != null) {
        queue.add(node.leftChild);
      }
      if (node.rightChild != null) {
        queue.add(node.rightChild);
      }
    }
    return result;
  }

  @Test(timeout = 100)
  public void exampleEncoding() {
    Node root = new Node((char) 0, 1);
    Node eNode = new Node('e', 0.6, root);
    Node xNode = new Node('x', 0.4, root);
    root.setLeftChild(eNode);
    root.setRightChild(xNode);
    assertEquals("0", Huffman.encode(eNode));
    assertEquals("1", Huffman.encode(xNode));
  }

  /**
   * Tree looks like this:
   *               root
   *     combined         combined
   *   e          x     f          g
   */
  @Test(timeout = 100)
  public void exampleEncoding2() {
    Node root = new Node((char) 0, 1);
    Node leftRoot = new Node((char) 0, 0.5, root);
    root.setLeftChild(leftRoot);
    Node eNode = new Node('e', 0.25, leftRoot);
    leftRoot.setLeftChild(eNode);
    Node xNode = new Node('x', 0.25, leftRoot);
    leftRoot.setRightChild(xNode);
    Node rightRoot = new Node((char) 0, 0.5, root);
    root.setRightChild(rightRoot);
    Node fNode = new Node('f', 0.25, rightRoot);
    rightRoot.setLeftChild(fNode);
    Node gNode = new Node('g', 0.25, rightRoot);
    rightRoot.setRightChild(gNode);
    assertEquals("00", Huffman.encode(eNode));
    assertEquals("01", Huffman.encode(xNode));
    assertEquals("10", Huffman.encode(fNode));
    assertEquals("11", Huffman.encode(gNode));
  }

  @Test(timeout = 100)
  public void specTestEncoding26() {
    ProblemInstance S = parseInputFile("twentySixNodes_3.in");
    Node ourSolution = refSolution(S.n, S.c, S.f);
    ourSolution.frequency = 1;
    List<Node> nodes = BFS(ourSolution);
    for (Node n : nodes) {
      if (n.symbol > 0) {
        assertEquals(refEncoding(n), Huffman.encode(n));
      }
    }
  }

  @Test(timeout = 100)
  public void specTestEncoding100() {
    ProblemInstance S = parseInputFile("hundredNodes_5.in");
    Node ourSolution = refSolution(S.n, S.c, S.f);
    ourSolution.frequency = 1;
    List<Node> nodes = BFS(ourSolution);
    for (Node n : nodes) {
      if (n.symbol > 0) {
        assertEquals(refEncoding(n), Huffman.encode(n));
      }
    }
  }

  @Test(timeout = 100)
  public void exampleBuildTree() {
    int n = 2;
    char[] chars = { 0, 'a', 'b' };
    double[] freq = { 0, 0.7, 0.3 };
    Node tree = Huffman.buildHuffman(n, chars, freq);
    assertNotNull(tree);
    assertNotNull(tree.leftChild);
    assertNotNull(tree.rightChild);
    if (tree.leftChild.symbol != 'a') {
      assertEquals('a', tree.rightChild.symbol);
      assertEquals('b', tree.leftChild.symbol);
    } else {
      assertEquals('a', tree.leftChild.symbol);
      assertEquals('b', tree.rightChild.symbol);
    }
  }

  @Test(timeout = 100)
  public void buildTreeHidden() {
    int n = 2;
    char[] chars = { 0, 'a', 'b' };
    double[] freq = { 0, 0.7, 0.3 };
    Node tree = Huffman.buildHuffman(n, chars, freq);
    assertEquals(1, getValue(tree, 0, chars, freq), 1e-5);
  }

  @Test(timeout = 100)
  public void buildLargerTreeCorrectValue() {
    int n = 5;
    char[] chars = { 0, 'a', 'b', 'c', 'd', 'e' };
    double[] freq = { 0, 0.25, 0.05, 0.1, 0.3, 0.3 };
    Node tree = Huffman.buildHuffman(n, chars, freq);
    Node ourTree = refSolution(n, chars, freq);
    assertEquals(getValue(ourTree, 0, chars, freq), getValue(tree, 0, chars, freq), 1e-5);
  }

  @Test(timeout = 100)
  public void buildLargerTreeCorrectNodes() {
    int n = 5;
    char[] chars = { 0, 'a', 'b', 'c', 'd', 'e' };
    double[] freq = { 0, 0.2, 0.1, 0.1, 0.4, 0.2 };
    Node tree = Huffman.buildHuffman(n, chars, freq);
    List<Node> nodes = BFS(tree);
    assertEquals(5, nodes.size());
    Set<Character> givenChars = new HashSet<>();
    for (Node node : nodes) {
      givenChars.add(node.getSymbol());
    }
    Set<Character> expectedChars = new HashSet<>();
    for (char c : chars) {
      if (c != 0) {
        expectedChars.add(c);
      }
    }
    assertEquals(expectedChars, givenChars);
  }
}

// _____________________________________________________________________________________________________________________________________________
// Solution:

package weblab;

import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Huffman {

  /**
   *  You should implement this method.
   *  @param node A Node in the Huffman encoding tree
   *  @return the encoded string representing the character in this node.
   */
  public static String encode(Node node) {
    String result = "";
    while (node.parent != null) {
      if (node.parent.getLeftChild() == node) {
        result = "0" + result;
      } else {
        result = "1" + result;
      }
      node = node.parent;
    }
    return result;
  }

  /**
   *  You should implement this method.
   *  @param n the number of characters that need to be encoded.
   *  @param characters The characters c_1 through c_n. Note you should use only characters[1] up to and including characters[n]!
   *  @param frequencies The frequencies f_1 through f_n. Note you should use only frequencies[1] up to and including frequencies[n]!
   *  @return The rootnode of an optimal Huffman tree that represents the encoding of the characters given.
   */
  public static Node buildHuffman(int n, char[] characters, double[] frequencies) {
    return properTreeBuilding(n, characters, frequencies);
  // return onlySort(n, characters, frequencies);
  }

  public static Node properTreeBuilding(int n, char[] characters, double[] frequencies) {
    PriorityQueue<Node> nodes = new PriorityQueue<>(new NodeComperator());
    for (int i = 1; i <= n; i++) {
      nodes.add(new Node(characters[i], frequencies[i]));
    }
    while (true) {
      Node smallestFrequency = nodes.poll();
      if (nodes.isEmpty()) {
        return smallestFrequency;
      } else {
        Node secondSmallest = nodes.poll();
        Node newNode = new Node(smallestFrequency, secondSmallest);
        smallestFrequency.setParent(newNode);
        secondSmallest.setParent(newNode);
        nodes.offer(newNode);
      }
    }
  }

  public static Node onlySort(int n, char[] characters, double[] frequencies) {
    PriorityQueue<Node> nodes = new PriorityQueue<>(new NodeComperator().reversed());
    for (int i = 1; i <= n; i++) {
      nodes.add(new Node(characters[i], frequencies[i]));
    }
    Node root = new Node((char) 0, 1);
    Node cur = root;
    while (!nodes.isEmpty()) {
      Node largestFreq = nodes.poll();
      if (cur.leftChild == null) {
        cur.leftChild = largestFreq;
      } else if (nodes.isEmpty()) {
        cur.rightChild = largestFreq;
      } else {
        cur.rightChild = new Node((char) 0, 0);
        cur = cur.rightChild;
        cur.leftChild = largestFreq;
      }
      largestFreq.parent = cur;
    }
    return root;
  }
}

class NodeComperator implements Comparator<Node> {

  @Override
  public int compare(Node node, Node node2) {
    return Double.compare(node.frequency, node2.frequency);
  }
}

/**
 * NOTE: You should ensure that if a Node is a part of a tree, then all nodes in the tree have their `parent`, `leftChild`, and `rightChild` set appropriately!
 * You may add methods to this class, provided you do not change the names of existing methods or fields!
 */
class Node {

  char symbol;

  double frequency;

  Node parent;

  Node leftChild;

  Node rightChild;

  public Node(char symbol, double frequency) {
    this.symbol = symbol;
    this.frequency = frequency;
  }

  public Node(char symbol, double frequency, Node parent) {
    this(symbol, frequency);
    this.parent = parent;
  }

  public Node(char symbol, double frequency, Node leftChild, Node rightChild) {
    this(symbol, frequency);
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  public Node(Node child1, Node child2) {
    this.symbol = 0;
    this.frequency = child1.frequency + child2.frequency;
    this.leftChild = child2;
    this.rightChild = child1;
    this.parent = null;
  }

  public char getSymbol() {
    return symbol;
  }

  public double getFrequency() {
    return frequency;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public Node getLeftChild() {
    return leftChild;
  }

  public void setLeftChild(Node leftChild) {
    this.leftChild = leftChild;
  }

  public Node getRightChild() {
    return rightChild;
  }

  public void setRightChild(Node rightChild) {
    this.rightChild = rightChild;
  }
}

