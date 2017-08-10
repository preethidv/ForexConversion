package forex.conversion.appln.parser;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;

import java.math.BigDecimal;
import java.util.Optional;

public class ConsoleOutputFormatter implements OutputFormatter {
  private CurrencyConversionBean bean;
  public static final String SUCCESS_OUTPUT_FORMAT = "%s %s = %s %s";
  public static final String FAILURE_OUTPUT_FORMAT = "Unable to find rate for %s/%s";

  public ConsoleOutputFormatter(CurrencyConversionBean convBean) {
    this.bean = convBean;
  }

  @Override
  public void display() {
    // input: AUD 100.00 in USD
    // output: AUD 100.00 = USD 83.71

    // or, for error
    //Unable to find rate for KRW/FJD
    String shortFormInputCurrency = null;
    String shortFormOutputCurrency = null;

    if (bean != null) {
      Optional<CurrencyBean> inputCurrencyOptional = Optional.ofNullable(bean.getCurrentCurrency());
      Optional<CurrencyBean> outputCurrencyOptional = Optional.ofNullable(bean.getCurrencyToConvert());

      if (inputCurrencyOptional.isPresent() && outputCurrencyOptional.isPresent()) {
        CurrencyBean inputCurrency = bean.getCurrentCurrency();
        CurrencyBean outputCurrency = bean.getCurrencyToConvert();

        shortFormInputCurrency = inputCurrency.getShortForm().trim();
        shortFormOutputCurrency = outputCurrency.getShortForm().trim();

        BigDecimal inputValue = inputCurrency.getValue();
        BigDecimal outputValue = outputCurrency.getValue();

        if (inputValue != null && outputValue != null) {
          int decimalPlaces = outputCurrency.getDecimalPlaces();
          outputValue = outputValue.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
          String outputString = String.format(SUCCESS_OUTPUT_FORMAT, 
              shortFormInputCurrency,inputValue,
              shortFormOutputCurrency,outputValue);
          System.out.println(outputString);
        } else {
          String outputString = String.format(FAILURE_OUTPUT_FORMAT,
              shortFormInputCurrency,shortFormOutputCurrency);
          System.out.println(outputString);
        } 
      }
    }
  }

  @Override
  public void setCurrencyConversionBean(CurrencyConversionBean bean) {
    this.bean = bean;
  }
}
