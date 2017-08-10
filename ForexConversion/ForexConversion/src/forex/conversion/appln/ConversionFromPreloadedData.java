package forex.conversion.appln;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class ConversionFromPreloadedData implements Converter { 
  private CurrencyRatesInfoLoader infoLoader = CurrencyRatesInfoLoader.getInstance();


  /**converts a value from a input currency to output currency.
   * The input value is in the input currency object
   */
  public BigDecimal convert(CurrencyBean inputCurr, CurrencyBean outputCurr) {
    BigDecimal convertedValue = BigDecimal.ONE;
    if (inputCurr.getShortForm().equalsIgnoreCase(outputCurr.getShortForm())) {
      convertedValue =  inputCurr.getValue();
    } else {
      String valueFromMap = infoLoader.getConversionMatrixValue(inputCurr,outputCurr);
      BigDecimal updatedExchangeRate = getUpdatedExchangeRate(valueFromMap, inputCurr, outputCurr);
      BigDecimal inputValue = inputCurr.getValue();
      convertedValue = getConvertedForexValue(inputValue, updatedExchangeRate);
    }
    return convertedValue;
  }

  /** gets the final converted value of currency provided as 
   * input and forex rate.
   * @param value - currency amount to be converted
   * @param updatedExchangeRate - forex rate to be used for conversion
   * @return BigDecimal
   */
  public BigDecimal getConvertedForexValue(BigDecimal value,BigDecimal updatedExchangeRate) {
    BigDecimal convertedValue = null;
    if (value != null) {
      convertedValue = value.multiply(updatedExchangeRate);
    }
    return convertedValue;
  }



  /**returns the exchange rate by looking up both matrices
   * return ZERO if the exchange rate is not possible to be found.
   * @param mapVal - value found in conversion matrix map for the input and output currencies
   * @param inputCurr - input currency bean object
   * @param outputCurr - output currency bean object
   * @return BigDecimal  
   */
  public BigDecimal getUpdatedExchangeRate(String mapVal, CurrencyBean inputCurr,
                                                            CurrencyBean outputCurr) {
    // assuming a default of ONE as exchange rate, as ONE would mean same currency, 
    // and that is a safe default.
    BigDecimal exchangeValue = BigDecimal.ONE;
    if (mapVal != null) {
      List<String> currShortFormList = infoLoader.getShortFormSupportedCurrenciesList();
      boolean isSupportedCurrency = currShortFormList.stream().anyMatch(mapVal::equalsIgnoreCase);

      if (isSupportedCurrency) {
        CurrencyBean intermediateCurr = infoLoader.getCurrencyObject(mapVal) ;
        String value = infoLoader.getConversionMatrixValue(inputCurr,intermediateCurr);
        // call recursively same method as the mapVal can be again D,INV or a supported currency
        BigDecimal intermediateExchgRate = getUpdatedExchangeRate(value,inputCurr,intermediateCurr);

        value = infoLoader.getConversionMatrixValue(intermediateCurr,outputCurr);
        // call recursively same method as the mapVal can be again D,INV or a supported currency
        exchangeValue = getUpdatedExchangeRate(value, intermediateCurr, outputCurr);
        exchangeValue = getConvertedForexValue(intermediateExchgRate, exchangeValue);
      } else if (mapVal.equalsIgnoreCase(CurrencyConversionBean.DIRECT_CONVERSION)) {
        exchangeValue = getExchangeRateFromRatesTable(inputCurr,outputCurr);
      } else if (mapVal.equalsIgnoreCase(CurrencyConversionBean.INDIRECT_CONVERSION)) {
        String val = infoLoader.getConversionMatrixValue(outputCurr,inputCurr);
        // call recursively same method as the mapVal can be again D,INV or a supported currency
        BigDecimal invertedValue = getUpdatedExchangeRate(val, outputCurr, inputCurr);
        exchangeValue = BigDecimal.ONE.divide(invertedValue,10, RoundingMode.CEILING);
      } 
    }
    return exchangeValue;
  }

  /**get the exchange rate for a given input and output currency.
   * @param inputCurr - input currency object
   * @param outputCurr - output currency object
   * @return BigDecimal
   */
  public BigDecimal getExchangeRateFromRatesTable(CurrencyBean inputCurr,CurrencyBean outputCurr) {
    BigDecimal exchangeRate = BigDecimal.ZERO;
    if (inputCurr != null && outputCurr != null) {
      exchangeRate = infoLoader.getExchangeRate(inputCurr, outputCurr);
    }
    return exchangeRate;
  }
}
