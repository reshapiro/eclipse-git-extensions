package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RadioButtonParameter;
import org.res.gitx.parameter.RefParameter;


/**
 * Merge with strategy and FF options
 * 
 * @author reshapiro
 * 
 */
public class MergeCommand
      extends GitCommandHandler {
   
   private static final Parameter REF = new RefParameter("merge");
   
   private static final String[] FF_OPTIONS = {
      "ff", "ff-only", "no-ff", "squash"
   };
   
   private static final String[] STRATEGY_OPTIONS = {
      "default", "resolve", "recursive", "octopus","ours","subtree"
   };
   
   private static final Parameter FF_OPTION = new RadioButtonParameter("Merge", "Fast Forward", FF_OPTIONS, FF_OPTIONS[0]);
   private static final Parameter STRATEGY_OPTION = new RadioButtonParameter("Merge", "Strategy", STRATEGY_OPTIONS, STRATEGY_OPTIONS[0]);
   private static final ParameterSet PARAMS = new ParameterSet("Merge", FF_OPTION, STRATEGY_OPTION, REF);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      String ffOption = "--" + PARAMS.getParameterValue(FF_OPTION);
      String strategyOption = PARAMS.getParameterValue(STRATEGY_OPTION);
      append("merge");
      if (!strategyOption.equals(STRATEGY_OPTIONS[0])) {
         append("-s").append(strategyOption);
      }
      append(ffOption).append(PARAMS, REF);
      /* TODO: recursive options like: ours, theirs, patience, ignore-space-change, ignore-all-space, ignore-space-at-eol */
   }
   

   @Override
   String getJobName() {
      return "Merge";
   }
}