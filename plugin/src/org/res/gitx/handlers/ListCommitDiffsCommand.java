   package org.res.gitx.handlers;

import org.res.gix.util.CheckBoxParameter;
import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;
import org.res.gix.util.RefParameter;


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
   
   private static final ParameterSet PARAMETERS = new ParameterSet("List commits in Ref 1 but not Ref 2",
                                                                   new CheckBoxParameter("oneline", "Show brief display", 3),
                                                                   new RefParameter("Ref 1", 1),
                                                                   new RefParameter("Ref 2", 2));

   
   private static final String[] ARGS = new String[] {
      "log", null, null, null
   };
   
   private static final String[] SIMPLE_ARGS = new String[3];

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, ARGS);
      
      ARGS[2] = "^" + ARGS[2];
      
      boolean useOnline = ARGS[3] != null && Boolean.valueOf(ARGS[3]);
      if (useOnline) {
         ARGS[3] = "--oneline";
         return ARGS;
      } else {
         System.arraycopy(ARGS, 0, SIMPLE_ARGS, 0, SIMPLE_ARGS.length);
         return SIMPLE_ARGS;
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