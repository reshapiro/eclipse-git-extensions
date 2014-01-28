package egitex.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class Launcher {
   private File output;
   private final ProcessBuilder builder;

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

   void launchAndWait() {
      Process process;
      try {
         process = builder.start();
      } catch (IOException e) {
         throw new IllegalStateException("Failed to start process");
      }
      while (true) {
         try {
            process.waitFor();
            return;
         } catch (InterruptedException e) {
            /* keep waiting. */
         }
      }
   }
   
   String getOutput() {
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
