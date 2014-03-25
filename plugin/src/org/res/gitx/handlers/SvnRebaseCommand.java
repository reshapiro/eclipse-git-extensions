package org.res.gitx.handlers;

/**
 * Execute the Git operation that will 'pull' and rebase any new commits on the
 * active branch from the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnRebaseCommand
      extends GitCommandHandler {

   @Override
   void getArgs() {
      append("svn", "rebase");
   }

   @Override
   String getJobName() {
      return "'Pull' new commits from SVN";
   }
}