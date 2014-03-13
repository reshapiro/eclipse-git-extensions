package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.FileParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that replays a bisect from a saved log.
 * 
 * @author reshapiro
 * 
 */
public class BisectReplayCommand
      extends GitCommandHandler {
   
   private static final Parameter FILE = new FileParameter("Log file", true);
   private final ParameterSet PARAMETERS = new ParameterSet("Replay bisect", FILE);
   
   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      args.add("bisect");
      args.add("replay");
      args.add(PARAMETERS.getParameterValue(FILE));
   }
   
   @Override
   String getJobName() {
      return "Replay bisect log";
   }

}