package forex.conversion.appln.parser;

public class OutputFormatStrategy {
  private OutputFormatter outputFormatter;

  public OutputFormatStrategy(OutputFormatter outputFormatter) {
    this.outputFormatter = outputFormatter;
  }

  public void setOutputFormatStrategy(OutputFormatter outputFormatter) {
    this.outputFormatter = outputFormatter;
  }

  public OutputFormatter getOutputFormatStrategy() {
    return this.outputFormatter;
  }

  public void display() {
    outputFormatter.display();
  }
}
