package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.Parameter;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;

/**
 * Execute the Git operation that will find the SVN revision for a given Git Commit
 * 
 * @author reshapiro
 * 
 */
public class PruneAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   
   private static final ParameterSet PARAMS = new ParameterSet("Prune remote refs", new Parameter("Remote", 2, true, "origin"));
   
   private static final String[] ARGS = new String[] {
      "remote",
      "prune",
      ""
   };

   
   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Delete obsolete remote refs";
   }

   @Override
   boolean touch() {
      return false;
   }
}