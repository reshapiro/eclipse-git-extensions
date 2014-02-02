package egitex.actions;

@SuppressWarnings("serial")
class MissingRequiredParameterException
      extends Exception {
   MissingRequiredParameterException(Parameter param) {
      super("The required parameter " + param.getName() + " was not provided");
   }
}
