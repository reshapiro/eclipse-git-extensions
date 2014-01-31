package egitex.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.eclipse.core.runtime.IProgressMonitor;

class Launcher {
   private File output;
   private final ProcessBuilder builder;
   private IOException launchException;

   Launcher(File repoRoot, String... args) {
      builder = new ProcessBuilder(args);
      builder.directory(repoRoot);
      try {
         output = File.createTempFile("egit-ex", ".txt");
      } catch (IOException e) {
         output = null;
      }
      if (output != null) {
         output.deleteOnExit();
         builder.redirectOutput(output);
         builder.redirectError(output);
      }
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
      } catch (IOException e) {
         launchException = e;
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
         monitor.worked(10);
         /* Still waiting for completion, sleep a bit */
         try {
            Thread.sleep(50);
         } catch (InterruptedException e) {
            /* interrupts aren't relevant here. */
         }
      }
   }
   
   String getOutput() {
      if (launchException != null) {
         return launchException.getMessage();
      }
      if (output == null) {
         return "No output, failed to create temp file";
      }
      StringBuilder fileContentToText = new StringBuilder();
      try (Reader reader = new BufferedReader(new FileReader(output))) {
         char[] buffer = new char[1000];
         int bytesRead = 0;
         while (bytesRead >= 0) {
            bytesRead = reader.read(buffer);
            if (bytesRead > 0) {
               fileContentToText.append(buffer, 0, bytesRead);
            }
         }
         return fileContentToText.toString();
      } catch (IOException e) {
         return "Failed to read process output";
      } finally {
         output.delete();
      }
   }
}
