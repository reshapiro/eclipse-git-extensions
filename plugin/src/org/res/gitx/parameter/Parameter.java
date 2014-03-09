package org.res.gitx.parameter;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

/**
 * The description of s parameter provided by the user through a dialog.
 * @author reshapiro
 *
 */
public class Parameter
      implements ParameterGroup {
   private final String name;
   private final boolean required;
   private final String defaultValue;
   private int index;
   
   /**
    * Make a parameter with no default value
    * @param name The name of the parameter
    * @param index The index in the Git command argument list
    * @param required whether or not the user must provide a value.
    */
   public Parameter(String name, int index, boolean required) {
      this(name, index, required, null);
   }
   
   /**
    * Make a parameter with a default value
    * @param name The name of the parameter
    * @param index The index in the Git command argument list
    * @param required whether or not the user must provide a value.
    * @param defaultValue the default value for this parameter.
    */

   public Parameter(String name, int index, boolean required, String defaultValue) {
      this.name = name;
      this.required = required;
      this.defaultValue = defaultValue;
      this.index = index;
   }
   
   ParameterType getParameterType() {
      return ParameterType.STRING;
   }
   
   FileChooser getFileChooser(Composite container) {
      return null;
   }
   
   CheckBox getCheckBox(Composite container) {
      return null;
   }

   RefTree getRefTree(Composite container) {
      return null;
   }
   
   int getIndex() {
      return index;
   }

   String getName() {
      return name;
   }
   
   String getDefaultValue() {
      return defaultValue;
   }
   
   boolean isRequired() {
      return required;
   }

   @Override
   public List<Parameter> getParameters() {
      return Collections.singletonList(this);
   }
}
