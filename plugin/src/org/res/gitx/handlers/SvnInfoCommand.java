package org.res.gitx.handlers;


/**
 * Execute the Git operation that will show information about the SVN repository.
 * 
 * @author reshapiro
 * 
 */
public class SvnInfoCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "svn", "info"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Show the status of the bridged SVN repo";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}