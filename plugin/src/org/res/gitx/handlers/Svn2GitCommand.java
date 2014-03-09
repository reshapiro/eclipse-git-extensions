package org.res.gitx.handlers;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will show the Git SHA for a given SVN revision.
 * 
 * @author reshapiro
 * 
 */
public class Svn2GitCommand
      extends GitCommandHandler {
   
   private static final int SVN_REV_PARAM_INDEX = 2;
   private static final Parameter SVN_REV_PARAM = new Parameter("SVN rev", SVN_REV_PARAM_INDEX, true);
   
   private static final ParameterSet PARAMS = 
         new ParameterSet("Show Git commit for SVN revison", SVN_REV_PARAM);
   
   private static final String[] ARGS = new String[] {
      "svn", "find-rev", null
   };

   @Override
   String[] getArgs()
            throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      String rev = PARAMS.getParameterValue(SVN_REV_PARAM);
      if (!rev.startsWith("r")) {
         ARGS[SVN_REV_PARAM_INDEX] = "r" + rev;
      }
      
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Display the SHA for a given SVN revision";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}