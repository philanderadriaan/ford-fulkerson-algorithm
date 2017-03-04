
package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods to visualize graph output files using Graphviz.
 * 
 * @author Phil Adriaan
 */
public class GraphvizUtility
{

  /**
   * Cannot construct this class.
   */
  private GraphvizUtility()
  {
  }

  /**
   * Visualize graph generated by BipartiteGraph.java.
   * 
   * @param the_path Path to bipartite graph output file.
   * @throws IOException if there's error when reading and writing.
   */
  public static void visualizeBipartiteGraph(String the_path) throws IOException
  {
    // Graphs generated by BipartiteGraph.java is tab separated.
    visualizeGraph(the_path, "\t");
  }

  /**
   * Visualize graph generated by RandomGraph.java.
   * 
   * @param the_path Path to random graph output file.
   * @throws IOException if there's error when reading and writing.
   */
  public static void visualizeRandomGraph(String the_path) throws IOException
  {
    // Graphs generated by RandomGraph.java is tab separated.
    visualizeGraph(the_path, " ");
  }

  /**
   * Visualize graph generated by MeshGenerator.java.
   * 
   * @param the_path Path to mesh graph output file.
   * @throws IOException if there's error when reading and writing.
   */
  public static void visualizeMeshGraph(String the_path) throws IOException
  {
    // Graphs generated by MeshGenerator.java is tab separated.
    visualizeGraph(the_path, " ");
  }

  /**
   * Visualize an arbitrary graph.
   * 
   * @param the_path Path to graph output file.
   * @param the_separator Regular expression that separates the values.
   * @throws IOException if there's error when reading and writing.
   */
  public static void visualizeGraph(String the_path, String the_separator) throws IOException
  {
    // Reads the file on the path.
    String[] input_array = FileUtility.read(the_path);

    // List of strings to store graphviz script line by line.
    List<String> output_list = new ArrayList<String>();

    // Create a directed graph.
    output_list.add("digraph g");
    output_list.add("{");

    // Align vertices from left to right.
    output_list.add("rankdir = \"LR\";");

    // Reads through the input line by line.
    for (String line : input_array)
    {
      // Split the line using the regex.
      String[] line_split = line.split(the_separator);
      int number_of_columns = line_split.length;
      int initial_index = number_of_columns - 3;

      // Get the vertexes and capacity adjacent to the current edge from the
      // separated values.
      String vertex_from = line_split[initial_index + 0];
      String vertex_to = line_split[initial_index + 1];
      String capacity = line_split[initial_index + 2];

      // Convert values to graphviz script.
      StringBuilder string_builder = new StringBuilder();
      string_builder.append("\"");
      string_builder.append(vertex_from);
      string_builder.append("\" -> \"");
      string_builder.append(vertex_to);
      string_builder.append("\"");

      // Hide capacities of 0 capacity edges.
      if (!"0".equals(capacity))
      {
        // Create edge capacity labels in the graphviz script.
        string_builder.append(" [label = \"");
        string_builder.append(capacity);
        string_builder.append("\"]");
      }

      // Adds the line of script into the list.
      string_builder.append(";");
      String output_line = string_builder.toString();
      output_list.add(output_line);
    }

    // Ends the script.
    output_list.add("}");

    // Converts the list into an array.
    String[] output_array = new String[0];
    output_array = output_list.toArray(output_array);

    // Save graphviz script to a .gv file in the same path.
    String file_name_only = FileUtility.getNameOnly(the_path);
    String output_path = "graphviz\\" + file_name_only + ".gv";
    FileUtility.write(output_path, output_array);

    // Runs graphviz and draws the graph.
    Desktop desktop = Desktop.getDesktop();
    File script_file = new File(output_path);
    //desktop.open(script_file);
  }
}
