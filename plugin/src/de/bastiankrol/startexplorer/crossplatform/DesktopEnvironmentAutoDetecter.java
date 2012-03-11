package de.bastiankrol.startexplorer.crossplatform;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.IOException;

public class DesktopEnvironmentAutoDetecter
{
  private static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_WINDOWS = "Windows"
      .toLowerCase();
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_LINUX = "Linux"
      .toLowerCase();
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_MAC = "Mac OS"
      .toLowerCase();

  public static DesktopEnvironment findDesktopEnvironment()
  {
    OperatingSystem operatingSystem = findOperatingSystem();
    switch (operatingSystem)
    {
      case WINDOWS:
        return DesktopEnvironment.WINDOWS;
      case LINUX:
        return findLinuxDesktopEnvironment();
      case MAC_OS:
        return DesktopEnvironment.MAC_OS;
      case UNKNOWN:
        // fall through
      default:
        return DesktopEnvironment.UNKNOWN;
    }
  }

  private static OperatingSystem findOperatingSystem()
  {
    String osName = System.getProperty(SYSTEM_PROPERTY_OS_NAME);
    if (osName == null)
    {
      // WTF?
      getPluginContext()
          .getLogFacility()
          .logWarning(
              "Could not autodetect operating system. Currently, only Windows, Linux and Mac OS are supported. Your system returned null for System.getProperty("
                  + SYSTEM_PROPERTY_OS_NAME + ").");
      return OperatingSystem.UNKNOWN;
    }

    osName = osName.toLowerCase();
    if (osName.contains(SYSTEM_PROPERTY_OS_NAME_VALUE_LINUX))
    {
      return OperatingSystem.LINUX;
    }
    else if (osName != null
        && osName.contains(SYSTEM_PROPERTY_OS_NAME_VALUE_WINDOWS))
    {
      return OperatingSystem.WINDOWS;
    }
    else if (osName != null
        && osName.contains(SYSTEM_PROPERTY_OS_NAME_VALUE_MAC))
    {
      return OperatingSystem.MAC_OS;
    }
    else
    {
      getPluginContext()
          .getLogFacility()
          .logWarning(
              "Could not autodetect operating system. Currently, only Windows and Linux are supported. Your system returned <"
                  + osName
                  + "> for System.getProperty("
                  + SYSTEM_PROPERTY_OS_NAME + ").");
      return OperatingSystem.UNKNOWN;
    }
  }

  private static DesktopEnvironment findLinuxDesktopEnvironment()
  {
    try
    {
      if (checkProcessNames("gnome-session"))
      {
        return DesktopEnvironment.LINUX_GNOME;
      }
      if (checkProcessNames("ksmserver"))
      {
        return DesktopEnvironment.LINUX_KDE;
      }
      if (checkProcessNames("xfce4-session", "xfce-mcs-manage"))
      {
        return DesktopEnvironment.LINUX_XFCE;
      }
      if (checkProcessNames("lxsession"))
      {
        return DesktopEnvironment.LINUX_LXDE;
      }
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
    catch (IOException e)
    {
      getLogFacility().logWarning(
          "Could not autodetect desktop environment due to IOException: "
              + e.getMessage());
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
    catch (InterruptedException e)
    {
      getLogFacility().logWarning(
          "Could not autodetect desktop environment due to InterruptedException: "
              + e.getMessage());
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
    catch (Exception e)
    {
      getLogFacility().logWarning(
          "Could not autodetect desktop environment due to "
              + e.getClass().getName() + ": " + e.getMessage());
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
  }

  private static boolean checkProcessNames(String... processNames)
      throws IOException, InterruptedException
  {
    for (String processName : processNames)
    {
      if (executePidOfCommand(processName))
      {
        return true;
      }
    }
    return false;
  }

  private static boolean executePidOfCommand(String processName)
      throws IOException, InterruptedException
  {
    Process process = Runtime.getRuntime().exec(
        new String[] { "pidof", processName });
    return process.waitFor() == 0;
  }

  // Provided to quickly manually test this class
  public static void main(String[] args)
  {
    System.out.println(DesktopEnvironmentAutoDetecter.findDesktopEnvironment());
  }
}