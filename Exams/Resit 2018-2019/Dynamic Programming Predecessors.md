### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class SkippingAround {

  public static int[] solve(int n, int[] s, int[] f) {
  // TODO
  }

  public static class Item {

    int s;

    int f;

    public Item(int s, int f) {
      this.s = s;
      this.f = f;
    }
  }

  private static class SortByStartTime implements Comparator<Integer> {

    private Item[] items;

    public SortByStartTime(Item[] items) {
      this.items = items;
    }

    @Override
    public int compare(Integer a, Integer b) {
      Item item = items[a];
      Item item2 = items[b];
      int x = Integer.compare(item.s, item2.s);
      if (x == 0) {
        x = Integer.compare(item.f, item2.f);
      }
      return x;
    }
  }
}


```

### Test:
```java
import static org.junit.Assert.*;
import java.util.Scanner;
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

  private static class ProblemInstance {

    int n;

    int[] s;

    int[] f;

    public ProblemInstance(int n, int[] s, int[] f) {
      this.n = n;
      this.s = s;
      this.f = f;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int[] s = new int[n + 1];
    int[] f = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      s[i] = sc.nextInt();
      f[i] = sc.nextInt();
    }
    sc.close();
    return new ProblemInstance(n, s, f);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    StringBuilder sb = new StringBuilder();
    int[] sol = SkippingAround.solve(S.n, S.s, S.f);
    for (int i = 0; i < sol.length; i++) {
      sb.append(sol[i]);
      sb.append('\n');
    }
    assertEquals(WebLab.getData(fileName + ".out").trim(), sb.toString().trim());
  }
}
```

_______________________________________________________________________________________________________________________
### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class SkippingAround {

  public static int[] solve(int n, int[] s, int[] f) {
    return solveProper(n, s, f);
  }

  public static int[] solveProper(int n, int[] s, int[] f) {
    int[] skip = new int[n + 1];
    Item[] items = new Item[n + 1];
    Integer[] itemsS = new Integer[n + 1];
    // Should never be used.
    items[0] = new Item(-1000, -1000);
    itemsS[0] = 0;
    for (int i = 1; i <= n; i++) {
      items[i] = new Item(s[i], f[i]);
      itemsS[i] = i;
    }
    Arrays.sort(itemsS, new SortByStartTime(items));
    for (int i = 1; i <= n; i++) {
      skip[i] = 0;
      for (int j = 1; j < i; j++) {
        if (items[j].f > items[i].s) {
          skip[i]++;
        }
      }
    }
    return skip;
  }

  public static class Item {

    int s;

    int f;

    public Item(int s, int f) {
      this.s = s;
      this.f = f;
    }
  }

  private static class SortByStartTime implements Comparator<Integer> {

    private Item[] items;

    public SortByStartTime(Item[] items) {
      this.items = items;
    }

    @Override
    public int compare(Integer a, Integer b) {
      Item item = items[a];
      Item item2 = items[b];
      int x = Integer.compare(item.s, item2.s);
      if (x == 0) {
        x = Integer.compare(item.f, item2.f);
      }
      return x;
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/resit_2_2018_2019/dynamic_programming_pred/data/";
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
    int[] s = new int[n + 1];
    int[] f = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      s[i] = sc.nextInt();
      f[i] = sc.nextInt();
    }
    sc.close();
    int[] skip = SkippingAround.solve(n, s, f);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < skip.length; i++) {
      sb.append(skip[i]);
      sb.append('\n');
    }
    return sb.toString();
  }
}
```
