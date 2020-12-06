You are an employee of St. Mungo’s, a hospital dealing with a large number of patients.
Since your department is being overrun with cases, you want to figure out if there is an illness that should get your priority.

So you set out to answer the following question:
Given a set of n patients, is there a majority of more than n/2 patients that have the same illness?
If this is the case then the hospital should really get some more staff working on curing that illness.

Unfortunately we are currently unable to check what illness a patient has due to a malfunction on the Janus Thickey ward on the fourth floor with all the labs.
The head of that department indicated they can only compare two patients and see if they have the same illness.
You realise this means that a brute-force O(n2)
solution could now easily be implemented by comparing all the patients to each other.
This is not good enough however, as the large number of patients would make this take far too long.
Fortunately you realise that you can implement an O(nlogn)

solution instead, which St. Mungo’s tells you will be good enough.

Implement the function patientCare which returns true in O(nlogn)

iff there are more than n/2 patients with an equivalent illness.

The Patient interface has a method hasSameIllnesses that you can use to check if two patients have the same illnesses.

### Template:
```java
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class StMungo {

  /**
   * You should implement this method.
   * @param n the number of patients
   * @param patients the patients at indices 1 through n, note that you should ignore index 0!
   * @return true iff more than n/2 of the patients have the same illnesses.
   */
  public static boolean patientCare(int n, Patient[] patients) {
  // TODO
  }

  /*
     A class you may find useful to keep track of three pieces of data together.
     Although our reference solution uses it, there is no obligation to use it!
     */
  static class Triple {

    int cnt;

    int index;

    Patient patient;

    public Triple(int cnt, int index, Patient patient) {
      this.cnt = cnt;
      this.index = index;
      this.patient = patient;
    }
  }
}
```

### Library:
```java

import java.util.Set;

interface Patient {

  boolean hasSameIllnesses(Patient other);
}

class SpecTestPatient implements Patient {

  public static int counter = 0;

  int id;

  Set<Integer> equivalent;

  public SpecTestPatient(int id, Set<Integer> equivalent) {
    this.id = id;
    this.equivalent = equivalent;
  }

  @Override
  public boolean hasSameIllnesses(Patient other) {
    counter++;
    if (other == this) {
      return true;
    }
    if (other instanceof SpecTestPatient) {
      return this.equivalent.contains(((SpecTestPatient) other).id);
    }
    return false;
  }
}
```

### Test:
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
   * NOTE: Although these public tests are simply patients that use an id, our spec tests have a more involved method of checking for equivalence!
   * You cannot assume anything about these Patient objects, other than that the method hasSameIllnesses exists!
   */
  class PublicTestPatient implements Patient {

    int id;

    public PublicTestPatient(int id) {
      this.id = id;
    }

    @Override
    public boolean hasSameIllnesses(Patient other) {
      if (other instanceof PublicTestPatient) {
        return this.id == ((PublicTestPatient) other).id;
      }
      return false;
    }
  }

  @Test
  public void onlyTwoPatients() {
    int n = 2;
    Patient[] patients = new Patient[n + 1];
    patients[1] = new PublicTestPatient(0);
    patients[2] = new PublicTestPatient(1);
    Assert.assertFalse(StMungo.patientCare(n, patients));
    patients[2] = new PublicTestPatient(0);
    Assert.assertTrue(StMungo.patientCare(n, patients));
  }

  @Test
  public void example() {
    int n = 20;
    Patient[] patients = new Patient[n + 1];
    for (int i = 1; i <= n; i++) {
      patients[i] = new PublicTestPatient(i);
    }
    // They are all different
    Assert.assertFalse(StMungo.patientCare(n, patients));
    for (int i = 1; i <= n / 2; i++) {
      patients[i] = new PublicTestPatient(1);
    }
    // Exactly half have the same illness now.
    Assert.assertFalse(StMungo.patientCare(n, patients));
    patients[n] = new PublicTestPatient(1);
    // Exactly half + 1 have the same illness now.
    Assert.assertTrue(StMungo.patientCare(n, patients));
  }
}
```

### Solution:
```java
/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class StMungo {

  /**
   * You should implement this method.
   * @param n the number of patients
   * @param patients the patients at indices 1 through n, note that you should ignore index 0!
   * @return true iff more than n/2 of the patients have the same illnesses.
   */
  public static boolean patientCare(int n, Patient[] patients) {
    return majority(patients, 1, n) != null;
  }
  
  private static Patient majority(Patient[] patients, int start, int end) {
    if(start == end) return patients[start];
    
    int mid = (start+end)/2;
    Patient left = majority(patients, start, mid);
    Patient right = majority(patients, mid+1, end);

    if(left != null && right != null && left.hasSameIllnesses(right)) return left;
    
    int counterL = 0, counterR = 0;
    for(int i = start; i <= end; i++) {
      if(left != null && left.hasSameIllnesses(patients[i])) counterL++;
      if(right != null && right.hasSameIllnesses(patients[i])) counterR++;
    }
    if(counterL > counterR && counterL > (end-start+1)/2) return left;
    else if(counterR > counterL && counterR > (end-start+1)/2) return right;
    else return null;
  }


  /*
     A class you may find useful to keep track of three pieces of data together.
     Although our reference solution uses it, there is no obligation to use it!
     */
  static class Triple {

    int cnt;

    int index;

    Patient patient;

    public Triple(int cnt, int index, Patient patient) {
      this.cnt = cnt;
      this.index = index;
      this.patient = patient;
    }
  }
}
```
