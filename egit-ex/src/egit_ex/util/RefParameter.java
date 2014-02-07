package egit_ex.util;

public class RefParameter
      extends Parameter {

   /**
    * Simple form of prompting the user for a required reference.
    * For now the user must enter the reference manually.
    * 
    * Eventually this should include a chooser gui of some kind.
    * 
    * @param index which argument in the git command
    */
   public RefParameter(int index) {
      this("Branch, tag or reference", index);
   }
   
   /**
    * Simple form of prompting the user for a required reference.
    * For now the user must enter the reference manually.
    * 
    * Eventually this should include a chooser gui of some kind.
    * 
    * @param prompt the label in the dialog
    * 
    * @param index which argument in the git command
    */
   public RefParameter(String prompt, int index) {
      super(prompt, index, true);
   }
}
