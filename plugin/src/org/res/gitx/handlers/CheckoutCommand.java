package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameter;
import org.res.gitx.util.Resolver;

/**
 * Git checkout, typically faster than EGit for large repositories
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class CheckoutCommand
      extends GitCommandHandler {

   private static final Parameter PATH = new CheckBoxParameter("Selected path only", "selected path", false);
   private static final Parameter REF = new RefParameter("HEAD");
   
   private static final ParameterSet PARAMS = new ParameterSet("Checkout", PATH, REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     addArg("checkout");
     
     addArg(PARAMS, REF);
     
     boolean usePath = PARAMS.getBooleanParameterValue(PATH);
     String relativePath = usePath ? Resolver.resolveVariable("git_repo_relative_path") : null;
     if (relativePath != null && !relativePath.isEmpty()) {
        addArgs("--", relativePath);
     }
   }

   @Override
   String getJobName() {
      return "Checkout";
   }
}