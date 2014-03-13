package org.res.gitx.handlers;



/**
 * Execute the Git operation that will 'pull' and rebase any new commits on the
 * active branch from the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnRebaseLocalCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs() {
      addArgs("svn", "rebase", "--local");
   }

   @Override
   String getJobName() {
      return "'Rebase' from fectched SVN commits";
   }
}