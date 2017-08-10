package forex.conversion.appln.util;

import forex.conversion.appln.CurrencyRatesInfoLoader;
import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;

import java.io.Serializable;

public class CurrencyConversionUtilHelper implements Cloneable,Serializable {
  private static final long serialVersionUID = 1L;

  private static final class InnerClassHelper {
    private InnerClassHelper() {}
    
    public static final CurrencyConversionUtilHelper INSTANCE = new CurrencyConversionUtilHelper();
  }

  private CurrencyConversionUtilHelper() {
  }

  public static CurrencyConversionUtilHelper getInstance() {
    return InnerClassHelper.INSTANCE;
  }

  /**derives the CurrencyConversionBean from a string like USDEUR.
   * @param str - string to parse
   * @return CurrencyConversionBean object
   */
  public CurrencyConversionBean getConvBeanFromStr(String str) {
    if (str != null && str.length() > 3) {
      String inputCurr = str.substring(0,3);
      String outputCurr = str.substring(3,str.length());

      CurrencyBean c1 = CurrencyRatesInfoLoader.getInstance().getCurrencyObject(inputCurr);
      CurrencyBean c2 = CurrencyRatesInfoLoader.getInstance().getCurrencyObject(outputCurr);
      return new CurrencyConversionBean(c1, c2);
    }
    return null;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }


  protected Object readResolve() {
    // return same object even after serialization 
    return getInstance();
  }
}
