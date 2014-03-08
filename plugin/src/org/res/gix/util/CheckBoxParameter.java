package org.res.gix.util;

import org.eclipse.swt.widgets.Composite;

public class CheckBoxParameter
      extends Parameter {

   private String label;
   private final boolean defaultValue;

   public CheckBoxParameter(String name, String label, int index) {
      this(name, label, index, false);
      this.label = label;
   }
   
   public CheckBoxParameter(String name, String label, int index, boolean defaultValue) {
      super(name, index, false);
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
