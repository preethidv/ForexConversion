package forex.conversion.appln.parser;

import forex.conversion.appln.CurrencyRatesInfoLoader;
import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.util.StringUtilHelper;
import forex.conversion.appln.validation.IncorrectInputException;
import forex.conversion.appln.validation.InputFormatException;

import java.math.BigDecimal;

public class ConsoleInputParser implements InputParser {
  private String strToParse;
  private CurrencyConversionBean bean;

  public ConsoleInputParser(String str) {
    this.strToParse = str;
  }

  @Override
  public CurrencyConversionBean parse() throws Exception {
    CurrencyConversionBean convBean = null;
    if (strToParse != null && strToParse.length() > 0) {
      StringUtilHelper strHelper = new StringUtilHelper();
      // AUD 100.00 in USD
      String updatedStr = strToParse.trim();
      String[] tokens = updatedStr.split(" ");
      boolean invalidInput = false;
      if (tokens != null && tokens.length > 3) {
        String currentCurrencyStr = tokens[0];
        String valueStr = tokens[1];
        String currencyToConvertStr = tokens[3];

        CurrencyRatesInfoLoader loader = CurrencyRatesInfoLoader.getInstance();
        CurrencyBean currentCurrency = loader.getCurrencyObject(currentCurrencyStr.trim());
        CurrencyBean currencyToConvert = loader.getCurrencyObject(currencyToConvertStr.trim());
        convBean = new CurrencyConversionBean(currentCurrency, currencyToConvert);
        if (currentCurrency != null && currencyToConvert != null) {
          this.bean = convBean;
          try {
            currentCurrency.setValue(new BigDecimal(valueStr.trim()));
          } catch (NumberFormatException e) {
            invalidInput = true;
            throw new InputFormatException("Invalid input provided."
                + " Check if the string is in supported format : "
                + "<Currency 1> <money> in <Currency 2>");
          }
        } else {
          invalidInput = true;
          throw new IncorrectInputException(String.format(
              ConsoleOutputFormatter.FAILURE_OUTPUT_FORMAT,
              currentCurrencyStr,currencyToConvertStr));
        }
      } else {
        invalidInput = true;
      }
      if (invalidInput) {
        throw new InputFormatException("Invalid input provided."
            + " Check if the string is in supported format : "
            + "<Currency 1> <money> in <Currency 2>");
      }
    }
    return convBean;
  }

  @Override
  public CurrencyConversionBean getCurrencyConversionDetails() {
    return bean;
  }
}
