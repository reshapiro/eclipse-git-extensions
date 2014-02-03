package egit_ex.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class FileParameter
      extends Parameter {

   public FileParameter(String name, int index, boolean required) {
      super(name, index, required);
   }

   @Override
   ParameterType getParameterType() {
      return ParameterType.FILE;
   }

   @Override
   FileChooser getFileChooser(Composite container) {
      return new FileChooser(container, SWT.OPEN);
   }

}
