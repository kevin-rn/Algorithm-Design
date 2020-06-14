One of the most difficult things about going on a holiday is making sure your
luggage does not exceed the maximum weight. You, chairman of the Backpacker’s
Association for Packing Carry-ons, are faced with exactly this problem. You
are going on a lovely holiday with one of your friends, but now most of your
time is spent in frustration while trying to pack your backpack. In order to
optimize this process, you and your friend have independently set upon trying to
find better ways to pack.

After some time you have a serious breakthrough! Somehow you managed to solve
the Knapsack problem in polynomial time, defying expectations everywhere. You
are not interested in any theoretical applications, so you immediately return to
your friend’s apartment in order to now quickly pack your backpack optimally.

When you arrive there, you find that your friend has set upon her own solution,
namely to enumerate all possible packings. This means that all items you
possibly wanted to bring are scattered across the entire apartment, and it would
take a really long time to get all the items back together.

Luckily you can use the work your friend has done. For every possible subset of
items that you can possibly bring, she has written down the total weight
of these items. Alas, she did not write down what items were part of this
total, so you do not know what items contributed to each total weight. If the
original weights of the items formed a collection (a1,…,an)

of non-negative integers, then your friend has
written down the multiset

S((a1,…,an))={∑i∈Iai∣I⊆{1,…,n}}

For example, if your friend had two items, and the weights of those two items
are 2,3

, then your friend has written down

    0, corresponding to the empty set 

;
2, corresponding to the subset 2
;
3, corresponding to the subset 3
;
5, corresponding to the subset 2,3

    .

You want to reconstruct the weights of all
the individual items so you can start using your Knapsack algorithm. It
might have happened that your friend made a mistake in adding all these weights,
so it might happen that her list is not consistent.
Input

    One line containing a single integer 1≤n≤18

the number of items.
(2^n) lines each containing a single integer 0≤w≤228

    , the combined weight of a subset of the items. Every subset occurs exactly once.

Output

Output non-negative integers a1,…,an
on n

lines in
non-decreasing order such that
(S\big((a_1,\dotsc,a_n)\big)={b_1,\dotsc,b_{2^n}}), provided that such
integers exist.
Otherwise, output a single line containing impossible.
Examples

For each example, the first block is the input and the second block is the corresponding output.
Example 1

1
0
5

5

Example 2

3
7
5
2
4
1
6
3
0

1
2
4

Example 3

2
0
1
2
4

impossible

Example 4

2
0
1
1
2

1
1

#### Template:
```java
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner sc, PrintStream out) {
    int n = sc.nextInt();
    ArrayList<Integer> l = new ArrayList<Integer>();
    ArrayList<Integer> results = new ArrayList<Integer>();
    for (int i = 0; i < (1 << n); i++) {
      int cur = sc.nextInt();
      l.add(cur);
    }
    boolean done = false;
    for (int step = 0; step < n; step++) {
      Collections.sort(l);
      Integer cur = l.get(1) - l.get(0);
      results.add(cur);
      ArrayList<Integer> new_l = new ArrayList<Integer>();
      HashMap<Integer, Integer> seen = new HashMap<Integer, Integer>();
      for (int i = 0; i < l.size(); i++) {
        Integer k = l.get(i);
        if (seen.containsKey(k - cur) && seen.get(k - cur) > 0) {
          seen.put(k - cur, seen.get(k - cur) - 1);
        } else {
          if (!seen.containsKey(k))
            seen.put(k, 0);
          seen.put(k, seen.get(k) + 1);
          new_l.add(k);
        }
      }
      boolean all_zero = true;
      for (Integer val : seen.values()) {
        all_zero &= (val == 0);
      }
      if (l.get(0) != 0 || new_l.size() != l.size() / 2 || !all_zero) {
        done = true;
        out.println("impossible");
        break;
      }
      l = new_l;
    }
    if (!done) {
      for (int i = 0; i < results.size(); i++) {
        out.println(results.get(i));
      }
    }
  }
}

```

___________________________________________________________________
### Solution:
```
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Solution {

  public static void run(InputStream in, PrintStream out) {
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
    new Solution().solve(sc, out);
    sc.close();
  }

  public void solve(Scanner sc, PrintStream out) {
    int n = sc.nextInt();
    ArrayList<Integer> l = new ArrayList<Integer>();
    ArrayList<Integer> results = new ArrayList<Integer>();
    for (int i = 0; i < (1 << n); i++) {
      int cur = sc.nextInt();
      l.add(cur);
    }
    boolean done = false;
    for (int step = 0; step < n; step++) {
      Collections.sort(l);
      Integer cur = l.get(1) - l.get(0);
      results.add(cur);
      ArrayList<Integer> new_l = new ArrayList<Integer>();
      HashMap<Integer, Integer> seen = new HashMap<Integer, Integer>();
      for (int i = 0; i < l.size(); i++) {
        Integer k = l.get(i);
        if (seen.containsKey(k - cur) && seen.get(k - cur) > 0) {
          seen.put(k - cur, seen.get(k - cur) - 1);
        } else {
          if (!seen.containsKey(k))
            seen.put(k, 0);
          seen.put(k, seen.get(k) + 1);
          new_l.add(k);
        }
      }
      boolean all_zero = true;
      for (Integer val : seen.values()) {
        all_zero &= (val == 0);
      }
      if (l.get(0) != 0 || new_l.size() != l.size() / 2 || !all_zero) {
        done = true;
        out.println("impossible");
        break;
      }
      l = new_l;
    }
    if (!done) {
      for (int i = 0; i < results.size(); i++) {
        out.println(results.get(i));
      }
    }
  }
}

```
