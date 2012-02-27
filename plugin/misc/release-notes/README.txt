Version 1.0.0
=============
* Finally, this plug-in supports Linux and Mac OS and not only Windows!
* Bugfix: Select File in File Manager option was neither stored nor
  used.

Version 0.9.0
=============
* Context Assist for variables in configuration dialog for custom commands.
* You can now configure the resource type a custom command is intended for,
  either "Files", "Folders" or "Files & Folders". If a custom command that
  is intended for files is execute on a directory, a dialog box with an error
  message is shown and no action is taken. If, on the other hand, a custom
  command intended for a folder is executed on a file, the command
  automatically uses the parent directory. Finally, for a custom command that
  is intended for files & folders, no such check is executed before invoking
  the command.
* "Copy resource path to clipboard" is now also available from the editor view.
  This didn't make sense before (because it more or less would only copy the
  selected text to the clipboard) but in connection with the feature to use the
  file opened in editor when the text selection is empty this is quite useful.
  By executing this on an empty text selection, you can now copy the path of
  the edited file directly to your clipboard.  

Version 0.8.0
=============
* Changed behaviour for "Show resource(s) in Windows Explorer" and "Start a
  Windows Explorer in this path": if you select a file (not a directory), the
  corresponding file will be selected in the Windows Explorer instance that is
  to be opened. This new behaviour can be disabled by unchecking "Select File
  In Explorer" in the preference page.  

Version 0.7.0
=============
* Now it is possible to invoke the StartExplorer commands (predefined and
  custom ones) from the editor window for the file currently opened in the
  editor. If the current text selection is empty, the file being edited will be
  used for "Start Windows Explorer", "Start file with system editor", "Start
  cmd.exe", ...
* New defaults for custom commands. The default custom command "Edit with
  UltraEdit" has been discarded, instead, there are two custom commands using
  Notepad and one that echos all variables just to showcase which variables can
  be used. 

Version 0.6.2
=============
* Comprehensive Eclipse Help documenation. Go to 
  Help -> Help Contents -> StartExplorer Help
  to read it.
* Two new variables:
  In addition to ${resource_path}, ${resource_name} and ${resource_parent},
  there are now two new variables: 
  - ${resource_name_without_extension}: File name or directory name of the resource,
  without path and without extension
  - ${resource_extension}: Only the file's extension (aka suffix)