package org.res.gitx.handlers;

import java.util.List;


/**
 * Execute the Git operation that will mark the HEAD as bad in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectBadCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs(List<String> args) {
      args.add("bisect");
      args.add("bad");
   }

   @Override
   String getJobName() {
      return "Mark bisect checkout as bad";
   }
}