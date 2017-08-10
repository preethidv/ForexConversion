package forex.conversion.appln.test;

import forex.conversion.appln.CurrencyRatesInfoLoader;
import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCurrencyRatesInfoLoader extends TestCase {
  CurrencyRatesInfoLoader infoLoader;

  @Before
  protected void setUp() throws Exception {
    infoLoader = CurrencyRatesInfoLoader.getInstance();
  }

  /*	@Test
	public void testPopulateSupportedCurrencies(){
		List<String> list = new ArrayList<String>();
		list.add("AUD=2 decimal places");
		list.add("CAD=2 decimal places");
		list.add("CNY=2 decimal places");
		list.add("CZK=2 decimal places");
		list.add("DKK=2 decimal places");
		list.add("EUR=2 decimal places");
		list.add("GBP=2 decimal places");
		list.add("JPY=0 decimal places");
		list.add("NOK=2 decimal places");
		list.add("NZD=2 decimal places");
		list.add("USD=2 decimal places");

		infoLoader.populateSupportedCurrencies(list);
		List<CurrencyBean> idealList = new ArrayList<CurrencyBean>();
		idealList.add(new CurrencyBean("AUD",2));
		idealList.add(new CurrencyBean("CAD",2));
		idealList.add(new CurrencyBean("CNY",2));
		idealList.add(new CurrencyBean("CZK",2));
		idealList.add(new CurrencyBean("DKK",2));
		idealList.add(new CurrencyBean("EUR",2));
		idealList.add(new CurrencyBean("GBP",2));
		idealList.add(new CurrencyBean("JPY",0));
		idealList.add(new CurrencyBean("NOK",2));
		idealList.add(new CurrencyBean("NZD",2));
		idealList.add(new CurrencyBean("USD",2));


		assertEquals(idealList,infoLoader.getSupportedCurrenciesList());
	}*/

  /*	@Test
	public void testGetCurrencyObject(){
		CurrencyBean c = new CurrencyBean("USD",2);
		List<String> list = new ArrayList<String>();
		list.add("USD=2 decimal places");
		infoLoader.populateSupportedCurrencies(list);
		assertEquals(c,infoLoader.getCurrencyObject("USD"));
	}*/

  /*	@Test
	public void testPopulateCurrencyExchangeRates(){
		List<String> list = new ArrayList<String>();
		list.add("AUDUSD=0.8371");

		List<String> currencyList = new ArrayList<String>();
		CurrencyBean c1 = new CurrencyBean("AUD");
		CurrencyBean c2 = new CurrencyBean("USD");
		currencyList.add("USD=2 decimal places");
		currencyList.add("AUD=2 decimal places");

		infoLoader.populateSupportedCurrencies(currencyList);
		infoLoader.populateCurrencyExchangeRates(list);

		CurrencyConversionBean convBean = new CurrencyConversionBean(c1, c2);
		HashMap<CurrencyConversionBean,BigDecimal> idealHashMap = new HashMap<CurrencyConversionBean,BigDecimal>();
		idealHashMap.put(convBean, 0.8371);

		BigDecimal d = infoLoader.getExchangeRate(convBean);
		assertEquals(d,new BigDecimal("0.8371"));
		BigDecimal d2=infoLoader.getExchangeRate(c1, c2);
		assertEquals(d2,new BigDecimal("0.8371"));
	}*/


  @Test
  public void testPopulateCurrencyConversionMatrix(){
    /* possible values:
     * USDEUR=D
		// USDINR=INV
		// USDJPY=EUR
		// USDUSD=1:1
     */
    /*
		   		USD   EUR   INR
		  USD  1:1   D     D
		  EUR	INV	  1:1   USD 
		  INR	INV	  INV	1:1

		  represented as:
		  USDUSD=1:1
		  USDEUR=D
		  USDINR=D
		  EURUSD=INV
		  EUREUR=1:1
		  EURINR=USD
		  INRUSD=INV
		  INREUR=INV
		  INRINR=1:1
     */
    List<String> list = new ArrayList<String>();
    list.add("USDUSD=1:1");
    list.add("USDEUR=D");
    list.add("USDINR=D");
    list.add("EURUSD=INV");
    list.add("EUREUR=1:1");
    list.add("EURINR=USD");
    list.add("INRUSD=INV");
    list.add("INREUR=INV");
    list.add("INRINR=1:1");

    List<String> currencyList = new ArrayList<String>();
    currencyList.add("USD=2 decimal places");
    currencyList.add("INR=2 decimal places");
    currencyList.add("EUR=2 decimal places");

    infoLoader.populateSupportedCurrencies(currencyList);
    infoLoader.populateCurrencyConversionMatrix(list);

    CurrencyBean c1 = new CurrencyBean("USD");
    CurrencyBean c2 = new CurrencyBean("EUR");
    CurrencyBean c3 = new CurrencyBean("INR");
    CurrencyConversionBean convBean = new CurrencyConversionBean(c1,c2);
    assertEquals("D",infoLoader.getConversionMatrixValue(convBean));

    convBean = new CurrencyConversionBean(c3,c1);
    assertEquals("INV",infoLoader.getConversionMatrixValue(convBean));

    convBean = new CurrencyConversionBean(c2,c3);
    assertEquals("USD",infoLoader.getConversionMatrixValue(convBean));
    
    convBean = new CurrencyConversionBean(c1,c1);
    assertEquals("1:1",infoLoader.getConversionMatrixValue(convBean));
  }

  @After
  protected void tearDown() throws Exception {
    infoLoader = null;
  }
}
