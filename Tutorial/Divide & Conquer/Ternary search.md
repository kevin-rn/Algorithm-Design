Description

On her holiday at a tourist attraction, Alex climbed a ladder to take a picture and climbed back down. 
However, the picture file got corrupted. To fix that, she needs to know exactly when the picture was taken. 
Luckily, Alex was wearing an altimeter that saved her exact altitude at each point in time and stored it in the array altitude. 
Your job is to design an algorithm that figures out at what point in time Alex took the picture accessing the array altitude as few times as possible.

We know that:
- Alex definitely took the picture between t=0 and t=altitude.length
- Alex never descended before taking the picture, so until the maximum the function only increases
- Similarly, Alex never ascended after taking the picture, so after the maximum, the function only decreases
- Note that these two things together are exactly how we define a “unimodal function”.

Note that we specifically do not know:
- Alex’ altitude at the moment she took the picture
- Alex’ climbing speed; it could have varied a lot during time

Hints:
- If we ask the altitude at t=altitude.length/2, can we deduce anything? Does it help to also ask t=0 and t=altitude.length?
- If we ask the altitude at t=altitude.length/3 and t=altitude.length*2/3, can we deduce anything?
- If we know Alex took the picture between t=0 and t=altitude.length*2/3, can we solve the problem then? 
And what if we know she took the picture between t=altitude.length/3 and t=altitude.length?

```java
package weblab;

class Solution {

  /**
   * Finds the x coordinate with the highest value of an array with a unimodal function, by recursively evaluating the values at one-third and two-thirds of the range.
   * Depending on which is higher, a new evaluation is made with a smaller range to find the x coordinate with the highest value.
   * @param altitude the array with the unimodal function
   * @return the x coordinate with the highest value
   */
  public static int findPictureTime(double[] altitude) {
    return findPictureTime(altitude, 0, altitude.length - 1);
  }

  /**
   * Finds the x coordinate with the highest value of an array with a unimodal function, by recursively evaluating the values at one-third and two-thirds of the range.
   * Depending on which is higher, a new evaluation is made with a smaller range to find the x coordinate with the highest value.
   * @param altitude the array with the unimodal function
   * @param a lower bound on the x coordinate
   * @param an upper bound on the x coordinate
   * @return the x coordinate with the highest value
   */
  public static int findPictureTime(double[] altitude, int low, int high) {
    // base case goes here
    // Checks last three points, can also be done in for loop it is in constant time
    if(high - low < 3) {
      if(altitude[low] > altitude[low+1]) {
        if(altitude[low] > altitude[high]) {
          return low;
        }
        return high;
      } else {
        if(altitude[low+1] > altitude[high]) {
          return low+1;
        }
        return high;
      }
    }
    
    int first = low + ((high - low) /3);
    int second = low + ((high - low) /3) * 2;
    
    //if first part is higher than second part then we are going lower and we can ignore last part.
    if(altitude[first] > altitude[second]) return findPictureTime(altitude, low, second);
    else return findPictureTime(altitude, first, high);
    //else we discard first third and continue in last two thirds
  }
}


```
