package egit_ex.util;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

/**
 * A file chooser widget cribbed from the we.  Ugly but functional
 * 
 */
public class FileChooser
      extends Composite {

   private Text mText;
   private Button mButton;
   private final int type;

   FileChooser(Composite parent, int type) {
      super(parent, SWT.NULL);
      this.type = type;
      createContent();
   }

   File getFile() {
      String text = mText.getText();
      if (text.length() == 0) {
         return null;
      }
      return new File(text);
   }

   void createContent() {
      GridLayout layout = new GridLayout(2, false);
      setLayout(layout);

      mText = new Text(this, SWT.SINGLE | SWT.BORDER);
      GridData gd = new GridData(GridData.FILL_BOTH);
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = GridData.FILL;
      mText.setLayoutData(gd);

      mButton = new Button(this, SWT.NONE);
      mButton.setText("...");
      mButton.addSelectionListener(new SelectionListener() {

         @Override
         public void widgetDefaultSelected(SelectionEvent e) {
            /* no-op for now */
         }

         @Override
         public void widgetSelected(SelectionEvent e) {
            FileDialog dlg = new FileDialog(mButton.getShell(), type);
            dlg.setText(type == SWT.OPEN ? "Open" : "Save");
            String path = dlg.open();
            if (path == null) {
               return;
            }
            mText.setText(path);
         }
      });
   }
}