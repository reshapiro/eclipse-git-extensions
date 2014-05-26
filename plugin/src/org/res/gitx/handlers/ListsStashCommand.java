package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will list all stashes
 * 
 * @author reshapiro
 * 
 */
public class ListsStashCommand
      extends GitCommandHandler {
   

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      append("stash").append("list");
      
   }

   @Override
   String getJobName() {
      return "List Stashes";
   }
}