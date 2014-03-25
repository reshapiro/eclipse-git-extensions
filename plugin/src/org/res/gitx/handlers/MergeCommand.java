package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
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
   
   private static final Parameter REF = new RefParameter("Merge from");
   
   private static final String[] FF_OPTIONS = {
      "ff", "ff-only", "no-ff", "squash"
   };
   
   private static final String[] STRATEGY_OPTIONS = {
      "Git default", "resolve (Egit default)", "recursive", "octopus","ours","subtree"
   };
   
   private static final String[] THEIRS_OURS = {
      "default", "ours", "theirs"
   };
   
   private static final Parameter RECURSIVE_IGNORE_SPACES = new CheckBoxParameter("recursive", "ignore whitespace");
   private static final Parameter RECURSIVE_PATIENCE = new CheckBoxParameter("recursive", "patience");
   private static final Parameter FF_OPTION = new RadioButtonParameter("Merge", "Fast Forward", FF_OPTIONS, FF_OPTIONS[0]);
   private static final Parameter STRATEGY_OPTION = new RadioButtonParameter("Merge", "Strategy", STRATEGY_OPTIONS, STRATEGY_OPTIONS[0]);
   private static final Parameter THEIRS_OURS_OPTION = new RadioButtonParameter("Merge", "Recursive", THEIRS_OURS, THEIRS_OURS[0]);
   private static final ParameterSet PARAMS = new ParameterSet("Merge", STRATEGY_OPTION, FF_OPTION, THEIRS_OURS_OPTION, RECURSIVE_PATIENCE,
                                                               RECURSIVE_IGNORE_SPACES, REF);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      String ffOption = "--" + PARAMS.getParameterValue(FF_OPTION);
      String strategyOption = PARAMS.getParameterValue(STRATEGY_OPTION);
      String theirsOurs = PARAMS.getParameterValue(THEIRS_OURS_OPTION);
      
      append("merge");
      if (!strategyOption.equals(STRATEGY_OPTIONS[0])) {
         append("-s").append(strategyOption);
      }
      
      if (strategyOption.equals("recursive") || strategyOption.equals(STRATEGY_OPTIONS[0])) {
         if (!theirsOurs.equals(THEIRS_OURS[0])) {
            append("-X" + theirsOurs);
         }
         if (PARAMS.getBooleanParameterValue(RECURSIVE_PATIENCE)) {
            append("-Xpatience");
         }
         if (PARAMS.getBooleanParameterValue(RECURSIVE_IGNORE_SPACES)) {
            append("-Xignore-all-space");
         }
         /* TODO: other recursive white-space options: ignore-space-change,  ignore-space-at-eol */
      }
      append(ffOption).append(PARAMS, REF);
   }
   

   @Override
   String getJobName() {
      return "Merge";
   }
}