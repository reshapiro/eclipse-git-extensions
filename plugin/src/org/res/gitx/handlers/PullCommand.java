package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Simple pull from upstream. This is here because it can sometimes be
 * considerably faster than the JGit version. In OS X it can also access the
 * keychain. In addition it supports optional pruning.
 * 
 * @author reshapiro
 * 
 */
public class PullCommand
      extends GitCommandHandler {

   private static final Parameter PRUNE = new CheckBoxParameter("Pull", "Prune", true);

   private static final ParameterSet PARAMS = new ParameterSet("Pull", PRUNE);

   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      args.add("pull");
      if (PARAMS.getBooleanParameterValue(PRUNE)) {
         args.add("-p");
      }
   }

   @Override
   String getJobName() {
      return "Pull from upstream";
   }
}