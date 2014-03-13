package org.res.gitx.handlers;



/**
 * Execute the Git operation that skips the next bisect step
 * 
 * @author reshapiro
 * 
 */
public class BisectSkipCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs() {
      addArgs("bisect", "skip");
   }
   
   @Override
   String getJobName() {
      return "Skip bisect";
   }

}