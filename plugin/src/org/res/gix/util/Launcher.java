package org.res.gix.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;

import org.eclipse.core.runtime.IProgressMonitor;

public class Launcher {
   
   private static final int COMPLETION_CHECK_MILLIS = 50;
   private static final int STREAMING_TEXT_BUFFER_SIZE = 10;

   private final ConsoleWriter console;
   private final ProcessBuilder builder;
   /**
    * Handles process launching.  Process output goes to console
    * 
    * @param repoRoot the repository root.
    * @param console where to show the process output
    * @param args Git command arguments
    */
   public Launcher(File repoRoot, ConsoleWriter console, String... args) {
      this(repoRoot, console, null, args);
   }
   
   /**
    * Handles process launching.  Process output goes to a file
    * 
    * @param repoRoot the repository root.
    * @param saveTo where to show the process output
    * @param args Git command arguments
    */
   public Launcher(File repoRoot, File saveTo, String... args) {
      this(repoRoot, null, saveTo, args);
   }
   
   private Launcher(File repoRoot, ConsoleWriter console, File saveTo,  String... args) {
      this.console = console;
      builder = new ProcessBuilder(args);
      if (saveTo != null) {
         builder.redirectOutput(saveTo);
      }
      builder.redirectErrorStream(true);
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
         if (builder.redirectInput() == Redirect.PIPE) {
            new ConsoleRedirect(process).start();
         }
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
            Thread.sleep(COMPLETION_CHECK_MILLIS);
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

   private  class ConsoleRedirect
         extends Thread {
      final byte[] buffer = new byte[STREAMING_TEXT_BUFFER_SIZE];
         private final InputStream in;
         
         ConsoleRedirect(Process process) {
            setDaemon(true);
            this.in = new BufferedInputStream(process.getInputStream());
         }
         
         @Override
         public void run() {
            while (true) {
               try {
                  int count = in.read(buffer);
                  if (count < 0) {
                     return;
                  } else if (count > 0) {
                     offerText(count);
                  }
               } catch (IOException e) {
                 return;
               }
            }
         }

         void offerText(int count)
               throws IOException {
            Sender sender = new Sender(new String(buffer, 0, count));
            console.run(sender);
            /* Sleep in order to give the gui thread a chance to run. A yield is not sufficient. */
            try {
               Thread.sleep(1);
            } catch (InterruptedException e) {
               /* interrupts are ok */
            }
         }
   }
}
