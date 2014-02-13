package egitex.handlers;


/**
 * Execute the Git operation that will 'push' any new commits on the active
 * branch to the corresponding SVN branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnDcommitCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "svn", "dcommit"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }

   @Override
   String getJobName() {
      return "'Push' new commits to SVN";
   }
}