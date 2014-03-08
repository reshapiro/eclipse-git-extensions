package org.res.gitx.handlers;

import org.res.gix.util.CheckBoxParameter;
import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;

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

   private static final CheckBoxParameter PRUNE = new CheckBoxParameter("Pull", "Prune", 0, true);

   private static final ParameterSet PARAMS = new ParameterSet("Pull", PRUNE);

   private static final String[] ARGS = new String[] {
      null
   };

   private static final String[] WITH_PRUNE = new String[] {
      "pull",
      "-p"
   };
   private static final String[] SANS_PRUNE = new String[] {
      "pull"
   };

   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      String prune = PARAMS.getParameterValue(PRUNE);
      if (Boolean.parseBoolean(prune)) {
         return WITH_PRUNE;
      } else {
         return SANS_PRUNE;
      }
   }

   @Override
   String getJobName() {
      return "Pull from upstream";
   }
}