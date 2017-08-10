package forex.conversion.appln.test;

import forex.conversion.appln.ConversionFromPreloadedData;
import forex.conversion.appln.ConversionStrategy;
import forex.conversion.appln.Converter;
import forex.conversion.appln.bean.CurrencyBean;
import junit.framework.TestCase;

import java.math.BigDecimal;

import org.junit.Test;

public class TestConversionStrategy extends TestCase {
  private ConversionStrategy convStrategy;
  
  @Override
  protected void setUp() throws Exception {
  }

  @Test
  public void testSetConversionStategy() {
    Converter con = new ConversionFromPreloadedData();
    convStrategy = new ConversionStrategy(con);
    assertEquals(con,convStrategy.getConversionStrategy());
  }
  
  
  @Test
  public void testSetConversionStategy2() {
    Converter con = new ConversionFromPreloadedData();
    convStrategy = new ConversionStrategy(con);
  
    class TestCon implements Converter {
      @Override
      public BigDecimal convert(CurrencyBean inputCurrency, CurrencyBean outputCurrency) {
        return BigDecimal.ZERO;
      }
    }

    Converter con2 = new TestCon();
    convStrategy.setConversionStrategy(con2);
    assertEquals(con2,convStrategy.getConversionStrategy());
  }
  
  @Test
  public void testSetConversionStategy3() {
    Converter con = new ConversionFromPreloadedData();
    convStrategy = new ConversionStrategy(con);
  
    class TestCon implements Converter {
      @Override
      public BigDecimal convert(CurrencyBean inputCurrency, CurrencyBean outputCurrency) {
        return BigDecimal.ZERO;
      }
    }

    Converter con2 = new TestCon();
    CurrencyBean inputCurrency = new CurrencyBean("USD",2);
    CurrencyBean outputCurrency = new CurrencyBean("EUR",2);
    convStrategy.setConversionStrategy(con2);
    assertEquals(BigDecimal.ZERO,convStrategy.convert(inputCurrency, outputCurrency));
  }
  
  @Override
  protected void tearDown() throws Exception {
    convStrategy = null;
  }
}
