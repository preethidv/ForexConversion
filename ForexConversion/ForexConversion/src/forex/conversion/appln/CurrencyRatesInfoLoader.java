package forex.conversion.appln;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.util.ConcurrentHashSet;
import forex.conversion.appln.util.CurrencyConversionUtilHelper;
import forex.conversion.appln.util.StringUtilHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyRatesInfoLoader implements Cloneable,Serializable {
  private static final long serialVersionUID = 1L;

  private ConcurrentHashSet<CurrencyBean> supportedCurrenciesSet = 
                                        new ConcurrentHashSet<CurrencyBean>();
  
  private ConcurrentHashMap<CurrencyConversionBean,BigDecimal> conversionRatesMap =
                                        new ConcurrentHashMap<CurrencyConversionBean,BigDecimal>(); 
  
  private ConcurrentHashMap<CurrencyConversionBean,String> conversionMatrixMap =
                                        new ConcurrentHashMap<CurrencyConversionBean,String>();


  private static final class CurrencyRatesInfoLoaderHelper {
    private CurrencyRatesInfoLoaderHelper() {}
    
    public static final CurrencyRatesInfoLoader INSTANCE = new CurrencyRatesInfoLoader();
  }

  private CurrencyRatesInfoLoader() {
  }

  public static CurrencyRatesInfoLoader getInstance() {
    return CurrencyRatesInfoLoaderHelper.INSTANCE;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }


  protected Object readResolve() {
    return getInstance();
  }

  /**populate the list of supported currencies, The list should contain format like this:
   * AUD=2 decimal places
   * CAD=2 decimal places
   * CNY=2 decimal places
   * @param currencyList - list of strings.
   */
  public void populateSupportedCurrencies(List<String> currencyList) {
    //list will be given in the following format:
    /*
       AUD=2 decimal places
       CAD=2 decimal places
       CNY=2 decimal places
       CZK=2 decimal places
       DKK=2 decimal places
       EUR=2 decimal places
       GBP=2 decimal places
       JPY=0 decimal places
       NOK=2 decimal places
       NZD=2 decimal places
       USD=2 decimal places
     */
    if (currencyList != null && !currencyList.isEmpty()) {
      for (String str:currencyList) {
        if (str != null) {
          String[] strParser = str.split("=");
          String shortForm = strParser[0];
          int decimalNum = 0;
          String decimalString = strParser[1];
          int index = decimalString.indexOf(' ');
          String decStr = decimalString.substring(0, index);
          try {
            decimalNum = Integer.parseInt(decStr);
          } catch (NumberFormatException e) {
            System.out.println("Unable to retrieve the decimal places for the currency:" + str);
            e.printStackTrace();
          }
          CurrencyBean c = new CurrencyBean(shortForm.toUpperCase(),decimalNum);
          supportedCurrenciesSet.add(c);
        }
      }
    }
  }

  /**given the short form of the currency, return the CurrencyBean object.
   * @param shortForm - string shortform of currency object
   * @return CurrencyBean object
   */
  public CurrencyBean getCurrencyObject(String shortForm) {
    if (supportedCurrenciesSet != null && !supportedCurrenciesSet.isEmpty()) {
      StringUtilHelper strHelper = new StringUtilHelper();
      if (!strHelper.isEmptyOrNull(shortForm)) {
        Iterator<CurrencyBean> it = supportedCurrenciesSet.iterator();
        while (it.hasNext()) {
          CurrencyBean c = it.next();
          if (c != null && c.getShortForm().equalsIgnoreCase(shortForm)) {
            return c;
          }
        }
      }
    }

    return null;
  }

  /**get the list of supported currencies.
   * @return ConcurrentHashSet
   */
  public final ConcurrentHashSet<CurrencyBean> getSupportedCurrenciesList() {
    return supportedCurrenciesSet;
  }

  /**check if the currency provided in the short form is actually one of 
   * the supported currencies.
   * @param value - string to check if it is a supported currency short form
   * @return boolean
   */
  public boolean isSupportedCurrency(String value) {
    List<String> shortFormForSupportedCurrencies = getShortFormSupportedCurrenciesList();
    boolean isSupportedCurrency = false;
    if (!shortFormForSupportedCurrencies.isEmpty()) {
      isSupportedCurrency = shortFormForSupportedCurrencies.stream()
                              .anyMatch(value::equalsIgnoreCase);
    }
    return isSupportedCurrency;
  }

  /**check if the currency object is in the list of supported currencies.
   * @param currency - CurrencyBean object
   * @return boolean
   */
  public boolean isSupportedCurrency(CurrencyBean currency) {
    ConcurrentHashSet<CurrencyBean> supportedCurrencies = getSupportedCurrenciesList();
    boolean isSupportedCurrency = false;
    if (supportedCurrenciesSet != null && !supportedCurrencies.isEmpty() && currency != null) {
      isSupportedCurrency = supportedCurrenciesSet.contains(currency);
    }
    return isSupportedCurrency;
  }


  /**get the list of all supported currencies in the short form list.
   * @return string list.
   */
  public final List<String> getShortFormSupportedCurrenciesList() {
    List<String> shortFormList = new ArrayList<String>();
    if (supportedCurrenciesSet != null && !supportedCurrenciesSet.isEmpty()) {
      Iterator<CurrencyBean> it = supportedCurrenciesSet.iterator();
      while (it.hasNext()) {
        shortFormList.add(it.next().getShortForm());
      }
    }
    return shortFormList;
  }


  /**
   * populate the exchange rates table. The list should be in the format:
   * AUDUSD=0.8371
   * CADUSD=0.8711
   * @param exchangeRatesList - list of strings
   */
  public void populateCurrencyExchangeRates(List<String> exchangeRatesList) {
    // given in the form of:
    /*
     AUDUSD=0.8371
     CADUSD=0.8711
     USDCNY=6.1715
     EURUSD=1.2315
     GBPUSD=1.5683
     NZDUSD=0.7750
     USDJPY=119.95
     EURCZK=27.6028
     EURDKK=7.4405
     EURNOK=8.6651
     */

    if (exchangeRatesList != null && !exchangeRatesList.isEmpty()
                            && !supportedCurrenciesSet.isEmpty()) {
      for (String str : exchangeRatesList) {
        String[] rateStr = str.split("=");
        if (rateStr != null && rateStr.length > 0) {
          BigDecimal rate = BigDecimal.ZERO;
          try {
            rate = new BigDecimal(rateStr[1]);
          } catch (NumberFormatException e) {
            System.out.println("Unable to find the exchange rate in: " + str);
            e.printStackTrace();
          }
          String currStr = rateStr[0];
          CurrencyConversionUtilHelper utilHelper = CurrencyConversionUtilHelper.getInstance();
          CurrencyConversionBean convBean = utilHelper.getConvBeanFromStr(currStr);
          conversionRatesMap.put(convBean, rate);
        }
      }
    }
  }

  /**given the CurrencyConversionBean object, return the exact exchange rate 
   * from the exchange rates table.
   * @param convBean - CurrencyConversionBean object.
   * @return BigDecimal
   */
  public BigDecimal getExchangeRate(CurrencyConversionBean convBean) {
    BigDecimal exchangeRate = BigDecimal.ZERO;
    if (convBean != null && conversionRatesMap != null && !conversionRatesMap.isEmpty()) {
      BigDecimal value = conversionRatesMap.get(convBean);
      if (value != null) {
        exchangeRate = value;
      }
    }
    return exchangeRate;
  }

  /**given a input and output currency, return the exchange rate.
   * @param inputCurrency - CurrencyBean object
   * @param outputCurrency - CurrencyBean object
   * @return BigDecimal
   */
  public BigDecimal getExchangeRate(CurrencyBean inputCurrency,CurrencyBean outputCurrency) {
    CurrencyConversionBean convBean = new CurrencyConversionBean(inputCurrency,outputCurrency);
    return getExchangeRate(convBean);
  }

  /**
   * populate the conversion matrix which is having one of the values for each of the currencies- 
   * D = direct feed - eg. the currency pair can be looked up directly.
   * INV = inverted - eg. the currency pair can be looked up if base and
   *                  terms are flipped (and rate = 1/rate)
   * 1:1 = unity - the rate is always 1.
   * CCY = in order to calculate this rate, you need to cross via this currency.
   * @param matrixValues - list of strings
   */
  public void populateCurrencyConversionMatrix(List<String> matrixValues) {
    // assuming the following format in which data is passed from the 2d matrix:
    // USDEUR=D
    // USDINR=INV
    // USDJPY=EUR
    // USDUSD=1:1

    if (matrixValues != null && !matrixValues.isEmpty() && !supportedCurrenciesSet.isEmpty()) {
      CurrencyConversionUtilHelper helper = CurrencyConversionUtilHelper.getInstance();
      for (String str:matrixValues) {
        String[] convValues = str.split("=");
        if (convValues != null && convValues.length > 0) {
          String value = convValues[1];
          String currStr = convValues[0];
          if (currStr != null && currStr.length() > 0) {
            CurrencyConversionBean convBean = helper.getConvBeanFromStr(currStr);
            conversionMatrixMap.put(convBean, value);
          }
        }
      }
    }
  }

  /**return the String value from the conversion matrix for 
   * a given CurrencyConversionBean object.
   * @param convBean - CurrencyConversionBean object
   * @return String
   */
  public String getConversionMatrixValue(CurrencyConversionBean convBean) {
    if (conversionMatrixMap != null && !conversionMatrixMap.isEmpty()) {
      return conversionMatrixMap.get(convBean);
    }
    return null;
  }

  /**return the String value from the conversion matrix for a given input 
   * and output currencies.
   * @param inputCurrency - CurrencyBean object
   * @param outputCurrency - CurrencyBean object
   * @return String
   */
  public String getConversionMatrixValue(CurrencyBean inputCurrency,CurrencyBean outputCurrency) {
    CurrencyConversionBean convBean = new CurrencyConversionBean(inputCurrency,outputCurrency);
    return getConversionMatrixValue(convBean);
  }
}
