
package test;

import given_code.BipartiteGraph;
import given_code.MeshGenerator;
import given_code.RandomGraph;

import java.io.IOException;

/**
 * Utility to generate graphs.
 * 
 * @author Phil Adriaan
 * @version 1
 */
public class GraphGeneratorUtility
{
  /**
   * Folder for output graph.
   */
  private static final String GRAPH_FOLDER = "graphs";

  /**
   * This class shouldn't be constructed.
   */
  private GraphGeneratorUtility()
  {
  }


  /**
   * Creates test cases for the bipartite graph.
   * 
   * @param the_number_of_left_vertices Vertices close to the source.
   * @param the_number_of_right_vertices Vertices close to the sink.
   * @param the_maximum_probability Probability of an edge between left and
   *          right.
   * @param the_minimum_capacity Minimum capacity.
   * @param the_maximum_capacity Maximum capacity.
   * @throws Exception If graph generator produces errors.
   */
  public static void generateBipartiteGraph(int the_number_of_left_vertices,
                                         int the_number_of_right_vertices,
                                         double the_maximum_probability,
                                         int the_minimum_capacity, int the_maximum_capacity)
      throws Exception
  {
    StringBuilder file_name_builder = new StringBuilder();
    file_name_builder.append(GRAPH_FOLDER);
    file_name_builder.append("\\");
    file_name_builder.append("b-");
    file_name_builder.append(the_number_of_left_vertices);
    file_name_builder.append("-");
    file_name_builder.append(the_number_of_right_vertices);
    file_name_builder.append("-");
    file_name_builder.append(the_maximum_probability);
    file_name_builder.append("-");
    file_name_builder.append(the_minimum_capacity);
    file_name_builder.append("-");
    file_name_builder.append(the_maximum_capacity);
    file_name_builder.append(".txt");
    String file_name = file_name_builder.toString();

    String[] args = new String[6];
    args[0] = Integer.toString(the_number_of_left_vertices);
    args[1] = Integer.toString(the_number_of_right_vertices);
    args[2] = Double.toString(the_maximum_probability);
    args[3] = Integer.toString(the_minimum_capacity);
    args[4] = Integer.toString(the_maximum_capacity);
    args[5] = file_name;

    BipartiteGraph bipartite_graph = new BipartiteGraph();
    bipartite_graph.main(args);

    FileUtility.add(file_name);
    //GraphvizUtility.visualizeBipartiteGraph(file_name);
  }

  public static void generateRandomGraph(int the_number_of_vertices, int the_number_of_edges,
                                      int the_minimum_capacity, int the_maximum_capacity)
      throws IOException
  {
    StringBuilder file_name_builder = new StringBuilder();
    file_name_builder.append(GRAPH_FOLDER);
    file_name_builder.append("\\");
    file_name_builder.append("r-");
    file_name_builder.append(the_number_of_vertices);
    file_name_builder.append("-");
    file_name_builder.append(the_number_of_edges);
    file_name_builder.append("-");
    file_name_builder.append(the_minimum_capacity);
    file_name_builder.append("-");
    file_name_builder.append(the_maximum_capacity);
    file_name_builder.append(".txt");
    String file_name = file_name_builder.toString();

    String[] args = new String[5];
    args[0] = Integer.toString(the_number_of_vertices);
    args[1] = Integer.toString(the_number_of_edges);
    args[2] = Integer.toString(the_minimum_capacity);
    args[3] = Integer.toString(the_maximum_capacity);
    args[4] = file_name;

    RandomGraph random_graph = new RandomGraph();
    random_graph.main(args);

    
    FileUtility.add(file_name);
    //GraphvizUtility.visualizeRandomGraph(file_name);
  }

  public static void generateMeshGraph(int the_number_of_rows, int the_number_of_columns,
                                    int the_maximum_capacity, boolean the_constant_capacity)
      throws IOException
  {
    String constant_capacity_flag = "";
    if (the_constant_capacity)
    {
      constant_capacity_flag = "-cc";
    }

    StringBuilder file_name_builder = new StringBuilder();
    file_name_builder.append(GRAPH_FOLDER);
    file_name_builder.append("\\");
    file_name_builder.append("m-");
    file_name_builder.append(the_number_of_rows);
    file_name_builder.append("-");
    file_name_builder.append(the_number_of_columns);
    file_name_builder.append("-");
    if (the_constant_capacity)
    {
      file_name_builder.append(the_maximum_capacity);
    }
    else
    {
      file_name_builder.append(1);
    }
    file_name_builder.append("-");
    file_name_builder.append(the_maximum_capacity);
    file_name_builder.append(".txt");
    String file_name = file_name_builder.toString();

    String[] args = new String[5];
    args[0] = Integer.toString(the_number_of_rows);
    args[1] = Integer.toString(the_number_of_columns);
    args[2] = Integer.toString(the_maximum_capacity);
    args[3] = file_name;
    args[4] = constant_capacity_flag;

    MeshGenerator mesh_graph = new MeshGenerator(args);
    mesh_graph.generate();

    FileUtility.add(file_name);
    //GraphvizUtility.visualizeMeshGraph(file_name);
  }
}
