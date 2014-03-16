package org.res.gitx.handlers;

import java.io.File;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.SaveFileParameter;

/**
 * Execute the Git operation that will log the bisect.
 * 
 * @author reshapiro
 * 
 */
public class BisectLogCommand
      extends GitCommandHandler {
   
   private static final Parameter LOG_FILE_PARAM = new SaveFileParameter("Save log as (optional)", false);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Log file", LOG_FILE_PARAM);
   
   private String logFilePath;

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("bisect", "log");
      
      logFilePath = PARAMETERS.getParameterValue(LOG_FILE_PARAM);
   }
   
   @Override
   String getJobName() {
      return "Log bisect";
   }

   @Override
   boolean touch() {
      return false;
   }

   @Override
   File getOutputFile() {
      if (logFilePath != null && !logFilePath.isEmpty()) {
         return new File(logFilePath);
      }
      return super.getOutputFile();
   }

  

}