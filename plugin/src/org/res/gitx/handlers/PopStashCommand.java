package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will apply and remove a stash.
 * 
 * @author reshapiro
 * 
 */
public class PopStashCommand
      extends GitCommandHandler {

   private static final Parameter STASH = new Parameter("Optional stash", false);
   private static final ParameterSet PARAMETERS = new ParameterSet("Pop Stash", STASH);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("stash").append("pop");
      String stash = PARAMETERS.getParameterValue(STASH);
      if (stash != null && !stash.isEmpty()) {
         append(stash);
      }
   }

   @Override
   String getJobName() {
      return "Pop Stash";
   }
}