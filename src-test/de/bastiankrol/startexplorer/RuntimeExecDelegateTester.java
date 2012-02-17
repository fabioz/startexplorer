package de.bastiankrol.startexplorer;

import java.io.File;
import java.io.IOException;

class RuntimeExecDelegateTester extends RuntimeExecDelegate
{

  RuntimeExecDelegateTester()
  {
    super(true);
  }

  @Override
  public void exec(String execCommandString, File workingDirectory)
  {
    System.out.println("Executing: <" + execCommandString
        + ">, working directory: <" + workingDirectory + ">");
    try
    {
      this.getRuntime().exec(execCommandString, null, workingDirectory);
    }
    catch (IOException e)
    {
      StringBuilder builder = new StringBuilder();
      builder.append("The command could not be executed.");
      builder.append("\n");
      if (e.getMessage() != null)
      {
        builder.append(" Message: ");
        builder.append(e.getMessage());
        builder.append("\n");
      }
      System.err.println(builder.toString());
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    RuntimeExecCallsGnome gnomeCalls = new RuntimeExecCallsGnome(
        new RuntimeExecDelegateTester());
    // gnomeCalls.startShellForFile(new File("/var/log")); // Works
    // gnomeCalls.startFileManagerForFile(new File("/var/log"), true); // Works
    gnomeCalls
        .startSystemApplicationForFile(new File(
            "/home/bastian/eclipse/runtime-workspace/startexplorer-test-project/some.txt")); // Works
  }
}
