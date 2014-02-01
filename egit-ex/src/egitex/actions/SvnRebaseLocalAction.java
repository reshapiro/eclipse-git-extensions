package egitex.actions;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will 'pull' and rebase any new commits on the
 * active branch from the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnRebaseLocalAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "rebase",
      "--local"
   };

   @Override
   String[] getArgs(Shell shell) {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "'Rebase' from fectched SVN commits";
   }
}