package egit_ex.util;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

/**
 * A file chooser widget from the web (cleaned up)
 * 
 * The GridData for the {@link #filePath} text box is supposed to resize as needed,
 * but that isn't working unless the containing dialog is itself resized manually.
 * 
 */
public class FileChooser
      extends Composite {

   private Text filePath;
   private final int type;
   private final String title;

   FileChooser(Composite parent, int type,String title) {
      super(parent, SWT.NULL);
      this.type = type;
      this.title = title;
      createContent();
   }

   /**
    * @return the selected file, or null if none.
    */
   File getFile() {
      String text = filePath.getText();
      if (text.length() == 0) {
         return null;
      }
      return new File(text);
   }

   private void createContent() {
      setLayout(new GridLayout(2, false));
      
      filePath = new Text(this, SWT.SINGLE | SWT.BORDER);
      filePath.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      
      Button activationButton = new Button(this, SWT.NONE);
      activationButton.setText("...");
      activationButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            Button src = (Button) e.getSource();
            FileDialog dlg = new FileDialog(src.getShell(), type);
            dlg.setText(title);
            String path = dlg.open();
            if (path == null) {
               return;
            }
            filePath.setText(path);
         }
      });
   }
}