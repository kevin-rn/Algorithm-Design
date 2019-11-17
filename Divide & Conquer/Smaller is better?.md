### Smaller is better?

Description

Implement the findMin method, this method should use divide and conquer to find the value x for which the difference between two discrete functions is minimal.

Take for example the linear equation f(x)=8x−240
and g(x)=6x−156, these two lines will intersect each other in the point (42,96)

, so the result of this algorithm should be 42.

Take note that we are using discrete quadratic functions
Public Library classes

We have already provided several classes that might be useful for you in the Library tab.
You cannot edit these classes, but you can use them in your implementation.

### Library:
```java
abstract class Equation {

  public abstract long evaluate(long x);
}

class QuadraticEquation extends Equation {

  private long secondPolynomial;

  private long firstPolynomial;

  private long constant;

  /**
   * Constructs a quadratic equation in the form of:
   * f(x) = secondPolynomial * x^2 + firstPolynomial * x + constant
   *
   * @param secondPolynomial the parameter for the second degree polynomial
   * @param firstPolynomial the parameter for the first degree polynomial
   * @param constant the parameter for the constant
   */
  public QuadraticEquation(long secondPolynomial, long firstPolynomial, long constant) {
    this.secondPolynomial = secondPolynomial;
    this.firstPolynomial = firstPolynomial;
    this.constant = constant;
  }

  /**
   * Evaluates the equation with the given x.
   * @param x value used to evaluate
   * @return the result of the equation1
   */
  public long evaluate(long x) {
    return secondPolynomial * x * x + firstPolynomial * x + constant;
  }
}
```

### Test:
```java
import static org.junit.Assert.*;
import org.junit.*;

public class UTest {

  @Test(timeout = 100)
  public void testExample() {
    Equation eq1 = new QuadraticEquation(0, 8, -240);
    Equation eq2 = new QuadraticEquation(0, 6, -156);
    assertEquals(42, Solution.findMin(eq1, eq2, 0, 100));
  }
}
```

___________________________________________________________________________________________________________________________

### Solution:
```java

```
