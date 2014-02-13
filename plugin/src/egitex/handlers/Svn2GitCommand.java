package egitex.handlers;

import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.Parameter;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;

/**
 * Execute the Git operation that will show the Git SHA for a given SVN revision.
 * 
 * @author reshapiro
 * 
 */
public class Svn2GitCommand
      extends GitCommandHandler {
   
   private static final Parameter SVN_REV_PARAM = new Parameter("SVN rev", 2, true);
   
   private static final ParameterSet PARAMS = 
         new ParameterSet("Show Git commit for SVN revison", SVN_REV_PARAM);
   
   private static final String[] ARGS = new String[] {
      "svn", "find-rev", null
   };

   @Override
   String[] getArgs()
            throws PromptCancelledException, MissingRequiredParameterException {
      promptForParameters(PARAMS, ARGS);
      String rev = PARAMS.getParameterValue(SVN_REV_PARAM);
      if (!rev.startsWith("r")) {
         ARGS[SVN_REV_PARAM.getIndex()] = "r" + rev;
      }
      
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Display the SHA for a given SVN revision";
   }
   
   @Override
   boolean touch() {
      return false;
   }
}