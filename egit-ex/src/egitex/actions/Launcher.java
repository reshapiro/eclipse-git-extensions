package egitex.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Launcher {
   private File output;
   private final ProcessBuilder builder;

   Launcher(File repoRoot, String... args) {
      builder = new ProcessBuilder(args);
      builder.directory(repoRoot);
      try {
         output = File.createTempFile("egit-ex", ".txt");
         builder.redirectOutput(output);
         builder.redirectError(output);
      } catch (final IOException e) {
         // fuck you
      }
   }

   void launchAndWait() {
      Process process;
      try {
         process = builder.start();
      } catch (final IOException e) {
         throw new IllegalStateException("Failed to start process");
      }
      while (true) {
         try {
            process.exitValue();
            return;
         } catch (final IllegalThreadStateException e) {
            // keep waiting
         }
      }
   }

   public String getOutput() {
      if (output == null) {
         return "no output";
      }
      final StringBuilder builder = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(new FileReader(output))) {
         String line;
         while ((line = reader.readLine()) != null) {
            builder.append(line);
         }
         return builder.toString();
      } catch (final IOException e) {
         return "";
      } finally {
         output.delete();
      }
   }
}
