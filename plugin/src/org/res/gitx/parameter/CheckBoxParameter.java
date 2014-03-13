package org.res.gitx.parameter;

import org.eclipse.swt.widgets.Composite;

public class CheckBoxParameter
      extends Parameter {

   private String label;
   private final boolean defaultValue;

   public CheckBoxParameter(String name, String label) {
      this(name, label, false);
      this.label = label;
   }
   
   public CheckBoxParameter(String name, String label, boolean defaultValue) {
      super(name, false);
      this.label = label;
      this.defaultValue = defaultValue;
   }
   @Override
   ParameterType getParameterType() {
      return ParameterType.BOOLEAN;
   }

   @Override
   CheckBox getCheckBox(Composite container) {
      return new CheckBox(container, label, defaultValue);
   }

}
