package org.res.gitx.handlers;

/**
 * Execute the Git operation that will rebase any newly fetched commits.
 * 
 * @author reshapiro
 * 
 */
public class SvnRebaseLocalCommand
      extends GitCommandHandler {

   @Override
   void getArgs() {
      append("svn", "rebase", "--local");
   }

   @Override
   String getJobName() {
      return "'Rebase' from fectched SVN commits";
   }
}