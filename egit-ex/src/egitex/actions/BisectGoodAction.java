package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will mark the HEAD as good in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectGoodAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "bisect",
      "good"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "Mark bisect checkout as good";
   }

}