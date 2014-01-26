package egitex.actions;

import java.io.IOException;

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

class MessageUtils {
   private static final String CONSOLE_NAME = "git-ex-console";
   
   private final IWorkbenchWindow window;
   
   MessageUtils(IWorkbenchWindow window) {
      this.window = window;
   }

   void displayErrorMessage(String op, String message) {
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

   void displayInfoMessage(String op, String message) {
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

   
   MessageConsole findConsole() {
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

   private void showConsole(MessageConsole myConsole) {
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
