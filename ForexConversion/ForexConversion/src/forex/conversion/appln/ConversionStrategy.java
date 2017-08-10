package forex.conversion.appln;

import forex.conversion.appln.bean.CurrencyBean;

import java.math.BigDecimal;

public class ConversionStrategy {
  private Converter conv;

  public ConversionStrategy(Converter c) {
    this.conv = c;
  }

  public void setConversionStrategy(Converter conv) {
    this.conv = conv;
  }

  public Converter getConversionStrategy() {
    return this.conv;
  }

  public BigDecimal convert(CurrencyBean inputCurrency, CurrencyBean outputCurrency) {
    return conv.convert(inputCurrency, outputCurrency);
  }
}
