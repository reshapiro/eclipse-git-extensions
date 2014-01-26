package egitex.actions;

import java.io.IOException;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

class ConsoleWriter {
   private static final String CONSOLE_NAME = "git-ex-console";
   private final IWorkbenchWindow window;

   ConsoleWriter(IWorkbenchWindow window) {
      this.window = window;
   }

   enum MessageType {
      INFO,
      ERROR
   }
   
   void displayMessage(String message, MessageType type) {
      MessageConsole console = findConsole();
      try (MessageConsoleStream out = console.newMessageStream()) {
         /* TODO: set foreground based on type */
         out.println(message);
      } catch (IOException e) {
         e.printStackTrace();
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
      IWorkbenchPage page = window.getActivePage();
      String id = IConsoleConstants.ID_CONSOLE_VIEW;
      IConsoleView view;
      try {
         view = (IConsoleView) page.showView(id);
         view.display(myConsole);
      } catch (PartInitException e) {
         e.printStackTrace();
      }
   }
}
