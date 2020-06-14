This assignment is part of Dynamic Programming TA Review Task 2A.2,
please do the theoretical assignment before doing the practical part.

Implement the function that finds the minimum total sum of squares of the slacks of all lines and returns a pretty-printed version of the input string.

The first line contains the line Length L, the second line contains the amount of words n and the next line contains the input string.

Given the following problem instance:

42
13
The Answer to the Great Question of Life, the Universe and Everything is Forty-two.

we expect the following as answer:
["The Answer to the Great Question of Life,", "the Universe and Everything is Forty-two."]

### Template:
```java
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Solution {

  public static List<String> solve(InputStream in) {
  // TODO
  }
}
```

### Test:
```java
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    InputStream in = new ByteArrayInputStream(("42\n14\nThe Answer to the Great Question of Life, " + "the Universe and Everything is Forty-two.").getBytes());
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("The Answer to the Great Question of Life,");
    expected.add("the Universe and Everything is Forty-two.");
    assertEquals(expected, Solution.solve(in));
  }
}
```
___________________________________________________________________
### Solution:
```java
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Solution {

  public static List<String> solve(InputStream in) {
    // Read the input file
    Scanner sc = new Scanner(in);
    int L = sc.nextInt();
    int n = sc.nextInt();
    String[] words = new String[n];
    for (int i = 0; i < n; i++) {
      words[i] = sc.next();
    }
    sc.close();
    int[][] slacks = computeSlacks(L, n, words);
    int[] opt = calculateOpt(n, slacks);
    return prettyPrint(n, opt, slacks, words);
  }

  private static List<String> prettyPrint(int j, int[] opt, int[][] slacks, String[] words) {
    if (j == 0) {
      return new ArrayList<String>();
    }
    // Keep track of current best value
    int printVal = 1;
    int curVal = Integer.MAX_VALUE;
    for (int i = 1; i <= j; i++) {
      if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) {
        // skip max values due to overflows when squaring.
        continue;
      }
      int squared = slacks[i - 1][j - 1] * slacks[i - 1][j - 1];
      // Find the current best for this line
      if (squared + opt[i - 1] < curVal) {
        curVal = squared + opt[i - 1];
        printVal = i;
      }
    }
    // Recursively traverse the words, start with the last word of the previous sentence
    List<String> result = prettyPrint(printVal - 1, opt, slacks, words);
    // Build this line
    String res = String.join(" ", Arrays.asList(words).subList(printVal - 1, j));
    result.add(res);
    return result;
  }

  private static int[] calculateOpt(int n, int[][] slacks) {
    int[] opt = new int[n + 1];
    for (int j = 1; j <= n; j++) {
      opt[j] = Integer.MAX_VALUE;
      for (int i = 1; i <= j; i++) {
        if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) {
          // skip max values due to overflows when squaring.
          continue;
        }
        int squared = slacks[i - 1][j - 1] * slacks[i - 1][j - 1];
        // Check whether new opt was found for j.
        if (squared + opt[i - 1] < opt[j]) {
          opt[j] = squared + opt[i - 1];
        }
      }
    }
    return opt;
  }

  private static int[][] computeSlacks(int L, int n, String[] words) {
    int[][] slacks;
    slacks = new int[n][n];
    // Fill the slacks array.
    for (int i = 0; i < n; i++) {
      // Compensate for no initial space
      int l = -1;
      for (int j = i; j < n; j++) {
        // Add one word incl. space
        l = l + words[j].length() + 1;
        slacks[i][j] = l > L ? Integer.MAX_VALUE : L - l;
      }
    }
    return slacks;
  }
}

```

