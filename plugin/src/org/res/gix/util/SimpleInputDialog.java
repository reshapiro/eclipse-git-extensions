package org.res.gix.util;

import java.io.File;
import java.util.ArrayList;
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
 * Very simple text input dialog.
 * Any number of parameters can be provided, with or without default values.
 * They will displayed as one text-box per row, with a fixed width for the box.
 * @author reshapiro
 *
 */
public class SimpleInputDialog
      extends TitleAreaDialog {

   private final ParameterSet parameters;
   private final List<Text> inputTexts;
   private final List<FileChooser> choosers;
   private final List<CheckBox> checkboxes;
   private final List<RefTree> refTrees;
   
   /**
    * This variant will display as many boxes as there are parameters.
    * 
    * @param parentShell root window.
    * @param title The title of the dialog
    * @param parameters The parameter specifications
    */
   public SimpleInputDialog(Shell parentShell, ParameterSet parameters) {
      super(parentShell);
      parameters.init();
      this.parameters = parameters;
      int size = parameters.size();
      this.inputTexts = new ArrayList<>(size);
      this.choosers = new ArrayList<>(size);
      this.checkboxes = new ArrayList<>(size);
      this.refTrees = new ArrayList<>(size);
   }
   
   @Override
   public void create() {
     super.create();
     setTitle(parameters.getTitle());
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

     for (int i=0; i < parameters.size(); i++) {
        addInputBox(container, data, i);
     }
   }

   private void addInputBox(Composite container, GridData data, int index) {
      Label label = new Label(container, SWT.NONE);
      Parameter parameter = parameters.getParameter(index);
      label.setText(parameter.getName());
      switch (parameter.getParameterType()) {
         case FILE:
            choosers.add(parameter.getFileChooser(container));
            break;
         case BOOLEAN:
            checkboxes.add(parameter.getCheckBox(container));
            break;
            
         case STRING:
            Text inputText = new Text(container, SWT.BORDER);
            inputText.setLayoutData(data);
            String defaultValue = parameters.getParameterValue(parameter);
            if (defaultValue != null) {
               inputText.setText(defaultValue);
            }
            inputTexts.add(inputText);
            break;
            
         case REF:
            refTrees.add(parameter.getRefTree(container));
            break;
            
         default:
            /* ignore */
      }
   }
   
   @Override
   protected boolean isResizable() {
     return true;
   }

   // save content of the Text fields because they get disposed
   // as soon as the Dialog closes
   private void saveInput() {
      int textIndex = 0;
      int fileIndex = 0;
      int checkBoxIndex = 0;
      int refIndex = 0;
      for (int i=0; i < parameters.size(); i++) {
         Parameter parameter = parameters.getParameter(i);
         switch (parameter.getParameterType()) {
            case STRING:
               String text = inputTexts.get(textIndex++).getText();
               parameters.setParameterValue(parameter, text);
               break;
               
            case FILE:
               File file = choosers.get(fileIndex++).getFile();
               parameters.setParameterValue(parameter, file != null ? file.getAbsolutePath() : null);
               break;
               
            case BOOLEAN:
               CheckBox checkBox = checkboxes.get(checkBoxIndex++);
               parameters.setParameterValue(parameter, Boolean.toString(checkBox.getStatus()));
               break;
               
            case REF:
               RefTree tree = refTrees.get(refIndex++);
               parameters.setParameterValue(parameter, tree.getText());
               break;
               
            default:
         }
      }
   }

   @Override
   protected void okPressed() {
     saveInput();
     super.okPressed();
   }
}
