package org.res.gitx.handlers;

/**
 * Execute the Git operation that will mark the HEAD as bad in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectBadCommand
      extends GitCommandHandler {

   @Override
   void getArgs() {
      append("bisect", "bad");
   }

   @Override
   String getJobName() {
      return "Mark bisect checkout as bad";
   }
}