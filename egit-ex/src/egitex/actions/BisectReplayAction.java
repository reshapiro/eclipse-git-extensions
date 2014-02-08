package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.util.FileParameter;
import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;

/**
 * Execute the Git operation that replays a bisect from a saved log.
 * 
 * @author reshapiro
 * 
 */
public class BisectReplayAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   
   private final ParameterSet PARAMETERS = new ParameterSet("Replay bisect", new FileParameter("Log file", 2, true));
   
   private static final String[] ARGS = new String[] {
      "bisect", "replay", null
   };
   
   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, ARGS);
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "Replay bisect log";
   }

}