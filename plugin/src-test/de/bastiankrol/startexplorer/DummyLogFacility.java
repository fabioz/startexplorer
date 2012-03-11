package de.bastiankrol.startexplorer;

public class DummyLogFacility extends LogFacility
{

  @Override
  public void logDebug(String message)
  {
    System.out.println(message);
  }

  @Override
  public void logInfo(String message)
  {
    System.out.println(message);
  }

  @Override
  public void logWarning(String message)
  {
    System.out.println(message);
  }

  @Override
  public void logError(String message)
  {
    System.out.println(message);
  }

  @Override
  public void logException(Throwable t)
  {
    System.out.println(t);
  }

  @Override
  public void logException(String message, Throwable t)
  {
    System.out.println(message);
    System.out.println(t);
  }

}
