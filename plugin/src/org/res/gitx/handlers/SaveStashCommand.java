package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will save a stash, with an optional message/name.
 * 
 * @author reshapiro
 * 
 */
public class SaveStashCommand
      extends GitCommandHandler {

   private static final Parameter MESSAGE = new Parameter("Optional message ", false);
   private static final ParameterSet PARAMETERS = new ParameterSet("Stash", MESSAGE);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("stash").append("save");
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