package de.bastiankrol.startexplorer.crossplatform;

public class RuntimeExecCallsLxdeTestRunner extends
    AbstractRuntimExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsLxdeTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsLxde(new RuntimeExecDelegateForTests());
  }
}
