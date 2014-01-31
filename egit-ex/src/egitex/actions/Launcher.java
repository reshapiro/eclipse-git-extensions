package egitex.actions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;

class Launcher {
   private final ConsoleWriter messages;
   private final ProcessBuilder builder;

   Launcher(File repoRoot, ConsoleWriter messages, String... args) {
      this.messages = messages;
      builder = new ProcessBuilder(args);
      builder.directory(repoRoot);
   }

   /**
    * Launch a process to run the Git command. Wait for completion or
    * cancellation.
    * 
    * The waiting does not use process.waitFor() by design, since it does not
    * want to block the thread.
    * 
    * @param monitor used to check for cancellation.
    */
   void launchAndWait(IProgressMonitor monitor) {
      Process process;
      try {
         process = builder.start();
         new Writer(monitor, process.getInputStream()).start();
      } catch (IOException e) {
         return;
      }
      while (true) {
         /* First check for user cancellation. */
         if (monitor.isCanceled()) {
            /* User cancelled.  Kill the process and exit */
            process.destroy();
            break;
         }
         /* Next check for process completion via side-effect. */
         try {
            process.exitValue();
            /* If we get here the process is done */
            break;
         } catch (IllegalThreadStateException e) {
            /* If we get here the launched process is still running */
         }
         /* Still waiting for completion, sleep a bit */
         try {
            Thread.sleep(50);
         } catch (InterruptedException e) {
            /* interrupts aren't relevant here. */
         }
      }
   }
   
   private final class Sender
         implements Runnable {
      
      private final String message;
      
      Sender(String message) {
         this.message = message;
      }

      @Override
      public void run() {
         messages.displayMessage(message);
      }
      
   }
   

   private final class Writer
         extends Thread {
         private final byte[] buffer = new byte[50];
         final private InputStream in;
         Writer(IProgressMonitor monitor, InputStream in) {
            setDaemon(true);
            this.in = in;
         }
         
         @Override
         public void run() {
            while (true) {
               try {
                  int count = in.read(buffer);
                  if (count < 0) {
                     return;
                  } else if (count > 0) {
                     String text = new String(buffer, 0, count);
                     Display.getDefault().asyncExec(new Sender(text));
                  }
               } catch (IOException e) {
                 return;
               }
            }
         }
   }
}
