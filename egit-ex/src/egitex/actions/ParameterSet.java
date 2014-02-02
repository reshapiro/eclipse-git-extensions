package egitex.actions;

import java.util.HashMap;
import java.util.Map;

class ParameterSet {
   private final Map<Parameter, String> values = new HashMap<>();
   private final Parameter[] parameters;
   private final String title;
   
   ParameterSet(String title, Parameter... parameters) {
      this.title = title;
      this.parameters = parameters;
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
   
   String getParameterValue(Parameter parameter) {
      return values.get(parameter);
   }

   void splice(String[] array)
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

   int size() {
      return values.size();
   }
}
