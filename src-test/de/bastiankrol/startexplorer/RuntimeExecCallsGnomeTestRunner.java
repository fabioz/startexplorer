package de.bastiankrol.startexplorer;


public class RuntimeExecCallsGnomeTestRunner extends
    AbstractExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsGnomeTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsGnome(new RuntimeExecDelegateForTests());
  }
}
