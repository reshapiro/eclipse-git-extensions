package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RadioButtonParameter;
import org.res.gitx.parameter.RefParameterWithDefault;

/**
 * The --keep variant of git reset
 * 
 * @author reshapiro
 * 
 */
public class ResetCommand
      extends GitCommandHandler {

   private static final ParameterSet CONFIRM = new ParameterSet("Hard reset will modify working tree. Proceed?");
   
   private static final String[] MODE_OPTIONS = {
      "soft", "mixed", "hard", "keep", "merge"
   };
   
   private static final Parameter MODE_OPTION = new RadioButtonParameter("Reset", "mode", MODE_OPTIONS, "mixed");
   
   private static final Parameter REF = new RefParameterWithDefault("HEAD", "Reset to");
   
   private static final ParameterSet PARAMS = new ParameterSet("Reset", MODE_OPTION, REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     String mode = PARAMS.getParameterValue(MODE_OPTION);
     if (mode.equals("hard")) {
        promptForParameters(CONFIRM);
     }
     append("reset", "--" + mode).append(PARAMS, REF);
   }

   @Override
   String getJobName() {
      return "Reset";
   }
}