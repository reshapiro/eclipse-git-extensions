package org.res.gitx.handlers;

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
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      addArgs("bisect", "replay");
      addArg(PARAMETERS, FILE);
   }
   
   @Override
   String getJobName() {
      return "Replay bisect log";
   }

}