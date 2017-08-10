package forex.conversion.appln.util;

public class StringUtilHelper {

  /**checks if a string is null or empty.
   * @param str - string to check.
   * @return boolean.
   */
  public boolean isEmptyOrNull(String str) {
    if (str == null || str.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
}
