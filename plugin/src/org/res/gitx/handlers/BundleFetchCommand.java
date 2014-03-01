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
public class BundleFetchCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "fetch", null
   };

   private static final ParameterSet PARAMS = 
         new ParameterSet("Bundle file", new FileParameter("Fetch from bundle file", 1, true));
   
   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Fetch from bundle";
   }
}