package de.bastiankrol.startexplorer.crossplatform;

public class RuntimeExecCallsCustomTestRunner extends
    AbstractRuntimExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsCustomTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsCustom(new RuntimeExecDelegateForTests(),
        new CustomDesktopEnvironmentContainer(
            "explorer.exe /e,${resource_path}",
            "explorer.exe /select,${resource_path}", WorkingDirectoryMode.NONE,
            "cmd.exe /c start /d ${resource_path}", WorkingDirectoryMode.NONE,
            "cmd.exe /c ${resource_path}", WorkingDirectoryMode.NONE,
            WorkingDirectoryMode.NONE, true, true));
  }
}
