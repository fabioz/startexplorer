package de.bastiankrol.startexplorer.crossplatform;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public enum DesktopEnvironment
{
  WINDOWS(OperatingSystem.WINDOWS), //
  LINUX_GNOME(OperatingSystem.LINUX, "Gnome"), //
  LINUX_KDE(OperatingSystem.LINUX, "KDE"), //
  LINUX_XFCE(OperatingSystem.LINUX, "Xfce"), //
  LINUX_LXDE(OperatingSystem.LINUX, "LXDE"), //
  LINUX_UNKNOWN(OperatingSystem.LINUX, "Unknown"), //
  MAC(OperatingSystem.MAC), //
  UNKNOWN(OperatingSystem.UNKNOWN);

  private static final Map<String, DesktopEnvironment> LABEL_TO_VALUE;

  private final OperatingSystem operatingSystem;
  private final String label;

  static
  {
    LABEL_TO_VALUE = new LinkedHashMap<String, DesktopEnvironment>();
    for (DesktopEnvironment value : DesktopEnvironment.values())
    {
      LABEL_TO_VALUE.put(value.getCombinedLabel(), value);
    }
  }

  private DesktopEnvironment(OperatingSystem operatingSystem)
  {
    this(operatingSystem, null);
  }

  private DesktopEnvironment(OperatingSystem operatingSystem, String label)
  {
    this.operatingSystem = operatingSystem;
    this.label = label;
  }

  public OperatingSystem getOperatingSystem()
  {
    return this.operatingSystem;
  }

  public String getDesktopEnvironmentLabel()
  {
    return this.label;
  }

  public String getCombinedLabel()
  {
    return this.operatingSystem.getLabel()
        + ((this.label != null) ? ("/" + this.label) : "");
  }

  public static Collection<String> allLabels()
  {
    Collection<String> labels = LABEL_TO_VALUE.keySet();

    // TODO: Support MacOS
    labels.remove(MAC.getCombinedLabel());
    return labels;
  }

  public static DesktopEnvironment fromLabel(String label)
  {
    return LABEL_TO_VALUE.get(label);
  }
}