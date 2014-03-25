package org.res.gitx.parameter;

public class RefParameterWithDefault
      extends RefParameter {

   private final String defaultRef;

   public RefParameterWithDefault(String deaultRef, String prompt) {
      super(prompt);
      this.defaultRef = deaultRef;
   }

   @Override
   String getDefaultReference() {
      return defaultRef;
   }
}
