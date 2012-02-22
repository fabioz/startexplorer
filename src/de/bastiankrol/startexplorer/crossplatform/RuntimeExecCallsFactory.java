package de.bastiankrol.startexplorer.crossplatform;

public class RuntimeExecCallsFactory
{
  public static RuntimeExecCallsWindows windows()
  {
    return new RuntimeExecCallsWindows();
  }

  public static RuntimeExecCallsGnome linuxGnome()
  {
    return new RuntimeExecCallsGnome();
  }

  public static RuntimeExecCallsKde linuxKde()
  {
    return new RuntimeExecCallsKde();
  }

  public static RuntimeExecCallsXfce linuxXfce()
  {
    return new RuntimeExecCallsXfce();
  }

  public static RuntimeExecCallsLxde linuxLxde()
  {
    return new RuntimeExecCallsLxde();
  }

  public static RuntimeExecCallsMacOs macOs()
  {
    return new RuntimeExecCallsMacOs();
  }

  public static RuntimeExecCallsCustom custom(
      CustomDesktopEnvironmentContainer container)
  {
    return new RuntimeExecCallsCustom(container);
  }

  public static IRuntimeExecCalls unsupported()
  {
    return new RuntimeExecCallsUnsupported();
  }
}
