package org.res.gitx.handlers;

import java.util.List;

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
   
   private static final Parameter SVN_REV_PARAM = new Parameter("SVN rev", true);
   
   private static final ParameterSet PARAMS = new ParameterSet("Show Git commit for SVN revison", SVN_REV_PARAM);
   
   @Override
   void getArgs(List<String> args)
            throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      String rev = PARAMS.getParameterValue(SVN_REV_PARAM);
      if (!rev.startsWith("r")) {
         rev = "r" + rev;
      }
      args.add("svn");
      args.add("find-rev");
      args.add(rev);
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