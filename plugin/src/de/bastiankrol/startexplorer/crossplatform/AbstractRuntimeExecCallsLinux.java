package de.bastiankrol.startexplorer.crossplatform;

import java.net.URL;

abstract class AbstractRuntimeExecCallsLinux extends
    AbstractRuntimeExecCalls
{

  AbstractRuntimeExecCallsLinux()
  {
    super();
  }

  AbstractRuntimeExecCallsLinux(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartFileManager(URL url)
  {
    throw new UnsupportedOperationException(
        "Illegal call: AbstractRuntimeExecCallsLinux#getCommandForStartFileManager(URL)");
  }

  @Override
  boolean doFilePartsWantWrapping()
  {
    return false;
  }

  @Override
  boolean doFilePartsWantEscaping()
  {
    return false;
  }

  @Override
  boolean isWindows()
  {
    return false;
  }
}
