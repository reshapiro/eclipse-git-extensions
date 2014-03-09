package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.ParameterGroup;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefPair;
import org.res.gitx.parameter.RefParameter;
import org.res.gitx.parameter.SaveFileParameter;

/**
 * Fetch commits from a bundle file
 * 
 * @author reshapiro
 * 
 */
public class BundleCreateCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "bundle", "create",  null, null, null
   };
   
   private static final String[] ACTUAL_ARGS = new String[] {
      "bundle", "create", null, null
   };
   
   private static final RefParameter START = new RefParameter("Start commit", 3);
   private static final RefParameter END = new RefParameter("End commit", 4);
   private static final ParameterGroup GROUP = new RefPair(START, END);
   
   private static final ParameterSet PARAMS = new ParameterSet("Bundle Spec",
                                                               new SaveFileParameter("Save to bundle file", 2, true), GROUP);

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
         ACTUAL_ARGS[2] = ARGS[2];
         ACTUAL_ARGS[3]  = ARGS[3] + ".." + ARGS[4];
         return ACTUAL_ARGS;
   }

   @Override
   String getJobName() {
      return "Create bundle";
   }

   @Override
   boolean touch() {
      return false;
   }
   
}