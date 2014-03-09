package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameter;

/**
 * Git checkout, typically faster than EGit for large repositories
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class SwitchCommand
      extends GitCommandHandler {

   private static final String[] ARGS = new String[] {
      "checkout", null
   };

   private static final ParameterSet PARAMS = 
         new ParameterSet("Switch",  new RefParameter(1));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS, ARGS);
     return ARGS;
     
   }

   @Override
   String getJobName() {
      return "Switch branch";
   }
}