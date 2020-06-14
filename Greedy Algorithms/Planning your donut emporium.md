You are the owner of a new store chain (called Amazing Donuts, or AD for short), and business is running well, so you decide to open up some more stores.
You decide to do this in the town of Scatterville, where the houses of all residents are literally scattered around the village.
Of course, you want to reach as many people with your donuts as possible. Since you want to place knew stores in Scatterville, 
you decide to divide the houses in k “clusters”, and each cluster will get its own store.
Every house will belong to exactly one store cluster.

Given the coordinates of the houses in Scatterville, and how many stores you will open, can you calculate where the new stores should be placed?

Consider the following example.
Here, three stores will be opened in Scatterville.
The first and second clusters contain four houses each,
while the third cluster only contains two houses.
The stores will be placed at the centers of these clusters.

Public Library classes

We have already provided several classes that might be useful for you in the Library tab.
You cannot edit these classes, but you can use them in your implementation.
Input and output

The first line of the input stream will contain two numbers:
n (with 1≤n≤1000), which is the number of houses;
and k (with 1≤k≤n), which is the number of stores you will open.
Following this are n lines, each with two integers, which are the x and y coordinates of each house.

You should return a string that contains k lines, each line containing two numbers, which are the x and y coordinates of each new store. The order of the stores does not matter, and per number you can have a maximum relative error of 10−6.

The following example represents the image above:

10 3
4 5
5 10
7 7
8 20
11 6
12 22
22 15
23 9
25 12
26 17

The resulting output would be in this case:

6.75 7
10 21
24 13.25



### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int k = sc.nextInt();
    List<House> houses = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      houses.add(new House(i, sc.nextInt(), sc.nextInt()));
    }
    int m = n * (n - 1) / 2;
    List<Distance> distances = new ArrayList<>(m);
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        distances.add(new Distance(houses.get(i), houses.get(j)));
      }
    }
    UnionFind unionFind = new UnionFind(houses);
    // TODO

  }
}
```

### Library:
```java
import java.util.*;

class House {

  int id, x, y;

  public House(int id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
  }
}

class Distance {

  House a, b;

  long distance;

  public Distance(House a, House b) {
    this.a = a;
    this.b = b;
    // Square Euclidean distance, to avoid floating-point errors
    this.distance = (long) (a.x - b.x) * (a.x - b.x) + (long) (a.y - b.y) * (a.y - b.y);
  }
}

class UnionFind {

  private List<House> houses;

  private int[] parent;

  private int[] rank;

  UnionFind(List<House> houses) {
    this.houses = houses;
    int n = houses.size();
    parent = new int[n];
    rank = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
  }

  /**
   * Joins two disjoint sets together, if they are not already joined.
   * @return false if x and y are in same set, true if the sets of x and y are now joined.
   */
  boolean join(House x, House y) {
    int xrt = find(x.id);
    int yrt = find(y.id);
    if (rank[xrt] > rank[yrt])
      parent[yrt] = xrt;
    else if (rank[xrt] < rank[yrt])
      parent[xrt] = yrt;
    else if (xrt != yrt)
      rank[parent[yrt] = xrt]++;
    return xrt != yrt;
  }

  /**
   * @return The house that is indicated as the "root" of the set of house h.
   */
  House find(House h) {
    return houses.get(find(h.id));
  }

  private int find(int x) {
    return parent[x] == x ? x : (parent[x] = find(parent[x]));
  }

  /**
   * @return All clusters of houses
   */
  Collection<List<House>> clusters() {
    Map<Integer, List<House>> map = new HashMap<>();
    for (int i = 0; i < parent.length; i++) {
      int root = find(i);
      if (!map.containsKey(root))
        map.put(root, new ArrayList<>());
      map.get(root).add(houses.get(i));
    }
    return map.values();
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

  static class Store implements Comparable<Store> {

    double x, y;

    public Store(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public Store(String s) {
      String[] split = s.split(" ");
      x = Double.parseDouble(split[0]);
      y = Double.parseDouble(split[1]);
    }

    @Override
    public int compareTo(Store store) {
      return Comparator.<Store>comparingDouble(s -> s.x).thenComparingDouble(s -> s.y).compare(this, store);
    }
  }

  private static void runTestWithFile(String fileName) {
    assertApproximatelyEquals(WebLab.getData(fileName + ".out").trim(), Solution.run(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8))).trim());
  }

