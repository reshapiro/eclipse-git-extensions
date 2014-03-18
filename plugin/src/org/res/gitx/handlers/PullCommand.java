package org.res.gitx.handlers;


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

   @Override
   void getArgs() {
      append("pull");
   }

   @Override
   String getJobName() {
      return "Pull from upstream";
   }
}