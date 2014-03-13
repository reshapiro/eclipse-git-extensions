package org.res.gitx.parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SaveFileParameter
      extends FileParameter {

   public SaveFileParameter(String name, boolean required) {
      super(name, required);
   }

   @Override
   FileChooser getFileChooser(Composite container) {
      return new FileChooser(container, SWT.SAVE, getName());
   }

}
