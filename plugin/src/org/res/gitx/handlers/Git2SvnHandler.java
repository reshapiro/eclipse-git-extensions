package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameter;

/**
 * Execute the Git operation that will find the SVN revision for a given Git Commit
 * 
 * @author reshapiro
 * 
 */
public class Git2SvnHandler
      extends GitCommandHandler {
   
   private static Parameter REF =  new RefParameter();
   private static final ParameterSet PARAMS = new ParameterSet("Show SVN revision for SHA",REF);
   
   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      args.add("svn");
      args.add("find-rev");
      args.add(PARAMS.getParameterValue(REF));
   }

   @Override
   String getJobName() {
      return "Display SVN revision for a given SHA";
   }

   @Override
   boolean touch() {
      return false;
   }
}