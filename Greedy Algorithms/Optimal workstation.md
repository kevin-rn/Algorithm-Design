A cloud service offers the opportunity for n researchers to submit jobs from workstations in a nearby office
building. Access to these workstations is controlled by an operator. This operator needs to unlock the
workstations for the researchers who come to run their computations at the cloud service. However, this
operator is very lazy. She can unlock the machines remotely from her desk, but does not feel that this menial
task matches her qualifications. She therefore decides to ignore the security guidelines and to simply ask the
researchers not to lock their workstations when they leave, and then assign new researchers to workstations
that are not used any more but that are still unlocked. That way, she only needs to unlock each workstation
for the first researcher using it. Unfortunately, unused workstations lock themselves automatically if they are
unused for more than m

minutes. After a workstation has locked itself, the operator has to unlock it again
for the next researcher using it. Given the exact schedule of arriving and leaving researchers, can you tell the
operator how many unlockings she may save by asking the researchers not to lock their workstations when
they leave and assigning arriving researchers to workstations in an optimal way? You may assume that there
are always enough workstations available.

You are given start times si
and duration di for all 1≤i≤n

.
Given the following research times and a lock time of 10 minutes:

2 6
1 2
17 7
3 9
15 6

the output should be 3.

### Tests:
```java
package weblab;

import static org.junit.Assert.*;
import java.io.*;
import java.nio.charset.*;
import java.util.ArrayList;
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

    int n, m;

    int[] start, end;

    public ProblemInstance(int n, int m, int[] start, int[] end) {
      this.n = n;
      this.m = m;
      this.start = start;
      this.end = end;
    }
  }

  public static ProblemInstance parseInput(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName + ".in"));
    int n = sc.nextInt();
    int m = sc.nextInt();
    int[] start = new int[n + 1];
    int[] end = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      start[i] = sc.nextInt();
      end[i] = sc.nextInt();
    }
    sc.close();
    return new ProblemInstance(n, m, start, end);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance x = parseInput(fileName);
    int expected = Integer.parseInt(WebLab.getData(fileName + ".out"));
    assertEquals(expected, Solution.solve(x.n, x.m, x.start, x.end));
  }

  @Test(timeout = 100)
  public void example() {
    int n = 5;
    int m = 10;
    int[] start = { 0, 2, 1, 17, 3, 15 };
    int[] end = { 0, 6, 2, 7, 9, 6 };
    assertEquals(3, Solution.solve(n, m, start, end));
  }

  @Test(timeout = 100)
  public void test01() {
    runTestWithFile("test1");
  }

  @Test(timeout = 50)
  public void test02() {
    // Fails when not using the right priority queue for assigned machines
    runTestWithFile("test2");
  }

  @Test(timeout = 50)
  public void test03() {
    // Requests must be sorted by start time
    runTestWithFile("test3");
  }

  @Test(timeout = 50)
  public void test04() {
    runTestWithFile("test4");
  }

  @Test(timeout = 50)
  public void test05() {
    runTestWithFile("test5");
  }

  @Test(timeout = 50)
  public void test06() {
    runTestWithFile("test6");
  }

  @Test(timeout = 1000)
  public void test07() {
    runTestWithFile("test7");
  }

  @Test(timeout = 4500)
  public void test08() {
    runTestWithFile("test8");
  }

  @Test(timeout = 6000)
  public void test09() {
    runTestWithFile("test9");
  }

  @Test(timeout = 6000)
  public void test10() {
    runTestWithFile("test10");
  }

  @Test(timeout = 4000)
  public void test11() {
    runTestWithFile("test11");
  }

  @Test(timeout = 500)
  public void test12() {
    runTestWithFile("test12");
  }
}

```
_________________________________________________________________________________________________________

### Solution:
```java
package weblab;

import java.io.*;
import java.util.*;

class Solution {

  public static /**
   * @param n number of researchers
   * @param m number of processes
   * @param start start times of jobs 1 through n. NB: you should ignore start[0]
   * @param end end times of jobs 1 through n. NB: you should ignore start[0]
   * @return the number of unlocks that can be saved.
   */
  int solve(int n, int m, int[] start, int[] end) {
    ArrayList<Session> sessionList = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
      sessionList.add(new Session(start[i], end[i]));
    }
    Collections.sort(sessionList);
    int unlocksSaved = 0;
    PriorityQueue<Machine> machineQueue = new PriorityQueue<>();
    for (Session s : sessionList) {
      while (!machineQueue.isEmpty()) {
        if (machineQueue.peek().available > s.start) {
          break;
        }
        Machine firstMachine = machineQueue.poll();
        // and an unlock is saved
        if (firstMachine.locked >= s.start) {
          unlocksSaved++;
          break;
        }
      }
      machineQueue.add(new Machine(s.start + s.duration, s.start + s.duration + m));
    }
    return unlocksSaved;
  }
}

class Session implements Comparable<Session> {

  public int start;

  public int duration;

  public Session(int arrives, int duration) {
    this.start = arrives;
    this.duration = duration;
  }

  @Override
  public int compareTo(Session otherJob) {
    return Integer.compare(start, otherJob.start);
  }
}

class Machine implements Comparable<Machine> {

  public int available;

  public int locked;

  public Machine(int available, int locked) {
    this.available = available;
    this.locked = locked;
  }

  @Override
  public int compareTo(Machine otherMachine) {
    return Integer.compare(locked, otherMachine.locked);
  }
}
```
