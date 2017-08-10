package forex.conversion.appln.parser;

import forex.conversion.appln.bean.CurrencyConversionBean;

public interface InputParser {
  public CurrencyConversionBean getCurrencyConversionDetails();
  
  public CurrencyConversionBean parse() throws Exception;
}
