   package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterGroup;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefPair;
import org.res.gitx.parameter.RefParameter;


/**
 * List commits in one ref but not another
 * 
 * TODO: make oneline optional
 * 
 * @author reshapiro
 * 
 */
public class ListCommitDiffsCommand
      extends GitCommandHandler {
   
   private static final RefParameter Ref1 = new RefParameter("Ref 1");
   private static final RefParameter Ref2 = new RefParameter("Ref 2");
   private static final ParameterGroup GROUP = new RefPair(Ref1, Ref2);
   private static final Parameter ONE_LINE = new CheckBoxParameter("oneline", "Show brief display");
   
   private static final ParameterSet PARAMETERS = new ParameterSet("List commits in Ref 1 but not Ref 2", ONE_LINE ,  GROUP);

   
   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      Ref1.setDefaultReference("HEAD");
      promptForParameters(PARAMETERS);
      addArgs("log");
      addArg(PARAMETERS, Ref1);
      addArg("^" + PARAMETERS.getParameterValue(Ref2));
      
      
      
      boolean useOnline = PARAMETERS.getBooleanParameterValue(ONE_LINE);
      if (useOnline) {
         addArg("--oneline");
      }
      
   }

   @Override
   String getJobName() {
      return "List commits in one branch but not another";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}