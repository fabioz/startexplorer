package de.bastiankrol.startexplorer.crossplatform;

public class RuntimeExecCallsKdeTestRunner extends
    AbstractRuntimExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsKdeTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsKde(new RuntimeExecDelegateForTests());
  }
}
