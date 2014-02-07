package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will list orphaned commits
 * 
 * @author reshapiro
 * 
 */
public class ListOrphanedCommitsAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "fsck", "--lost-found"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "List orphan commits";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}