package egitex.actions;

import java.io.File;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

class FileInputDialog
      extends TitleAreaDialog {
   
   private final String title;
   private FileChooser chooser;
   private File selectedFile;

   FileInputDialog(String title, Shell shell) {
      super(shell);
      this.title = title;
   }

   @Override
   public void create() {
     super.create();
     setTitle(title);
     setMessage("git extensions", IMessageProvider.INFORMATION);
   }
   
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite area = (Composite) super.createDialogArea(parent);
      Composite container = new Composite(area, SWT.NONE);
      container.setLayoutData(new GridData(GridData.FILL_BOTH));
      GridLayout layout = new GridLayout(2, false);
      container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      container.setLayout(layout);
      this.chooser = new FileChooser(getShell());
      GridData data = new GridData();
      chooser.setLayoutData(data);
      return area;
   }
   
   @Override
   protected void okPressed() {
     selectedFile = chooser.getFile();
     super.okPressed();
   }
   
   File getSelectedFile() {
      return selectedFile;
   }
}
