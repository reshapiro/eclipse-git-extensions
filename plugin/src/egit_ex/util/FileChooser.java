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
 * A file chooser widget cribbed from the we.  Ugly but functional
 * 
 */
public class FileChooser
      extends Composite {

   private Text filePath;
   private Button activationButton;
   private final int type;
   private final String title;

   FileChooser(Composite parent, int type,String title) {
      super(parent, SWT.NULL);
      this.type = type;
      this.title = title;
      createContent();
   }

   File getFile() {
      String text = filePath.getText();
      if (text.length() == 0) {
         return null;
      }
      return new File(text);
   }

   void createContent() {
      GridLayout layout = new GridLayout(2, false);
      setLayout(layout);

      filePath = new Text(this, SWT.SINGLE | SWT.BORDER);
      GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = GridData.FILL;
      filePath.setLayoutData(gd);

      activationButton = new Button(this, SWT.NONE);
      activationButton.setText("...");
      activationButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            FileDialog dlg = new FileDialog(activationButton.getShell(), type);
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