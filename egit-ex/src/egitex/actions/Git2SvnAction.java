package egitex.actions;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will 'push' any new commits on the active
 * branch to the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class Git2SvnAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "find-rev",
      ""
   };

   @Override
   String[] getArgs(Shell shell) {
      SimpleInputDialog dialog = new SimpleInputDialog(shell, "Get SVN revision for SHA", "branch, tag or reference");
      dialog.create();
      if (dialog.open() == Window.OK) {
         String sha = dialog.getInput();
         if (sha != null) {
            ARGS[2] = sha;
            return ARGS;
         }
      }
      return null;
   }
}