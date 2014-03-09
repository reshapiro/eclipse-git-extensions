package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameter;

/**
 * Execute the Git operation that will end a bisect.
 * 
 * @author reshapiro
 * 
 */
public class BisectEndCommand
      extends GitCommandHandler {
   
   private static final ParameterSet PARAMETERS = new ParameterSet("End Bisect", new RefParameter("Reset to (optional)", 2, false));
   
   private static final String[] FULL_ARGS = new String[] {
      "bisect", "reset", null
   };
   
   private static final String[] SIMPLE_ARGS = new String[] {
      "bisect", "reset"
   };

   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, FULL_ARGS);
      String resetTo = FULL_ARGS[2];
      if (resetTo == null || resetTo.isEmpty()) {
         return SIMPLE_ARGS;
      }
      return FULL_ARGS;
   }
   
   @Override
   String getJobName() {
      return "End bisect";
   }

}