package org.res.gitx.handlers;


/**
 * Execute the Git operation that will mark the HEAD as good in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectGoodCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "bisect", "good"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "Mark bisect checkout as good";
   }

}