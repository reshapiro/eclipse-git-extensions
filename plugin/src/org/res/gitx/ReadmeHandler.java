package org.res.gitx;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

class ReadmeHandler
      extends AbstractHandler {
   @Override
   public Object execute(ExecutionEvent event)
         throws ExecutionException {
      IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
      Shell shell = window.getShell();
      TitleAreaDialog dialog = new TitleAreaDialog(shell);
      dialog.setTitle("Important Notes");
      String message =
            "Be sure the current selection is a file in Git repository!"
                  + "\n Otherwise you might see the message 'Command is not enabled."
                  + "\n\n You must also defined the Eclipse String Substitution variable 'git_exec.  The value should be"
                  + " the absolute path to your Git executable.";
      dialog.setMessage(message);
      dialog.open();
      return null;
   }
}
