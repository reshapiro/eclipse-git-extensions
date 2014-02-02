package egit_ex.util;

import java.util.HashMap;
import java.util.Map;

/**
 * An ordered group of {@link Parameter} specifications.
 * 
 * @author reshapiro
 *
 */
public class ParameterSet {
   private final Map<Parameter, String> values = new HashMap<>();
   private final Parameter[] parameters;
   private final String title;
   
   /**
    * Create one
    * 
    * @param title The title of the dialog
    * @param parameters the members
    */
   public ParameterSet(String title, Parameter... parameters) {
      this.title = title;
      this.parameters = parameters;
   }
   
   /**
    * 
    * @param parameter a member of the group
    * @return the user-provided value of the given parameter.
    */
   public String getParameterValue(Parameter parameter) {
      return values.get(parameter);
   }

   /**
    * Inject the parameter values into to command array.
    * @param array the base array of command arguments.
    * 
    * @throws MissingRequiredParameterException if the user has not provided a value for a parameter that requires one.
    */
   public void splice(String[] array)
         throws MissingRequiredParameterException {
      for (Map.Entry<Parameter, String> entry : values.entrySet()) {
         String value = entry.getValue();
         Parameter param = entry.getKey();
         if (param.isRequired() && (value == null || value.isEmpty())) {
            throw new MissingRequiredParameterException(param);
         }
         array[param.getIndex()] = value;
      }
   }

   void init() {
      values.clear();
      for (Parameter parameter : parameters) {
         values.put(parameter, parameter.getDefaultValue());
      }
   }
   
   String getTitle() {
      return title;
   }
   
   Parameter getParameter(int index) {
      return parameters[index];
   }

   void setParameterValue(Parameter parameter, String value) {
      values.put(parameter, value);
   }
   
   int size() {
      return values.size();
   }
}
