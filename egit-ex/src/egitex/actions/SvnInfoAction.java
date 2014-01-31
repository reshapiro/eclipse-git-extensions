package egitex.actions;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will show information about the SVN repository.
 * 
 * @author reshapiro
 * 
 */
public class SvnInfoAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "info"
   };

   @Override
   String[] getArgs(Shell shell) {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Show the status of the bridged SVN repo";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}