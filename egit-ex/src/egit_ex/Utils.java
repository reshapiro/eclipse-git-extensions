package egit_ex;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public final class Utils {
   private static final String CONSOLE_NAME = "git-ex-console";

   private Utils() {
   }

   public static IWorkbenchWindow window;

   public static void displayErrorMessage(String op, String message) {
      MessageConsole console = findConsole();
      if (console != null) {
         try (MessageConsoleStream out = console.newMessageStream()) {
            /* TODO: set foreground to red ? */
            out.println(message);
            return;
         } catch (IOException e) {
            //  use message dialog instead
         }
         MessageDialog.openError(window.getShell(), op, message);
      }
   }

   public static void displayInfoMessage(String op, String message) {
      MessageConsole console = findConsole();
      if (console != null) {
         try (MessageConsoleStream out = console.newMessageStream()) {
            out.println(message);
            return;
         } catch (IOException e) {
            //  use message dialog instead
         }
         MessageDialog.openInformation(window.getShell(), op, message);
      }
   }

   public static String resolveVariable(String var) {
      final IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
      try {
         return manager.performStringSubstitution("${" + var + "}");
      } catch (final CoreException e) {
         return ""; //$NON-NLS-1$
      }
   }
   
  
   
   private static MessageConsole findConsole() {
      String name = CONSOLE_NAME;
      ConsolePlugin plugin = ConsolePlugin.getDefault();
      IConsoleManager conMan = plugin.getConsoleManager();
      IConsole[] existing = conMan.getConsoles();
      for (int i = 0; i < existing.length; i++)
         if (name.equals(existing[i].getName())) {
            MessageConsole messageConsole = (MessageConsole) existing[i];
            showConsole(messageConsole);
            return messageConsole;
         }
      //no console found, so create a new one
      MessageConsole myConsole = new MessageConsole(name, null);
      conMan.addConsoles(new IConsole[]{myConsole});
      showConsole(myConsole);
      return myConsole;
   }

   private static void showConsole(MessageConsole myConsole) {
      IWorkbench wb = PlatformUI.getWorkbench();
      IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
      IWorkbenchPage page = win.getActivePage();
      String id = IConsoleConstants.ID_CONSOLE_VIEW;
      IConsoleView view;
      try {
         view = (IConsoleView) page.showView(id);
         view.display(myConsole);
      } catch (PartInitException e) {
         // ignore this for now
         e.printStackTrace();
      }
   }
}
