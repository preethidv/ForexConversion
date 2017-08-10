package forex.conversion.appln.bean;

import java.util.zip.CRC32;

public class CurrencyConversionBean {
  public static final String DIRECT_CONVERSION = "D";
  public static final String INDIRECT_CONVERSION = "INV";
  public static final String NO_CONVERSION = "1:1";

  private CurrencyBean currentCurrency;
  private CurrencyBean currencyToConvert;

  public CurrencyConversionBean(CurrencyBean c1,CurrencyBean c2) {
    this.currentCurrency = c1;
    this.currencyToConvert = c2;
  }

  public CurrencyBean getCurrentCurrency() {
    return currentCurrency;
  }
  
  public void setCurrentCurrency(CurrencyBean currentCurrency) {
    this.currentCurrency = currentCurrency;
  }
  
  public CurrencyBean getCurrencyToConvert() {
    return currencyToConvert;
  }
  
  public void setCurrencyToConvert(CurrencyBean currencyToConvert) {
    this.currencyToConvert = currencyToConvert;
  }

  @Override
  public int hashCode() {
    int code1 = currentCurrency.hashCode();
    int code2 = currencyToConvert.hashCode();
    CRC32 crc = new CRC32();
    crc.update((code1 * 31) + (code2 * 31));
    return (int)crc.getValue(); 
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CurrencyConversionBean) {
      CurrencyConversionBean conv = (CurrencyConversionBean) obj;
      CurrencyBean c1 = conv.getCurrentCurrency();
      CurrencyBean c2 = conv.getCurrencyToConvert();
      if (c1.equals(currentCurrency)  
            &&  c2.equals(currencyToConvert)) {
        return true;
      }
    }
    return false;
  }
}
