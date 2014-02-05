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
   
   private static final String[] ACTUAL_ARGS = new String[] {
      "bundle",
      "create",
      null,
      null
   };
   
   private static final ParameterSet PARAMS = new ParameterSet("Bundle Spec",
                                                               new SaveFileParameter("Bundle file", 2, true),
                                                               new Parameter("Start commit", 3, true),
                                                               new Parameter("End commit", 4, true));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
         ACTUAL_ARGS[2] = ARGS[2];
         ACTUAL_ARGS[3]  = ARGS[3] + ".." + ARGS[4];
         return ACTUAL_ARGS;
   }

   @Override
   String getJobName() {
      return "Create bundle";
   }

   @Override
   boolean touch() {
      return false;
   }
   
}