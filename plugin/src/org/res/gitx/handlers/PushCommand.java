package org.res.gitx.handlers;



/**
 * Simple push to upstream.  This is here because it can sometimes be considerably faster
 * than the JGit version. In OS X it can also access the keychain.
 * 
 * @author reshapiro
 * 
 */
public class PushCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "push"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Push to upstream";
   }
}