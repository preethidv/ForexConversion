package forex.conversion.appln.test.validation;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.validation.CurrencyValidator;
import forex.conversion.appln.validation.ValidationException;

import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestCurrencyValidator extends TestCase {
  CurrencyValidator validator;
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  @Override
  protected void setUp() throws Exception {
    validator = new CurrencyValidator();
  }
  
  @Test
  public void testValidateValue() {
    BigDecimal val = new BigDecimal("-183");
    try{
      validator.validateValue(val);
    } catch (Exception e){
      
    }
    
    exception.expect(ValidationException.class);
  }
  
  @Test
  public void testValidateValue2() {
    BigDecimal val = null;
    try{
      validator.validateValue(val);
    } catch (Exception e){
      
    }
    
    exception.expect(ValidationException.class);
  }
  
  @Test
  public void testValidateCurrencies() {
    CurrencyConversionBean convBean = new CurrencyConversionBean(null, null);
    try{
      validator.validateCurrencies(convBean);
    } catch (Exception e){
      
    }
    
    exception.expect(ValidationException.class);
  }
  
  @Test
  public void testValidateCurrencies2() {
    CurrencyConversionBean convBean = new CurrencyConversionBean(new CurrencyBean("QWE",2), null);
    try{
      validator.validateCurrencies(convBean);
    } catch (Exception e){
      
    }
    
    exception.expect(ValidationException.class);
  }
  
  @Test
  public void testValidate() {
    CurrencyBean inputBean = new CurrencyBean("USD",2);
    inputBean.setValue(new BigDecimal("123"));
    CurrencyBean outputBean = new CurrencyBean("EUR",2);
    CurrencyConversionBean convBean = new CurrencyConversionBean(inputBean,outputBean);
    try{
      validator.validate(convBean);
    } catch (Exception e){
      
    }
  }
  
  @Override
  protected void tearDown() throws Exception {
    validator = null;
  }

}