  // Sorts the resulting stores by coordinate (so order doesn't matter) and checks whether it's within 1e-6 error
  private static void assertApproximatelyEquals(String expected, String actual) {
    String[] expectedStrings = expected.split("\n");
    String[] actualStrings = actual.split("\n");
    if (expectedStrings.length != actualStrings.length)
      throw new ComparisonFailure("Lengths of lists different!", Integer.toString(expectedStrings.length), Integer.toString(actualStrings.length));
    Store[] expectedStores = Arrays.stream(expectedStrings).map(Store::new).sorted().toArray(Store[]::new);
    Store[] actualStores = Arrays.stream(actualStrings).map(Store::new).sorted().toArray(Store[]::new);
    for (int i = 0; i < actualStores.length; i++) {
      Store s1 = expectedStores[i], s2 = actualStores[i];
      assertEquals("x-coordinate of store " + i, s1.x, s2.x, s1.x * 1e-6);
      assertEquals("y-coordinate of store " + i, s1.y, s2.y, s1.y * 1e-6);
    }
  }

  private static void runSmallTest(String in, String out) {
    assertApproximatelyEquals(out.trim(), Solution.run(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8))).trim());
  }

  @Test(timeout = 100)
  public void example() {
    runTestWithFile("example");
  }

  @Test(timeout = 100)
  public void oneHouse() {
    runSmallTest("1 1\n1 1", "1 1");
  }

  @Test(timeout = 100)
  public void twoHousesOneCluster() {
    runSmallTest("2 1\n1 1\n3 3", "2 2");
  }

  @Test(timeout = 100)
  public void twoHousesTwoClusters() {
    runSmallTest("2 2\n1 1\n3 3", "1 1\n3 3");
  }

  @Test(timeout = 100)
  public void threeHousesTwoClusters() {
    runSmallTest("3 2\n1 1\n3 3\n4 4", "1 1\n3.5 3.5");
  }

  @Test(timeout = 100)
  public void floaters() {
    runSmallTest("3 1\n1 1\n1 1\n2 2", "1.333333333333333 1.333333333333333");
  }

  @Test(timeout = 2000)
  public void n1000k3() {
    runTestWithFile("1000points3clusters");
  }

  @Test(timeout = 2000)
  public void n1000k500() {
    runTestWithFile("1000points500clusters");
  }
}

```

_____________________________________________________________________________________________________________________

### Official Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int k = sc.nextInt();
    List<House> houses = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      houses.add(new House(i, sc.nextInt(), sc.nextInt()));
    }
    int m = n * (n - 1) / 2;
    List<Distance> distances = new ArrayList<>(m);
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        distances.add(new Distance(houses.get(i), houses.get(j)));
      }
    }
    UnionFind unionFind = new UnionFind(houses);
    {
      // Sort by shortest distance
      distances.sort(Comparator.comparingLong(d -> d.distance));
      int count = 0;
      for (Distance d : distances) {
        if (count == n - k)
          break;
        if (unionFind.join(d.a, d.b))
          count++;
      }
      StringBuilder res = new StringBuilder();
      for (List<House> cluster : unionFind.clusters()) {
        long c = 0, sumX = 0, sumY = 0;
        for (House house : cluster) {
          c++;
          sumX += house.x;
          sumY += house.y;
        }
        // Adding 1e-6 to test if it's accepted
        res.append((double) sumX / c + 1e-6).append(' ').append((double) sumY / c).append('\n');
      }
      return res.toString();
    }
  }
}
```


### My Solution:
```java
import java.io.*;
import java.util.*;

class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) { return new Solution().solve(in); }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int k = sc.nextInt();
    List<House> houses = new ArrayList<>(n);
    for (int i = 0; i < n; i++) houses.add(new House(i, sc.nextInt(), sc.nextInt()));
    sc.close();
    int m = n * (n - 1) / 2;
    List<Distance> distances = new ArrayList<>(m);
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        distances.add(new Distance(houses.get(i), houses.get(j)));
      }
    }
    UnionFind unionFind = new UnionFind(houses);
    distances.sort(Comparator.comparingLong(d -> d.distance));
    int count = 0;
    for(Distance distance : distances) {
      if(count == n -k) break;
      if(unionFind.join(distance.a, distance.b)) count++;
    }
    StringBuilder str = new StringBuilder();
    for(List<House> cluster : unionFind.clusters()) {
      long c = 0, sumX = 0, sumY = 0;
      for (House house : cluster) {
        c++;
        sumX += house.x;
        sumY += house.y;
      }
      str.append((double) sumX / c + 1e-6).append(' ').append((double) sumY / c).append('\n');
    }
    return str.toString();
  }
}
```
