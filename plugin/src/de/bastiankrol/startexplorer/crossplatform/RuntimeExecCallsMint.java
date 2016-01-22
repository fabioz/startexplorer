package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

public class RuntimeExecCallsMint extends RuntimeExecCallsGnome
{
  private static final Capabilities MINT_CAPABILITIES = Capabilities.create()
      .withFileSelectionSupport().build();

  @Override
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    return new String[] { "nemo", getPath(file) };
  }

  @Override
  public Capabilities getCapabilities()
  {
    return MINT_CAPABILITIES;
  }
}
