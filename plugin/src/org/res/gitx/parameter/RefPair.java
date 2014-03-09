package org.res.gitx.parameter;

import java.util.ArrayList;
import java.util.List;

public class RefPair
      implements ParameterGroup {

   private final List<Parameter> parameters = new ArrayList<>();

   public RefPair(RefParameter first, RefParameter second) {
      parameters.add(first);
      parameters.add(second);
   }

   @Override
   public List<Parameter> getParameters() {
      return parameters;
   }
}
