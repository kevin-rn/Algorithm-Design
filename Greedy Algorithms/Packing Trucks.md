### Packing Trucks (5/11)

As the owner of a shipping company, you won a large contract requiring you to ship a certain amount of boxes nto a certain destination. 
The contract gives the weight of the boxes in order of their arrival. 
This is also the order in which the boxes must be shipped. 
To maximize profit, you want to minimize the number of trucks used. 
Each truck can carry boxes up to a total of weight W. Every box i with 1≤i≤n has a weight W.
Implement an algorithm that determines the minimal amount of trucks needed to ship the boxes to the destination.

If we can carry at most 48 weight units in one truck (W=48) then the following shipment of boxes (41 29 12 26) should result in an output of 3.

```java
class Solution {

  public static /**
   * @param n the number of packages
   * @param weights the weights of all packages 1 through n. Note that weights[0] should be ignored!
   * @param maxWeight the maximum weight a truck can carry
   * @return the minimal number of trucks required to ship the packages _in the given order_.
   */
  int minAmountOfTrucks(int n, int[] weights, int maxWeight) {
  int amount = 0, w, i=1;
  while(i<=n) {
    w = 0;
    while(i<=n && w + weights[i] <= maxWeight){
      w += weights[i++];
    }
    amount++;
  }
  return amount;
  }
}
```

### Alternative solution:
```java
class Solution {

  public static /**
   * @param n the number of packages
   * @param weights the weights of all packages 1 through n. Note that weights[0] should be ignored!
   * @param maxWeight the maximum weight a truck can carry
   * @return the minimal number of trucks required to ship the packages _in the given order_.
   */
  int minAmountOfTrucks(int n, int[] weights, int maxWeight) {
    int amount = (n <= 0)? 0 : 1, current_total = 0;
    for(int i = 1; i <= n; i++) {
      if(weights[i] + current_total > maxWeight) {
        amount++;
        current_total = weights[i];
      }
      else current_total+= weights[i];
    }
    return amount;
  }
}
```

### Official solution:
```java
package weblab;


class Solution {

  public static /**
   * @param n the number of packages
   * @param weights the weights of all packages 1 through n. Note that weights[0] should be ignored!
   * @param maxWeight the maximum weight a truck can carry
   * @return the minimal number of trucks required to ship the packages _in the given order_.
   */
  int minAmountOfTrucks(int n, int[] weights, int maxWeight) {
    int trucks = 0;
    int currentWeight = 0;
    if (n == 0)
      return 0;
    if (n > 0) {
      trucks++;
      currentWeight = weights[1];
    }
    for (int i = 2; i <= n; i++) {
      if (currentWeight + weights[i] > maxWeight) {
        trucks++;
        currentWeight = weights[i];
      } else {
        currentWeight = currentWeight + weights[i];
      }
    }
    return trucks;
  }
}
```
