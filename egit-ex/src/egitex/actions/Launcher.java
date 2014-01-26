package egitex.actions;

import java.io.File;
import java.io.IOException;

import egitex.actions.ConsoleWriter.MessageType;

class Launcher {
   private final ProcessBuilder builder;
   private final ConsoleWriter messages;

   Launcher(File repoRoot, ConsoleWriter messages, String... args) {
      this.messages = messages;
      builder = new ProcessBuilder(args);
      builder.directory(repoRoot);
   }

   void launchAndWait() {
      Process process;
      try {
         process = builder.start();
      } catch (IOException e) {
         throw new IllegalStateException("Failed to start process");
      }
      new ProcessOutputThread(messages, process.getInputStream(), MessageType.INFO).start();
      new ProcessOutputThread(messages, process.getErrorStream(), MessageType.ERROR).start();
      while (true) {
         try {
            process.exitValue();
            return;
         } catch (IllegalThreadStateException e) {
            // keep waiting
         }
      }
   }
}
