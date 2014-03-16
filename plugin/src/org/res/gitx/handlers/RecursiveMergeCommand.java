package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
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
   
   private static final Parameter FF_ONLY = new CheckBoxParameter("Merge", "ff-only", false);
   private static final Parameter NO_FF = new CheckBoxParameter("Merge", "no-ff", false);
   private static final Parameter SQUASH = new CheckBoxParameter("Merge", "squash", false);
   private static final Parameter REF = new RefParameter("merge");

   private static final ParameterSet PARAMS = new ParameterSet("Merge", FF_ONLY, NO_FF, SQUASH, REF);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      append("merge", "-s", "recursive");
      
      /* TODO at most one of these should be true */
      if (PARAMS.getBooleanParameterValue(FF_ONLY)) {
         append("--ff-only");
      }
      
      if (PARAMS.getBooleanParameterValue(NO_FF)) {
         append("--no-ff");
      }
      
      if (PARAMS.getBooleanParameterValue(SQUASH)) {
         append("--squash");
      }
      append(PARAMS, REF);
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