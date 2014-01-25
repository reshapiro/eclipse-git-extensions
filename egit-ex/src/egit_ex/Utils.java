package egit_ex;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public final class Utils {
   private Utils() {
   }

   public static IWorkbenchWindow window;

   public static void displayErrorMessage(String op, String message) {
      MessageDialog.openError(window.getShell(), op, message);
   }

   public static void displayInfoMessage(String op, String message) {
      MessageDialog.openInformation(window.getShell(), op, message);
   }

   public static String resolveVariable(String var) {
      final IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
      try {
         return manager.performStringSubstitution("${" + var + "}");
      } catch (final CoreException e) {
         return ""; //$NON-NLS-1$
      }
   }
}
