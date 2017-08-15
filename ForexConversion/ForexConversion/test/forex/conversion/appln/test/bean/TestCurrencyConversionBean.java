package forex.conversion.appln.test.bean;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TestCurrencyConversionBean extends TestCase {
  CurrencyConversionBean convBean;

  @Test
  public void testGetCurrentCurrency() {
    CurrencyBean c1 = new CurrencyBean("USD");
    CurrencyBean c2 = new CurrencyBean("INR");
    convBean = new CurrencyConversionBean(c1, c2);
    assertEquals(c1,convBean.getCurrentCurrency());

    convBean.setCurrentCurrency(c2);
    assertEquals(c2,convBean.getCurrentCurrency());
  }

  @Test
  public void testGetCurrencyToConvert() {
    CurrencyBean c1 = new CurrencyBean("USD");
    CurrencyBean c2 = new CurrencyBean("INR");
    convBean = new CurrencyConversionBean(c1, c2);
    assertEquals(c2,convBean.getCurrencyToConvert());

    convBean.setCurrencyToConvert(c1);
    assertEquals(c1,convBean.getCurrencyToConvert());
  }
  
  @Test
  public void testEquals() {
    CurrencyBean c1 = new CurrencyBean("ABC");
    CurrencyBean c2 = new CurrencyBean("PQR");
    CurrencyConversionBean bean1 = new CurrencyConversionBean(c1, c2);
    
    c1 = new CurrencyBean("ABC");
    c2 = new CurrencyBean("PQR");
    CurrencyConversionBean bean2 = new CurrencyConversionBean(c1, c2);
    
    assertTrue(bean1.equals(bean2) && bean2.equals(bean1));
    assertTrue(bean1.hashCode() == bean2.hashCode());
  }
  
  @Test
  public void testEquals2() {
    CurrencyBean c1 = new CurrencyBean("ABC");
    CurrencyBean c2 = new CurrencyBean("PQR");
    CurrencyConversionBean bean1 = new CurrencyConversionBean(c1, c2);
    
    c1 = new CurrencyBean("QWE");
    c2 = new CurrencyBean("PQR");
    CurrencyConversionBean bean2 = new CurrencyConversionBean(c1, c2);
    
    Assert.assertNotEquals(bean1,bean2);
  }

  @Test
  public void testEquals3() {
    CurrencyBean c1 = new CurrencyBean("ABC");
    CurrencyBean c2 = new CurrencyBean("PQR");
    CurrencyConversionBean bean1 = new CurrencyConversionBean(c1, c2);
    
    Assert.assertNotEquals(bean1,new Object());
  }
}
