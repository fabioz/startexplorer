package de.bastiankrol.startexplorer.crossplatform;

public class RuntimeExecCallsFactory
{
  public static RuntimeExecCallsWindows windows()
  {
    return new RuntimeExecCallsWindows();
  }

  public static RuntimeExecCallsWindowsPowerShell windowsPowerShell()
  {
    return new RuntimeExecCallsWindowsPowerShell();
  }

  public static RuntimeExecCallsWindowsCygwin windowsCygwin()
  {
    return new RuntimeExecCallsWindowsCygwin();
  }

  public static RuntimeExecCallsWindowsGitBash windowsGitBash()
  {
    return new RuntimeExecCallsWindowsGitBash();
  }

  public static RuntimeExecCallsGnome linuxGnome()
  {
    return new RuntimeExecCallsGnome();
  }

  public static RuntimeExecCallsMint linuxMint()
  {
    return new RuntimeExecCallsMint();
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

  public static RuntimeExecCallsMate linuxMate()
  {
    return new RuntimeExecCallsMate();
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
