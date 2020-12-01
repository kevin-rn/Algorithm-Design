Implement the function computeC(int[] a, int[] b), which takes an array a of length n and an array b of length m as input and returns a n x m matrix. This matrix contains the values abji, where ai) is the i-th value in array a and bj is the j

-th value in array b.

In this exercise you are not allowed to use Math.pow() and should implement fast binary exponentiation by yourself.

### Template
```java
class Solution {

  /**
     * Computes the matrix C, containing the values for a^b, for all values in a and b.
     *
     * @param a array containing the bases
     * @param b array containing the exponents
     * @return matrix C
     */
  public static int[][] computeC(int[] a, int[] b) {
    int[][] C = new int[a.length][b.length];
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < b.length; j++) {
        C[i][j] = fastBinaryExponentiation(a[i], b[j]);
      }
    }
    return C;
  }

  public static int fastBinaryExponentiation(int a, int b) {
    if (b == 0) {
      return 1;
    }
    if (b == 1) {
      return a;
    }
    if (b % 2 == 0) {
      int t = fastBinaryExponentiation(a, b / 2);
      return t * t;
    } else {
      return a * fastBinaryExponentiation(a, b - 1);
    }
  }
}
```

### Solution
```java
class Solution {
  
  /**
     * Computes the matrix C, containing the values for a^b, for all values in a and b.
     *
     * @param a array containing the bases
     * @param b array containing the exponents
     * @return matrix C
     */
  public static int[][] computeC(int[] a, int[] b) {
    // TODO
  }
}
```
