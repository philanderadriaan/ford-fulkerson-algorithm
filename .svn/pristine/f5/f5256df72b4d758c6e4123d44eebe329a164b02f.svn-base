
package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class stores runtime results for one graph.
 * 
 * @author Phil Adriaan
 * @version 1
 */
public class Result
{

  /**
   * Maps the results to an algorithm.
   */
  Map<String, List<Long>> result_map = new HashMap<String, List<Long>>();

  /**
   * Constructs the class.
   */
  public Result()
  {
  }

  /**
   * Adds an algorithm and its runtime to the mapl
   * 
   * @param the_algorithm Algorithm ran.
   * @param the_time Running time.
   */
  public void add(String the_algorithm, long the_time)
  {
    if (result_map.containsKey(the_algorithm))
    {
      List<Long> result_list = result_map.get(the_algorithm);
      result_list.add(the_time);
    }
    else
    {
      List<Long> result_list = new ArrayList<Long>();
      result_list.add(the_time);
      result_map.put(the_algorithm, result_list);
    }
  }

  /**
   * Gets the average time for that algorithm.
   * 
   * @param the_algorithm Algorithm to query.
   * @return Average time for that algorithm to run.
   */
  public long getAverage(String the_algorithm)
  {

    List<Long> result_list = result_map.get(the_algorithm);
    result_list.remove(0);

    long total_time = 0;
    for (long i : result_list)
    {
      total_time += i;
    }
    long iteration_count = result_list.size();
    long average_time = total_time / iteration_count;
    return average_time;
  }

}
