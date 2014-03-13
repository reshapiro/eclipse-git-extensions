package org.res.gitx.handlers;

import java.util.List;


/**
 * Execute the Git operation that skips the next bisect step
 * 
 * @author reshapiro
 * 
 */
public class BisectSkipCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs(List<String> args) {
      args.add("bisect");
      args.add("skip");
   }
   
   @Override
   String getJobName() {
      return "Skip bisect";
   }

}