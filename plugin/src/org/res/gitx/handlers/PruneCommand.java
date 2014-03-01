package org.res.gitx.handlers;

import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.Parameter;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;

/**
 * Execute the Git operation that will find the SVN revision for a given Git Commit
 * 
 * @author reshapiro
 * 
 */
public class PruneCommand
      extends GitCommandHandler {
   
   private static final ParameterSet PARAMS = new ParameterSet("Prune remote refs", new Parameter("Remote", 2, true, "origin"));
   
   private static final String[] ARGS = new String[] {
      "remote", "prune", null
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