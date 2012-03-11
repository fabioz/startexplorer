package de.bastiankrol.startexplorer;

import java.util.ArrayList;
import java.util.List;

import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.customcommands.SharedFileFinder;
import de.bastiankrol.startexplorer.preferences.PreferenceModel;
import de.bastiankrol.startexplorer.variables.VariableManager;
import de.bastiankrol.startexplorer.variables.VariableManagerForLayoutTests;

public class ActivatorForLayoutTests extends Activator
{
  @Override
  public void initContext()
  {
    this.pluginContext = new PluginContext()
    {

      @Override
      public PreferenceModel getPreferenceModel()
      {
        this.preferenceModel = new PreferenceModel();
        return this.preferenceModel;
      }

      @Override
      void loadPreferencesFromEclipseStore()
      {
        // Do nothing
      }

      @Override
      VariableManager initVariableManager()
      {
        return new VariableManagerForLayoutTests();
      }

      @Override
      public LogFacility getLogFacility()
      {
        return new DummyLogFacility();
      }

      @Override
      public SharedFileFinder getSharedFileFinder()
      {
        return new SharedFileFinder()
        {
          @Override
          public void startSearch()
          {
            // do nothing
          }

          @Override
          public boolean hasFinished()
          {
            return false;
          }

          @Override
          public List<CommandConfig> getResult()
          {
            return new ArrayList<CommandConfig>();
          }
        };
      }
    };
  }
}
