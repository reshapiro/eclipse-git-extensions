package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will create a branch from a a stash
 * 
 * @author reshapiro
 * 
 */
public class BranchStashCommand
      extends GitCommandHandler {

   private static final Parameter STASH = new Parameter("Stash ", false);
   private static final Parameter BRANCH = new Parameter("New branch", true);
   private static final ParameterSet PARAMETERS = new ParameterSet("Branch Stash", STASH);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("stash").append("branch").append(PARAMETERS.getParameterValue(BRANCH));
      String stash = PARAMETERS.getParameterValue(STASH);
      if (stash != null && !stash.isEmpty()) {
         append(stash);
      }
   }

   @Override
   String getJobName() {
      return "Branch Stash";
   }
}