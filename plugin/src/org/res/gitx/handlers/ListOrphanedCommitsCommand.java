package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will list orphaned commits
 * 
 * @author reshapiro
 * 
 */
public class ListOrphanedCommitsCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "fsck", "--lost-found"
   };
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Finding orphan commits is potentially slow. Proceed?");

   @Override
   String[] getArgs() throws
         PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, ARGS);
      return ARGS;
   }

   @Override
   String getJobName() {
      return "List orphan commits";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}