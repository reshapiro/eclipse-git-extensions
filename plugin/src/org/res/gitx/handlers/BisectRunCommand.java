package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will run a command to determine good v bad in a bisect.
 * Later it will be possible to log to a file.
 * 
 * @author reshapiro
 * 
 */
public class BisectRunCommand
      extends GitCommandHandler {
   
   private static final Parameter CMD_ARGS_PARAM = new Parameter("Command", true);
   private static final ParameterSet PARAMETERS = new ParameterSet("Bisect Command args", CMD_ARGS_PARAM);
   
   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      args.add("bisect");
      args.add("run");
      
      String parameterValue = PARAMETERS.getParameterValue(CMD_ARGS_PARAM);
      String[] cmd = parameterValue.split(" ");
      for (String op : cmd) {
         args.add(op);
      }
   }
   
   @Override
   String getJobName() {
      return "Run bisect command";
   }

}