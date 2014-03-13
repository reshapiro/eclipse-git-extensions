package org.res.gitx.handlers;

import java.util.List;


/**
 * Execute the Git operation that will gc svn 
 * 
 * @author reshapiro
 * 
 */
public class SvnGcCommand
      extends GitCommandHandler {
   
   @Override
   void getArgs(List<String> args) {
      args.add("svn");
      args.add("gc");
   }

   @Override
   String getJobName() {
      return "SVN gc";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}