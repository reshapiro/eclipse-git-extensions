package egitex.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

/**
 * Very simple input dialog.
 * 
 * Eventually it will support multiple parameters, for now only one.
 * @author reshapiro
 *
 */
class SimpleInputDialog
      extends TitleAreaDialog {

   private static final List<String> EMPTY = Collections.emptyList();
   private final String title;
   private final List<String>  params;
   private final List<String> defautValues;
   private final List<Text> inputTexts;
   private final List<String> inputs;
   
   SimpleInputDialog(Shell parentShell, String title, String param) {
      this(parentShell, title, Collections.singletonList(param), EMPTY);
   }
   
   SimpleInputDialog(Shell parentShell, String title, String param, String defaultValue) {
      this(parentShell, title, Collections.singletonList(param), Collections.singletonList(defaultValue));
   }

   SimpleInputDialog(Shell parentShell, String title, List<String> params, List<String> defaultValues) {
      super(parentShell);
      int size = params.size();
      this.inputs = new ArrayList<>(size);
      this.inputTexts = new ArrayList<>(size);
      this.title = title;
      this.params = params;
      this.defautValues = defaultValues;
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

     createInputBoxes(container);

     return area;
   }

   private void createInputBoxes(Composite container) {

     GridData data = new GridData();
     data.grabExcessHorizontalSpace = true;
     data.horizontalAlignment = GridData.FILL;

     /* For now create one box, later create all */
     addInputBox(container, data, 0);
   }

   private void addInputBox(Composite container, GridData data, int index) {
      Label label = new Label(container, SWT.NONE);
      label.setText(params.get(index));
      Text inputText = new Text(container, SWT.BORDER);
      inputText.setLayoutData(data);
      if (defautValues.size() > 0) {
         String defaultValue = defautValues.get(0);
         if (defaultValue != null) {
            inputText.setText(defaultValue);
         }
      }
      inputTexts.add(inputText);
   }
   
   @Override
   protected boolean isResizable() {
     return true;
   }

   // save content of the Text fields because they get disposed
   // as soon as the Dialog closes
   private void saveInput() {
      for (int i=0; i < params.size(); i++) {
         inputs.add(inputTexts.get(i).getText());
      }
   }

   @Override
   protected void okPressed() {
     saveInput();
     super.okPressed();
   }

   public List<String> getInputs() {
     return inputs;
   }
}
