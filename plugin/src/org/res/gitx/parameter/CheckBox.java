package org.res.gitx.parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A file chooser widget from the web (cleaned up)
 * 
 * The GridData for the {@link #filePath} text box is supposed to resize as
 * needed, but that isn't working unless the containing dialog is itself resized
 * manually.
 * 
 * For now the resizing is forced by changing the bounds.
 * 
 */
public class CheckBox
      extends Composite {

   private final String title;
   private final boolean defaultValue;
   private Button button;

   CheckBox(Composite parent, String title, boolean defaultValue) {
      super(parent, SWT.NULL);
      this.defaultValue = defaultValue;
      this.title = title;
      createContent();
   }

   /**
    * @return the selected file, or null if none.
    */
   boolean getStatus() {
      return button.getSelection();
   }

   private void createContent() {
      setLayout(new GridLayout(2, false));
      button = new Button(this, SWT.CHECK);
      button.setText(title);
      button.setSelection(defaultValue);

   }
}