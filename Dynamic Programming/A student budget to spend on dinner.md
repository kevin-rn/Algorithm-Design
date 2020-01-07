### A student budget to spend on dinner
You are given a knapsack that you must fill optimally.  
Given a knapsack of weight W, and n items each with a weight and value, find a subset of items in the knapsack such that the value of the combined items is maximized.  
There are no precedence requirements or subset requirements between the subsets themselves, meaning you may add or remove any item as long as the final value is maximized.  
Implement an algorithm that finds this maximized value, utilizing dynamic programming, in at most O(nW) time.

You may assume all given instances have a valid solution, and not trick solutions will be given to you.

For this exercise you are required to parse the input yourself.
The input is provided in an InputStream that you can for instance pass to a Scanner and is structured as follows:

The first line contains the maximum knapsack weight W
as a positive integer and the number of items n.  
Then, for each item n, there is a single line containing two positive integers separated by a space.  
These numbers represent the weight of that item, and its value, respectively.  
Your algorithm must output the maximum possible value for that instance. An example input is given below.

10 3  
8 25     
3 4     
5 9     

The output for this example is ‘25’, since the only other combination yields ‘4 + 9’, which is less than ‘25’.

### Template:
```java
import java.io.*;
import java.util.*;

class Solution {

  // inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
  // TODO
  }
}

```

### Test:
```java
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
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

  private static void runTestWithFile(String fileName) {
    assertEquals(WebLab.getData(fileName + ".out").trim(), Solution.run(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8))).trim());
  }

  @Test(timeout = 100)
  public void example() {
    runTestWithFile("example");
  }

  @Test(timeout = 100)
  public void test02() {
    runTestWithFile("test2");
  }

  @Test(timeout = 100)
  public void test03() {
    runTestWithFile("test3");
  }

  @Test(timeout = 100)
  public void test04() {
    runTestWithFile("test4");
  }

  @Test(timeout = 100)
  public void test05() {
    runTestWithFile("test5");
  }

  @Test(timeout = 100)
  public void test06() {
    runTestWithFile("test6");
  }

  @Test(timeout = 2000)
  public void test07() {
    runTestWithFile("test7");
  }
}

```

___________________________________________________________________________________________________________________
### Solution:
```java
package weblab;

import java.io.*;
import java.util.*;

class Solution {

  // inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  public String solve(InputStream in) {
    ArrayList<Item> items = new ArrayList<Item>();
    Scanner sc = new Scanner(in);
    int w = sc.nextInt(), n = sc.nextInt();
    
    for(int i = 0; i < n; i++) items.add(new Item(sc.nextInt(), sc.nextInt()));
    sc.close();
    int[][] sack = new int[n+1][w+1];
    for(int i=0; i<=n;i++) {
      for(int j=0; j<=w; j++) {
        if(i == 0 || j ==0) sack[i][j] = 0;
        else {
          if(items.get(i-1).weight > j) sack[i][j] = sack[i-1][j];
          else sack[i][j] = Math.max(items.get(i-1).value + sack[i-1][j - items.get(i-1).weight] , sack[i-1][j]);
        }
      }
    }
    return "" + sack[n][w];
  }
}
  
class Item {
    int weight, value;
    
    public Item(int weight, int value) {
      this.weight = weight;
      this.value = value;
    }
}


```
