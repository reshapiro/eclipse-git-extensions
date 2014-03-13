package org.res.gitx.handlers;

import org.res.gitx.parameter.FileParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Fetch commits from a bundle file
 * 
 * @author reshapiro
 * 
 */
public class BundlePullCommand
      extends GitCommandHandler {
   
   private static final Parameter FILE = new FileParameter("Pull from bundle file", true);
   private static final ParameterSet PARAMS = new ParameterSet("Bundle file", FILE);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      addArg("pull");
      addArg(PARAMS, FILE);
   }

   @Override
   String getJobName() {
      return "Pull from bundle";
   }
}