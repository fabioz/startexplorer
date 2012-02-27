StartExplorer
=============

Eclipse-Update-URL: http://startexplorer.sourceforge.net/update/

About
-----

The StartExplorer Eclipse plug-in offers tight integration of the system file manager (Windows Explorer, Gnome Nautilus, KDE Konqueror, Mac Finder, ...) and the shell (cmd.exe, Linux/Mac terminal) in Eclipse.

Every now and then when working with Eclipse you'd like to examine a file or a folder inside the Eclipse workspace with your file manager or open a shell/cmd.exe in this location. Or you edit a file in Eclipse and would like to open the parent folder of this file in the file manager or shell. Or the file you are editing contains a string which references another file in the filesystem and you would like to do some of the things mentioned above with that referenced file. This plug-in gives you a convenient way to do all this by adding a few entries to Eclipse's context menus. Furthermore, it offers a different, more convenient way to issue any shell/dos command you like through customizable menu commands. Last but not the least this plug-in offers a tighter integration of the clip board, so copying a file's/folder's path to the clip board is only two mouse clicks (instead of opening the Properties dialog and selecting the path manually). 

Supported Platforms
-------------------

This plug-in is inherently not platform-independent. Currently, the following operating systems/desktop environments are supported out of the box:

* Windows
* Mac OS
* Linux with Gnome
* Linux with KDE
* Linux with Xfce
* Linux with LXDE

However, even if your system is not listed there, you can still easily configure 
StartExplorer to work correctly on your system.

Features
--------

* Start file manager in selected folder from Eclipse Package Explorer/Project Explorer/Navigator
* Start shell in selected folder from Eclipse Package Explorer/Project Explorer/Navigator
* Start the default system app for any file from Eclipse Package Explorer/Project Explorer/Navigator
* From Eclipse Package Explorer/Project Explorer/Navigator, copy any resource's path directly to the clipboard, without having to select it manually in the Properties dialog
* Define custom commands and execute them on any resource in the Eclipse Package Explorer/Project Explorer/Navigator
* Do all of the above for multiple selected files/folders
* Select a text region in any Eclipse editor; if the selected text is a file system path, you can start a file manager in this location
* Select a text region in any Eclipse editor; if the selected text is a file system path, you can start a shell in this location
* Select a text region in any Eclipse editor; if the selected text is a file system path, you can start the default system application for this file
* Define custom commands and execute them on the file/folder represented by the current selection in any Eclipse editor
* Select a text region in any Eclipse editor and pass the selected text to any application in your system