package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that skips the next bisect step
 * 
 * @author reshapiro
 * 
 */
public class BisectSkipAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   
   private static final String[] ARGS = new String[] {
      "bisect", "skip"
   };
   
   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "Skip bisect";
   }

}