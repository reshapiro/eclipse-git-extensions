package org.res.gitx.parameter;

/**
 * Thrown when the user cancels out of a parameter dialog
 * 
 * @author reshapiro
 * 
 */
@SuppressWarnings("serial")
public class PromptCancelledException
      extends Exception {
   public PromptCancelledException() {
      super("Prompt Dialog Cancelled");
   }
}
