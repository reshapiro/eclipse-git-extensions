package org.res.gitx.parameter;

/**
 * Thrown if the user does not provide one of the required parameter values.
 * 
 * @author reshapiro
 * 
 */
@SuppressWarnings("serial")
public class MissingRequiredParameterException
      extends Exception {

   public MissingRequiredParameterException(Parameter param) {
      this("The required parameter " + param.getName() + " was not provided");
   }

   public MissingRequiredParameterException(String message) {
      super(message);
   }
}
