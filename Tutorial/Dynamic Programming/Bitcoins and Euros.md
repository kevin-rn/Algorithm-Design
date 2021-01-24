At t=0 you have received 0.1 bitcoins.
You may trade your bitcoins until time T, when you aim to use the value to buy a house (in euros).
A friend provided you with a predictor of the euro–bitcoin exchange rate, which for every day t gives the rate rt of 1 bitcoin in euro with trusted buyers/sellers.
Because of transaction costs, if you sell x bitcoins you receive only 0.95⋅x⋅rt, and when you buy bitcoins, you pay a fixed amount of €5 for the transaction.

Give (a) recursive function(s) describing the amount in euro your 0.1
bitcoin is worth by time T, based on optimal trading decisions and assuming perfect exchange rate predictions rt.

### Assignment:
```java
import java.util.Arrays;

class Solution {

    /**
     * Implement this method
     *
     * @param t - the number of days you have
     * @param r - exchange rates of each day, ignore index 0. I.e. the exchange rate of the first day can be found using r[1].
     * @return the maximum amount of euros after T days
     */
    public static double optimalTrade(int t, double[] r) {
        // TODO
    }
}
```

### Test:
```java
import static org.junit.Assert.assertEquals;

import org.junit.*;

public class UTest {

    @Test
    public void testExampleA() {
        int t = 3;
        double[] r = {0.0, 500.0, 100.0, 10000.0};
        // Best course of action is to sell bitcoins at t = 1, buy more bitcoins at t = 2, and sell again at t = 3
        assertEquals(4037.5, Solution.optimalTrade(t, r), 0.001);
    }

    @Test
    public void testExampleB() {
        int t = 5;
        double[] r = {0.0, 2010.0, 1950.0, 2020.0, 1607.5, 1904.42};
        // Best course of action is to sell bitcoins at t = 3, buy more bitcoins at t = 4, sell at t = 5
        assertEquals(210.35103769828928, Solution.optimalTrade(t, r), 1e-6);
    }

    @Test
    public void testExampleBExtended() {
        int t = 7;
        double[] r = {0.0, 2010.0, 1950.0, 2020.0, 1607.5, 1904.42, 2010.0, 1904.42};
        // Best course of action is to sell bitcoins at t = 3, buy more bitcoins at t = 4, sell at t = 5, buy more at t = 6, and again sell at t = 7
        assertEquals(222.01278382581648, Solution.optimalTrade(t, r), 1e-6);
    }

    @Test
    public void testExampleDontBuyMore() {
        int t = 3;
        double[] r = {0.0, 500.0, 480.0, 510.0};
        // Best course of action is to only sell at t = 3
        assertEquals(48.45, Solution.optimalTrade(t, r), 0.001);
    }
}
```

### Solution:
```java
import java.util.Arrays;

class Solution {

    /**
     * Implement this method
     *
     * @param t - the number of days you have
     * @param r - exchange rates of each day, ignore index 0. I.e. the exchange rate of the first day can be found using r[1].
     * @return the maximum amount of euros after T days
     */
    public static double optimalTrade(int t, double[] r) {
        // Create the arrays to store the results for the bitcoins and euros per day
        double[] b = new double[t + 1];
        double[] e = new double[t + 1];

        // Initialize the arrays for t=0 (base cases)
        b[0] = 0.1;
        e[0] = 0.0;

        // Iteratively build the solution
        for (int i = 1; i <= t; i++) {
            b[i] = Math.max(b[i - 1], (e[i - 1] - 5.0) / r[i]);
            e[i] = Math.max(e[i - 1], b[i - 1] * 0.95 * r[i]);
        }

        return e[t];
    }
}
```
