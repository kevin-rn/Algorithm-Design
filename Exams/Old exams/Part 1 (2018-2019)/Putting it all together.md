### Undominated solutions
No input parsing needed by students.

### Template:
```java
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class DominatedSolutions {

  /**
   *  You should implement this method.
   *  @param S the list of solutions to get the pareto optima from.
   *  @return the list of pareto optimal solutions.
   */
  public static List<Solution> getParetoOptima(List<Solution> S) {
  // TODO
  }

  static class Solution {

    int numberOfHours;

    int quality;

    public Solution(int numberOfHours, int quality) {
      this.numberOfHours = numberOfHours;
      this.quality = quality;
    }

    /*
			You should not need the equals and hashcode method below, we just use them in the test.
		 */
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Solution that = (Solution) o;
      return numberOfHours == that.numberOfHours && quality == that.quality;
    }

    @Override
    public int hashCode() {
      return Objects.hash(numberOfHours, quality);
    }
  }
}
```

### Tests:
```java
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

  @Test(timeout = 100)
  public void example() {
    List<DominatedSolutions.Solution> list = new ArrayList<>();
    list.add(new DominatedSolutions.Solution(10, 1));
    list.add(new DominatedSolutions.Solution(9, 7));
    list.add(new DominatedSolutions.Solution(1, 6));
    list.add(new DominatedSolutions.Solution(5, 3));
    List<DominatedSolutions.Solution> result = DominatedSolutions.getParetoOptima(list);
    assertEquals(2, result.size());
    assertTrue(result.contains(new DominatedSolutions.Solution(1, 6)));
    assertTrue(result.contains(new DominatedSolutions.Solution(9, 7)));
  }

  private static List<DominatedSolutions.Solution> parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    List<DominatedSolutions.Solution> list = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      list.add(new DominatedSolutions.Solution(sc.nextInt(), sc.nextInt()));
    }
    sc.close();
    return list;
  }

  private static List<DominatedSolutions.Solution> parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    sc.nextInt();
    sc.nextInt();
    List<DominatedSolutions.Solution> list = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      list.add(new DominatedSolutions.Solution(sc.nextInt(), sc.nextInt()));
    }
    // list.add(sol);
    sc.close();
    return list;
  }

  private static void runTestWithFile(String fileName) {
    List<DominatedSolutions.Solution> S = parseInputFile(fileName + ".in");
    // DominatedSolutions.Solution sol = S.get(S.size()-1);
    // S.remove(S.size()-1);
    List<DominatedSolutions.Solution> theirSolution = DominatedSolutions.getParetoOptima(S);
    theirSolution.sort((t1, t2) -> t1.numberOfHours != t2.numberOfHours ? t1.numberOfHours - t2.numberOfHours : t2.quality - t1.quality);
    assertEquals(parseOutputFile(fileName + ".out"), theirSolution);
  }

  @Test(timeout = 100)
  public void zeroCostAndQuantity() {
    runTestWithFile("zeroCostAndQuantity");
  }

  @Test(timeout = 100)
  public void EqualMaxAndN() {
    runTestWithFile("n_equals_m_equals_1");
  }

  @Test(timeout = 100)
  public void test01() {
    runTestWithFile("n_1000_m_10");
  }

  @Test(timeout = 100)
  public void test02() {
    runTestWithFile("n_1000_m_50");
  }

  @Test(timeout = 100)
  public void test03() {
    runTestWithFile("n_1000_m_100");
  }

  @Test(timeout = 250)
  public void test04() {
    runTestWithFile("n_10000_m_10000");
  }

  @Test(timeout = 100)
  public void test05() {
    runTestWithFile("n_1000_m_1000");
  }

  @Test(timeout = 100)
  public void test06() {
    runTestWithFile("n_1000_m_3000");
  }

  @Test(timeout = 100)
  public void test07() {
    runTestWithFile("n_1000_m_5000");
  }

  @Test(timeout = 100)
  public void test08() {
    runTestWithFile("n_1000_m_50000");
  }

  @Test(timeout = 5000)
  public void test09() {
    runTestWithFile("test_time");
  }
}
```

__________________________________________________________________________________________________________________________

