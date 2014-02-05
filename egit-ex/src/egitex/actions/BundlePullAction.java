package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.util.FileParameter;
import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;

/**
 * Fetch commits from a bundle file
 * 
 * @author reshapiro
 * 
 */
public class BundlePullAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   
   private static final String[] ARGS = new String[] {
      "pull",
      null
   };
   
   private static final ParameterSet PARAMS = 
         new ParameterSet("Bundle file", new FileParameter("Bundle file", 1, true));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Pull from bundle";
   }
}