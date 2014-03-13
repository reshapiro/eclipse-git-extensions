package org.res.gitx.handlers;



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
   void getArgs() {
      addArgs("svn", "dcommit");
   }

   @Override
   String getJobName() {
      return "'Push' new commits to SVN";
   }
}