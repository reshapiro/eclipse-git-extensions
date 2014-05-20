package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will clear all stashes
 * 
 * @author reshapiro
 * 
 */
public class ClearStashCommand
      extends GitCommandHandler {
   
   private static final ParameterSet CONFIRM = new ParameterSet("Remove all stashes?");
   

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(CONFIRM);
      append("stash").append("clear");
      
   }

   @Override
   String getJobName() {
      return "Clear Stashes";
   }
}