package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;

import de.bastiankrol.startexplorer.Activator;

public class DesktopEnvironmentAutoDetecter
{
  private static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_WINDOWS = "Windows"
      .toLowerCase();
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_LINUX = "Linux"
      .toLowerCase();
  private static final String SYSTEM_PROPERTY_OS_NAME_VALUE_MAC = "Mac OS"
      .toLowerCase();

  private static final int SHELL_SCRIPT_EXIT_VALUE_GNOME = 11;
  private static final int SHELL_SCRIPT_EXIT_VALUE_KDE = 12;
  private static final int SHELL_SCRIPT_EXIT_VALUE_XFCE = 13;
  private static final int SHELL_SCRIPT_EXIT_VALUE_LXDE = 14;
  private static final int SHELL_SCRIPT_EXIT_VALUE_UNKNOWN = 99;

  private static boolean inEclipse = true;

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
      Activator
          .logMessage(
              IStatus.WARNING,
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
      Activator
          .logMessage(
              IStatus.WARNING,
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
      URL scriptUrl = DesktopEnvironmentAutoDetecter.class
          .getResource("find-desktop-environmnet.sh");
      String scriptFileName;
      if (inEclipse)
      {
        File scriptFile = new File(FileLocator.toFileURL(scriptUrl).getPath());
        scriptFileName = scriptFile.getAbsolutePath();
      }
      else
      {
        scriptFileName = scriptUrl.getFile();
      }

      Process process = Runtime.getRuntime().exec(scriptFileName);
      int exitValue = process.waitFor();
      switch (exitValue)
      {
        case SHELL_SCRIPT_EXIT_VALUE_KDE:
          return DesktopEnvironment.LINUX_KDE;
        case SHELL_SCRIPT_EXIT_VALUE_GNOME:
          return DesktopEnvironment.LINUX_GNOME;
        case SHELL_SCRIPT_EXIT_VALUE_XFCE:
          return DesktopEnvironment.LINUX_XFCE;
        case SHELL_SCRIPT_EXIT_VALUE_LXDE:
          return DesktopEnvironment.LINUX_LXDE;
        case SHELL_SCRIPT_EXIT_VALUE_UNKNOWN:
          Activator
              .logMessage(
                  IStatus.WARNING,
                  "Could not autodetect desktop environment, maybe your desktop environment is not yet covered by the auto detection script.");
          return DesktopEnvironment.LINUX_UNKNOWN;
        default:
          Activator
              .logMessage(
                  IStatus.ERROR,
                  "Could not autodetect desktop environment. The auto detection script returned an unknown exit code: "
                      + exitValue);
          return DesktopEnvironment.LINUX_UNKNOWN;
      }
    }
    catch (IOException e)
    {
      Activator.logMessage(
          IStatus.WARNING,
          "Could not autodetect desktop environment due to IOException: "
              + e.getMessage());
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
    catch (InterruptedException e)
    {
      Activator.logMessage(IStatus.WARNING,
          "Could not autodetect desktop environment due to InterruptedException: "
              + e.getMessage());
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
    catch (Exception e)
    {
      Activator.logMessage(IStatus.WARNING,
          "Could not autodetect desktop environment due to "
              + e.getClass().getName() + ": " + e.getMessage());
      return DesktopEnvironment.LINUX_UNKNOWN;
    }
  }

  // Provided to quickly manually test this class
  public static void main(String[] args)
  {
    inEclipse = false;
    System.out.println(DesktopEnvironmentAutoDetecter.findDesktopEnvironment());
  }
}