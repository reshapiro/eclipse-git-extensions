package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameterWithDefault;

/**
 * Reset the selected path. EGit doesn't support this if the path is a directory
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
      List<String> paths = getSelection();
      if (paths.isEmpty()) {
         throw new MissingRequiredParameterException("No path selected");
      }
      append("reset").append(PARAMS, REF).append("--").append(paths);
   }

   @Override
   String getJobName() {
      return "Reset Selected Path";
   }
}