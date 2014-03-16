package org.res.gitx.handlers;



/**
 * Abort the current merge
 * 
 * @author reshapiro
 * 
 */
public class MergeAbortCommand
      extends GitCommandHandler {
   

   @Override
   void getArgs() {
      append("merge", "--abort");
   }
   

   @Override
   String getJobName() {
      return "Abort Merge";
   }
}