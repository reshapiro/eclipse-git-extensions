package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameter;

/**
 * Execute the Git operation that will find the SVN revision for a given Git
 * Commit
 * 
 * @author reshapiro
 * 
 */
public class LightweightTagCommand
      extends GitCommandHandler {

   private static final String[] ARGS = new String[] {
      "tag", null, null
   };

   private static final ParameterSet PARAMS = 
         new ParameterSet("Lightweight Tag", new Parameter("Name", 1, true), new RefParameter(2));

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS, ARGS);
     return ARGS;
     
   }

   @Override
   String getJobName() {
      return "Create Lightweight Tag";
   }

   @Override
   boolean touch() {
      return false;
   }
}