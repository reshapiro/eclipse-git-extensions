   package org.res.gitx.handlers;

import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;
import org.res.gix.util.RefParameter;


/**
 * List files which differ between two branches.
 * 
 * @author reshapiro
 * 
 */
public class ListChangedFiles
      extends GitCommandHandler {
   
   private static final RefParameter Ref1 = new RefParameter("Ref 1", 2);
   private static final RefParameter Ref2 = new RefParameter("Ref 2", 3);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("List files with differences", Ref1,  Ref2);

   
   private static final String[] ARGS = new String[] {
      "diff", "--stat", null, null
   };
   

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      Ref1.setDefaultReference("HEAD");
      promptForParameters(PARAMETERS, ARGS);
      
      return ARGS;
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