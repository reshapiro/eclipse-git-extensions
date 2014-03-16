package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
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
   
   private static final RefParameter START = new RefParameter("Start commit");
   private static final RefParameter END = new RefParameter("End commit");
   private static final ParameterGroup GROUP = new RefPair(START, END);
   private static final Parameter FILE = new SaveFileParameter("Save to bundle file", true);
   
   private static final ParameterSet PARAMS = new ParameterSet("Bundle Spec", FILE, GROUP);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      append("bundle", "create").append(PARAMS, FILE).append(PARAMS.getParameterValue(START) + ".." + PARAMS.getParameterValue(END));
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