package egitex.handlers;

import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;
import egit_ex.util.RefParameter;


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
                                                                   new RefParameter("Ref 1", 1),
                                                                   new RefParameter("Ref 2", 2));

   
   private static final String[] ARGS = new String[] {
      "log", null, null, "--oneline"
   };

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, ARGS);
      ARGS[2] = "^" + ARGS[2];
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