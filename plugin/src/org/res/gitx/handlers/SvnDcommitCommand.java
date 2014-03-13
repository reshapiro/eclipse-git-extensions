package org.res.gitx.handlers;

import java.util.List;


/**
 * Execute the Git operation that will 'push' any new commits on the active
 * branch to the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnDcommitCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs(List<String> args) {
      args.add("svn");
      args.add("dcommit");
   }

   @Override
   String getJobName() {
      return "'Push' new commits to SVN";
   }
}