package org.res.gitx.handlers;

import java.util.List;

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
public class SwitchCommand
      extends GitCommandHandler {

   private static final Parameter PATH = new CheckBoxParameter("Selected path only", "selected path", false);
   private static final Parameter REF = new RefParameter();
   
   private static final ParameterSet PARAMS = new ParameterSet("Switch", PATH, REF);

   @Override
   void getArgs(List<String> args) 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     args.add("checkout");
     
     args.add(PARAMS.getParameterValue(REF));
     
     boolean usePath = PARAMS.getBooleanParameterValue(PATH);
     String relativePath = usePath ? Resolver.resolveVariable("git_repo_relative_path") : null;
     if (relativePath != null && !relativePath.isEmpty()) {
        args.add("--");
        args.add(relativePath);
     }
   }

   @Override
   String getJobName() {
      return "Switch branch";
   }
}