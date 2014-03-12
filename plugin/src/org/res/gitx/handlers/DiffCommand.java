   package org.res.gitx.handlers;

import java.util.ArrayList;
import java.util.List;

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
   private static final int IGNORE_WHITESPACE_INDEX = 1;
   private static final int PATH_INDEX = 2;
   private static final int BASE_INDEX = 3;
   private static final int REF_INDEX = 4;
   private static final Parameter IGNORE_WHITESPACE = new CheckBoxParameter("Ignore whitespace", "Ignore whitespace", IGNORE_WHITESPACE_INDEX, true);
   private static final Parameter PATH = new CheckBoxParameter("Selected path only", "selected path", PATH_INDEX, false);
   private static final RefParameter BASE = new RefParameter("Baseline", BASE_INDEX);
   private static final RefParameter REF = new RefParameter("Ref", REF_INDEX);
   private static final ParameterGroup GROUP = new RefPair(BASE, REF);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Diff", IGNORE_WHITESPACE,  PATH, GROUP);

   
   private static final String[] ARGS = new String[] {
      "diff", null, null, null, null
   };
   
   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      
      BASE.setDefaultReference("HEAD");
      promptForParameters(PARAMETERS, ARGS);
      
      
      List<String> actualArgs = new ArrayList<>(4);
      actualArgs.add(ARGS[0]);
      
      boolean ignoreWhiteSpace = ARGS[IGNORE_WHITESPACE_INDEX] != null && Boolean.valueOf(ARGS[IGNORE_WHITESPACE_INDEX]);
      if (ignoreWhiteSpace) {
         actualArgs.add("-w");
      }
      actualArgs.add(ARGS[BASE_INDEX]);
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
      return "Diff";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}