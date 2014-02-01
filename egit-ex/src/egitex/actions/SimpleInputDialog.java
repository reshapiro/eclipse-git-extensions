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
 * Any number of parameters can be provided, with or without default values.
 * They will displayed as one text-box per row, with a fixed width for the box.
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
   
   /**
    * 
    * This variant will display one text box, with no default value.
    * 
    * @param parentShell root window
    * @param title The title of the dialog.
    * @param param The name of a single parameter.
    * 
    */
   SimpleInputDialog(Shell parentShell, String title, String param) {
      this(parentShell, title, Collections.singletonList(param), EMPTY);
   }
   
   /**
    * 
    * This variant will display one text box, with a default value.
    * 
    * @param parentShell root window.
    * @param title The title of the dialog.
    * @param param The name of a single parameter.
    * @param defaultValue The default value for this parameter.
    * 
    */
   SimpleInputDialog(Shell parentShell, String title, String param, String defaultValue) {
      this(parentShell, title, Collections.singletonList(param), Collections.singletonList(defaultValue));
   }

   /**
    * This variant will display as many boxes as there are parameters.
    * 
    * @param parentShell root window.
    * @param title The title of the dialog
    * @param params The parameter names, in order.
    * @param defaultValues Parallel list of default values. A value of null is
    *        ok here, it simply means no defaults. Similarly if an entry in this
    *        list is null or missing, the corresponding parameter has no
    *        default value.
    */
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

     for (int i=0; i < params.size(); i++) {
        addInputBox(container, data, i);
     }
   }

   private void addInputBox(Composite container, GridData data, int index) {
      Label label = new Label(container, SWT.NONE);
      label.setText(params.get(index));
      Text inputText = new Text(container, SWT.BORDER);
      inputText.setLayoutData(data);
      if (defautValues != null && defautValues.size() >= index) {
         String defaultValue = defautValues.get(index);
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
