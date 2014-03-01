package org.res.gitx.handlers;

import java.io.File;

import org.res.gix.util.MissingRequiredParameterException;
import org.res.gix.util.Parameter;
import org.res.gix.util.ParameterSet;
import org.res.gix.util.PromptCancelledException;
import org.res.gix.util.SaveFileParameter;

/**
 * Execute the Git operation that will log the bisect.
 * Later it will be possible to log to a file.
 * 
 * @author reshapiro
 * 
 */
public class BisectLogCommand
      extends GitCommandHandler {
   
   private static final Parameter LOG_FILE_PARAM = new SaveFileParameter("Save log as (optional)", 0, false);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Log file", LOG_FILE_PARAM);
   
   private static final String[] OPTIONS = new String[] {
      null
   };
   
   private static final String[] ARGS = new String[] {
      "bisect", "log"
   };

   private String logFilePath;

   @Override
   String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS, OPTIONS);
      
      logFilePath = PARAMETERS.getParameterValue(LOG_FILE_PARAM);
      return ARGS;
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