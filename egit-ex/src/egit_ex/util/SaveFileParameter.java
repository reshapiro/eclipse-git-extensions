package egit_ex.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SaveFileParameter
      extends FileParameter {

   public SaveFileParameter(String name, int index, boolean required) {
      super(name, index, required);
   }

   @Override
   FileChooser getFileChooser(Composite container) {
      return new FileChooser(container, SWT.SAVE);
   }

}
