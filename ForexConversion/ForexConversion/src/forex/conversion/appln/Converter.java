package forex.conversion.appln;

import forex.conversion.appln.bean.CurrencyBean;

import java.math.BigDecimal;

public interface Converter {
  public BigDecimal convert(CurrencyBean inputCurrency, CurrencyBean outputCurrency);
}
