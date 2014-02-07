package egitex.actions;

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
      "bisect", "bad"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Mark bisect checkout as bad";
   }
}