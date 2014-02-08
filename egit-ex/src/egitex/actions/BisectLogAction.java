package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will log the bisect.
 * Later it will be possible to log to a file.
 * 
 * @author reshapiro
 * 
 */
public class BisectLogAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   
   private static final String[] ARGS = new String[] {
      "bisect", "log"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "End bisect";
   }

   @Override
   boolean touch() {
      return false;
   }

}