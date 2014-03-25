package org.res.gitx.handlers;

/**
 * Execute the Git operation that will gc svn
 * 
 * @author reshapiro
 * 
 */
public class SvnGcCommand
      extends GitCommandHandler {

   @Override
   void getArgs() {
      append("svn", "gc");
   }

   @Override
   String getJobName() {
      return "SVN gc";
   }

   @Override
   boolean touch() {
      return false;
   }
}