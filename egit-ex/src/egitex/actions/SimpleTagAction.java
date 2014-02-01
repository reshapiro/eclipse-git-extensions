package egitex.actions;

import java.util.ArrayList;
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
public class SimpleTagAction
      extends GitAction

      implements IWorkbenchWindowActionDelegate {
   private static final String[] ARGS = new String[] {
      "tag",
      "",
      ""
   };

   @Override
   String[] getArgs(Shell shell) {
      List<String> params = new ArrayList<>(2);
      params.add("Name");
      params.add("Reference");
      SimpleInputDialog dialog = new SimpleInputDialog(shell, "Simple tag", params, null);
      dialog.create();
      if (dialog.open() == Window.OK) {
         List<String> inputs = dialog.getInputs();
         if (inputs != null && inputs.size() == 2) {
            String tag = inputs.get(0);
            String ref = inputs.get(1);
            if (tag != null && ref != null) {
               ARGS[1] = tag;
               ARGS[2] = ref;
               return ARGS;
            }
         }
      }
      return null;
   }

   @Override
   String getJobName() {
      return "Delete obsolete remote refs";
   }

   @Override
   boolean touch() {
      return false;
   }
}