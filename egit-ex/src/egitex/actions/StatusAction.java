package egitex.actions;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will 'push' any new commits on the active
 * branch to the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class StatusAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "status"
   };

   @Override
   String[] getArgs(Shell shell) {
      return ARGS;
   }
}