package org.res.gitx.handlers;

import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.Parameter;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;

/**
 * Execute the Git operation that will show the Git SHA for a given SVN revision.
 * 
 * @author reshapiro
 * 
 */
public class Svn2GitCommand
      extends GitCommandHandler {
   
   private static final Parameter SVN_REV_PARAM = new Parameter("SVN rev", 2, true);
   
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
         ARGS[SVN_REV_PARAM.getIndex()] = "r" + rev;
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