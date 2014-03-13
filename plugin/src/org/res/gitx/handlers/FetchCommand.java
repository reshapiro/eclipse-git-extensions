package org.res.gitx.handlers;

import java.util.List;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;


/**
 * Simple fetch from upstream.  This is here because it can sometimes be considerably faster
 * than the JGit version. In OS X it can also access the keychain. In addition it supports
 * optional pruning.
 * 
 * @author reshapiro
 * 
 */
public class FetchCommand
      extends GitCommandHandler {
   
   private static final Parameter PRUNE = new CheckBoxParameter("Fetch", "Prune", true);

   private static final ParameterSet PARAMS = new ParameterSet("Fetch", PRUNE);

   @Override
   void getArgs(List<String> args)
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS);
      args.add("fetch");
      if (PARAMS.getBooleanParameterValue(PRUNE)) {
         args.add("-p");
      }
   }
   

   @Override
   String getJobName() {
      return "Fetch from upstream";
   }


   @Override
   boolean touch() {
      return false;
   }
}