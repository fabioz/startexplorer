package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows path and
 * <ul>
 * <li>opens a Windows Explorer for the directory in the location described by
 * that path or</li>
 * <li>starts a system application for that file.</li>
 * </ul>
 * 
 * @author Bastian Krol
 */
public abstract class AbstractStartFromStringHandler extends
    AbstractStartExplorerHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  public Object execute(ExecutionEvent event) throws ExecutionException
  {
    Object applicationContext = event.getApplicationContext();
    if (!(applicationContext instanceof IEvaluationContext))
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current application context is not an IEvaluationContext.");
      return null;
    }
    IEvaluationContext appContext = (IEvaluationContext) applicationContext;
    ISelection selection = (ISelection) appContext
        .getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
    if (selection == null)
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection is null, no action is taken.");
      return null;
    }
    if (selection.isEmpty())
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection is empty, no action is taken.");
      return null;
    }
    return this.executeForSelection(event, selection, appContext);
  }

  public Object executeForSelection(ExecutionEvent event, ISelection selection,
      IEvaluationContext appContext) throws ExecutionException
  {
    String selectedText;
    try
    {
      selectedText = this.extractStringFromSelection(selection);
    }
    catch (IllegalArgumentException e)
    {
      return null;
    }
    if (selectedText == null)
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection's text is null.");
      return null;
    }
    if (selectedText.equals(""))
    {
      Object editorInputObject = appContext.getParent().getVariable(
          "activeEditorInput");

      if (editorInputObject != null
          && IFileEditorInput.class.isAssignableFrom(editorInputObject
              .getClass()))
      {
        IFileEditorInput editorInput = (IFileEditorInput) editorInputObject;
        IResource fileInEditor = editorInput.getFile();
        File file = this.resourceToFile(fileInEditor, this.getResourceType(),
            event);
        AbstractStartFromResourceHandler startFromResourceHandler = this
            .getAppropriateStartFromResourceHandler();
        if (startFromResourceHandler != null)
        {
          startFromResourceHandler.doActionForFileList(Collections
              .singletonList(file));
          return null;
        }
        else
        {
          MessageDialog
              .openError(
                  HandlerUtil.getActiveShellChecked(event),
                  "Empty text selection",
                  "The current selection is an empty text selection, and since this command is not enabled for resources it can not be invoked for the resource opened in the editor instead.");
          return null;
        }
      }
      else
      {
        Activator
            .logMessage(
                org.eclipse.core.runtime.IStatus.WARNING,
                "The current selection is an empty text selection, so the command was invoked for the resource opened in the editor. "
                    + "But the object fetched by event.getApplicationContext().getParent().getVariable(\"activeEditorInput\") is not of expected type IFileEditorInput: "
                    + editorInputObject);
        return null;
      }
    }
    if (this.shouldInterpretTextSelectionAsFileName())
    {
      selectedText = selectedText.trim();
      File file = this.getPathChecker().checkPath(selectedText,
          this.getResourceType(), event);
      if (file != null)
      {
        this.doActionForFile(file);
      }
    }
    else
    {
      try
      {
        this.doActionForSelectedText(selectedText);
      }
      catch (IOException e)
      {
        MessageDialog
            .openError(
                HandlerUtil.getActiveShellChecked(event),
                "IO Error",
                "An IO error occured while trying to create a temp file to pass the selected text to the application. Message: "
                    + e.getLocalizedMessage());
        return null;
      }
    }
    return null;
  }

  /**
   * Determines wether the text selection is to be interpreted as a file name.
   * By default, this method returns <code>true</code>. Subclasses may override
   * this method, in this case they should also override
   * {@link #doActionForSelectedText(String)}.
   * 
   * @return <code>true</code>
   */
  protected boolean shouldInterpretTextSelectionAsFileName()
  {
    return true;
  }

  /**
   * Executes the appropriate action for the given <code>selectedText</code>; by
   * default this method does nothing. This method needs to be overridden when
   * {@link #shouldInterpretTextSelectionAsFileName()} is overridden.
   * 
   * @param selectedText the String to do something with
   * @throws IOException
   */
  protected void doActionForSelectedText(String selectedText)
      throws IOException
  {
    // Empty default implementation
  }

  /**
   * Returns the resource type appropriate for this handler.
   * 
   * @return the resource type appropriate for this handler.
   */
  protected abstract PathChecker.ResourceType getResourceType();

  /**
   * Executes the appropriate action for the given <code>file</code>
   * 
   * @param file the File object to do something with
   */
  protected abstract void doActionForFile(File file);

  private String extractStringFromSelection(ISelection selection)
  {
    if (!(selection instanceof ITextSelection))
    {
      String message = "Current selection is not a text selection (ITextSelection), [selection.getClass(): "
          + selection.getClass()
          + ", selection.toString(): "
          + selection.toString() + "]";
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING, message);
      throw new IllegalArgumentException(message);
    }
    else
    {
      ITextSelection textSelection = (ITextSelection) selection;
      String pathString = textSelection.getText();
      return pathString;
    }
  }

  abstract AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler();
}
