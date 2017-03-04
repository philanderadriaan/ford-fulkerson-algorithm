
package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Utility for reading and writing to excel files.
 * 
 * @author Phil Adriaan
 * @version 1
 */
public final class XLSUtility
{

  /**
   * Cannot construct this class.
   */
  private XLSUtility()
  {
  }

  /**
   * Writes the content of a 2D list into an excel file.
   * 
   * @param the_path File name.
   * @param the_data Raw data of all Table.
   * @throws IOException For reading and writing errors.
   */
  public static void write(final String the_path, final List<List<String>> the_data)
      throws IOException
  {
    final Workbook book = new HSSFWorkbook();
    final Sheet sheet = book.createSheet(FileUtility.getNameOnly(the_path));
    for (int i = 0; i < the_data.size(); i++)
    {
      final Row row = sheet.createRow(i);
      for (int j = 0; j < the_data.get(i).size(); j++)
      {
        final String cell = the_data.get(i).get(j);
        row.createCell(j).setCellValue(cell);
      }
    }
    for (int i = 0; i < the_data.size(); i++)
    {
      sheet.autoSizeColumn(i);
    }
    FileOutputStream output;
    output = new FileOutputStream(the_path);
    book.write(output);
    output.close();
  }

}
