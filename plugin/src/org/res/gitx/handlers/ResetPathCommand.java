package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameterWithDefault;
import org.res.gitx.util.Resolver;

/**
 * Git checkout, typically faster than EGit for large repositories
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class ResetPathCommand
      extends GitCommandHandler {

   private static final Parameter REF = new RefParameterWithDefault("HEAD", "Reset to");
   
   private static final ParameterSet PARAMS = new ParameterSet("Reset selected path", REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     String relativePath = Resolver.resolveVariable("git_repo_relative_path");
     if (relativePath == null || relativePath.isEmpty()) {
        throw new MissingRequiredParameterException("No path selected");
     }
     
     append("reset").append(PARAMS, REF). append("--", relativePath);
     
   }

   @Override
   String getJobName() {
      return "Reset Selected Path";
   }
}