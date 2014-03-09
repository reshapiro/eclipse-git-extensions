package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

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