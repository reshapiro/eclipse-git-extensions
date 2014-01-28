package egitex.actions;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egitex.actions.ConsoleWriter.MessageType;

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

   private ConsoleWriter messages;
   private Shell shell;

   GitAction() {
   }

   /**
    * 
    * @param activeShell use this to prompt for args when necessary
    * @return the arguments to the Git command
    */
   abstract String[] getArgs(Shell activeShell);

   /**
    * The action has been activated. The argument of the method represents the
    * 'real' action sitting in the workbench UI.
    * 
    * @see IWorkbenchWindowActionDelegate#run
    */
   @Override
   public void run(IAction action) {
      String gitExec = resolveVariable(GIT_EXEC_VAR);
      if (gitExec.isEmpty()) {
         messages.displayMessage("You must define the String Substitution variable '" + GIT_EXEC_VAR + "'", MessageType.ERROR);
         return;
      }

      String repoPath = resolveVariable(EGIT_WORK_TREE_VAR);
      if (repoPath.isEmpty()) {
         messages.displayMessage("No git project is selected", MessageType.ERROR);
         return;
      }

      File repo = new File(repoPath);
      String[] gitArgs = getArgs(shell);
      if (gitArgs == null) {
         /* Some required parameter wasn't provided */
         messages.displayMessage("Some required parameter wasn't provided", MessageType.ERROR);
         return;
      }
      String[] fullArgs = new String[gitArgs.length + 1];
      fullArgs[0] = gitExec;
      System.arraycopy(gitArgs, 0, fullArgs, 1, gitArgs.length);
      Launcher launcher = new Launcher(repo, fullArgs);
      launcher.launchAndWait();
      messages.displayMessage(launcher.getOutput(), MessageType.INFO);
      try {
         refreshWorkspace();
      } catch (CoreException e) {
         /* ignore for now */
      }
   }

   private String resolveVariable(String var) {
      IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
      try {
         return manager.performStringSubstitution("${" + var + "}");
      } catch (CoreException e) {
         return "";
      }
   }

   private void refreshWorkspace()
         throws CoreException {
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
      this.messages = new ConsoleWriter(window);
      this.shell = window.getShell();
   }
}