package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will end a bisect.
 * 
 * @author reshapiro
 * 
 */
public class BisectEndAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "bisect",
      "reset"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "End bisect";
   }

}