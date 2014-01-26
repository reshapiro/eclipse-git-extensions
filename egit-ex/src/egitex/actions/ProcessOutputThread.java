package egitex.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import egitex.actions.ConsoleWriter.MessageType;

class ProcessOutputThread
      extends Thread {
   private final Launcher launcher;
   private final InputStream in;
   private final MessageType type;

   ProcessOutputThread(Launcher launcher, InputStream in, MessageType type) {
      setDaemon(true);
      this.launcher = launcher;
      this.in = in;
      this.type = type;
   }
   
   @Override
   public void run() {
      try (BufferedReader rdr = new BufferedReader(new InputStreamReader(in))) {
         String line;
         while ((line = rdr.readLine()) != null) {
            launcher.displayMessage(line, type);
         }
      } catch (IOException e) {
         /* just quit */
         return;
      }
   }
}
