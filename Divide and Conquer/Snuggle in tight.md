Description

In this exercise you will have to implement an algorithm to find the closest pair of points.
One way to find the closest pair is by going over all possible pairs, which consists of n2 possibilities.
To make this more efficient you will have to implement an algorithm that has as worst case run-time complexity O(nlogn).
Pseudocode and explanation of this algorithm can be found in chapter 5.4 of your book.

Public Library classes

We have already provided several classes that might be useful for you in the Library tab.
You cannot edit these classes, but you can use them in your implementation.

### Template:
```java
import java.util.*;

class Solution {

  /**
   * Takes a list of points and returns the distance between the closest pair.
   * This is done with divide and conquer.
   *
   * @param points
   *     - list of points that need to be considered.
   * @return smallest pair-wise distance between points.
   */
  public static double closestPair(List<Point> points) {
  // TODO
  }
}


```

### Library:
```java
import java.util.*;

/**
 * Class representing a 2D point.
 */
class Point {

  public double x;

  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
}

/**
 * Useful methods for this assignment.
 */
class Util {

  /**
   * Takes two points and computes the euclidean distance between the two points.
   *
   * @param point1 - first point.
   * @param point2 - second point.
   * @return euclidean distance between the two points.
   * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">https://en.wikipedia.org/wiki/Euclidean_distance</a>
   */
  public static double distance(Point point1, Point point2) {
    return Math.sqrt(Math.pow(point1.x - point2.x, 2.0D) + Math.pow(point1.y - point2.y, 2.0D));
  }

  /**
   * Takes a list of points and sorts it on x (ascending).
   *
   * @param points - points that need to be sorted.
   */
  public static void sortByX(List<Point> points) {
    Collections.sort(points, Comparator.comparingDouble(point -> point.x));
  }

  /**
   * Takes a list of points and sorts it on y (ascending) .
   *
   * @param points - points that need to be sorted.
   */
  public static void sortByY(List<Point> points) {
    Collections.sort(points, Comparator.comparingDouble(point -> point.y));
  }

  /**
   * Takes a list of points and returns the distance between the closest pair.
   * This is done by brute forcing.
   *
   * @param points - list of points that need to be considered.
   * @return smallest pair-wise distance between points.
   */
  public static double bruteForce(List<Point> points) {
    int size = points.size();
    if (size <= 1)
      return Double.POSITIVE_INFINITY;
    double bestDist = Double.POSITIVE_INFINITY;
    for (int i = 0; i < size - 1; i++) {
      Point point1 = points.get(i);
      for (int j = i + 1; j < size; j++) {
        Point point2 = points.get(j);
        double distance = Util.distance(point1, point2);
        if (distance < bestDist)
          bestDist = distance;
      }
    }
    return bestDist;
  }
}

```

### Tests:
```java
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
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

  private static /*
For this implementation the input will be parsed for you.

The input will come as following, we first start with an int for the number of points in your dataset, followed by two doubles per row, one for each coordinate of a 2D point.
```
6
2.0 3.0
12.0 30.0
40.0 50.0
5.0 1.0
12.0 10.0
3.0 4.0
```

The expected result is stored in a file with one row with the answer as a double.

```
1.4142135623730951
```
   */
  void runTestWithFile(String fileName) {
    List<Point> points = parseInput(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8)));
    double expected = Double.parseDouble(WebLab.getData(fileName + ".out").trim());
    assertEquals(expected, Solution.closestPair(points), expected * 1e-6);
  }

  private static List<Point> parseInput(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    List<Point> points = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      points.add(new Point(sc.nextDouble(), sc.nextDouble()));
    }
    sc.close();
    return points;
  }

  @Test(timeout = 1000)
  public void testTwoPoints() {
    List<Point> points = new ArrayList<>();
    points.add(new Point(1, 2));
    points.add(new Point(4, 6));
    assertEquals(5, Solution.closestPair(points), 5e-6);
  }

  @Test(timeout = 1000)
  public void testSmall() {
    List<Point> points = new ArrayList<>();
    points.add(new Point(2, 3));
    points.add(new Point(12, 30));
    points.add(new Point(40, 50));
    points.add(new Point(5, 1));
    points.add(new Point(12, 10));
    points.add(new Point(3, 4));
    assertEquals(1.4142135623730951, Solution.closestPair(points), 1e-6);
  }

  @Test(timeout = 1000)
  public void testLarge() {
    runTestWithFile("testLarge");
  }

  @Test(timeout = 1000)
  public void testMassive() {
    runTestWithFile("testMassive");
  }

  @Test(timeout = 100)
  public void testBorder() {
    runTestWithFile("testBorder");
  }

  @Test(timeout = 100)
  public void testDelta() {
    runTestWithFile("testDelta");
  }

  @Test(timeout = 1000)
  public void testInsane() {
    runTestWithFile("testInsane");
  }
}

```

