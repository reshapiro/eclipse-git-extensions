package org.res.gitx.handlers;

import org.res.gitx.parameter.FileParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;

/**
 * Execute the Git operation that will apply a patch file to HEAD
 * 
 * @author reshapiro
 * 
 */
public class ApplyPatchCommand
      extends GitCommandHandler {

   private static final Parameter PATCH_FILE_PARAM = new FileParameter("Patch with", true);
   private static final ParameterSet PARAMETERS = new ParameterSet("Appply patch", PATCH_FILE_PARAM);

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("apply").append(PARAMETERS.getParameterValue(PATCH_FILE_PARAM));
   }

   @Override
   String getJobName() {
      return "Apply patch";
   }
}