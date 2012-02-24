package de.bastiankrol.startexplorer;

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
    };
  }
}
