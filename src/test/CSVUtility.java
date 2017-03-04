
package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to process CSV files.
 * 
 * @author Phil Adriaan
 * @version 1
 */
public final class CSVUtility
{

  /**
   * Private constructor prevents instantiation.
   */
  private CSVUtility()
  {
  }

  /**
   * Reads a CSV file and outputs it as a 2D list of strings.
   * 
   * @param the_path Path to the CSV file.
   * @return 2D array containing the data.
   * @throws IOException For IO errors.
   */
  public static String[][] read(final String the_path) throws IOException
  {
    final List<String[]> data_list = new ArrayList<String[]>();
    String[] line_array = FileUtility.read(the_path);
    for (String i : line_array)
    {
      String[] line_split = i.split(",");
      data_list.add(line_split);
    }
    String[][] data_array = new String[0][0];
    data_array = data_list.toArray(data_array);
    return data_array;
  }

  /**
   * Writes the data from a 2D array to the file defined by the path.
   * 
   * @param the_path Path of CSV file.
   * @param the_data Data to be written to the CSV file.
   * @throws IOException Throws exception when error in reading or writing.
   */
  public static void write(final String the_path, final String[][] the_data)
      throws IOException
  {
    int line_count = the_data.length;
    String[] line_array = new String[line_count];
    for (int i = 0; i < line_count; i++)
    {
      int line_length = the_data[i].length;
      StringBuilder line_builder = new StringBuilder();
      for (int j = 0; j < line_length; j++)
      {
        if (j > 0)
        {
          line_builder.append(",");
        }
        line_builder.append(the_data[i][j]);
      }
      String line = line_builder.toString();
      line_array[i] = line;
    }
    FileUtility.write(the_path, line_array);
  }

  /**
   * Appends a line of data to the end of a CSV file. No commas allowed!
   * 
   * @param the_path Path of CSV file.
   * @param the_line Line to be added to the CSV file.
   * @throws IOException Throws exception when error in reading or writing.
   */
  public static void append(final String the_path, String[] the_line)
      throws IOException
  {
    final BufferedWriter writer = new BufferedWriter(new FileWriter(the_path, true));
    final StringBuilder builder = new StringBuilder();
    builder.append(the_line[0]);
    for (int i = 1; i < the_line.length; i++)
    {
      builder.append(',');
      builder.append(the_line[i]);
    }
    builder.append("\n");
    writer.append(builder.toString());
    writer.close();
  }
}
