package org.res.gitx.handlers;

import java.util.List;

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

   private static final RefParameter BAD = new RefParameter("Bad (optional)", false);
   private static final RefParameter GOOD = new RefParameter("Good(optional)", false);
   private static final ParameterGroup GROUP = new RefPair(BAD, GOOD);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Start Bisect", GROUP);

   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      args.add("bisect");
      args.add("start");
      
      String bad = PARAMETERS.getParameterValue(BAD);
      String good = PARAMETERS.getParameterValue(GOOD);
      if (bad != null) {
         args.add(bad);
         if (good != null) {
            args.add(good);
         }
      }
   }

   @Override
   String getJobName() {
      return "Start bisect";
   }

}