package org.res.gitx.handlers;

import java.util.List;



/**
 * Simple push to upstream.  This is here because it can sometimes be considerably faster
 * than the JGit version. In OS X it can also access the keychain.
 * 
 * @author reshapiro
 * 
 */
public class PushCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs(List<String> args) {
      args.add("push");
   }

   @Override
   String getJobName() {
      return "Push to upstream";
   }
}