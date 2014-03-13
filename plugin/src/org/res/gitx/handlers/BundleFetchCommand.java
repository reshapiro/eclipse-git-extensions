package org.res.gitx.handlers;

import java.util.List;

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
public class BundleFetchCommand
      extends GitCommandHandler {
   
   private static final Parameter FILE = new FileParameter("Fetch from bundle file", true);
   private static final ParameterSet PARAMS = new ParameterSet("Bundle file", FILE);
   
   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      args.add("fetch");
      args.add(PARAMS.getParameterValue(FILE));
   }

   @Override
   String getJobName() {
      return "Fetch from bundle";
   }
}