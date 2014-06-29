package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute a Git operation with arbitrary arguments
 * 
 * @author reshapiro
 * 
 */
public class UserCommand
      extends GitCommandHandler {

   private static final Parameter REFRESH = new CheckBoxParameter("refresh", "Refresh workspace on completion", true);
   private static final Parameter ARGS = new Parameter("args", true);
   private static final ParameterSet PARAMETERS = new ParameterSet("Arguments", ARGS, REFRESH);
   
   private boolean touch;

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      
      promptForParameters(PARAMETERS);
      touch = PARAMETERS.getBooleanParameterValue(REFRESH);
      String args = PARAMETERS.getParameterValue(ARGS);
      append(args.split(" "));
   }

   @Override
   String getJobName() {
      return "Run arbitrary command";
   }

   @Override
   boolean touch() {
      return touch;
   }
}