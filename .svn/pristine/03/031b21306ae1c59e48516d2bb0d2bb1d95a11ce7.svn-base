
package test;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

/**
 * This class manages results for all test results.
 * 
 * @author Phil Adriaan
 * @version 1
 */
public class ResultUtility
{
  /**
   * Results folder name.
   */
  private static final String FOLDER_NAME = "results";

  /**
   * Result queue for storage. First in first out so map is not used.
   */
  private static List<Entry<String, Result>> my_result_queue =
      new ArrayList<Entry<String, Result>>();


  /**
   * Cannot construct this class.
   */
  private ResultUtility()
  {
  }
  
  /**
   * Filter the results for desired output.
   * 
   * @param the_path Path to the csv file.
   * @param the_filter Filter the data you want.
   * @throws IOException If there's error
   */
  public static void filter(String the_path, String... the_filter) throws IOException
  {
    String[][] input_data = CSVUtility.read(the_path);
    List<List<String>> output_data = new ArrayList<List<String>>();
    int row_count = input_data.length;

    for (int i = 0; i < row_count; i++)
    {
      boolean add = true;
      String equal = null;
      int cell_count = input_data[i].length;

      for (int j = 0; j < cell_count; j++)
      {
        if (!the_filter[j].equals("*"))
        {
          if ("=".equals(the_filter[j]))
          {
            if (equal == null)
            {
              equal = input_data[i][j];
            }
            else if (!equal.equals(input_data[i][j]))
            {
              add = false;
            }
          }
          else if (!the_filter[j].equalsIgnoreCase(input_data[i][j]))
          {
            add = false;
          }
        }
      }
      if (i == 0 || add)
      {
        List<String> row = new ArrayList<String>();
        for (int j = 0; j < cell_count; j++)
        {
            row.add(input_data[i][j]);
        }
        output_data.add(row);
      }
    }

    String name_only = FileUtility.getNameOnly(the_path);
    StringBuilder file_name_builder = new StringBuilder();
    file_name_builder.append("xls\\");
    file_name_builder.append(name_only);
    for (String i : the_filter)
    {
      file_name_builder.append("-");
      if ("*".equals(i))
      {
        file_name_builder.append("star");
      }
      else
      {
        file_name_builder.append(i);
      }
    }
    file_name_builder.append(".xls");

    String output_path = file_name_builder.toString();
    XLSUtility.write(output_path, output_data);
  }

  /**
   * Whether the queue contains the key.
   * 
   * @param the_key Key in question.
   * @return True if there's a key, false if otherwise.
   */
  private static boolean containsKey(String the_key)
  {
    boolean found = false;
    for (Entry<String, Result> i : my_result_queue)
    {
      if (the_key.equals(i.getKey()))
      {
        found = true;
        break;
      }
    }
    return found;
  }

  /**
   * Gets the result corresponding to a key.
   * 
   * @param the_key Key in question.
   * @return The result for that key.
   */
  private static Result get(String the_key)
  {
    for (Entry<String, Result> i : my_result_queue)
    {
      if (the_key.equals(i.getKey()))
      {
        return i.getValue();
      }
    }
    return null;
  }

  /**
   * Adds a key, an algorithm and a time into the record.
   * 
   * @param the_key Type of graph.
   * @param algorithm Algorithm used.
   * @param the_time Time taken to complete the algorithm.
   */
  public static void add(String the_key, String algorithm, long the_time)
  {
    if (containsKey(the_key))
    {
      Result previous_results = get(the_key);
      previous_results.add(algorithm, the_time);
    }
    else
    {
      Result new_result = new Result();
      new_result.add(algorithm, the_time);
      Entry<String, Result> new_entry =
          new AbstractMap.SimpleEntry<String, Result>(the_key, new_result);
      my_result_queue.add(new_entry);
    }
  }

  /**
   * Publish all the results to CSV files.
   * 
   * @throws IOException
   */
  public static void publish() throws IOException
  {
    CSVUtility.write("results\\bipartite.csv", new String[][] {{"Left", "Right", "Density",
        "Minimum Capacity", "Capacity", "Ford Fulkerson", "Scaling Ford Fulkerson",
        "Preflow Push"}});
    CSVUtility.write("results\\mesh.csv", new String[][] {{"Row", "Column",
        "Minimum Capacity", "Capacity", "Ford Fulkerson", "Scaling Ford Fulkerson",
        "Preflow Push"}});
    CSVUtility.write("results\\random.csv", new String[][] {{"Vertices", "Density",
        "Minimum Capacity", "Capacity", "Ford Fulkerson", "Scaling Ford Fulkerson",
        "Preflow Push"}});

    for (Entry<String, Result> i : my_result_queue)
    {
      String key = i.getKey();
      Result result = i.getValue();

      String[] key_split = key.split("-");
      List<String> fixed_list = Arrays.asList(key_split);
      List<String> line_list = new ArrayList<String>(fixed_list);
      String graph_type = line_list.remove(0);
      String path = "results\\";

      if ("b".equalsIgnoreCase(graph_type))
      {
        path += "bipartite.csv";
        String probability = line_list.remove(2);
        String density;
        if ("0.0".equalsIgnoreCase(probability))
        {
          density = "Sparse";
        }
        else
        {
          density = "Dense";
        }
        line_list.add(2, density);
      }
      else if ("m".equalsIgnoreCase(graph_type))
      {
        path += "mesh.csv";
      }
      else if ("r".equalsIgnoreCase(graph_type))
      {
        path += "random.csv";
        String edges = line_list.remove(1);
        String density;
        if ("0".equalsIgnoreCase(edges))
        {
          density = "Sparse";
        }
        else
        {
          density = "Dense";
        }
        line_list.add(1, density);
      }
      long average = result.getAverage("f");
      String average_string = Long.toString(average);
      line_list.add(average_string);

      average = result.getAverage("s");
      average_string = Long.toString(average);
      line_list.add(average_string);

      average = result.getAverage("p");
      average_string = Long.toString(average);
      line_list.add(average_string);

      String[] cell_array = new String[0];
      cell_array = line_list.toArray(cell_array);
      CSVUtility.append(path, cell_array);
    }
  }
}
