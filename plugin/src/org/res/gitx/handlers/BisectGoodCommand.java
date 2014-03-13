package org.res.gitx.handlers;

import java.util.List;


/**
 * Execute the Git operation that will mark the HEAD as good in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectGoodCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs(List<String> args) {
      args.add("bisect");
      args.add("good");
   }
   
   @Override
   String getJobName() {
      return "Mark bisect checkout as good";
   }

}