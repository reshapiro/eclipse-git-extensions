package egitex.actions;

class Parameter {
   private final String name;
   private final boolean required;
   private final String defaultValue;
   private int index;
   
   Parameter(String name, int index, boolean required) {
      this(name, index, required, null);
   }
   Parameter(String name, int index, boolean required, String defaultValue) {
      this.name = name;
      this.required = required;
      this.defaultValue = defaultValue;
      this.index = index;
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

   int getIndex() {
      return index;
   }
}
