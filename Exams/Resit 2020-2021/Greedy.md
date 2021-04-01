A particularly bored guard at a museum has been trying to figure out how often they actually need to take a walk through the museum.    
Since most events have been cancelled anyway, nobody will notice if they take things a bit more easy, now would they? Unfortunately for the guard, some events are still scheduled and they shouldn’t be caught slacking!  
This is where you come in.  
Implement the function whenToWalk which returns a set of times at which the guard should take a walk so that 1) the guard visits each event and 2) the number of walks is minimised.
Your input consists of a number n representing the number of events scheduled this day, as well as two arrays s and f containing the start and end times of the events.   
Note that these times are inclusive, meaning if an event starts or ends at a time x and the guard takes a walk at time x we consider this event “visited”. (We thus ignore the time it takes the guard to walk past all the scheduled events at that moment x.)

### Template
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class DoingTheRounds {

  /**
   *  You should implement this method.
   *  @param n the number of important events.
   *  @param s an array of size n+1, containing the start times of the events s_1 through s_n. You should ignore s[0].
   *  @param f an array of size n+1, containing the end times of the events f_1 through f_n. You should ignore f[0].
   *  @return A smallest set of times at which the guard should take a walk to check in on all important events.
   */
  public static Set<Integer> whenToWalk(int n, int[] s, int[] f) {
  // TODO
  }
}

```

### Test:
```java
import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 100)
  public void example() {
    int n = 3;
    int[] s = { 0, 1, 2, 3 };
    int[] f = { 0, 3, 4, 5 };
    /*
     * If we do just one round at time 3, we got all events covered.
     */
    Set<Integer> result = DoingTheRounds.whenToWalk(n, s, f);
    assertEquals("Just the one will do", 1, result.size());
    assertTrue("At time 3", result.contains(3));
  }

  @Test(timeout = 100)
  public void example_one_event() {
    int n = 1;
    int[] s = { 0, 10 };
    int[] f = { 0, 32 };
    // Just do the one round.
    assertEquals("Just the one", 1, DoingTheRounds.whenToWalk(n, s, f).size());
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
class DoingTheRounds {

  /**
   *  You should implement this method.
   *  @param n the number of important events.
   *  @param s an array of size n+1, containing the start times of the events s_1 through s_n. You should ignore s[0].
   *  @param f an array of size n+1, containing the end times of the events f_1 through f_n. You should ignore f[0].
   *  @return A smallest set of times at which the guard should take a walk to check in on all important events.
   */
  public static Set<Integer> whenToWalk(int n, int[] s, int[] f) {
    Set<Integer> result = new HashSet<>();
    
    // Create a list of event objects to iterate through
    ArrayList<Event> events = new ArrayList<>();
    for (int i = 1; i <= n; i++) events.add(new Event(s[i], f[i]));
    // Sort based on end time
    Collections.sort(events);
    
    int end;
    while(!events.isEmpty()) {
      end = events.get(0).f;
      result.add(end);
      for (int i = 0; i < events.size(); i++) {
        // Check for current walk if all current events start is before the last end, 
        // if so remove it from the list to ignore it in the next iteration
        if (events.get(i).s <= end) events.remove(i--);
      }
    }

    return result;
  
  }
}

    class Event implements Comparable<Event> {
      int s, f;
      
      public Event(int s, int f) {
        this.s = s;
        this.f = f;
      }
      
      @Override
      public int compareTo(Event other) {
        return this.f-other.f;
      }

}

```
