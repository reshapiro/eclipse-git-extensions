package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will display the git gui
 * 
 * @author reshapiro
 * 
 */
public class GuiCommand
      extends GitCommandHandler {
   

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      append("gui");
      
   }

   @Override
   String getJobName() {
      return "Git Gui";
   }
}