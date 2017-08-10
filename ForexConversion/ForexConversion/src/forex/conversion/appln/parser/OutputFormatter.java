package forex.conversion.appln.parser;

import forex.conversion.appln.bean.CurrencyConversionBean;

public interface OutputFormatter {
  public void display();

  public void setCurrencyConversionBean(CurrencyConversionBean bean);
}
