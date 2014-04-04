package org.res.gitx.handlers;

import java.io.File;

import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefParameter;
import org.res.gitx.parameter.SaveFileParameter;

/**
 * Execute the Git operation that will create a patch file.
 * You need to select another reference to patch against.
 * 
 * The assumption is that it will be run when the current branch
 * is the one where you will ultimately apply the patch, and
 * the selected reference is where you will be patching from.
 * 
 * @author reshapiro
 * 
 */
public class CreatePatchCommand
      extends GitCommandHandler {

   private static final Parameter PATCH_FILE_PARAM = new SaveFileParameter("Save patch as", true);
   private static final RefParameter REF = new RefParameter("source");
   private static final ParameterSet PARAMETERS = new ParameterSet("Create patch file", PATCH_FILE_PARAM, REF);

   private String patchFilePath;

   @Override
   void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMETERS);
      append("diff").append("-R").append(PARAMETERS.getParameterValue(REF));
      patchFilePath = PARAMETERS.getParameterValue(PATCH_FILE_PARAM);
   }

   @Override
   String getJobName() {
      return "Create patch";
   }

   @Override
   boolean touch() {
      return false;
   }

   @Override
   File getOutputFile() {
      if (patchFilePath != null && !patchFilePath.isEmpty()) {
         return new File(patchFilePath);
      }
      return super.getOutputFile();
   }

}