package org.res.gitx.parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

class RadioButtons
      extends Composite {
   private String selection;

   RadioButtons(Composite parent, String title, String[] options, String defaultValue) {
      super(parent, SWT.NULL);
      selection = defaultValue;
      setLayout(new RowLayout());

      Label label = new Label(this, SWT.NULL);
      label.setText(title);

      for (String option : options) {
         Button button = new Button(this, SWT.RADIO);
         button.addSelectionListener(new RBListener());
         button.setText(option);
         if (defaultValue.equals(option)) {
            button.setSelection(true);
         }
      }
      pack();
   }

   String getSelection() {
      return selection;
   }

   private final class RBListener
         implements SelectionListener {
      @Override
      public void widgetSelected(SelectionEvent e) {
         Button button = (Button) e.getSource();
         selection = button.getText();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
         /* */
      }
   }

}
