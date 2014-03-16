package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RadioButtonParameter;
import org.res.gitx.parameter.RefParameter;


/**
 * Simple fetch from upstream.  This is here because it can sometimes be considerably faster
 * than the JGit version. In OS X it can also access the keychain. In addition it supports
 * optional pruning.
 * 
 * @author reshapiro
 * 
 */
public class RecursiveMergeCommand
      extends GitCommandHandler {
   
   private static final Parameter REF = new RefParameter("merge");
   
   private static final String[] OPTIONS = {
      "ff", "ff-only", "no-ff", "squash"
   };
   
   private static final Parameter FF_OPTION = new RadioButtonParameter("Merge", "Fast Forward", OPTIONS, OPTIONS[0]);
   private static final ParameterSet PARAMS = new ParameterSet("Merge", FF_OPTION, REF);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      String ffOption = "--" + PARAMS.getParameterValue(FF_OPTION);
      append("merge", "-s", "recursive", ffOption).append(PARAMS, REF);
   }
   

   @Override
   String getJobName() {
      return "Recursive merge";
   }


   @Override
   boolean touch() {
      return true;
   }
}