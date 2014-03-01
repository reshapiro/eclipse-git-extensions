package org.res.gitx.handlers;

import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.Parameter;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;

/**
 * Execute the Git operation that will run a command to determine good v bad in a bisect.
 * Later it will be possible to log to a file.
 * 
 * @author reshapiro
 * 
 */
public class BisectRunCommand
      extends GitCommandHandler {
   
   private static final Parameter CMD_ARGS_PARAM = new Parameter("Command", 0, true);
   private static final ParameterSet PARAMETERS = new ParameterSet("Bisect Command args", CMD_ARGS_PARAM);
   
   private static final String[] CMD_ARGS = new String[] {
      null
   };

   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, CMD_ARGS);
      String parameterValue = PARAMETERS.getParameterValue(CMD_ARGS_PARAM);
      
      String[] cmd = parameterValue.split(" ");
      String[] args = new String[cmd.length + 2];
      args[0] = "bisect";
      args[1] = "run";
      System.arraycopy(cmd, 0, args, 2, cmd.length);
      return args;
   }
   
   @Override
   String getJobName() {
      return "Run bisect command";
   }

}