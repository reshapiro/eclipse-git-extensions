package org.res.gitx.parameter;

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
 * Dialog to gather parameters for a given command
 * Any number of parameters can be provided, with or without default values.
 * 
 * @author reshapiro
 *
 */
public class ParametersDialog
      extends TitleAreaDialog {

   private final ParameterSet parameters;
   private final List<Text> inputTexts;
   private final List<FileChooser> choosers;
   private final List<CheckBox> checkboxes;
   private final List<RefTree> refTrees;
   private final List<RadioButtons> radioButtonSets;
   
   /**
    * This variant will display as many boxes as there are parameters.
    * 
    * @param parentShell root window.
    * @param title The title of the dialog
    * @param parameters The parameter specifications
    */
   public ParametersDialog(Shell parentShell, ParameterSet parameters) {
      super(parentShell);
      parameters.init();
      this.parameters = parameters;
      int size = parameters.size();
      this.inputTexts = new ArrayList<>(size);
      this.choosers = new ArrayList<>(size);
      this.checkboxes = new ArrayList<>(size);
      this.refTrees = new ArrayList<>(size);
      this.radioButtonSets = new ArrayList<>();
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
     Composite container = makeContainer(area, 2);
     createInputBoxes(container);

     return area;
   }
   
   private Composite makeContainer(Composite area, int width) {
      Composite container = new Composite(area, SWT.NONE);
      container.setLayoutData(new GridData(GridData.FILL_BOTH));
      GridLayout layout = new GridLayout(width, false);
      container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      container.setLayout(layout);
      return container;
   }

   private void createInputBoxes(Composite container) {

     GridData data = new GridData();
     data.grabExcessHorizontalSpace = true;
     data.horizontalAlignment = GridData.FILL;

     for (ParameterGroup group : parameters.getGroups()) {
        addGroup(container, data, group);
     }
   }
   
   private void addGroup(Composite container, GridData data, ParameterGroup group) {
      List<Parameter> members = group.getParameters();
      int count = members.size();
      if (count == 1) {
         addInputBox(container, data, members.get(0));
      } else {
         Composite area = (Composite) super.createDialogArea(container);
         Composite subcontainer = makeContainer(area, 2 * count);
         for (Parameter parameter: group.getParameters()) {
            addInputBox(subcontainer, data, parameter);
         }
      }
   }

   private void addInputBox(Composite container, GridData data, Parameter parameter) {
      Label label = new Label(container, SWT.NONE);
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
            
         case RADIO:
            radioButtonSets.add(parameter.getRadioButtons(container));
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
      int radioIndex = 0;
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
               
            case RADIO:
               RadioButtons buttons = radioButtonSets.get(radioIndex++);
               parameters.setParameterValue(parameter, buttons.getSelection());
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
