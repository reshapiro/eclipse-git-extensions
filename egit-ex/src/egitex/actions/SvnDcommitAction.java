package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will 'push' any new commits on the active
 * branch to the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnDcommitAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "dcommit"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }
}