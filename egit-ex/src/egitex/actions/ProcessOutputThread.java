package egitex.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import egitex.actions.MessageUtils.MessageType;

class ProcessOutputThread
      extends Thread {
   private final MessageUtils messages;
   private final InputStream in;
   private final MessageType type;

   ProcessOutputThread(MessageUtils messages, InputStream in, MessageType type) {
      setDaemon(true);
      this.messages = messages;
      this.in = in;
      this.type = type;
   }
   
   @Override
   public void run() {
      try (BufferedReader rdr = new BufferedReader(new InputStreamReader(in))) {
         String line;
         while ((line = rdr.readLine()) != null) {
            messages.displayMessage(line, type);
         }
      } catch (IOException e) {
         /* just quit */
         return;
      }
   }
}