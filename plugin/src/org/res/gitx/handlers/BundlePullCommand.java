package org.res.gitx.handlers;

import org.res.gix.util.FileParameter;
import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;

/**
 * Fetch commits from a bundle file
 * 
 * @author reshapiro
 * 
 */
public class BundlePullCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "pull", null
   };
   
   private static final ParameterSet PARAMS = 
         new ParameterSet("Bundle file", new FileParameter("Pull from bundle file", 1, true));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Pull from bundle";
   }
}