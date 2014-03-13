package org.res.gitx.parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class FileParameter
      extends Parameter {

   public FileParameter(String name, boolean required) {
      super(name, required);
   }

   @Override
   ParameterType getParameterType() {
      return ParameterType.FILE;
   }

   @Override
   FileChooser getFileChooser(Composite container) {
      return new FileChooser(container, SWT.OPEN, getName());
   }

}
