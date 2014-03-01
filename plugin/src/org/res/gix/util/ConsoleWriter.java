package org.res.gix.util;

import java.io.IOException;

import org.eclipse.swt.widgets.Display;
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

/**
 * Handles all output to the console.
 * @author reshapiro
 *
 */
public class ConsoleWriter {
   private final IWorkbenchWindow window;
   private final Display display;

   public ConsoleWriter(IWorkbenchWindow window) {
      this.window = window;
      this.display = Display.getDefault();
   }

   /**
    * Display a single line to the console
    * @param line typically an error message.
    */
   public void displayLine(String line) {
      MessageConsole console = findConsole();
      try (MessageConsoleStream out = console.newMessageStream()) {
         out.println(line);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   void run(Runnable runnable) {
      display.asyncExec(runnable);
   }
   
   void displayMessage(String message) {
      MessageConsole console = findConsole();
      try (MessageConsoleStream out = console.newMessageStream()) {
         out.print(message);
         out.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   MessageConsole findConsole() {
      String name = "";
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
         view = (IConsoleView) page.showView(id, null, IWorkbenchPage.VIEW_VISIBLE);
         view.display(myConsole);
      } catch (PartInitException e) {
         e.printStackTrace();
      }
   }
}