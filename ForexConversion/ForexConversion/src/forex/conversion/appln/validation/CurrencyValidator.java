package forex.conversion.appln.validation;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;

import java.math.BigDecimal;

public class CurrencyValidator {
  
  /** validates the authenticity of the input and output currencies. 
   * @param convBean - CurrencyConversionBean object
   * @throws ValidationException - validation exception
   */
  public void validate(CurrencyConversionBean convBean) throws ValidationException {
    validateCurrencies(convBean);
    CurrencyBean inputCurrency = convBean.getCurrentCurrency();
    if (inputCurrency != null) {
      validateValue(inputCurrency.getValue());
    }
  }

  /** validate the value that is passed.
   * @param value - amount to be validated
   * @throws ValidationException - - validation exception
   */
  public void validateValue(BigDecimal value) throws ValidationException {
    if (value == null) {
      throw new ValidationException("Value to convert cannot be null or empty");
    } else if (value.signum() == -1) {
      throw new ValidationException("Value to convert cannot be negative");
    } 
  }

  /**validate that the currencies are supported.
   * @param convBean - CurrencyConversionBean object.
   * @throws ValidationException - validation exception
   */
  public void validateCurrencies(CurrencyConversionBean convBean) throws ValidationException {
    CurrencyBean inputCurrency = convBean.getCurrentCurrency();
    if (inputCurrency == null) {
      throw new ValidationException("Input currency provided is not supported.");
    }
    CurrencyBean outputCurrency = convBean.getCurrencyToConvert();
    if (outputCurrency == null) {
      throw new ValidationException("The currency to be converted is not supported.");
    }
  }
}
