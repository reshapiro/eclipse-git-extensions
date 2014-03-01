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
   
   private static final String[] ARGS = new String[] {
      "svn", "rebase"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "'Pull' new commits from SVN";
   }
}