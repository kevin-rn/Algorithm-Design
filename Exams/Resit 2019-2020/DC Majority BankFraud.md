You are an employee of Gringotts working on fraud detection with bank cards.
Since your department is just starting, you have first been asked to run the following security test:

Given a set of n bankcards, is there a majority of more than n/2 cards that are equivalent, i.e., access the same bank account?
If this is the case the bank needs to take serious action to block this account immediately!

Unfortunately checking if two cards are equivalent is a labour-intensive job for another department of the bank.
The head of that department, a man called Griphook, indicated they simply cannot compare every card to every other card.
You realise this means that a brute-force O(n2)
solution is not going to work.
Fortunately you remember that you can implement an O(nlogn)

solution instead, which Griphook (begrudgingly) tells you will work for his department.

Implement the function bankScams which returns true in O(nlogn)

iff there are more than n/2 equivalent bank cards.

The Card interface has a method isEquivalent that you can use to check if two cards are equivalent.

### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class BankFraud {

  /**
   * You should implement this method.
   * @param n the number of bank cards
   * @param cards the bank cards at indices 1 through n, note that you should ignore index 0!
   * @return true iff more than n/2 of the bankcards are equivalent.
   */
  public static boolean bankScams(int n, Card[] cards) {
  // TODO
  }

  /*
     A class you may find useful to keep track of three pieces of data together.
     Although our reference solution uses it, there is no obligation to use it!
     */
  static class Triple {

    int cnt;

    int index;

    Card card;

    public Triple(int cnt, int index, Card card) {
      this.cnt = cnt;
      this.index = index;
      this.card = card;
    }
  }
}

```

### Library
```java
import java.util.Set;

interface Card {

  boolean isEquivalent(Card other);
}

class SpecTestCard implements Card {

  public static int counter = 0;

  int id;

  Set<Integer> equivalent;

  public SpecTestCard(int id, Set<Integer> equivalent) {
    this.id = id;
    this.equivalent = equivalent;
  }

  @Override
  public boolean isEquivalent(Card other) {
    counter++;
    if (other == this) {
      return true;
    }
    if (other instanceof SpecTestCard) {
      return this.equivalent.contains(((SpecTestCard) other).id);
    }
    return false;
  }
}

```

### Test
```java
import java.util.*;
import org.junit.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class UTest {

  /**
   * NOTE: Although these public tests are simply cards that use an id, our spec tests have a more involved method of checking for equivalence!
   * You cannot assume anything about these Card objects, other than that the method isEquivalent exists!
   */
  class PublicTestCard implements Card {

    int id;

    public PublicTestCard(int id) {
      this.id = id;
    }

    @Override
    public boolean isEquivalent(Card other) {
      if (other instanceof PublicTestCard) {
        return this.id == ((PublicTestCard) other).id;
      }
      return false;
    }
  }

  @Test
  public void onlyTwoCards() {
    int n = 2;
    Card[] cards = new Card[n + 1];
    cards[1] = new PublicTestCard(0);
    cards[2] = new PublicTestCard(1);
    Assert.assertFalse(BankFraud.bankScams(n, cards));
    cards[2] = new PublicTestCard(0);
    Assert.assertTrue(BankFraud.bankScams(n, cards));
  }

  @Test
  public void example() {
    int n = 20;
    Card[] cards = new Card[n + 1];
    for (int i = 1; i <= n; i++) {
      cards[i] = new PublicTestCard(i);
    }
    // They are all different
    Assert.assertFalse(BankFraud.bankScams(n, cards));
    for (int i = 1; i <= n / 2; i++) {
      cards[i] = new PublicTestCard(1);
    }
    // Exactly half are equivalent now.
    Assert.assertFalse(BankFraud.bankScams(n, cards));
    cards[n] = new PublicTestCard(1);
    // Exactly half + 1 are equivalent now.
    Assert.assertTrue(BankFraud.bankScams(n, cards));
  }
}
```

### Solution
```java

import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class BankFraud {

/**
   * You should implement this method.
   * @param n the number of bank cards
   * @param cards the bank cards at indices 1 through n, note that you should ignore index 0!
   * @return true iff more than n/2 of the bankcards are equivalent.
   */
  public static boolean bankScams(int n, Card[] cards) {
    Card result = majority(cards, 1, n);

    if(result == null) {
      return false;
    }
    return true;
  }

  public static Card majority(Card[] cards, int low, int high) {
    if(high == low) {
      return cards[low];
    }

    int mid = (high + low) / 2;

    Card majorityLeft = majority(cards, low, mid);
    Card majorityRight = majority(cards, mid + 1, high);

    if(majorityLeft != null && majorityRight != null && majorityLeft.isEquivalent(majorityRight)) {
      return majorityLeft;
    }


    int leftCount = 0;
    int rightCount = 0;
    for(int i = low; i <= high; i++) {
      if(majorityLeft != null && majorityLeft.isEquivalent(cards[i])) {
        leftCount++;
      }

      if(majorityRight != null && majorityRight.isEquivalent(cards[i])) {
        rightCount++;
      }
    }

    if(leftCount > rightCount && leftCount > (high - low) / 2) {
      return majorityLeft;
    } else if(rightCount > leftCount && rightCount > (high - low) / 2) {
      return majorityRight;
    }
    
    

    return null;
  }
  
   /*
     A class you may find useful to keep track of three pieces of data together.
     Although our reference solution uses it, there is no obligation to use it!
     */
  static class Triple {

    int cnt;

    int index;

    Card card;

    public Triple(int cnt, int index, Card card) {
      this.cnt = cnt;
      this.index = index;
      this.card = card;
    }
  }
}
```
