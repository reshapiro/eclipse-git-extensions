package org.res.gitx;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class ReadmeHandler
      extends AbstractHandler {
   @Override
   public Object execute(ExecutionEvent event)
         throws ExecutionException {
      IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
      Shell shell = window.getShell();
      String title = "Important Notes";
      String message =
            "The current selection must be a file, directory or project in an Explorer view or an editor"
                  + ", and the selection must be part of a project managed by EGit."
                  + "\nSelecting a Working Set or any entry in the 'Git Repositories' view will not work!!"
                  + "\n If you see the message 'Command is not enabled', you got one of these wrong."
                  + "\n\n You must also define the Eclipse String Substitution variable 'git_exec'. " 
                  + "\n Do this in Preferences -> Run/Debug -> String Substitution." 
                  + "\nThe value should be the absolute path to your Git executable.";
      MessageDialog.open(MessageDialog.INFORMATION, shell, title, message, MessageDialog.NONE);
      return null;
   }
}
