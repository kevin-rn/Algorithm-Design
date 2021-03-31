### Undominated solutions

### Template:
```java
package weblab;

import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class DominatedSolutions {

  /**
   *  You should implement this method.
   *  @param sol a solution to compare the others to.
   *  @param S a set of solutions
   *  @return all solutions in S that are _not_ dominated by sol.
   */
  public static List<Solution> undominatedBy(Solution sol, List<Solution> S) {
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
		 * You should not need to change the equals and hashCode methods below. Our tests just use them.
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

### Test
```java
package weblab;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import org.junit.*;
//import org.junit.rules.*;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    // The resulting list of solutions should contain (9,7), (1,8), (5,5)
    List<DominatedSolutions.Solution> list = new ArrayList<>();
    list.add(new DominatedSolutions.Solution(10, 1));
    list.add(new DominatedSolutions.Solution(9, 7));
    list.add(new DominatedSolutions.Solution(1, 8));
    list.add(new DominatedSolutions.Solution(5, 5));
    DominatedSolutions.Solution sol = new DominatedSolutions.Solution(4, 4);
    List<DominatedSolutions.Solution> result = DominatedSolutions.undominatedBy(sol, list);
    assertEquals(3, result.size());
    assertTrue(result.contains(new DominatedSolutions.Solution(1, 8)));
    assertTrue(result.contains(new DominatedSolutions.Solution(9, 7)));
    assertTrue(result.contains(new DominatedSolutions.Solution(5, 5)));
  }
}

```

________________________________________________________________________________________________________________________

### Solution:
```java
package weblab;

//import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class DominatedSolutions {

  /**
   *  You should implement this method.
   *  @param sol a solution to compare the others to.
   *  @param S a set of solutions
   *  @return all solutions in S that are _not_ dominated by sol.
   */
  public static List<Solution> undominatedBy(Solution sol, List<Solution> S) {
    List<Solution> result = new ArrayList<>();
    for(Solution s:S) {
      if (!sol.dominated(s)) result.add(s);
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
    
    public boolean dominated(Solution other) {
      if(this.quality > other.quality && this.numberOfHours <= other.numberOfHours) return true;
      else if(this.quality >= other.quality && this.numberOfHours < other.numberOfHours) return true;
      return false;
    }

    /*
		 * You should not need to change the equals and hashCode methods below. Our tests just use them.
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
