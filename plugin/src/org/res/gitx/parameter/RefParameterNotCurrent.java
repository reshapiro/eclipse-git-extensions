package org.res.gitx.parameter;

public class RefParameterNotCurrent
      extends RefParameter {

   /**
    * Don't list the current branch as an option.
    * 
    * @param prompt the name of the parameter
    */
   public RefParameterNotCurrent(String prompt) {
      super(prompt, true);
   }

   @Override
   boolean skipCurrentBranch() {
      return true;
   }
}
