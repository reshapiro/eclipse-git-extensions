package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameterWithDefault;

/**
 * Git checkout, typically faster than EGit for large repositories
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class ResetKeepCommand
      extends GitCommandHandler {

   private static final Parameter REF = new RefParameterWithDefault("HEAD", "Reset to");
   
   private static final ParameterSet PARAMS = new ParameterSet("Reset -- keep", REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     addArg("reset");
     addArg("--keep");
     addArg(PARAMS, REF);
     
     
   }

   @Override
   String getJobName() {
      return "Reset --keep";
   }
}