__________________________________________________________________________________________________________________

### Solution:
```java
import java.util.*;

class Solution {

  /**
   * Takes a list of points and returns the distance between the closest pair.
   * This is done with divide and conquer.
   *
   * @param points
   *     - list of points that need to be considered.
   * @return smallest pair-wise distance between points.
   */
  public static double closestPair(List<Point> points) {
    if (points.size() <= 1) {
      return Double.POSITIVE_INFINITY;
    }
    List<Point> pointsX = new ArrayList<>(points);
    Util.sortByX(pointsX);
    List<Point> pointsY = new ArrayList<>(points);
    Util.sortByY(pointsY);
    return closestPair(pointsX, pointsY);
  }

  /**
   * Helper method that recursively finds the smallest distance.
   *
   * @param pointsX
   *     - a list of points sorted on x (ascending).
   * @param pointsY
   *     - a list of points sorted on y (ascending).
   * @return smallest pair-wise distance between points.
   */
  private static double closestPair(List<Point> pointsX, List<Point> pointsY) {
    int size = pointsX.size();
    if (size <= 3) {
      return Util.bruteForce(pointsX);
    }
    int mid = size / 2;
    List<Point> leftField = pointsX.subList(0, mid);
    List<Point> rightField = pointsX.subList(mid, size);

    List<Point> sortedLeft = new ArrayList<>();
    List<Point> sortedRight = new ArrayList<>();
    for (Point point: pointsY) {
      if (point.x < pointsX.get(mid).x)   {
        sortedLeft.add(point);
      } else {
        sortedRight.add(point);
      }
    }
    double left = closestPair(leftField, sortedLeft);

    double right = closestPair(rightField, sortedRight);

    double delta = Math.min(left, right);
    List<Point> midStrip = new ArrayList<>();
    double midX = leftField.get(leftField.size() - 1).x;
    for (Point point : pointsY) if (Math.abs(midX - point.x) < delta)
      midStrip.add(point);
    for (int i = 0; i < midStrip.size() - 1; i++) {
      Point point1 = midStrip.get(i);
      for (int j = i + 1; j < midStrip.size() && (midStrip.get(j).y - midStrip.get(i).y < delta); j++) {
        Point point2 = midStrip.get(j);
        double distance = Util.distance(point1, point2);
        if (distance < delta) {
          delta = distance;
        }
      }
    }
    return delta;
  }
}
```

### Different formatting Solution:
```java
import java.util.*;

class Solution {

  /**
   * Takes a list of points and returns the distance between the closest pair.
   * This is done with divide and conquer.
   *
   * @param points
   *     - list of points that need to be considered.
   * @return smallest pair-wise distance between points.
   */
  public static double closestPair(List<Point> points) {
    if (points.size() <= 1) return Double.POSITIVE_INFINITY;
    List<Point> pointsX = new ArrayList<>(points), pointsY = new ArrayList<>(points);
    Util.sortByX(pointsX);
    Util.sortByY(pointsY);
    return closestPair(pointsX, pointsY);
  }

  /**
   * Helper method that recursively finds the smallest distance.
   *
   * @param pointsX
   *     - a list of points sorted on x (ascending).
   * @param pointsY
   *     - a list of points sorted on y (ascending).
   * @return smallest pair-wise distance between points.
   */
  private static double closestPair(List<Point> pointsX, List<Point> pointsY) {
    int size = pointsX.size();
    if (size <= 3) return Util.bruteForce(pointsX);
    int mid = size / 2;
    List<Point> leftField = pointsX.subList(0, mid), rightField = pointsX.subList(mid, size);
    List<Point> sortedLeft = new ArrayList<>(), sortedRight = new ArrayList<>();
    for (Point point: pointsY) {
      if (point.x < pointsX.get(mid).x) sortedLeft.add(point);
      else sortedRight.add(point);
    }
    double left = closestPair(leftField, sortedLeft), right = closestPair(rightField, sortedRight);
    double delta = Math.min(left, right);
    List<Point> midStrip = new ArrayList<>();
    double midX = leftField.get(leftField.size() - 1).x;
    for (Point point : pointsY) if (Math.abs(midX - point.x) < delta) midStrip.add(point);
    for (int i = 0; i < midStrip.size() - 1; i++) {
      Point point1 = midStrip.get(i);
      for (int j = i + 1; j < midStrip.size() && (midStrip.get(j).y - midStrip.get(i).y < delta); j++) {
        Point point2 = midStrip.get(j);
        double distance = Util.distance(point1, point2);
        if (distance < delta) delta = distance;
      }
    }
    return delta;
  }
}
```
