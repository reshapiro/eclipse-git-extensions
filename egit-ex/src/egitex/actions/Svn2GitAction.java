package egitex.actions;

import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Execute the Git operation that will show the Git SHA for a given SVN revision.
 * 
 * @author reshapiro
 * 
 */
public class Svn2GitAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "svn",
      "find-rev",
      ""
   };

   @Override
   String[] getArgs(Shell shell) {
      SimpleInputDialog dialog = new SimpleInputDialog(shell, "Get Git commit for SVN revison", "rev");
      dialog.create();
      if (dialog.open() == Window.OK) {
         List<String> inputs = dialog.getInputs();
         if (inputs != null && !inputs.isEmpty()) {
            String rev = inputs.get(0);
            if (rev != null) {
               ARGS[2] = "r"+rev;
               return ARGS;
            }
         }
      }
      return null;
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