package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.ParameterGroup;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefPair;
import org.res.gitx.parameter.RefParameter;

/**
 * Execute the Git operation that will start a bisect.
 * 
 * @author reshapiro
 * 
 */
public class BisectStartCommand
      extends GitCommandHandler {

   private static final RefParameter BAD = new RefParameter("Bad (optional)", 2, false);
   private static final RefParameter GOOD = new RefParameter("Good(optional)", 3, false);
   private static final ParameterGroup GROUP = new RefPair(BAD, GOOD);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Start Bisect", GROUP);

   private static final String[] FULL_ARGS = new String[] {
      "bisect", "start",  null, null
   };
   
   private static final String[] ARGS_WITH_INITIAL_BAD = new String[] {
      "bisect", "start", null
   };
   
   private static final String[] ARGS_SANS_INIT = new String[] {
      "bisect", "start"
   };

   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, FULL_ARGS);
      String bad = FULL_ARGS[2];
      String good = FULL_ARGS[3];
      if (bad == null) {
         return ARGS_SANS_INIT;
      } else {
         if (good == null) {
            ARGS_WITH_INITIAL_BAD[2] = bad;
            return ARGS_WITH_INITIAL_BAD;
         }
      }
      return FULL_ARGS;
   }

   @Override
   String getJobName() {
      return "Start bisect";
   }

}