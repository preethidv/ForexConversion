package forex.conversion.appln.bean;

import java.math.BigDecimal;
import java.util.zip.CRC32;

public class CurrencyBean {
  private String shortForm;
  private int decimalPlaces;
  private BigDecimal value;

  public CurrencyBean(String shortDesc, int decimalPlaces) {
    this.shortForm = shortDesc;
    this.decimalPlaces = decimalPlaces;
  }
  
  public CurrencyBean(String shortDesc, int decimalPlaces, BigDecimal value) {
    this.shortForm = shortDesc;
    this.decimalPlaces = decimalPlaces;
    this.value = value;
  }

  public CurrencyBean(String shortForm) {
    // currency has to be created with min shortDesc info. 
    this.shortForm = shortForm;
  }

  public String getShortForm() {
    return shortForm;
  }

  public void setShortForm(String shortDesc) {
    this.shortForm = shortDesc;
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  public void setDecimalPlaces(int decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }
  
  @Override
  public int hashCode() {
    CRC32 crc = new CRC32();
    crc.update(shortForm.getBytes());
    return (int)crc.getValue();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CurrencyBean) {
      CurrencyBean c1 = (CurrencyBean)obj;
      if (c1.getShortForm().equalsIgnoreCase(getShortForm())) {
        return true;
      }
    }
    return false;
  }
}
