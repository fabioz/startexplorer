package de.bastiankrol.startexplorer.preferences;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

class ContentAssist
{
  static void addContentAssistAdapter(Text text)
  {
    Map<String, String> proposals = new LinkedHashMap<String, String>();
    proposals
        .put(
            "${resource_path}",
            "Absolute path to selected resource. For \"C:\\path\\to\\resource.txt\" this would be \"C:\\path\\to\\resource.txt\".");
    proposals
        .put(
            "${resource_name}",
            "File name or directory name of the resource, without path. For \"C:\\path\\to\\resource.txt\" this would be \"resource.txt\".");
    proposals
        .put(
            "${resource_parent}",
            "Absolute path to parent of selected resource. For \"C:\\path\\to\\resource.txt\" this would be \"C:\\path\\to\".");
    proposals
        .put(
            "${resource_name_without_extension}",
            "File name or directory name of the resource, without path and without extension. For \"C:\\path\\to\\resource.txt\" this would be \"resource\".");
    proposals
        .put(
            "${resource_extension}",
            "Only the file's extension, without leading dot. For \"C:\\path\\to\\resource.txt\" this would be \"txt\".");

    new ContentAssistCommandAdapter(text, new TextContentAdapter(),
        new StartExplorerContentProposalProvider(proposals), null, new char[] {
            '$', '{' }, true);
  }
}
