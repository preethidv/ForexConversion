package forex.conversion.appln.test;

import forex.conversion.appln.util.StringUtilHelper;

import junit.framework.TestCase;
import org.junit.Test;

public class TestStringUtilHelper extends TestCase {
  StringUtilHelper strHelper = null;

  @Override
  protected void setUp() throws Exception {
    strHelper = new StringUtilHelper();
  }

  @Test
  public void testisEmptyOrNull() {
    String s = null;
    assertEquals(true, strHelper.isEmptyOrNull(s));
  }

  @Test
  public void testisEmptyOrNull2() {
    String s = "";
    assertEquals(true, strHelper.isEmptyOrNull(s));
  }

  @Test
  public void testisEmptyOrNull3() {
    String s = "abcd";
    assertFalse(strHelper.isEmptyOrNull(s));
  }


  @Override
  protected void tearDown() throws Exception {
    strHelper = null;
  }
}
