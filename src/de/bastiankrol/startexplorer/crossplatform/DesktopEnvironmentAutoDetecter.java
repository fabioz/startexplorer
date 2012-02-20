package de.bastiankrol.startexplorer.crossplatform;

import java.io.IOException;
import java.net.URL;

public class DesktopEnvironmentAutoDetecter
{
  private static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_WINDOWS = "Windows";
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_LINUX = "Linux";

  private static final int SHELL_SCRIPT_EXIT_VALUE_GNOME = 11;
  private static final int SHELL_SCRIPT_EXIT_VALUE_KDE = 12;
  private static final int SHELL_SCRIPT_EXIT_VALUE_XFCE = 13;
  private static final int SHELL_SCRIPT_EXIT_VALUE_LXDE = 14;
  private static final int SHELL_SCRIPT_EXIT_VALUE_UNKNOWN = 99;

  public static DesktopManager findDesktopManager()
  {
    OperatingSystem operatingSystem = findOperatingSystem();
    switch (operatingSystem)
    {
      case WINDOWS:
        return DesktopManager.WINDOWS;
      case LINUX:
        return findLinuxDesktopManager();
      case MAC:
        return DesktopManager.UNKNOWN;
      case UNKNOWN:
        // fall through
      default:
        return DesktopManager.UNKNOWN;
    }
  }

  private static OperatingSystem findOperatingSystem()
  {
    String osName = System.getProperty(SYSTEM_PROPERTY_OS_NAME);
    if (osName != null && osName.contains(SYSTEM_PROPERTY_OS_NAME_VALUE_LINUX))
    {
      return OperatingSystem.LINUX;
    }
    else if (osName != null && osName.contains(SYSTEM_PROPERTY_OS_NAME_VALUE_WINDOWS))
    {
      return OperatingSystem.WINDOWS;
    }
    else
    {
      return OperatingSystem.UNKNOWN;
    }
  }

  private static DesktopManager findLinuxDesktopManager()
  {
    URL scriptUrl = DesktopEnvironmentAutoDetecter.class.getResource("find-desktop-environmnet.sh");
    String scriptFileName = scriptUrl.getFile();
    try
    {
      Process process = Runtime.getRuntime().exec(scriptFileName);
      int exitValue = process.waitFor();
      switch (exitValue)
      {
        case SHELL_SCRIPT_EXIT_VALUE_KDE:
          return DesktopManager.LINUX_KDE;
        case SHELL_SCRIPT_EXIT_VALUE_GNOME:
          return DesktopManager.LINUX_GNOME;
        case SHELL_SCRIPT_EXIT_VALUE_XFCE:
          return DesktopManager.LINUX_XFCE;
        case SHELL_SCRIPT_EXIT_VALUE_LXDE:
          return DesktopManager.LINUX_LXDE;
        case SHELL_SCRIPT_EXIT_VALUE_UNKNOWN:
          // TODO Write warning to Eclipse log
          return DesktopManager.LINUX_UNKNOWN;
        default:
          // TODO Write warning to Eclipse log
          return DesktopManager.LINUX_UNKNOWN;
      }
    }
    catch (IOException e)
    {
      // TODO Write warning to Eclipse log
      return DesktopManager.LINUX_UNKNOWN;
    }
    catch (InterruptedException e)
    {
      // TODO Write warning to Eclipse log
      return DesktopManager.LINUX_UNKNOWN;
    }
  }
  
  // Provided to quickly manually test this class
  public static void main(String[] args)
  {
    System.out.println(DesktopEnvironmentAutoDetecter.findDesktopManager());
  }
}