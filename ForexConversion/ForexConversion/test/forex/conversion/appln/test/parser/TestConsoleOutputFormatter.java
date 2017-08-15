package forex.conversion.appln.test;

import forex.conversion.appln.CurrencyRatesInfoLoader;
import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.parser.ConsoleOutputFormatter;
import forex.conversion.appln.parser.OutputFormatter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConsoleOutputFormatter extends TestCase {
  OutputFormatter outputFormatter;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @Test
  public void testDisplay() {
    BigDecimal d1 = new BigDecimal("24.17371");
    BigDecimal d2 = new BigDecimal("125.11");
    CurrencyBean c1 = new CurrencyBean("USD",2);
    c1.setValue(d1);
    CurrencyBean c2 = new CurrencyBean("EUR",2);
    c2.setValue(d2);
    CurrencyConversionBean bean = new CurrencyConversionBean(c1, c2);
    outputFormatter = new ConsoleOutputFormatter(bean);

    List<String> currencyList = new ArrayList<String>();
    currencyList.add("USD=2 decimal places");
    currencyList.add("EUR=2 decimal places");
    CurrencyRatesInfoLoader.getInstance().populateSupportedCurrencies(currencyList);
    outputFormatter.display();
    String outputString = String.format(
        ConsoleOutputFormatter.SUCCESS_OUTPUT_FORMAT,"USD",d1,"EUR",d2).trim();
    assertEquals(outputString, outContent.toString().trim());
  }

  @Test
  public void testDisplay2() {
    CurrencyBean c1 = new CurrencyBean("USD",2);
    CurrencyBean c2 = new CurrencyBean("KHG",2);
    CurrencyConversionBean bean = new CurrencyConversionBean(c1, c2);
    outputFormatter = new ConsoleOutputFormatter(bean);

    List<String> currencyList = new ArrayList<String>();
    currencyList.add("USD=2 decimal places");
    CurrencyRatesInfoLoader.getInstance().populateSupportedCurrencies(currencyList);
    outputFormatter.display();
    String outputString = String.format(
        ConsoleOutputFormatter.FAILURE_OUTPUT_FORMAT,"USD","KHG").trim();
    assertEquals(outputString, outContent.toString().trim());
  }

  @Test
  public void testDisplay3() {
    CurrencyBean c1 = null;
    CurrencyBean c2 = null;
    CurrencyConversionBean bean = new CurrencyConversionBean(c1, c2);
    outputFormatter = new ConsoleOutputFormatter(bean);

    outputFormatter.display();
    String outputString = "";
    assertEquals(outputString, outContent.toString().trim());
  }

  @Test
  public void testSetConvBean() {
    List<String> currencyList = new ArrayList<String>();
    currencyList.add("USD=2 decimal places");
    currencyList.add("KHG=2 decimal places");
    CurrencyRatesInfoLoader.getInstance().populateSupportedCurrencies(currencyList);

    CurrencyBean c1 = new CurrencyBean("USD",2);
    CurrencyBean c2 = new CurrencyBean("KHG",2);
    CurrencyConversionBean bean = new CurrencyConversionBean(c1, c2);
    outputFormatter = new ConsoleOutputFormatter(bean);
    BigDecimal d1 = new BigDecimal(123.12).setScale(2, RoundingMode.CEILING);
    BigDecimal d2 = new BigDecimal(14.12).setScale(2,RoundingMode.CEILING); 

    c1.setValue(d1);
    c2.setValue(d2);
    bean = new CurrencyConversionBean(c1, c2);
    outputFormatter.setCurrencyConversionBean(bean);
    outputFormatter.display();
    String outputString = String.format(
        ConsoleOutputFormatter.SUCCESS_OUTPUT_FORMAT,"USD",d1,"KHG",d2).trim();
    assertEquals(outputString, outContent.toString().trim()); 
  }

  @After
  public void tearDown() {
    System.setOut(null);
    System.setErr(null);
  }
}
