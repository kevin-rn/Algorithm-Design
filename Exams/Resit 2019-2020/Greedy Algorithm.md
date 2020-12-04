You are organising a virtual boardgame event to interact with your peers in these interesting times.
To that end all of your n participants need to do two tasks.
You need to talk to all of them individually to hear what board games they have experience with and they need to create an account.

The times vary for different participants: let a_i denote the required talking time to person 1≤i≤n.
However, before you can talk to them they also need to create an account at BoardgameArena, the platform you will be using.
Since not all of participants are CSE students, some take a little longer than others to create this account; let b_i be the time required for participant 1≤i≤n to create their account.

Since everyone can create an account in parallel, but they all need to talk to you individually, you realise you can use a greedy algorithm to determine an optimal order that minimises the total time required to get everyone ready for the tournament.

Implement the function boardgameTime which given the times described above, returns the time at which the last person will be ready to start the tournament using an optimal order of participants.

##### Note: See alternative at bottom!

### Template
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Tournament {

  /**
   *  You should implement this method.
   *  @param n the number of participants.
   *  @param a an array of size n+1, containing the interview times a_1 through a_n. You should ignore a[0].
   *  @param b an array of size n+1, containing the account creation time b_1 through b_n. You should ignore b[0].
   *  @return The minimum latest end time.
   */
  public static int boardgameTime(int n, int[] a, int[] b) {
  // TODO
  }
}

```


### Test
```java
import static org.junit.Assert.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;
import org.junit.*;
// import org.junit.rules.*;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    int n = 2;
    int[] a = { 0, 5, 2 };
    int[] b = { 0, 1, 3 };
    /*
     * Participant 1 then 2 gives a total time of: 6 + 2 = 8
     * Participant 2 then 1 gives a total time of: 5 + 5 = 10
     */
    assertEquals("Participant 1 first", 8, Tournament.boardgameTime(n, a, b));
  }

  @Test(timeout = 100)
  public void example_one_job() {
    int n = 1;
    int[] a = { 0, 32 };
    int[] b = { 0, 10 };
    /*
     * This one person will be done at time 42
     */
    assertEquals("Just the one", 42, Tournament.boardgameTime(n, a, b));
  }
}
```


### Solution
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Tournament {

  /**
   *  You should implement this method.
   *  @param n the number of participants.
   *  @param a an array of size n+1, containing the interview times a_1 through a_n. You should ignore a[0].
   *  @param b an array of size n+1, containing the account creation time b_1 through b_n. You should ignore b[0].
   *  @return The minimum latest end time.
   */
  public static int boardgameTime(int n, int[] a, int[] b) {
    // Create particpant array
    Participant[] p = new Participant[n+1];
    for(int i = 0; i<=n; i++) {
      p[i] = new Participant(a[i], b[i]);
    }
    // Sort on the total time for each
    Arrays.sort(p);
    int total = 0;
    // loop for each particpant
    for(int i =1; i<=n; i++) {
      // if there wasn't enough create account time add that, else just add only the interview time.
      total += (total < p[i].account) ? (p[i].account-total) + p[i].interview : p[i].interview;
    }
    return total;
  }
  
  // Helper class
  public static class Participant implements Comparable<Participant>  {
    int interview;
    int account;
    
    public Participant(int interview, int account) {
      this.interview = interview;
      this.account = account;
    }
    
    @Override
    public int compareTo(Participant p2) {
      return Integer.compare(this.account, p2.account);
    }
  }
}
```
______________________________________________________________________________________________________________________________________________
You are organising a virtual boardgame event to interact with your peers in these interesting times.
To that end all of your n participants need to create an account at BoardgameArena, the platform you will be using.
Since not all of participants are CSE students, some take a little longer than others to create this account; let ai be the time required for participant 1≤i≤n to create their account.
After they have created this account, you also need to talk to them to hear what board games they have experience with.
It is important that happens after they created an account, as they need to look at the list of games available on the platform.
Furthermore this time bi

spent talking differs per person as some are more talkative than others.

Since everyone can create an account in parallel, but they all need to talk to you individually, you realise you can use a greedy algorithm to determine an optimal order that minimises the total time required to get everyone ready for the tournament.

Implement the function boardgameTime which given the times described above, returns the time at which the last person will be ready to start the tournament using an optimal order of participants.

### Solution:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class Tournament {

  /**
   *  You should implement this method.
   *  @param n the number of participants.
   *  @param a an array of size n+1, containing the account creation a_1 through a_n. You should ignore a[0].
   *  @param b an array of size n+1, containing the interview times b_1 through b_n. You should ignore b[0].
   *  @return The minimum latest end time.
   */
  public static int boardgameTime(int n, int[] a, int[] b) {
    Participant[] participants = new Participant[n];
    for(int i = 1; i<=n; i++) participants[i-1] = new Participant(a[i], b[i]);
    Arrays.sort(participants);
    int result = 0;
    for(Participant p : participants) {
      result += (p.account > result) ? ((p.account - result) + p.interview) : p.interview;
    }
    return result;
  }
}

class Participant implements Comparable<Participant> {
  int account, interview;
  
  public Participant(int account, int interview) {
    this.account = account;
    this.interview = interview;
  }
  
  @Override
  public int compareTo(Participant other) {
    return Integer.compare(this.account, other.account);
  }
}

```
