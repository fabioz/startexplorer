package de.bastiankrol.startexplorer;

import java.io.File;

abstract class AbstractExecCallsTestRunner
{
  AbstractRuntimeExecCalls calls;

  void runTests()
  {
    this.before();
    this.runAllTests();
  }

  abstract void before();

  void runAllTests()
  {
    this.calls.startFileManagerForFile(new File("/var/log"), true);
    this.calls.startShellForFile(new File("/var/log"));
    this.calls
        .startSystemApplicationForFile(new File(
            "/home/bastian/eclipse/runtime-workspace/startexplorer-test-project/some.txt"));
  }
}
