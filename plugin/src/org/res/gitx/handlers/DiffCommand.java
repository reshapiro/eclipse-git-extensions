   package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterGroup;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefPair;
import org.res.gitx.parameter.RefParameter;
import org.res.gitx.util.Resolver;


/**
 * Diff two references
 * 
 * 
 * @author reshapiro
 * 
 */
public class DiffCommand
      extends GitCommandHandler {
   private static final Parameter IGNORE_WHITESPACE = new CheckBoxParameter("Ignore whitespace", "Ignore whitespace", true);
   private static final Parameter PATH = new CheckBoxParameter("Selected path only", "selected path", false);
   private static final RefParameter BASE = new RefParameter("Baseline");
   private static final RefParameter REF = new RefParameter("Ref");
   private static final ParameterGroup GROUP = new RefPair(BASE, REF);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Diff", IGNORE_WHITESPACE,  PATH, GROUP);

   
   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      
      BASE.setDefaultReference("HEAD");
      promptForParameters(PARAMETERS);
      addArg("diff");
      
      if (PARAMETERS.getBooleanParameterValue(IGNORE_WHITESPACE)) {
         addArg("-w");
      }
      addArgs(PARAMETERS, BASE, REF);
      
      boolean usePath = PARAMETERS.getBooleanParameterValue(PATH);
      String relativePath = usePath ? Resolver.resolveVariable("git_repo_relative_path") : null;
      if (relativePath != null && !relativePath.isEmpty()) {
         addArgs("--", relativePath);
      }
   }

   @Override
   String getJobName() {
      return "Diff";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}