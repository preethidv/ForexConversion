package forex.conversion.appln.test;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.parser.ConsoleOutputFormatter;
import forex.conversion.appln.parser.OutputFormatStrategy;
import forex.conversion.appln.parser.OutputFormatter;

import junit.framework.TestCase;
import org.junit.Test;

public class TestOutputFormatStrategy extends TestCase {
  private OutputFormatStrategy outputFormatStrategy;

  @Test
  public void testSetOutputFormatStrategy() {
    CurrencyBean c1 = new CurrencyBean("PQR",2);
    CurrencyBean c2 = new CurrencyBean("ABC",2);
    CurrencyConversionBean convBean = new CurrencyConversionBean(c1,c2);
    OutputFormatter formatter = new ConsoleOutputFormatter(convBean);
    outputFormatStrategy = new OutputFormatStrategy(formatter);
    assertEquals(formatter,outputFormatStrategy.getOutputFormatStrategy());
  }

  @Test
  public void testSetOutputFormatStrategy2() {
    CurrencyBean c1 = new CurrencyBean("PQR",2);
    CurrencyBean c2 = new CurrencyBean("ABC",2);
    CurrencyConversionBean convBean = new CurrencyConversionBean(c1,c2);
    OutputFormatter formatter = new ConsoleOutputFormatter(convBean);
    outputFormatStrategy = new OutputFormatStrategy(formatter);

    class TestOutputFormatter implements OutputFormatter {
      @Override
      public void display() {
      }

      @Override
      public void setCurrencyConversionBean(CurrencyConversionBean bean) {
      }
    }

    OutputFormatter formatter2 = new TestOutputFormatter();
    outputFormatStrategy.setOutputFormatStrategy(formatter2);
    assertEquals(formatter2,outputFormatStrategy.getOutputFormatStrategy());
  }
}
