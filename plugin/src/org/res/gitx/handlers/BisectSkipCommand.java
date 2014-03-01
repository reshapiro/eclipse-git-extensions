package org.res.gitx.handlers;


/**
 * Execute the Git operation that skips the next bisect step
 * 
 * @author reshapiro
 * 
 */
public class BisectSkipCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "bisect", "skip"
   };
   
   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "Skip bisect";
   }

}