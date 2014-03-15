package org.res.gitx.parameter;

import org.eclipse.swt.widgets.Composite;

/**
 * Prompt for a reference -- branch, tag, remote-ref, SHA etc.
 * 
 * For now the user must enter the reference as text. Eventually this should
 * include a chooser gui of some kind.
 * 
 * @author reshapiro
 * 
 */
public class RefParameter
      extends Parameter {
   /**
    * Prompt for a required reference with a given label.
    * 
    * @param prompt the label in the dialog
    */
   public RefParameter(String prompt) {
      super(prompt, true);
   }

   /**
    * Prompt for a reference with a given label.
    * @param prompt the label in the dialog.
    * @param required whether or not this parameter is required.
    */
   public RefParameter(String prompt, boolean required) {
      super(prompt, required);
   }
   
   String getDefaultReference() {
      return null;
   }

   @Override
   RefTree getRefTree(Composite container) {
      return new RefTree(container, getDefaultReference());
   }
   
   @Override
   ParameterType getParameterType() {
      return ParameterType.REF;
   }
}
