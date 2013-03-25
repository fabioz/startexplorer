package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.io.IOException;

class RuntimeExecDelegateForTests extends RuntimeExecDelegate
{

  RuntimeExecDelegateForTests()
  {
    super(true);
  }

  @Override
  public void exec(String[] cmdArray, File workingDirectory, boolean isWindows)
  {
    try
    {
      this.getRuntime().exec(cmdArray, null, workingDirectory);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
}
