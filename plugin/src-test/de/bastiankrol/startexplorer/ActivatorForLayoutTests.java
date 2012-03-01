package de.bastiankrol.startexplorer;

import de.bastiankrol.startexplorer.variables.VariableManager;
import de.bastiankrol.startexplorer.variables.VariableManagerForLayoutTests;

public class ActivatorForLayoutTests extends Activator
{
  @Override
  void initContext()
  {
    this.context = new PluginContext()
    {
      @Override
      void loadPreferencesFromEclipseStore()
      {
        // Do nothing
      }

      VariableManager initVariableManager()
      {
        return new VariableManagerForLayoutTests();
      }
    };
  }
}
