package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameterWithDefault;

/**
 * Git checkout, typically faster than EGit for large repositories
 * 
 * @author reshapiro
 * 
 */
public class CheckoutCommand
      extends GitCommandHandler {

   private static final Parameter REF = new RefParameterWithDefault("HEAD", "Checkout");
   
   private static final ParameterSet PARAMS = new ParameterSet("Checkout", REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     append("checkout").append(PARAMS, REF);
   }

   @Override
   String getJobName() {
      return "Checkout";
   }
}