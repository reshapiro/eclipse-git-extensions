package org.res.gitx.handlers;


/**
 * Execute the Git operation that will show the  current status.  This Git operation also
 * appears to have a side-effect that does some kind of caching.
 * 
 * @author reshapiro
 * 
 */
public class StatusCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "status"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Show repo status";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}