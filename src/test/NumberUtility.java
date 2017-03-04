
package test;

/**
 * @author Phil Adriaan
 * @version 1
 */
public class NumberUtility
{

  /**
   * Cannot construct this class.
   */
  private NumberUtility()
  {
  }

  /**
   * Gets the integer of this object if it is an instance of an integer.
   * 
   * @param the_object Object to be converted.
   * @return An integer.
   */
  public static int getInt(Object the_object)
  {
    if (the_object instanceof Integer)
    {
      return (int) the_object;
    }
    else if (the_object instanceof Double)
    {
      return ((Double) the_object).intValue();
    }
    System.err.print("Object is not an Integer or a Double.");
    return 0;
  }
}
