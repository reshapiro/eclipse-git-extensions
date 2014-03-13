package org.res.gitx.handlers;

import java.util.List;


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
   void getArgs(List<String> args) {
      args.add("svn");
      args.add("rebase");
   }

   @Override
   String getJobName() {
      return "'Pull' new commits from SVN";
   }
}