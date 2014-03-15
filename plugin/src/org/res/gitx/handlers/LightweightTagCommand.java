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

   private static final Parameter NAME = new Parameter("Name", true);
   private static final Parameter REF = new RefParameter("Tag to");
   
   private static final ParameterSet PARAMS = 
         new ParameterSet("Lightweight Tag", NAME , REF);

   @Override
   void getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
     promptForParameters(PARAMS);
     addArg("tag");
     addArgs(PARAMS, NAME, REF);
     
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