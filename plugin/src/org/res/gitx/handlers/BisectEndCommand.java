package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
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

   private static final Parameter REF = new RefParameter("Reset to (optional)", false);
   private static final ParameterSet PARAMETERS = new ParameterSet("End Bisect", REF);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("bisect", "reset");

      String resetTo = PARAMETERS.getParameterValue(REF);
      if (resetTo != null && !resetTo.isEmpty()) {
         append(resetTo);
      }
   }

   @Override
   String getJobName() {
      return "End bisect";
   }

}