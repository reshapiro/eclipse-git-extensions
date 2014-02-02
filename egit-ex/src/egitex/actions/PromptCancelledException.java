package egitex.actions;

@SuppressWarnings("serial")
class PromptCancelledException
      extends Exception {
   PromptCancelledException() {
      super("Prompt Dialog Cancelled");
   }
}
