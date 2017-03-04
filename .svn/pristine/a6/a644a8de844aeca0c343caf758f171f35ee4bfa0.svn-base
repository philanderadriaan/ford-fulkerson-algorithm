
package test;

import java.io.IOException;

import ford_fulkerson.FordFulkerson;
import ford_fulkerson.ScalingFordFulkerson;
import given_code.PreflowPush;

/**
 * This class creates scenarios for test cases.
 * 
 * @author Phil Adriaan
 */
public class Test
{

  /**
   * Range of vertices to be tested.
   */
  private static final int[] VERTEX_RANGE = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
  /**
   * Range of capacities to be tested.
   */
  private static final int[] CAPACITY_RANGE = {100};
  /**
   * Toggle the graph capacities. True is constant capacity, false is variable
   * capacity.
   */
  private static final boolean[] CONSTANT_TOGGLE = {true, false};
  /**
   * Toggle the graph density. True is dense, false is sparse.
   */
  private static final boolean[] DENSITY_TOGGLE = {true, false};

  /**
   * Cannot construct this class.
   */
  private Test()
  {
  }

  /**
   * Creates test cases.
   * 
   * @param args Command line arguments.
   * @throws Exception if the graph generator throws any exception.
   */
  public static void main(String[] args) throws Exception
  {
    generateGraphs();
    FileUtility.save();
    FileUtility.load();
    testGraphs();
    createXLS();
  }

  /**
   * Generates XLS files for creating charts.
   * 
   * @throws IOException If there's error reading/writing.
   */
  private static void createXLS() throws IOException
  {

    String path;

    for (int c : CAPACITY_RANGE)
    {
      for (boolean ct : CONSTANT_TOGGLE)
      {
        for (boolean dt : DENSITY_TOGGLE)
        {
          String min = "1";
          String max = Integer.toString(c);
          if (ct)
          {
            min = "=";
            max = "=";
          }

          String density = "Sparse";
          if (dt)
          {
            density = "Dense";
          }

          path = "results\\bipartite.csv";
          ResultUtility.filter(path, "*", "*", density, min, max, "*", "*", "*");

          path = "results\\mesh.csv";
          ResultUtility.filter(path, "*", "*", min, max, "*", "*", "*");

          path = "results\\random.csv";
          ResultUtility.filter(path, "*", density, min, max, "*", "*", "*");
        }
      }
    }
  }

  /**
   * Tests graphs with the algorithms and times them.
   * 
   * @throws IOException If there's error reading/writing to CSV file.
   * @throws InterruptedException If there's interruption.
   */
  private static void testGraphs() throws IOException, InterruptedException
  {
    String[] path_array = FileUtility.getGraphPaths();
    for (String path : path_array)
    {
      String file_name_only = FileUtility.getNameOnly(path);

      for (int i = 0; i < 3; i++)
      {

        FordFulkerson ford_fulkerson = new FordFulkerson(path);
        long start = System.nanoTime();
        int ford_fulkerson_max_flow = ford_fulkerson.run();
        long stop = System.nanoTime();
        long time = stop - start;
        String key = file_name_only;
        ResultUtility.add(key, "f", time);

        ScalingFordFulkerson scaling_ford_fulkerson = new ScalingFordFulkerson(path);
        start = System.nanoTime();
        int scaling_ford_fulkerson_max_flow = scaling_ford_fulkerson.run();
        stop = System.nanoTime();
        time = stop - start;
        key = file_name_only;
        ResultUtility.add(key, "s", time);

        PreflowPush preflow_push = new PreflowPush(path);
        start = System.nanoTime();
        int preflow_push_max_flow = preflow_push.findMaxFlow();
        stop = System.nanoTime();
        time = stop - start;
        key = file_name_only;
        ResultUtility.add(key, "p", time);

        if (ford_fulkerson_max_flow != scaling_ford_fulkerson_max_flow ||
            ford_fulkerson_max_flow != preflow_push_max_flow ||
            scaling_ford_fulkerson_max_flow != preflow_push_max_flow)
        {
          System.err.println();
          System.err.println("Inconsistent results:");
          System.err.println("Ford Fulkerson: " + ford_fulkerson_max_flow);
          System.err.println("Scaling Ford Fulkerson: " + scaling_ford_fulkerson_max_flow);
          System.err.println("Preflow Push " + preflow_push_max_flow);
          System.err.println();
          System.exit(0);
        }
      }
    }
    ResultUtility.publish();
  }

  /**
   * Generates graphs using the global variables.
   * 
   * @throws Exception If an exception occurs during graph generation.
   */
  private static void generateGraphs() throws Exception
  {
    for (int v : VERTEX_RANGE)
    {
      for (boolean constant_toggle : CONSTANT_TOGGLE)
      {
        for (int maximum_capacity : CAPACITY_RANGE)
        {
          int minimum_capacity = 1;
          if (constant_toggle)
          {
            minimum_capacity = maximum_capacity;
          }
          for (boolean density_toggle : DENSITY_TOGGLE)
          {
            double bipartite_probability = 0;
            int random_edge = 0;

            if (density_toggle)
            {
              bipartite_probability = 1;
              random_edge = v - 1;
            }
            GraphGeneratorUtility.generateBipartiteGraph(v / 2, v / 2, bipartite_probability,
                                                         minimum_capacity, maximum_capacity);

            GraphGeneratorUtility.generateMeshGraph(v, v,
                                                    maximum_capacity, constant_toggle);

            GraphGeneratorUtility.generateRandomGraph(v, random_edge, minimum_capacity,
                                                      maximum_capacity);
          }
        }
      }
    }
  }
}
