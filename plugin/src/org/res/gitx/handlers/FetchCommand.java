package org.res.gitx.handlers;

import org.res.gix.util.CheckBoxParameter;
import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;


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
   
   private static final CheckBoxParameter PRUNE = new CheckBoxParameter("Fetch", "Prune", 0, true);

   private static final ParameterSet PARAMS = new ParameterSet("Fetch", PRUNE);

   private static final String[] ARGS = new String[] {
      null
   };

   private static final String[] WITH_PRUNE = new String[] {
      "fetch",
      "-p"
   };
   private static final String[] SANS_PRUNE = new String[] {
      "fetch"
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
      return "Fetch from upstream";
   }


   @Override
   boolean touch() {
      return false;
   }
}