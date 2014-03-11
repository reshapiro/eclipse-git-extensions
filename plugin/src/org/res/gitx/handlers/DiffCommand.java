   package org.res.gitx.handlers;

import org.res.gitx.parameter.CheckBoxParameter;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterGroup;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.parameter.RefPair;
import org.res.gitx.parameter.RefParameter;


/**
 * Diff two references
 * 
 * 
 * @author reshapiro
 * 
 */
public class DiffCommand
      extends GitCommandHandler {
   private static final int IGNORE_WHITESPACE_INDEX = 1;
   private static final int BASE_INDEX = 2;
   private static final int REF_INDEX = 3;
   private static final Parameter IGNORE_WHITESPACE = new CheckBoxParameter("Ignore whitespace", "Ignore all whitespace", IGNORE_WHITESPACE_INDEX, true);
   private static final RefParameter BASE = new RefParameter("Baseline", BASE_INDEX);
   private static final RefParameter REF = new RefParameter("Ref", REF_INDEX);
   private static final ParameterGroup GROUP = new RefPair(BASE, REF);
   
   private static final ParameterSet PARAMETERS = new ParameterSet("Diff", IGNORE_WHITESPACE,  GROUP);

   
   private static final String[] ARGS = new String[] {
      "diff", null, null, null
   };
   
   private static final String[] SIMPLE_ARGS = new String[3];

   @Override
   String[] getArgs() 
         throws PromptCancelledException, MissingRequiredParameterException {
      BASE.setDefaultReference("HEAD");
      promptForParameters(PARAMETERS, ARGS);
      
      
      boolean ignoreWhiteSpace = ARGS[IGNORE_WHITESPACE_INDEX] != null && Boolean.valueOf(ARGS[IGNORE_WHITESPACE_INDEX]);
      
      if (ignoreWhiteSpace) {
         ARGS[IGNORE_WHITESPACE_INDEX] = "-w";
         return ARGS;
      } else {
         SIMPLE_ARGS[0] = ARGS[0];
         SIMPLE_ARGS[1] = ARGS[BASE_INDEX];
         SIMPLE_ARGS[2] = ARGS[REF_INDEX];
         return SIMPLE_ARGS;
      }
      
   }

   @Override
   String getJobName() {
      return "Diff";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}