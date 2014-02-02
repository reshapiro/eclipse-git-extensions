package egit_ex.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;

public class Launcher {
   private final ConsoleWriter console;
   private final ProcessBuilder builder;

   /**
    * Handles process launching
    * 
    * @param repoRoot the repository root.
    * @param console where to show the process output
    * @param args Git command arguments
    */
   public Launcher(File repoRoot, ConsoleWriter console, String... args) {
      this.console = console;
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
   public void launchAndWait(IProgressMonitor monitor) {
      Process process;
      try {
         process = builder.start();
         new Writer(process.getInputStream()).start();
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
      final String message;

      Sender(String message) {
         this.message = message;
      }
      @Override
      public void run() {
         console.displayMessage(message);
      }
   }

   private final class Writer
         extends Thread {
         private final byte[] buffer = new byte[50];
         final private InputStream in;
         
         Writer(InputStream in) {
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
                     Sender sender = new Sender(new String(buffer, 0, count));
                     console.run(sender);
                     try {
                        Thread.sleep(1);
                     } catch (InterruptedException e) {
                        /* interrupts are ok */
                     }
                  }
               } catch (IOException e) {
                 return;
               }
            }
         }
   }
}
