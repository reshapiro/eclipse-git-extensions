package org.res.gix.util;


/**
 *  Thrown if the user does not provide one of the required parameter values.
 * @author reshapiro
 *
 */
@SuppressWarnings("serial")
public class MissingRequiredParameterException
      extends Exception {
   public MissingRequiredParameterException(Parameter param) {
      super("The required parameter " + param.getName() + " was not provided");
   }
}
