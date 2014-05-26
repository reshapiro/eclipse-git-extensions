package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will show the contents of a stash
 * 
 * @author reshapiro
 * 
 */
public class ShowStashCommand
      extends GitCommandHandler {

   private static final Parameter MESSAGE = new Parameter("Stash", false);
   private static final ParameterSet PARAMETERS = new ParameterSet("Show Stash", MESSAGE);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("stash").append("show");
      String message = PARAMETERS.getParameterValue(MESSAGE);
      if (message != null && !message.isEmpty()) {
         append(message);
      }
   }

   @Override
   String getJobName() {
      return "Stash";
   }
}