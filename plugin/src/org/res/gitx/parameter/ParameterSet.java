package org.res.gitx.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An ordered group of {@link Parameter} specifications.
 * 
 * @author reshapiro
 * 
 */
public class ParameterSet {
   private final Map<Parameter, String> values = new HashMap<>();
   private final List<Parameter> parameters = new ArrayList<>();
   private final List<ParameterGroup> groups;
   private final String title;

   /**
    * Create one
    * 
    * @param title The title of the dialog
    * @param parameters the members
    */
   public ParameterSet(String title, ParameterGroup... groups) {
      this.title = title;
      this.groups = new ArrayList<>(groups.length);
      for (ParameterGroup group : groups) {
         this.groups.add(group);
         parameters.addAll(group.getParameters());
      }
   }

   /**
    * 
    * @param parameter a member of the group
    * @return the user-provided value of the given parameter.
    */
   public String getParameterValue(Parameter parameter) {
      return values.get(parameter);
   }

   public boolean getBooleanParameterValue(Parameter parameter) {
      String value = getParameterValue(parameter);
      return value != null && Boolean.valueOf(value);
   }

   public void checkRequirements()
         throws MissingRequiredParameterException {
      for (Map.Entry<Parameter, String> entry : values.entrySet()) {
         String value = entry.getValue();
         Parameter param = entry.getKey();
         if (param.isRequired() && (value == null || value.isEmpty())) {
            throw new MissingRequiredParameterException(param);
         }
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

   List<ParameterGroup> getGroups() {
      return groups;
   }

   Parameter getParameter(int index) {
      return parameters.get(index);
   }

   void setParameterValue(Parameter parameter, String value) {
      values.put(parameter, value);
   }

   int size() {
      return values.size();
   }
}
