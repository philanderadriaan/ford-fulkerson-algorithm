
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class reads and writes files.
 * 
 * @author Phil Adriaan
 */
public class FileUtility
{
  /**
   * List of graph paths.
   */
  private static List<String> my_graph_paths = new ArrayList<String>();

  /**
   * Cannot instantiate this class.
   */
  private FileUtility()
  {
  }

  /**
   * Adds a graph to the path.
   * 
   * @param the_path Path to the graph file.
   */
  public static void add(String the_path)
  {
    my_graph_paths.add(the_path);
  }
  
  /**
   * Saves the paths into a path file.
   * 
   * @throws IOException If there's error writing to the file.
   */
  public static void save() throws IOException
  {
    write("paths.txt", my_graph_paths.toArray(new String[0])); 
  }
  
  /**
   * Loads the paths to the file.
   * 
   * @throws IOException If there's error reading the file.
   */
  public static void load() throws IOException
  {
    my_graph_paths = Arrays.asList(read("paths.txt"));
  }

  /**
   * Gets the name of the file only. Useful if using it for keys.
   * 
   * @param the_path
   * @return
   */
  public static String getNameOnly(String the_path)
  {
    int folder_index = 0;
    int extension_index = 0;
    if (the_path.contains("\\"))
    {
      folder_index = the_path.lastIndexOf("\\");
      folder_index++;
    }
    if (the_path.contains("."))
    {
      extension_index = the_path.lastIndexOf(".");
    }
    String name_only = the_path.substring(folder_index, extension_index);
    return name_only;
  }

  /**
   * Gets the graph paths.
   * 
   * @return An array of graph paths.
   */
  public static String[] getGraphPaths()
  {
    String[] path_array = new String[0];
    path_array = my_graph_paths.toArray(path_array);
    return path_array;
  }
  
  
  /**
   * Reads a file.
   * 
   * @param the_path Path to the file.
   * @return A string array containing lines in the file.
   * @throws IOException If there's error when reading.
   */
  public static String[] read(String the_path) throws IOException
  {
    // For storing lines from the file.
    List<String> data_list = new ArrayList<String>();

    // Creates objects for reading file.
    FileReader file_reader = new FileReader(the_path);
    BufferedReader buffered_reader = new BufferedReader(file_reader);
    String line = buffered_reader.readLine();

    // Ends when reaches end of file.
    while (line != null)
    {
      // Adds the current line into the list and go to next line.
      data_list.add(line);
      line = buffered_reader.readLine();
    }

    // Close reader objects when done.
    buffered_reader.close();
    file_reader.close();

    // Converts the list into an array and returns it.
    String[] data_array = new String[0];
    data_array = data_list.toArray(data_array);
    return data_array;
  }

  /**
   * Writes a file.
   * 
   * @param the_path Path to the file.
   * @param the_data String array containing lines to be written into the file.
   * @throws IOException If there's error when writing.
   */
  public static void write(String the_path, String[] the_data) throws IOException
  {
    // Creates objects to write files.
    FileWriter file_writer = new FileWriter(the_path);
    BufferedWriter buffered_writer = new BufferedWriter(file_writer);
    StringBuilder builder = new StringBuilder();

    // Loop through every line in the array.
    for (String line : the_data)
    {
      // Append the line into the string builder.
      builder.append(line);
      builder.append("\n");
    }

    // Writes the output into the file.
    String output = builder.toString();
    buffered_writer.write(output);
    buffered_writer.close();
    file_writer.close();
  }
}
