package forex.conversion.appln.test;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.parser.ConsoleInputParser;
import forex.conversion.appln.parser.InputParser;
import forex.conversion.appln.parser.InputTypeStrategy;

import junit.framework.TestCase;
import org.junit.Test;

public class TestInputTypeStrategy extends TestCase {
  private InputTypeStrategy inputTypeStrategy;
  
  @Test
  public void testSetInputTypeStrategy() {
    InputParser parser = new ConsoleInputParser("");
    inputTypeStrategy = new InputTypeStrategy(parser);
    assertEquals(parser,inputTypeStrategy.getInputTypeStrategy());
  }
  
  
  @Test
  public void testSetInputTypeStrategy2() {
    InputParser parser = new ConsoleInputParser("");
    inputTypeStrategy = new InputTypeStrategy(parser);
  
    class TestParser implements InputParser {

      @Override
      public CurrencyConversionBean getCurrencyConversionDetails() {
        return null;
      }

      @Override
      public CurrencyConversionBean parse() throws Exception {
        return null;
      }
    }

    InputParser parser2 = new TestParser();
    inputTypeStrategy.setInputTypeStrategy(parser2);
    assertEquals(parser2,inputTypeStrategy.getInputTypeStrategy());
  }
  
  @Test
  public void testSetInputTypeStrategy3() {
    InputParser parser = new ConsoleInputParser("");
    inputTypeStrategy = new InputTypeStrategy(parser);
  
    class TestParser implements InputParser {

      @Override
      public CurrencyConversionBean getCurrencyConversionDetails() {
        CurrencyBean c1 = new CurrencyBean("PQR",2);
        CurrencyBean c2 = new CurrencyBean("ABC",2);
        CurrencyConversionBean convBean = new CurrencyConversionBean(c1, c2);
        return convBean;
      }

      @Override
      public CurrencyConversionBean parse() throws Exception {
        CurrencyBean c1 = new CurrencyBean("PQR",2);
        CurrencyBean c2 = new CurrencyBean("ABC",2);
        CurrencyConversionBean convBean = new CurrencyConversionBean(c1, c2);
        return convBean;
      }
    }

    InputParser parser2 = new TestParser();
    inputTypeStrategy.setInputTypeStrategy(parser2);
    try {
      CurrencyBean c1 = new CurrencyBean("PQR",2);
      CurrencyBean c2 = new CurrencyBean("ABC",2);
      CurrencyConversionBean testBean = new CurrencyConversionBean(c1,c2);
      CurrencyConversionBean convBean = inputTypeStrategy.parse();
      assertEquals(convBean, testBean);
    } catch (Exception e) {
      
    }
  }
  

  
}
