package egitex.actions;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will start a bisect.
 * 
 * @author reshapiro
 * 
 */
public class BisectStartAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "bisect start"
   };

   @Override
   String[] getArgs(Shell shell) {
      return ARGS;
   }
}