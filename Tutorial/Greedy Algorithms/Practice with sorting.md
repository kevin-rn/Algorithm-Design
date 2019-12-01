You are assigned by your employer (AD Inc.) to complete a list of tasks in the most efficient way possible.
In order to keep track of your progress, your employer also wants to know the median task(s) in terms of their efficiency.

In this exercise, you will receive an array of n tasks.
Your task is to sort them ascendingly based on their efficiency and return id(s) of the median task(s).
When two tasks have the same efficiency you should sort them by id ascendingly.

Note that if the array has an even length, you should return the ids of the two median tasks, separated by a space.

The efficiency of a task is defined as value/time.

```java
package weblab;

import java.util.*;

class Solution {

  /**
   * Return the id(s) of the tasks with the median value for their efficiency, where efficiency = value/time.
   * @param tasks Array of tasks, in no particular order.
   * @return The id(s) of the median task(s) based on efficiency. If you have an odd number of tasks, return the ids of the two median
   * tasks with a space between. eg if the median is 4,5. "(id of 4th task) (id of 5th task)"
   */
  public static String solve(Task[] tasks) {
    Arrays.sort(tasks);
    int middle = tasks.length/2; // length 5:2. length 4: 1 and 2, this only gives 2
    if(tasks.length % 2 == 0) return "" + tasks[middle-1].id + " " + tasks[middle].id;
    return "" + tasks[middle-1].id;
  }
}

class Task implements Comparable<Task> {

  int id;

  double time;

  double value;

  Task(double time, double value, int id) {
    this.time = time;
    this.value = value;
    this.id = id;
  }
  
  //Other way of sorting instead of Arrays.sort using compareTo
  public int compareTo(Task other) {
    if (Double.compare(this.value/this.time, other.value/other.time) == 0) //same efficiency
      return Integer.compare(this.id, other.id);
    return Double.compare(this.value/this.time, other.value/other.time); 
    
  }
}


```
