package egitex.actions;

import java.io.IOException;

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
   private MessageConsole console;

   MessageUtils() {
      console = findConsole();
   }

   enum MessageType {
      INFO,
      ERROR
   }
   
   void displayMessage(String message, MessageType type) {
      try (MessageConsoleStream out = console.newMessageStream()) {
         /* TODO: set foreground based on type */
         out.println(message);
      } catch (IOException e) {
         /* ignore for now */
      }
   }

   MessageConsole findConsole() {
      String name = CONSOLE_NAME;
      ConsolePlugin plugin = ConsolePlugin.getDefault();
      IConsoleManager conMan = plugin.getConsoleManager();
      IConsole[] existing = conMan.getConsoles();
      for (IConsole element : existing) {
         if (name.equals(element.getName())) {
            MessageConsole messageConsole = (MessageConsole) element;
            showConsole(messageConsole);
            return messageConsole;
         }
      }
      // no console found, so create a new one
      MessageConsole myConsole = new MessageConsole(name, null);
      conMan.addConsoles(new IConsole[] {
         myConsole
      });
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
