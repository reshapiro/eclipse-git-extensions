package egit_ex.util;

/**
 * Prompt for a reference -- branch, tag, remote-ref, SHA etc.
 * 
 * For now the user must enter the reference as text. Eventually this should
 * include a chooser gui of some kind.
 * 
 * @author reshapiro
 * 
 */
public class RefParameter
      extends Parameter {

   /**
    * Prompt for a required reference with the default label.
    * 
    * @param index which argument in the git command
    */
   public RefParameter(int index) {
      super("Branch, tag or reference", index, true);
   }

   /**
    * Prompt for a required reference with a given label.
    * 
    * @param prompt the label in the dialog
    * @param index which argument in the git command
    */
   public RefParameter(String prompt, int index) {
      super(prompt, index, true);
   }

   /**
    * Prompt for a reference with a given label.
    * @param prompt the label in the dialog.
    * @param index which argument in the git command
    * @param required whether or not this parameter is required.
    */
   public RefParameter(String prompt, int index, boolean required) {
      super(prompt, index, required);
   }
}
