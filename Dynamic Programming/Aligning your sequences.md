### Aligning your sequences
The sequence alignment problem aims to find the optimal alignment of two strings such that the mismatch penalty is minimized. 
For each mismatched character, you are allowed to add a penalty of ‘1’.
You are given two strings, of lengths n and m respectively, on two separate rows.  
The lengths of the strings can be different.  
The given strings are also entirely lower case, you do not have to re-format the given input to account for that.  
Your algorithm must calculated the minimum sequence alignment cost, and return it as an integer.  
An example input is given below.

kitten
sitting  

This example should return ‘3’.

### Template: 
```java
import java.util.*;

class Solution {

  public static int solve(String firstString, String secondString) {
  // TODO
  }
}

```

### Test: 
```java
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
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

  private static void runTestWithFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName + ".in"));
    String a = sc.next();
    String b = sc.next();
    sc.close();
    int expected = Integer.parseInt(WebLab.getData(fileName + ".out").trim());
    assertEquals(expected, Solution.solve(a, b));
  }

  @Test(timeout = 100)
  public void example() {
    String a = "kitten";
    String b = "sitting";
    assertEquals(3, Solution.solve(a, b));
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

  @Test(timeout = 100)
  public void test07() {
    runTestWithFile("test7");
  }

  @Test(timeout = 400)
  public void test08() {
    runTestWithFile("test8");
  }

  @Test(timeout = 100)
  public void test09() {
    runTestWithFile("test9");
  }

  @Test(timeout = 100)
  public void test10() {
    runTestWithFile("test10");
  }

  @Test(timeout = 400)
  public void test11() {
    runTestWithFile("test11");
  }
}

```

___________________________________________________________________________________________________________
### Solution:
```java
class Solution {

  public static int solve(String firstString, String secondString) {
  int[][] result = new int[firstString.length()+1][secondString.length()+1];
  for(int i=0; i<=firstString.length(); i++) result[i][0] = i;
  for(int j=0; j<=secondString.length(); j++) result[0][j] = j;
  
  for(int j =1; j<= secondString.length(); j++) {
    for(int i = 1; i<= firstString.length(); i++) {
      int a = result[i-1][j] + 1;
      int b = result[i][j-1] + 1;
      int c =  (firstString.charAt(i-1)== secondString.charAt(j-1)) ? result[i-1][j-1] : result[i-1][j-1]+1;
      result[i][j] = Math.min(Math.min(a, b), c);
    }
  }
  return result[firstString.length()][secondString.length()];
  }
}

```
