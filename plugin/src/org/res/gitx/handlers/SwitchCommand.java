package org.res.gitx.handlers;

import java.util.ArrayList;
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

   private static final String[] ARGS = new String[] {
      "checkout", null, null
   };

   private static final int PATH_INDEX = 1;
   private static final int REF_INDEX = 2;
   
   private static final Parameter PATH = new CheckBoxParameter("Selected path", "selected path", PATH_INDEX, false);
   
   private static final ParameterSet PARAMS = new ParameterSet("Switch", PATH, new RefParameter(REF_INDEX));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS, ARGS);
     List<String> actualArgs = new ArrayList<>(3);
     actualArgs.add(ARGS[0]);
     actualArgs.add(ARGS[REF_INDEX]);
     boolean usePath = ARGS[PATH_INDEX] != null && Boolean.valueOf(ARGS[PATH_INDEX]);
     String relativePath = usePath ? Resolver.resolveVariable("git_repo_relative_path") : null;
     if (relativePath != null && !relativePath.isEmpty()) {
        actualArgs.add("--");
        actualArgs.add(relativePath);
     }
     String[] argsArray = new String[actualArgs.size()];
     actualArgs.toArray(argsArray);
     return argsArray;
     
   }

   @Override
   String getJobName() {
      return "Switch branch";
   }
}