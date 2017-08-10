package forex.conversion.appln.test;

import forex.conversion.appln.bean.CurrencyBean;

import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.Test;

public class TestCurrencyBean extends TestCase {
  CurrencyBean bean ;

  @Test
  public void testGetDecimalPlaces() {
    bean = new CurrencyBean("USD",2);
    assertEquals(2,bean.getDecimalPlaces());

    bean.setDecimalPlaces(5);
    assertEquals(5,bean.getDecimalPlaces());
  }

  @Test
  public void testGetShortForm() {
    bean = new CurrencyBean("USD",2);
    assertEquals("USD",bean.getShortForm());

    bean.setShortForm("EUR");
    assertEquals("EUR",bean.getShortForm());
  }

  @Test
  public void testEquals() {
    CurrencyBean b1 = new CurrencyBean("ABC");
    CurrencyBean b2 = new CurrencyBean("ABC");
    assertTrue(b1.equals(b2) && b2.equals(b1));
    assertTrue(b1.hashCode() == b1.hashCode());
  }
 
  @Test
  public void testEquals2() {
    CurrencyBean b1 = new CurrencyBean("ABC");
    CurrencyBean b2 = new CurrencyBean("PQR");
    assertFalse(b1.equals(b2));
  }
  
  @Test
  public void testEquals3() {
    Object obj = new Object();
    CurrencyBean b2 = new CurrencyBean("PQR");
    assertFalse(b2.equals(obj));
  }
  
  @Test
  public void testGetValue() {
    bean = new CurrencyBean("ABC");
    BigDecimal val = new BigDecimal("12");
    bean.setValue(val);
    assertEquals(val,bean.getValue());
  }
  
  @Test
  public void testGetValue2() {
    BigDecimal val = new BigDecimal("15");
    bean = new CurrencyBean("ABC",2,val);
    assertEquals(val,bean.getValue());
  }
}
