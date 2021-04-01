Since the vaccines have arrived, there is a lot pressure to vaccinate people as quickly as possible.   
To this end the government has created a list in which order people should be vaccinated, where the lowest ranking numbers go first.   
Unfortunately not all n people seem happy with their place and are trying to cheat by butting ahead in line.  
You are tasked to efficiently figure how many switches have taken place, where a switch is defined as: person i i s ahead of person j in the list, whereas their ranking number is higher. Because of the urgency, you realise a brute-force O(n2) solution is not going to work.    
Fortunately you remember an O(nlogn) solution instead, which should be sufficiently efficient.   
Implement this O(nlogn) solution in the method numberOfSwitches.

Additional example: If we have the people with ranking numbers 1, 4, 3, 2 in that order there are a total of 3 switches. 4 is switched compared to 3 and 2, and 3 compared to 2.

### Template:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class ButtingInLine {

  /**
   * You should implement this method.
   * @param n the number of people
   * @param people the people at indices 1 through n, note that you should ignore index 0!
   * @return the number of switches that occurred (with person i appearing before person j, despite p[i].number > p[j].number)
   */
  public static int numberOfSwitches(int n, Person[] people) {
  // TODO
  }

  static class Person {

    int ranking_number;

    public Person(int ranking_number) {
      this.ranking_number = ranking_number;
    }
  }
}
```

### Test:
```java
import java.util.*;
import org.junit.*;

public class UTest {

  @Test
  public void onlyTwoPersons() {
    int n = 2;
    ButtingInLine.Person[] cards = new ButtingInLine.Person[n + 1];
    cards[1] = new ButtingInLine.Person(0);
    cards[2] = new ButtingInLine.Person(1);
    Assert.assertEquals(0, ButtingInLine.numberOfSwitches(n, cards));
    cards[1] = new ButtingInLine.Person(1);
    cards[2] = new ButtingInLine.Person(0);
    Assert.assertEquals(1, ButtingInLine.numberOfSwitches(n, cards));
  }

  @Test
  public void example() {
    int n = 20;
    ButtingInLine.Person[] cards = new ButtingInLine.Person[n + 1];
    for (int i = 1; i <= n; i++) {
      cards[i] = new ButtingInLine.Person(i);
    }
    // They are all in the correct place.
    Assert.assertEquals(0, ButtingInLine.numberOfSwitches(n, cards));
    cards[8] = new ButtingInLine.Person(20);
    // 9 through 19 all count one switch
    Assert.assertEquals(11, ButtingInLine.numberOfSwitches(n, cards));
  }
}
```

### Solution:
```java
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class ButtingInLine {

  /**
   * You should implement this method.
   * @param n the number of people
   * @param people the people at indices 1 through n, note that you should ignore index 0!
   * @return the number of switches that occurred (with person i appearing before person j, despite p[i].number > p[j].number)
   */
  public static int numberOfSwitches(int n, Person[] people) {
    // Call helper method to do merge sort and count the inversions
    return sort(people, 1, n);
  }
  
  static int sort(Person[] people, int l, int r) { 
        // variable to keep track of total counts
        int count = 0; 
        if (l < r) { 
            // middle int for divide
            int m = (l + r) / 2; 
            // recursive count left side of array
            count += sort(people, l, m); 
            // recursive count right side of array
            count += sort(people, m + 1, r); 
            // Count the inversions merge of both
            count += merge(people, l, m, r); 
        } 
        return count; 
    } 
    
  static int merge(Person[] people, int l, int m, int r) { 
        // Copy both sides for easier comparison and swapping in original array
        Person[] left = Arrays.copyOfRange(people, l, m + 1); 
        Person[] right = Arrays.copyOfRange(people, m + 1, r + 1);
        
        // Loop through array to swap
        int i = 0, j = 0, k = l, swaps = 0; 
        while (i < left.length && j < right.length) { 
            if (left[i].ranking_number <= right[j].ranking_number) 
                people[k++] = left[i++]; 
            else { 
                people[k++] = right[j++]; 
                swaps += (m + 1) - (l + i); 
            } 
        } 
        // Set left over elements
        while (i < left.length) {
            people[k++] = left[i++];
        }
        while (j < right.length) {
            people[k++] = right[j++];
        }
        return swaps; 
    } 
  
  static class Person {

    int ranking_number;

    public Person(int ranking_number) {
      this.ranking_number = ranking_number;
    }
  }
}
```
