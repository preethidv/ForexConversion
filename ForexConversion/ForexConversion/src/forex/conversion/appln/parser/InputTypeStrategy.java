package forex.conversion.appln.parser;

import forex.conversion.appln.bean.CurrencyConversionBean;

public class InputTypeStrategy {
  private InputParser inputParser;

  public InputTypeStrategy(InputParser inputParser) {
    this.inputParser = inputParser;
  }

  public void setInputTypeStrategy(InputParser inputParser) {
    this.inputParser = inputParser;
  }

  public InputParser getInputTypeStrategy() {
    return this.inputParser;
  }

  public CurrencyConversionBean parse() throws Exception {
    return inputParser.parse();
  }
}
