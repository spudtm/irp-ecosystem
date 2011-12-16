package uk.ac.dotrural.irp.ecosystem.resources.support.reporters;

public class ExceptionReporter extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public ExceptionReporter(String message)
  {
    super(message);
  }
  
  public ExceptionReporter(Exception ex)
  {
    super(ex);
  }
}
