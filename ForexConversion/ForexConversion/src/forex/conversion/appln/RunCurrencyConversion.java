package forex.conversion.appln;

import forex.conversion.appln.bean.CurrencyBean;
import forex.conversion.appln.bean.CurrencyConversionBean;
import forex.conversion.appln.parser.ConsoleInputParser;
import forex.conversion.appln.parser.ConsoleOutputFormatter;
import forex.conversion.appln.parser.InputTypeStrategy;
import forex.conversion.appln.parser.OutputFormatStrategy;
import forex.conversion.appln.validation.CurrencyValidator;
import forex.conversion.appln.validation.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RunCurrencyConversion {
  public static final String SUPPORTED_CURRENCIES_PROP_FILE_NAME = "supportedcurrencies.prop";
  public static final String FOREX_RATES_PROP_FILE_NAME = "forexrates.prop";
  public static final String CONVERSION_MATRIX_PROP_FILE_NAME = "conversionmatrix.prop";
  public static final String RESOURCE_LOC = "/forex/conversion/appln/resources/";

  /** runs the application.
   * @param args - cmd line args
   */
  public static void main(String[] args) {
    RunCurrencyConversion runCurrencyConvert = new RunCurrencyConversion();
    runCurrencyConvert.loadData();
    CurrencyValidator validator = new CurrencyValidator();

    CurrencyConversionBean convBean = runCurrencyConvert.readInput();
    
    try {
      validator.validate(convBean);
      ConversionStrategy converter = new ConversionStrategy(new ConversionFromPreloadedData());
      BigDecimal value = converter.convert(convBean.getCurrentCurrency(),
          convBean.getCurrencyToConvert());
      runCurrencyConvert.displayResult(value,convBean);
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
    
  }


  /** read the input.
   * @return CurrencyConversionBean object
   */
  public CurrencyConversionBean readInput() {
    CurrencyConversionBean convBean = null;
    System.out.println("Enter the conversion required (<CCY> <value> in <CCY>): ");
    Scanner sc = new Scanner(System.in);
    String inputString = sc.nextLine();
    InputTypeStrategy inputType = new InputTypeStrategy(new ConsoleInputParser(inputString));
    sc.close();

    try {
      convBean = inputType.parse();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    }
    return convBean;
  }

  /** displays the result. 
   * @param value - converted amount
   * @param convBean - object which has input and output
   *                   currency info.
   */
  public void displayResult(BigDecimal value,CurrencyConversionBean convBean) {
    CurrencyBean outputCurrency = convBean.getCurrencyToConvert();
    if (outputCurrency != null && outputCurrency.getValue() != BigDecimal.ZERO) {
      outputCurrency.setValue(value);
      convBean.setCurrencyToConvert(outputCurrency);
      OutputFormatStrategy outputFormat = new OutputFormatStrategy(
          new ConsoleOutputFormatter(convBean));
      outputFormat.display();
    }
  }


  /** load all data - supported currencies, exchange rates 
   *  table and conversion matrix table.
   */
  public void loadData() {
    
    CurrencyRatesInfoLoader infoLoader = CurrencyRatesInfoLoader.getInstance();
    try {
      // load supported currencies details
      List<String> currenciesList;
      File supportedCurrencies = new File(RunCurrencyConversion.class.getResource(
                        RESOURCE_LOC + SUPPORTED_CURRENCIES_PROP_FILE_NAME).getFile());

      if (supportedCurrencies.exists()) {
        currenciesList = getValuesFromPropertiesFile(supportedCurrencies);
      } else {
        currenciesList = getSupportedCurrencies();
      }
      infoLoader.populateSupportedCurrencies(currenciesList);

      // load conversion matrix details
      List<String> currencyConvList;
      File conversionMatrixFile = new File(RunCurrencyConversion.class.getResource(
          RESOURCE_LOC + CONVERSION_MATRIX_PROP_FILE_NAME).getFile());

      if (conversionMatrixFile.exists()) {
        currencyConvList  = getValuesFromPropertiesFile(conversionMatrixFile);
      } else {
        currencyConvList = getCurrencyConversionMatrix();  
      }
      infoLoader.populateCurrencyConversionMatrix(currencyConvList);
      
      // load forex rates details
      List<String> forexList;
      File forexRatesFile = new File(RunCurrencyConversion.class.getResource(
          RESOURCE_LOC + FOREX_RATES_PROP_FILE_NAME).getFile());
      
      if (forexRatesFile.exists()) {
        forexList = getValuesFromPropertiesFile(forexRatesFile);
      } else {
        forexList = getCurrencyExchangeRates();
      }
      infoLoader.populateCurrencyExchangeRates(forexList);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**unload all data.
   */
  public void unloadData() {
    CurrencyRatesInfoLoader infoLoader = CurrencyRatesInfoLoader.getInstance();
    infoLoader.populateSupportedCurrencies(null);
    infoLoader.populateCurrencyExchangeRates(null);
    infoLoader.populateCurrencyConversionMatrix(null);
  }

  /** get all values from property file
   * @param propFile - property file which has currency info
   * @return string List
   */
  public List<String> getValuesFromPropertiesFile(File propFile) 
      throws IOException {
    List<String> list = new ArrayList<String>();
    if (propFile != null  && propFile.exists()) {
      BufferedReader reader = null;
      FileReader fileReader = null;
      try {
        fileReader = new FileReader(propFile);
        reader = new BufferedReader(fileReader);
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
          list.add(currentLine);
        }   
      } finally {
        if (reader != null) {
          reader.close();
        }
        if (fileReader != null) {
          fileReader.close();
        }
      }
    }
    return list;
  }
  
  /** get all supported currencies list.
   * @return string list
   */
  public List<String> getSupportedCurrencies() {
    List<String> list = new ArrayList<String>();
    list.add("AUD=2 decimal places");
    list.add("CAD=2 decimal places");
    list.add("CNY=2 decimal places");
    list.add("CZK=2 decimal places");
    list.add("DKK=2 decimal places");
    list.add("EUR=2 decimal places");
    list.add("GBP=2 decimal places");
    list.add("JPY=0 decimal places");
    list.add("NOK=2 decimal places");
    list.add("NZD=2 decimal places");
    list.add("USD=2 decimal places");
    return list;
  }

  /**get the exchange rates list.
   * @return string list
   */
  public List<String> getCurrencyExchangeRates() {
    List<String> list = new ArrayList<String>();
    list.add("AUDUSD=0.8371");
    list.add("CADUSD=0.8711");
    list.add("USDCNY=6.1715");
    list.add("EURUSD=1.2315");
    list.add("GBPUSD=1.5683");
    list.add("NZDUSD=0.7750");
    list.add("USDJPY=119.95");
    list.add("EURCZK=27.6028");
    list.add("EURDKK=7.4405");
    list.add("EURNOK=8.6651");

    return list;
  }

  /** get the conversion matrix info.
   * @return string list
   */
  public List<String> getCurrencyConversionMatrix() {
    List<String> list = new ArrayList<String>();

    // AUD
    list.add("AUDAUD=1:1");
    list.add("AUDCAD=USD");
    list.add("AUDCNY=USD");
    list.add("AUDCZK=USD");
    list.add("AUDDKK=EUR");//updated
    list.add("AUDEUR=USD");
    list.add("AUDGBP=USD");
    list.add("AUDJPY=USD");
    list.add("AUDNOK=USD");
    list.add("AUDNZD=USD");
    list.add("AUDUSD=D");

    //CAD
    list.add("CADAUD=USD");
    list.add("CADCAD=1:1");
    list.add("CADCNY=USD");
    list.add("CADCZK=USD");
    list.add("CADDKK=USD");
    list.add("CADEUR=USD");
    list.add("CADGBP=USD");
    list.add("CADJPY=USD");
    list.add("CADNOK=USD");
    list.add("CADNZD=USD");
    list.add("CADUSD=D");

    //CNY
    list.add("CNYAUD=USD");
    list.add("CNYCAD=USD");
    list.add("CNYCNY=1:1");
    list.add("CNYCZK=USD");
    list.add("CNYDKK=USD");
    list.add("CNYEUR=USD");
    list.add("CNYGBP=USD");
    list.add("CNYJPY=USD");
    list.add("CNYNOK=USD");
    list.add("CNYNZD=USD");
    list.add("CNYUSD=INV");

    //CZK
    list.add("CZKAUD=USD");
    list.add("CZKCAD=USD");
    list.add("CZKCNY=USD");
    list.add("CZKCZK=1:1");
    list.add("CZKDKK=EUR");
    list.add("CZKEUR=INV");
    list.add("CZKGBP=USD");
    list.add("CZKJPY=USD");
    list.add("CZKNOK=EUR");
    list.add("CZKNZD=USD");
    list.add("CZKUSD=EUR");

    //DKK
    list.add("DKKAUD=USD");
    list.add("DKKCAD=USD");
    list.add("DKKCNY=USD");
    list.add("DKKCZK=EUR");
    list.add("DKKDKK=1:1");
    list.add("DKKEUR=INV");
    list.add("DKKGBP=USD");
    list.add("DKKJPY=USD");
    list.add("DKKNOK=EUR");
    list.add("DKKNZD=USD");
    list.add("DKKUSD=EUR");

    //EUR
    list.add("EURAUD=USD");
    list.add("EURCAD=USD");
    list.add("EURCNY=USD");
    list.add("EURCZK=D");
    list.add("EURDKK=D");
    list.add("EUREUR=1:1");
    list.add("EURGBP=USD");
    list.add("EURJPY=USD");
    list.add("EURNOK=D");
    list.add("EURNZD=USD");
    list.add("EURUSD=D");

    //GBP
    list.add("GBPAUD=USD");
    list.add("GBPCAD=USD");
    list.add("GBPCNY=USD");
    list.add("GBPCZK=USD");
    list.add("GBPDKK=USD");
    list.add("GBPEUR=USD");
    list.add("GBPGBP=1:1");
    list.add("GBPJPY=USD");
    list.add("GBPNOK=USD");
    list.add("GBPNZD=USD");
    list.add("GBPUSD=D");

    //JPY
    list.add("JPYAUD=USD");
    list.add("JPYCAD=USD");
    list.add("JPYCNY=USD");
    list.add("JPYCZK=USD");
    list.add("JPYDKK=USD");
    list.add("JPYEUR=USD");
    list.add("JPYGBP=USD");
    list.add("JPYJPY=1:1");
    list.add("JPYNOK=USD");
    list.add("JPYNZD=USD");
    list.add("JPYUSD=INV");

    //NOK
    list.add("NOKAUD=USD");
    list.add("NOKCAD=USD");
    list.add("NOKCNY=USD");
    list.add("NOKCZK=EUR");
    list.add("NOKDKK=EUR");
    list.add("NOKEUR=INV");
    list.add("NOKGBP=USD");
    list.add("NOKJPY=USD");
    list.add("NOKNOK=1:1");
    list.add("NOKNZD=USD");
    list.add("NOKUSD=EUR");

    //NZD
    list.add("NZDAUD=USD");
    list.add("NZDCAD=USD");
    list.add("NZDCNY=USD");
    list.add("NZDCZK=USD");
    list.add("NZDDKK=USD");
    list.add("NZDEUR=USD");
    list.add("NZDGBP=USD");
    list.add("NZDJPY=USD");
    list.add("NZDNOK=USD");
    list.add("NZDNZD=1:1");
    list.add("NZDUSD=D");

    //USD
    list.add("USDAUD=INV");
    list.add("USDCAD=INV");
    list.add("USDCNY=D");
    list.add("USDCZK=EUR");
    list.add("USDDKK=EUR");
    list.add("USDEUR=INV");
    list.add("USDGBP=INV");
    list.add("USDJPY=D");
    list.add("USDNOK=EUR");
    list.add("USDNZD=INV");
    list.add("USDUSD=1:1");

    return list;
  }
}
