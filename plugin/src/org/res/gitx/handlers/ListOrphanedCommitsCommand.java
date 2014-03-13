package org.res.gitx.handlers;

import java.util.List;

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
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Finding orphan commits is potentially slow. Proceed?");

   @Override
   void getArgs(List<String> args) throws
         PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      args.add("fsck");
      args.add("--lost-found");
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