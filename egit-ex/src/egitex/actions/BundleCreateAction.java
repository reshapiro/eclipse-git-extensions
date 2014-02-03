package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.Parameter;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;
import egit_ex.util.SaveFileParameter;

/**
 * Fetch commits from a bundle file
 * 
 * @author reshapiro
 * 
 */
public class BundleCreateAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "bundle",
      "create",
      null,
      null,
      null
   };
   
   private static final ParameterSet PARAMS = new ParameterSet("Bundle file",
                                                               new SaveFileParameter("bundle file", 2, true),
                                                               new Parameter("Start", 3, true),
                                                               new Parameter("End", 4, true));

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

   @Override
   boolean touch() {
      return false;
   }
   
}