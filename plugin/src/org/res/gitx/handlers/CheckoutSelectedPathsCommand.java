package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameterNotCurrent;

/**
 * Git checkout, typically faster than EGit for large repositories
 * 
 * @author reshapiro
 * 
 */
public class CheckoutSelectedPathsCommand
      extends GitCommandHandler {

   private static final Parameter REF = new RefParameterNotCurrent("Checkout paths");

   private static final ParameterSet PARAMS = new ParameterSet("Checkout paths", REF);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      List<String> paths = getSelection();
      if (paths.isEmpty()) {
         throw new MissingRequiredParameterException("No path selected");
      }
      append("checkout").append(PARAMS, REF).append("--").append(paths);
      
      
   }

   @Override
   String getJobName() {
      return "Checkout selected paths";
   }
}