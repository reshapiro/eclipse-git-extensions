package org.res.gitx.handlers;



/**
 * Execute the Git operation that will mark the HEAD as good in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectGoodCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs() {
      append("bisect", "good");
   }
   
   @Override
   String getJobName() {
      return "Mark bisect checkout as good";
   }

}