### Solution:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class DominatedSolutions {

  /**
   *  You should implement this method.
   *  @param S the list of solutions to get the pareto optima from.
   *  @return the list of pareto optimal solutions.
   */
  public static List<Solution> getParetoOptima(List<Solution> S) {
    Collections.sort(S);
    return findPareto(S, 0, S.size()-1);
  }
  
  public static List<Solution> findPareto(List<Solution> s, int low, int high) {
    if (low == high) {
      List<Solution> result = new ArrayList<>();
      result.add(s.get(low));
      return result;
    }
    int mid = (low + high)/2;
    List<Solution> l = findPareto(s, low, mid);
    List<Solution> r = findPareto(s, mid + 1, high);
    Solution sol = l.get(l.size() - 1);
    l.addAll(undominatedBy(sol, r));
    return l;
  }
  
  public static List<Solution> undominatedBy(Solution sol, List<Solution> S) {
    List<Solution> result = new ArrayList<>();
    for(Solution s: S) {
      if (!sol.dominated(s)) result.add(s);
    }
    return result;
  }

  static class Solution implements Comparable<Solution> {

    int numberOfHours;

    int quality;

    public Solution(int numberOfHours, int quality) {
      this.numberOfHours = numberOfHours;
      this.quality = quality;
    }

    public boolean dominated(Solution other) {
      if(this.quality > other.quality && this.numberOfHours <= other.numberOfHours) return true;
      else if(this.quality >= other.quality && this.numberOfHours < other.numberOfHours) return true;
      return false;
    }
    
    @Override
    public int compareTo(Solution other) {
      return Integer.compare(this.numberOfHours, other.numberOfHours);
    }

    /*
			You should not need the equals and hashcode method below, we just use them in the test.
		 */
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Solution that = (Solution) o;
      return numberOfHours == that.numberOfHours && quality == that.quality;
    }

    @Override
    public int hashCode() {
      return Objects.hash(numberOfHours, quality);
    }
  }
}
```

_____________________________________________________________________________________________________________

### Official solution:
```java
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class DominatedSolutions {

  /**
   *  You should implement this method.
   *  @param S the list of solutions to get the pareto optima from.
   *  @return the list of pareto optimal solutions.
   */
  public static List<Solution> getParetoOptima(List<Solution> S) {
    List<Solution> result = S;
    Collections.sort(result, (t1, t2) -> t1.numberOfHours - t2.numberOfHours);
    if (result.size() > 1) {
      result = findPareto(result, 0, result.size() - 1);
    }
    return result;
  }

  private static List<Solution> findPareto(List<Solution> list, int begin, int end) {
    List<Solution> result = new ArrayList<>();
    if (begin == end) {
      result.add(list.get(begin));
    } else {
      int mid = (begin + end) / 2;
      List<Solution> left = findPareto(list, begin, mid);
      List<Solution> right = findPareto(list, mid + 1, end);
      Solution lastLeft = left.get(left.size() - 1);
      left.addAll(undominatedBy(lastLeft, right));
      result = left;
    }
    return result;
  }

  private static List<Solution> findParetoAlternative(List<Solution> list, int begin, int end) {
    List<Solution> result = new ArrayList<>();
    if (list.isEmpty()) {
      return result;
    } else if (list.size() == 1) {
      result.add(list.get(begin));
    } else if (list.size() == 2) {
      result.addAll(undominatedBy(list.get(0), list));
      result.addAll(undominatedBy(list.get(1), list));
      Collections.sort(result, (t1, t2) -> t1.numberOfHours - t2.numberOfHours);
    } else {
      double mid = list.stream().mapToInt(s -> s.numberOfHours).average().getAsDouble();
      List<Solution> left = findParetoAlternative(list.stream().filter(s -> s.numberOfHours <= mid).collect(Collectors.toList()), 0, 0);
      List<Solution> right = findParetoAlternative(list.stream().filter(s -> s.numberOfHours > mid).collect(Collectors.toList()), 0, 0);
      Solution lastLeft = left.get(left.size() - 1);
      left.addAll(undominatedBy(lastLeft, right));
      result = left;
    }
    return result;
  }

  public static List<Solution> undominatedBy(Solution sol, List<Solution> S) {
    List<Solution> result = new ArrayList<>(S.size());
    for (Solution s : S) {
      if (!((sol.quality > s.quality && sol.numberOfHours <= s.numberOfHours) || (sol.numberOfHours < s.numberOfHours && sol.quality >= s.quality))) {
        result.add(s);
      }
    }
    return result;
  }

  static class Solution {

    int numberOfHours;

    int quality;

    public Solution(int numberOfHours, int quality) {
      this.numberOfHours = numberOfHours;
      this.quality = quality;
    }

    /*
			You should not need the equals and hashcode method below, we just use them in the test.
		 */
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Solution that = (Solution) o;
      return numberOfHours == that.numberOfHours && quality == that.quality;
    }

    @Override
    public int hashCode() {
      return Objects.hash(numberOfHours, quality);
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_1_2018_2019/dc_full/data/";
    File dir = new File(dirName);
    System.out.println(dir.exists());
    for (File f : dir.listFiles()) {
      if (f.getName().endsWith("in")) {
        FileInputStream in = new FileInputStream(f);
        System.out.println(f.getAbsolutePath());
        String ans = run(in);
        System.out.println(ans);
        PrintWriter out = new PrintWriter(f.getAbsolutePath().replace(".in", ".out"));
        out.println(ans);
        out.close();
        System.out.println();
      }
    }
  }

  public static String run(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    Solution sol = new Solution(sc.nextInt(), sc.nextInt());
    List<Solution> list = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      list.add(new Solution(sc.nextInt(), sc.nextInt()));
    }
    List<Solution> result = DominatedSolutions.getParetoOptima(list);
    result.sort((t1, t2) -> t1.numberOfHours != t2.numberOfHours ? t1.numberOfHours - t2.numberOfHours : t2.quality - t1.quality);
    StringBuilder sb = new StringBuilder();
    sb.append(result.size());
    sb.append('\n');
    for (Solution r : result) {
      sb.append(r.numberOfHours);
      sb.append(' ');
      sb.append(r.quality);
      sb.append('\n');
    }
    return sb.toString();
  }

  public static List<Solution> undominatedBySameCost(List<Solution> S) {
    List<Solution> result = new ArrayList<>(S.size());
    Collections.sort(S, (t1, t2) -> t1.numberOfHours - t2.numberOfHours);
    Solution lastOne = S.get(0);
    for (Solution s : S) {
      if (s.numberOfHours > lastOne.numberOfHours) {
        result.add(lastOne);
        lastOne = s;
      } else {
        if (s.quality > lastOne.quality) {
          lastOne = s;
        }
      }
    }
    result.add(lastOne);
    return result;
  }
}
```
