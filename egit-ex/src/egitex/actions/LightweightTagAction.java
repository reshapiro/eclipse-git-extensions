package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.Parameter;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;

/**
 * Execute the Git operation that will find the SVN revision for a given Git
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class LightweightTagAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {

   private static final String[] ARGS = new String[] {
      "tag", null, null
   };

   private static final ParameterSet PARAMS = 
         new ParameterSet("Simple Tag", new Parameter("Name", 1, true), new Parameter("Reference", 2, true));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS, ARGS);
     return ARGS;
     
   }

   @Override
   String getJobName() {
      return "Create Lightweight Tag";
   }

   @Override
   boolean touch() {
      return false;
   }
}