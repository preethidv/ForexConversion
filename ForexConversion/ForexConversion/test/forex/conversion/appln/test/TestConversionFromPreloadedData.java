package forex.conversion.appln.test;

import forex.conversion.appln.ConversionFromPreloadedData;
import forex.conversion.appln.RunCurrencyConversion;
import forex.conversion.appln.bean.CurrencyBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConversionFromPreloadedData extends TestCase {
  RunCurrencyConversion runConversion = null;
  ConversionFromPreloadedData converter;

  @Before
  protected void setUp() throws Exception {
    runConversion = new RunCurrencyConversion();
    runConversion.loadData();
    converter = new ConversionFromPreloadedData();
  }

  @Test
  public void testGetConvertedForexValue() {
    BigDecimal d1 = new BigDecimal("125");
    BigDecimal d2 = new BigDecimal("12");
    BigDecimal d3 = d1.multiply(d2);
    assertEquals(d3,converter.getConvertedForexValue(d1,d2));
  }

  @Test
  public void testConvert() {
    BigDecimal d1 = new BigDecimal("145.12");
    CurrencyBean inputCurrency = new CurrencyBean("USD",2);
    inputCurrency.setValue(d1);
    CurrencyBean outputCurrency = new CurrencyBean("USD",2);
    assertEquals(d1,converter.convert(inputCurrency,outputCurrency));
  }

  @Test
  public void testConvert2() {
    //testing INV
    BigDecimal d1 = new BigDecimal("145.12");
    CurrencyBean inputCurrency = new CurrencyBean("USD",2);
    inputCurrency.setValue(d1);
    CurrencyBean outputCurrency = new CurrencyBean("EUR",2);

    BigDecimal d2 = BigDecimal.ONE.divide(new BigDecimal("1.2315"),10, RoundingMode.CEILING);
    BigDecimal d3 = (d2.multiply(d1));
    assertEquals(d3,converter.convert(inputCurrency, outputCurrency));
  }

  @Test
  public void testConvert3() {
    //testing D
    BigDecimal d1 = new BigDecimal("145.12");
    CurrencyBean inputCurrency = new CurrencyBean("AUD",2);
    inputCurrency.setValue(d1);
    CurrencyBean outputCurrency = new CurrencyBean("USD",2);
    BigDecimal d2 = new BigDecimal("0.8371");
    BigDecimal d3 = d1.multiply(d2);
    assertEquals(d3,converter.convert(inputCurrency, outputCurrency));
  }


  @Test
  public void testConvert4() {
    //testing another supported currency
    BigDecimal d1 = new BigDecimal("145.12");
    CurrencyBean inputCurrency = new CurrencyBean("AUD",2);
    inputCurrency.setValue(d1);
    CurrencyBean outputCurrency = new CurrencyBean("JPY",0);
    BigDecimal d2 = new BigDecimal("100.410145");
    BigDecimal d3 = d1.multiply(d2);
    assertEquals(d3,converter.convert(inputCurrency, outputCurrency));
  }

  @Test
  public void testConvert5() {
    /*AUD-DKK=USD
      AUD-USD=0.8371
      USD-DKK=EUR=7.4405/1.2315

      So, AUD-DKK=(0.8371*7.4405)/1.2315
     */
    BigDecimal input = new BigDecimal("100.414");
    BigDecimal d1 = new BigDecimal("0.8371");
    BigDecimal d2 = new BigDecimal("1.2315");
    BigDecimal d3 = BigDecimal.ONE.divide(d2,10,RoundingMode.CEILING);
    BigDecimal d4 = d3.multiply(new BigDecimal("7.4405"));
    BigDecimal d6 = d4.multiply(d1);
    BigDecimal finalValue = input.multiply(d6);
    CurrencyBean audCurr = new CurrencyBean("AUD",2);
    audCurr.setValue(input);
    CurrencyBean dkkCurr = new CurrencyBean("DKK",2);
    assertEquals(finalValue,converter.convert(audCurr,dkkCurr));
  }

  @Test
  public void testgetExchangeRateFromRatesTable() {
    CurrencyBean audCurr = new CurrencyBean("AUD",2);
    CurrencyBean usdCurr = new CurrencyBean("USD",2);
    BigDecimal output = converter.getExchangeRateFromRatesTable(audCurr, usdCurr);
    BigDecimal expectedValue = new BigDecimal("0.8371");
    assertEquals(expectedValue,output);
  }

  @Test
  public void testgetExchangeRateFromRatesTable2() {
    CurrencyBean audCurr = new CurrencyBean("ABC",2);
    CurrencyBean usdCurr = new CurrencyBean("PQR",2);
    BigDecimal output = converter.getExchangeRateFromRatesTable(audCurr, usdCurr);
    BigDecimal expectedValue = BigDecimal.ZERO;
    assertEquals(expectedValue,output);
  }

  @Test
  public void testgetExchangeRateFromRatesTable3() {
    CurrencyBean audCurr = null;
    CurrencyBean usdCurr = null;
    BigDecimal output = converter.getExchangeRateFromRatesTable(audCurr, usdCurr);
    BigDecimal expectedValue = BigDecimal.ZERO;
    assertEquals(expectedValue,output);
  }


  @Test
  public void testUpdatedExchangeRateFromMatrix() {
    /*AUD-DKK=USD
      AUD-USD=0.8371
      USD-DKK=EUR=7.4405/1.2315
      So, AUD-DKK=(0.8371*7.4405)/1.2315
     */
    BigDecimal d1 = new BigDecimal("0.8371");
    BigDecimal d2 = new BigDecimal("1.2315");
    BigDecimal d3 = BigDecimal.ONE.divide(d2,10,RoundingMode.CEILING);
    BigDecimal d4 = d3.multiply(new BigDecimal("7.4405"));
    BigDecimal finalValue = d4.multiply(d1);
    CurrencyBean audCurr = new CurrencyBean("AUD",2);
    CurrencyBean dkkCurr = new CurrencyBean("DKK",2);
    assertEquals(finalValue,converter.getUpdatedExchangeRate("USD", audCurr, dkkCurr));
  }

  @Test
  public void testUpdatedExchangeRateFromMatrix2() {
    CurrencyBean usdCurr = new CurrencyBean("USD",2);
    CurrencyBean eurCurr = new CurrencyBean("EUR",2);
    BigDecimal d1 = new BigDecimal("1.2315");
    BigDecimal finalValue = BigDecimal.ONE.divide(d1,10,RoundingMode.CEILING);
    assertEquals(finalValue,converter.getUpdatedExchangeRate("INV",usdCurr,eurCurr));
  }

  @Test
  public void testUpdatedExchangeRateFromMatrix3() {
    CurrencyBean audCurr = new CurrencyBean("AUD",2);
    CurrencyBean usdCurr = new CurrencyBean("USD",2);
    BigDecimal d1 = new BigDecimal("0.8371");
    assertEquals(d1,converter.getUpdatedExchangeRate("D",audCurr,usdCurr));
  }

  @After
  protected void tearDown() throws Exception {
    runConversion.unloadData();
    runConversion = null;
  }
}
