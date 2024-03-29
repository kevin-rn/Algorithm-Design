### Scheduling Court Cases

The first line of input has two numbers C and m the number of court cases and courtrooms respectively.
After that follow C lines each comprised of a single word representing the name of the case and a single integer 0≤t≤10000

that represents the time the case takes in hours, such that ith line gives the time of court case i.

You should output the total number of hours we need to open the court room.

Example input

5 2
Butz 16
Fey 8
Powers 7
Edgeworth 5
Skye 16

Example output

33

### Test:
```java
import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
import org.junit.*;

public class UTest {

  private static void runTestWithFile(String fileName) {
    assertEquals(WebLab.getData(fileName + ".out").trim(), Solution.run(new ByteArrayInputStream(WebLab.getData(fileName + ".in").getBytes(StandardCharsets.UTF_8))).trim());
  }

  private static void runTestWithStrings(String in, String out) {
    assertEquals(out.trim(), Solution.run(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8))).trim());
  }

  @Test(timeout = 100)
  public void example() {
    runTestWithFile("example");
  }

  @Test(timeout = 100)
  public void test01() {
    // Tests with 1000 court cases and 10 court rooms.
    runTestWithFile("n_1000_m_10");
  }

  @Test(timeout = 100)
  public void exampleWithStrings() {
    runTestWithStrings("4 1\nByrde 1\nFey 2\nGalactica 3\nEngarde 4", "13");
  }
}
```
#### Template:
```java
package weblab;

import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  private int numCases;

  private int numCourtRooms;

  private long[] caseTimes;

  public String solve(InputStream in) {
    parseInput(in);
    return Long.toString(computeLastFinish());
  }

  /**
   *  You should implement this method to compute when the last court case will be finished.
   */
  private long computeLastFinish() {
  // TODO
  }

  /**
   *  This method parses the input from an inputstream. You should not need to modify this method.
   */
  private void parseInput(InputStream in) {
    Scanner sc = new Scanner(in);
    this.numCases = sc.nextInt();
    this.numCourtRooms = sc.nextInt();
    this.caseTimes = new long[this.numCases];
    for (int i = 0; i < this.numCases; i++) {
      sc.next();
      this.caseTimes[i] = sc.nextLong();
    }
    sc.close();
  }
}

```
___________________________________________________________________________________________________________________________________
### Solution: O(n log m) runtime complexity
```java
package weblab;

import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Solution {

  // Implement the solve method to return the answer to the problem posed by the inputstream.
  public static String run(InputStream in) {
    return new Solution().solve(in);
  }

  private int numCases;

  private int numCourtRooms;

  private long[] caseTimes;

  public String solve(InputStream in) {
    parseInput(in);
    return Long.toString(computeLastFinish());
  }

  /**
   *  You should implement this method to compute when the last court case will be finished.
   */
  private long computeLastFinish() {
    //use PriorityQueue to get lowest value using poll so we can avoid using a double loop
    PriorityQueue<Long> pq = new PriorityQueue<>();
    //fill queue with 0 values
    for (int i = 0; i < numCourtRooms; i++) pq.add(0l);
    long result = 0;
    //loop through cases
    for (int i = 0; i < numCases; i++) {
      //get room with lowest value and add the times at index i
      long finish = pq.poll() + caseTimes[i];
      //add it back to the queue
      pq.add(finish+1);
      //overwrite the result if necessary
      result = Math.max(result, finish);
    }
    return result;
  }

  /**
   *  This method parses the input from an inputstream. You should not need to modify this method.
   */
  private void parseInput(InputStream in) {
    Scanner sc = new Scanner(in);
    this.numCases = sc.nextInt();
    this.numCourtRooms = sc.nextInt();
    this.caseTimes = new long[this.numCases];
    for (int i = 0; i < this.numCases; i++) {
      sc.next();
      this.caseTimes[i] = sc.nextLong();
    }
    sc.close();
  }
}
```
##### Other solution: O(n^2) runtime complexity
```java
private long computeLastFinish() {
 ArrayList<Long> pq = new ArrayList<>();
    for (int i = 0; i < this.numCourtRooms; i++) {
      pq.add(0l);
    }
    long result = 0;
    for (int i = 0; i < this.numCases; i++) {
      long earliestCourtRoom = pq.get(0);
      int earliestCourRoomIndex = 0;
      for (int j = 0; j < pq.size(); j++) {
        if (pq.get(j) < earliestCourtRoom) {
          earliestCourRoomIndex = j;
          earliestCourtRoom = pq.get(j);
        }
      }
      pq.set(earliestCourRoomIndex, earliestCourtRoom + this.caseTimes[i] + 1);
      result = Math.max(result, earliestCourtRoom + this.caseTimes[i]);
    }
    return result;
 }
    ```
