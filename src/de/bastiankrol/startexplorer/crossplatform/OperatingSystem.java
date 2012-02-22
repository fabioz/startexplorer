package de.bastiankrol.startexplorer.crossplatform;

public enum OperatingSystem
{
  WINDOWS("Windows"), //
  LINUX("Linux"), //
  MAC("MacOS"), //
  UNKNOWN("Unknown");

  private final String label;

  private OperatingSystem(String label)
  {
    this.label = label;
  }

  public String getLabel()
  {
    return this.label;
  }
}