package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameterWithDefault;

/**
 * The --merge variant of git reset
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class ResetMergeCommand
      extends GitCommandHandler {

   private static final Parameter REF = new RefParameterWithDefault("HEAD", "Reset to");
   
   private static final ParameterSet PARAMS = new ParameterSet("Reset -- merge", REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     append("reset", "--merge").append(PARAMS, REF);
     
     
   }

   @Override
   String getJobName() {
      return "Reset --merge";
   }
}