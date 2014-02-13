package egitex.handlers;


/**
 * Execute the Git operation that will mark the HEAD as bad in a bisect
 * 
 * @author reshapiro
 * 
 */
public class BisectBadCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "bisect", "bad"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "Mark bisect checkout as bad";
   }
}