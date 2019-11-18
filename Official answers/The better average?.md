package weblab;

import java.io.*;
import java.util.*;

class Solution {

  public static double solve(int n, double[] list) {
    // We sort the list first, so that we can easily determine the median element.
    Arrays.sort(list);
    if (n % 2 == 0) {
      // For even-sized arrays, we need to average the middle two elements.
      double sum = (list[n / 2 - 1] + list[n / 2]);
      return (sum / 2);
    } else
      return list[n / 2];
  }
}
