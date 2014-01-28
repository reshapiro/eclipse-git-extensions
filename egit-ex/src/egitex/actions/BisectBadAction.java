package egitex.actions;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will mark the HEAD as bad in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectBadAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "bisect",
      "bad"
   };

   @Override
   String[] getArgs(Shell shell) {
      return ARGS;
   }
}