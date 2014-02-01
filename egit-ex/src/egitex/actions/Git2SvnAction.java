package egitex.actions;

import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will find the SVN revision for a given Git Commit
 * 
 * @author reshapiro
 * 
 */
public class Git2SvnAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "find-rev",
      ""
   };

   @Override
   String[] getArgs(Shell shell) {
      SimpleInputDialog dialog = new SimpleInputDialog(shell, "Get SVN revision for SHA", "branch, tag or reference");
      dialog.create();
      if (dialog.open() == Window.OK) {
         List<String> inputs = dialog.getInputs();
         if (inputs != null && !inputs.isEmpty()) {
            String sha = inputs.get(0);
            if (sha != null) {
               ARGS[2] = sha;
               return ARGS;
            }
         }
      }
      return null;
   }

   @Override
   String getJobName() {
      return "Display SVN revision for a given SHA";
   }

   @Override
   boolean touch() {
      return false;
   }
}