package org.res.gitx.parameter;

import org.eclipse.swt.widgets.Composite;

public class RadioButtonParameter
      extends Parameter {

   private final String label;
   private final String defaultValue;
   private final String[] options;

   public RadioButtonParameter(String name, String label, String[] options, String defaultValue) {
      super(name, false);
      this.label = label;
      this.defaultValue = defaultValue;
      this.options = options;
   }

   @Override
   ParameterType getParameterType() {
      return ParameterType.RADIO;
   }

   @Override
   RadioButtons getRadioButtons(Composite container) {
      return new RadioButtons(container, label, options, defaultValue);
   }

}
