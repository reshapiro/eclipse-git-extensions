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
public class Svn2GitAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "find-rev",
      ""
   };

   @Override
   String[] getArgs(Shell shell) {
      SimpleInputDialog dialog = new SimpleInputDialog(shell, "Get Git commit for SVN revison", "rev");
      dialog.create();
      if (dialog.open() == Window.OK) {
         String sha = dialog.getInput();
         if (sha != null) {
            ARGS[2] = "r"+sha;
            return ARGS;
         }
      }
      return null;
   }
}