package egitex.actions;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will show the  current status.  This Git operation also
 * appears to have a side-effect that does some kind of caching.
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

   @Override
   String getJobName() {
      return "Show repo status";
   }
}