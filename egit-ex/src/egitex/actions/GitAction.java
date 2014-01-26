package egitex.actions;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.Utils;

/**
 * Base class for all Git commands of interest
 * 
 * @author reshapiro
 * 
 */
abstract class GitAction
      implements IWorkbenchWindowActionDelegate {
   private static final String GIT_EXEC_VAR = "git_exec";
   private static final String EGIT_WORK_TREE_VAR = "git_work_tree";

   GitAction() {
   }

   /**
    * 
    * @return a string that will be used as the title of the displayed status or
    *         error message
    */
   abstract String getOperationName();

   /**
    * 
    * @return the arguments to the Git command
    */
   abstract String[] getArgs();

   /**
    * The action has been activated. The argument of the method represents the
    * 'real' action sitting in the workbench UI.
    * 
    * @see IWorkbenchWindowActionDelegate#run
    */
   @Override
   public void run(IAction action) {
      final String gitExec = Utils.resolveVariable(GIT_EXEC_VAR);
      final String op = getOperationName();
      if (gitExec.isEmpty()) {
         Utils.displayErrorMessage(op, "You must define the String Substitution variable '" + GIT_EXEC_VAR + "'");
         return;
      }

      final String repoPath = Utils.resolveVariable(EGIT_WORK_TREE_VAR);
      if (repoPath.isEmpty()) {
         Utils.displayErrorMessage(op, "No git project is selected");
         return;
      }

      final File repo = new File(repoPath);
      final String[] gitArgs = getArgs();
      final String[] fullArgs = new String[gitArgs.length + 1];
      fullArgs[0] = gitExec;
      System.arraycopy(gitArgs, 0, fullArgs, 1, gitArgs.length);
      final Launcher launcher = new Launcher(repo, fullArgs);
      launcher.launchAndWait();
      String message = launcher.getOutput();
      try {
         refreshWorkspace();
      } catch (CoreException e) {
         message = message + e.getMessage();
      }
      Utils.displayInfoMessage(op, message);
   }

   private void refreshWorkspace() throws CoreException {
      for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
         project.refreshLocal(IResource.DEPTH_ZERO, null);
      }
   }
   
   /**
    * Selection in the workbench has been changed. We can change the state of
    * the 'real' action here if we want, but this can only happen after the
    * delegate has been created.
    * 
    * @see IWorkbenchWindowActionDelegate#selectionChanged
    */
   @Override
   public void selectionChanged(IAction action, ISelection selection) {
      /* Don't currently need to do anything here */
   }

   /**
    * We can use this method to dispose of any system resources we previously
    * allocated.
    * 
    * @see IWorkbenchWindowActionDelegate#dispose
    */
   @Override
   public void dispose() {
      /* Don't currently need to do anything here */
   }

   /**
    * We will cache window object in order to be able to provide parent shell
    * for the message dialog.
    * 
    * @see IWorkbenchWindowActionDelegate#init
    */
   @Override
   public void init(IWorkbenchWindow window) {
      Utils.window = window;
   }
}