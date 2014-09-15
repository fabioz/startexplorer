package de.bastiankrol.startexplorer.handlers.delegates;

import static de.bastiankrol.startexplorer.Activator.getLogFacility;
import static de.bastiankrol.startexplorer.Activator.getPluginContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.ResourceUtil;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.crossplatform.Capabilities;
import de.bastiankrol.startexplorer.util.Validator;

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
public abstract class AbstractStartFromEditorHandlerDelegate extends
    AbstractHandlerDelegate
{

  public Object execute(ExecutionEvent event) throws ExecutionException
  {
    Object applicationContext = event.getApplicationContext();
    if (!(applicationContext instanceof IEvaluationContext))
    {
      getLogFacility().logWarning(
          "Current application context is not an IEvaluationContext.");
      return null;
    }
    IEvaluationContext appContext = (IEvaluationContext) applicationContext;
    ISelection selection = (ISelection) appContext
        .getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
    if (selection == null && !this.alwaysUseFileOpenedInEditor())
    {
      getLogFacility().logWarning(
          "Current selection is null, no action is taken.");
      return null;
    }
    if (selection.isEmpty() && !this.alwaysUseFileOpenedInEditor())
    {
      getLogFacility().logWarning(
          "Current selection is empty, no action is taken.");
      return null;
    }
    return this.executeForSelection(event, selection, appContext);
  }

  /**
   * Executes the commnd for a text selection.
   * 
   * @param event the Eclipse event
   * @param selection the selection object
   * @param appContext the evaluation context
   * @return null
   * @throws ExecutionException if things go wrong
   */
  public Object executeForSelection(ExecutionEvent event, ISelection selection,
      IEvaluationContext appContext) throws ExecutionException
  {
    String selectedText;
    selectedText = this.extractStringFromSelection(selection);
    if (selectedText == null && !this.alwaysUseFileOpenedInEditor())
    {
      getLogFacility().logWarning("Current selection's text is null.");
      return null;
    }

    File fileOpenedInEditor = this
        .prefetchFileOpenedInEditor(event, appContext);
    if (selectedText.equals("") || this.alwaysUseFileOpenedInEditor())
    {
      this.executeForFileOpenedInEditor(fileOpenedInEditor, event, appContext);
      return null;
    }
    else if (this.shouldInterpretTextSelectionAsFileName())
    {
      this.executeForSelectedText(event, selectedText, fileOpenedInEditor);
      return null;
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

  private File prefetchFileOpenedInEditor(ExecutionEvent event,
      IEvaluationContext appContext) throws ExecutionException
  {
    Object editorInputObject = appContext.getParent().getVariable(
        "activeEditorInput");
    if (editorInputObject == null)
    {
      return null;
    }

    File file = null;
    // case 1: a file in the workspace:
    if (IEditorInput.class.isAssignableFrom(editorInputObject.getClass()))
    {
      IEditorInput editorInput = (IEditorInput) editorInputObject;
      IResource fileInEditor = ResourceUtil.getResource(editorInput);
      if (fileInEditor != null)
      {
        file = this.resourceToFile(fileInEditor, ResourceType.FILE, event);
      }
    }
    // case 2: a file that is not part of the workspace
    if (file == null
        && IURIEditorInput.class.isAssignableFrom(editorInputObject.getClass()))
    {
      IURIEditorInput uriEditorInput = (IURIEditorInput) editorInputObject;
      URI uri = uriEditorInput.getURI();
      if ("file".equals(uri.getScheme()))
      {
        file = this.pathStringToFile(uri.getPath(), this.getResourceType(),
            event);
      }
    }
    // case 3: it could be a class file from a jar
    if (file == null)
    {
      file = uglyHackForFilesFromJars(editorInputObject, event);
    }
    // if neither of the above, then give up and return null

    return file;
  }

  private void executeForFileOpenedInEditor(File fileInEditor,
      ExecutionEvent event, IEvaluationContext appContext)
      throws ExecutionException
  {
    if (fileInEditor == null)
    {
      Object editorInputObject = appContext.getParent().getVariable(
          "activeEditorInput");
      if (editorInputObject == null)
      {
        getPluginContext()
            .getLogFacility()
            .logWarning(
                "The current selection is an empty text selection, so the command was invoked for the resource opened in the editor. "
                    + "But the object fetched by event.getApplicationContext().getParent().getVariable(\"activeEditorInput\") is null.");
        return;
      }
      else
      {
        getPluginContext()
            .getLogFacility()
            .logWarning(
                "The current selection is an empty text selection, so the command was invoked for the resource opened in the editor. "
                    + "But the object fetched by event.getApplicationContext().getParent().getVariable(\"activeEditorInput\") is neither of the expected types (IEditorInput, IURIEditorInput or IClassFileEditorInput) but of type "
                    + editorInputObject.getClass().getName());
        MessageDialog
            .openError(
                HandlerUtil.getActiveShellChecked(event),
                "This is not a normal file, is it?",
                "The current selection is an empty text selection, so the command was invoked for the resource opened in the editor. "
                    + "Unfortunately, the resource opened in the editor does not directly map to a file.");
        return;
      }
    }

    AbstractStartFromResourceHandlerDelegate startFromResourceHandlerDelegate = this
        .getAppropriateStartFromResourceHandlerDelegate();
    if (startFromResourceHandlerDelegate != null)
    {
      startFromResourceHandlerDelegate.doActionForFileList(Collections
          .singletonList(fileInEditor));
      return;
    }
    else
    {
      MessageDialog
          .openError(
              HandlerUtil.getActiveShellChecked(event),
              "Empty text selection",
              "The current selection is an empty text selection, and since this command is not enabled for resources it can not be invoked for the resource opened in the editor instead.");
      return;
    }
  }

  private File uglyHackForFilesFromJars(Object editorInput, ExecutionEvent event)
  {
    // I think I've never written a dirty hack as ugly as this.
    // I solemnly swear to never do it again. BK.
    // Oh, btw, it's done via reflection to avoid a dependency to JDT for
    // StartExplorer but still work for the class file editor for a class from
    // an external Jar.
    Class<?>[] interfaces = editorInput.getClass().getInterfaces();
    for (Class<?> c : interfaces)
    {
      if (c.getName().equals(
          "org.eclipse.jdt.internal.ui.javaeditor.IClassFileEditorInput"))
      {
        try
        {
          Object classFile = editorInput.getClass().getMethod("getClassFile")
              .invoke(editorInput);
          Object jar = classFile.getClass().getMethod("getOpenableParent")
              .invoke(classFile);
          Object jarRoot = jar.getClass().getMethod("getParent").invoke(jar);
          IPath path = (IPath) jarRoot.getClass().getMethod("getPath")
              .invoke(jarRoot);
          if (path != null)
          {
            return this.pathToFile(path, this.getResourceType(), event);
          }
          else
          {
            return null;
          }
        }
        catch (Exception e)
        {
          return null;
        }
      }
    }
    return null;
  }

  private void executeForSelectedText(ExecutionEvent event,
      String selectedText, File fileOpenedInEditor)
      throws ExecutionException
  {
    selectedText = selectedText.trim();
    Validator.MaybeFile maybeFile = this.getValidator().checkPath(selectedText,
        this.getResourceType(), fileOpenedInEditor);
    if (maybeFile.file != null)
    {
      this.doActionForFile(maybeFile.file);
      return;
    }
    // If selectedText is not a valid file, it might be a valid URL. Some
    // actions support URLs (on some platforms), so we might give it a try.
    else if (this
        .areUrlsSupported(this.getRuntimeExecCalls().getCapabilities()))
    {
      URL url = this.getValidator().checkUrl(selectedText, event);
      if (url != null)
      {
        this.doActionForUrl(url);
        return;
      }
    }

    // Unable to interprete selectedText as file or as URL, show message dialog
    // explaining failure.
    this.getValidator().showMessageFor(maybeFile.reason, selectedText, event);
  }

  /**
   * Determines whether the text selection is to be interpreted as a file name.
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
   * Determines whether this handler should always act on the resource currently
   * opened in the editor instead of only doing so when the current text
   * selection is empty. By default, this method returns <code>false</code>.
   * Subclasses may override this method.
   * 
   * @return <code>false</code>
   */
  protected boolean alwaysUseFileOpenedInEditor()
  {
    return false;
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
  protected abstract ResourceType getResourceType();

  /**
   * Executes the appropriate action for the given <code>file</code>.
   * 
   * @param file the File object to do something with
   */
  protected abstract void doActionForFile(File file);

  /**
   * Executes the appropriate action for the given <code>url</code>, if that is
   * supported. Must not be called if {@code this.areUrlsSuppored()} returns
   * {@code false}. Subclasses that override this should also override
   * {@link #areUrlsSupported()}.
   * 
   * @param url the URL object to do something with
   */
  protected void doActionForUrl(URL url)
  {
    throw new UnsupportedOperationException(
        "This action does not support URLs.");
  }

  /**
   * Checks if this action can also be called with an URL on the current
   * platform. The default implementation is to always return {@¢ode
   * false}.
   * 
   * Subclasses that override this, also need to override
   * {@link #doActionForUrl(URL)}.
   * 
   * @param capabilities the platform's capabilities
   * 
   * @return {@code true} if this action might be able to handle URLs.
   */
  protected boolean areUrlsSupported(Capabilities capabilities)
  {
    // default: no URL support
    return false;
  }

  private String extractStringFromSelection(ISelection selection)
  {
    if (!(selection instanceof ITextSelection))
    {
      String message = "Current selection is not a text selection (ITextSelection), [selection.getClass(): "
          + selection.getClass()
          + ", selection.toString(): "
          + selection.toString() + "]";
      getLogFacility().logWarning(message);
      return null;
    }
    else
    {
      ITextSelection textSelection = (ITextSelection) selection;
      String pathString = textSelection.getText();
      return pathString;
    }
  }

  /**
   * Returns a handler delegate object that handles the same action as this
   * handler, but for a structured resource selection instead of a text
   * selection.
   * 
   * @return an AbstractStartFromResourceHandlerDelegate
   */
  abstract AbstractStartFromResourceHandlerDelegate getAppropriateStartFromResourceHandlerDelegate();
}
