package egitex.handlers;


/**
 * Execute the Git operation that will 'fetch' any new commits from SVN on any
 * branch to the corresponding Git branch.
 * 
 * @author reshapiro
 * 
 */
public class SvnFetchCommand
      extends GitCommandHandler {
   
   private static final String[] ARGS = new String[] {
      "svn", "fetch"
   };

   @Override
   String[] getArgs() {
      return ARGS;
   }
   
   @Override
   String getJobName() {
      return "'Fetch' new commits from SVN";
   }
}