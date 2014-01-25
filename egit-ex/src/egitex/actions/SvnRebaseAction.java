package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will 'pull' and rebase any new commits on the
 * active branch from the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnRebaseAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String OPERATION_NAME = "pull from svn";
   private static final String[] ARGS = new String[] {
      "svn",
      "rebase"
   };

   @Override
   String getOperationName() {
      return OPERATION_NAME;
   }

   @Override
   String[] getArgs() {
      return ARGS;
   }
}