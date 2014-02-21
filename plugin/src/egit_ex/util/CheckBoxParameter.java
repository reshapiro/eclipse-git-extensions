package egit_ex.util;

import org.eclipse.swt.widgets.Composite;

public class CheckBoxParameter
      extends Parameter {

   private String label;

   public CheckBoxParameter(String name, String label, int index) {
      super(name, index, false);
      this.label = label;
   }
   
   @Override
   ParameterType getParameterType() {
      return ParameterType.BOOLEAN;
   }

   @Override
   CheckBox getCheckBox(Composite container) {
      return new CheckBox(container, label);
   }

}
