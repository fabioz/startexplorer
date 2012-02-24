package de.bastiankrol.startexplorer.crossplatform;

import de.bastiankrol.startexplorer.crossplatform.RuntimeExecCallsGnome;


public class RuntimeExecCallsGnomeTestRunner extends
    AbstractRuntimExecCallsTestRunner
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
