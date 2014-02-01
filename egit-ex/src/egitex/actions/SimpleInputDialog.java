package egitex.actions;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class SimpleInputDialog
      extends TitleAreaDialog {

   private final String title;
   private final String param;
   private final String defautValue;
   private Text inputText;
   private String input;
   
   SimpleInputDialog(Shell parentShell, String title, String param) {
      this(parentShell, title, param, null);
   }

   SimpleInputDialog(Shell parentShell, String title, String param, String defaultValue) {
      super(parentShell);
      this.title = title;
      this.param = param;
      this.defautValue = defaultValue;
      
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

     createInputBox(container);

     return area;
   }

   private void createInputBox(Composite container) {
     Label label = new Label(container, SWT.NONE);
     label.setText(param);

     GridData data = new GridData();
     data.grabExcessHorizontalSpace = true;
     data.horizontalAlignment = GridData.FILL;

     inputText = new Text(container, SWT.BORDER);
     inputText.setLayoutData(data);
     if (defautValue != null) {
        inputText.setText(defautValue);
     }
   }
   
   @Override
   protected boolean isResizable() {
     return true;
   }

   // save content of the Text fields because they get disposed
   // as soon as the Dialog closes
   private void saveInput() {
     input = inputText.getText();

   }

   @Override
   protected void okPressed() {
     saveInput();
     super.okPressed();
   }

   public String getInput() {
     return input;
   }
}
