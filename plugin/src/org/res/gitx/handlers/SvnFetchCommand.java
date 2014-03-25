package org.res.gitx.handlers;

/**
 * Execute the Git operation that will 'fetch' any new commits from SVN on any
 * branch to the corresponding Git branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnFetchCommand
      extends GitCommandHandler {

   @Override
   void getArgs() {
      append("svn", "fetch");
   }

   @Override
   String getJobName() {
      return "'Fetch' new commits from SVN";
   }
}