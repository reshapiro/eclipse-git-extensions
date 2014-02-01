package egitex.actions;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will find the SVN revision for a given Git Commit
 * 
 * @author reshapiro
 * 
 */
public class PruneAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "remote",
      "find-prune",
      ""
   };

   @Override
   String[] getArgs(Shell shell) {
      SimpleInputDialog dialog = new SimpleInputDialog(shell, "Prune remote refs", "remote", "origin");
      dialog.create();
      if (dialog.open() == Window.OK) {
         String remote = dialog.getInput();
         if (remote != null) {
            ARGS[2] = remote;
            return ARGS;
         }
      }
      return null;
   }

   @Override
   String getJobName() {
      return "Delete obsolete remote refs";
   }

   @Override
   boolean touch() {
      return false;
   }
}