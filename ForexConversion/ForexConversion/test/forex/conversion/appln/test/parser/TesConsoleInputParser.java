package forex.conversion.appln.test.parser;

import forex.conversion.appln.RunCurrencyConversion;
import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.parser.ConsoleInputParser;
import forex.conversion.appln.parser.InputParser;
import forex.conversion.appln.validation.IncorrectInputException;
import forex.conversion.appln.validation.InputFormatException;

import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestConsoleInputParser extends TestCase {
  InputParser ip;
  RunCurrencyConversion runConversion = new RunCurrencyConversion();

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Override
  protected void setUp() throws Exception {
    runConversion.loadData();
    ip = new ConsoleInputParser("");
  }

  @Test
  public void testParse() {
    String str = "AUD 100.00 in USD";
    ip = new ConsoleInputParser(str);
    try {
      ip.parse();
    } catch (Exception e) {
      
    }
    CurrencyBean c1 = new CurrencyBean("AUD");
    CurrencyBean c2 = new CurrencyBean("USD");
    CurrencyConversionBean bean = new CurrencyConversionBean(c1, c2);
    assertEquals(bean,ip.getCurrencyConversionDetails());
  }

  @Test
  public void testParse2() {
    String str = "100.00 in USD";
    ip = new ConsoleInputParser(str);
    try {
      ip.parse();
    } catch (Exception e) { 
      
    }
    
    assertEquals(null,ip.getCurrencyConversionDetails());
    exception.expect(InputFormatException.class);
  }

  @Test
  public void testParse3() {
    String str = "something";
    ip = new ConsoleInputParser(str);
    try {
      ip.parse();
    } catch(Exception e) {
    }

    assertEquals(null,ip.getCurrencyConversionDetails());
    exception.expect(InputFormatException.class);
  }


  @Test
  public void testParse4() {
    String str = "THY 100 in WJD";
    ip = new ConsoleInputParser(str);
    try {
      ip.parse();
    } catch(Exception e) {
    }

    assertEquals(null,ip.getCurrencyConversionDetails());
    exception.expect(IncorrectInputException.class);
  }
  
  @Test
  public void testParse5() {
    String str = "DKK ABC in USD";
    ip = new ConsoleInputParser(str);
    try {
      ip.parse();
    } catch(Exception e) {
    }
    exception.expect(InputFormatException.class);
  }

  @Override
  protected void tearDown() throws Exception {
    runConversion.unloadData();
    ip = null;
  }
}
