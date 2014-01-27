package egitex.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import egitex.actions.ConsoleWriter.MessageType;

class Launcher {
   private final ProcessBuilder builder;
   private final ConsoleWriter messageWriter;
   private final List<Message> pendingMessages = new ArrayList<>();
   private final Object waitLock = "wait for process completion";
   private boolean processCompleted;
   private Process process;

   Launcher(File repoRoot, ConsoleWriter messages, String... args) {
      this.messageWriter = messages;
      builder = new ProcessBuilder(args);
      builder.directory(repoRoot);
   }

   void launchAndWait() {
      try {
         process = builder.start();
      } catch (IOException e) {
         throw new IllegalStateException("Failed to start process");
      }
      new Checker().start();
      new ProcessOutputThread(this, process.getInputStream(), MessageType.INFO).start();
      new ProcessOutputThread(this, process.getErrorStream(), MessageType.ERROR).start();
      
      synchronized (waitLock) {
         while (!processCompleted) {
            try {
               waitLock.wait();
               showPendingMessages();
               Thread.yield();
            } catch (InterruptedException e) {
               // keep waiting
            }
         }
      }
   }
   
   void displayMessage(String text, ConsoleWriter.MessageType type) {
      synchronized (pendingMessages) {
         pendingMessages.add(new Message(text, type));
      }
   }
   
   private void showPendingMessages() {
      synchronized (pendingMessages) {
         for (Message message : pendingMessages) {
            message.show();
         }
         pendingMessages.clear();
      }
   }
   
   private class Message {
      String message;
      ConsoleWriter.MessageType type;
      
      Message(String message, ConsoleWriter.MessageType type) {
         this.message = message;
         this.type = type;
      }
      
      void show() {
         messageWriter.displayMessage(message, type);
      }
   }
   
   private class Checker
         extends Thread {
      
      Checker() {
         setDaemon(true);
      }
      
      @Override
      public void run() {
         while (!processCompleted) {
            synchronized (waitLock) {
               waitLock.notify();
            }
            if (process != null) {
               try {
                  process.exitValue();
                  processCompleted = true;
               } catch (IllegalThreadStateException e) {
                  // keep waiting
               }
            }
            try {
               Thread.sleep(10);
            } catch (InterruptedException e) {
               // don't care if sleep interrupted
            }
         }
      }
   }
}
