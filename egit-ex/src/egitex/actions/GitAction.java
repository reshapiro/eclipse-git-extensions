package egitex.actions;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

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
    * 
    * @return the name shown in the progress area.
    */
   abstract String getJobName();
   
   /**
    * Override to return false if no refresh is required
    * @return
    */
   boolean touch() {
      return true;
   }

   
   /**
    * Run the Git command if possible
    */
   @Override
   public void run(IAction action) {
      String gitExec = resolveVariable(GIT_EXEC_VAR);
      if (gitExec.isEmpty()) {
         messages.displayMessage("You must define the String Substitution variable '" + GIT_EXEC_VAR + "'\n");
         return;
      }

      String repoPath = resolveVariable(EGIT_WORK_TREE_VAR);
      if (repoPath.isEmpty()) {
         messages.displayMessage("No git project is selected\n");
         return;
      }

      File repo = new File(repoPath);
      String[] gitArgs = getArgs(shell);
      if (gitArgs == null) {
         /* Some required parameter wasn't provided */
         messages.displayMessage("Some required parameter wasn't provided\n");
         return;
      }
      String[] fullArgs = new String[gitArgs.length + 1];
      fullArgs[0] = gitExec;
      System.arraycopy(gitArgs, 0, fullArgs, 1, gitArgs.length);
      
      Launcher launcher = new Launcher(repo, messages, fullArgs);
      Job job = new GitJob(getJobName(), launcher);
      job.schedule();
   }

   private String resolveVariable(String var) {
      IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
      try {
         return manager.performStringSubstitution("${" + var + "}");
      } catch (CoreException e) {
         return "";
      }
   }

   /*
    * Refresh each open project.
    * 
    * FIXME this will only reliably refresh one project, though it works fine in debug mode.
    * 
    * Possibly a new progress monitor is needed for each?
    */
   private void refreshWorkspace(IProgressMonitor monitor) {
      for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
         if (project.isOpen()) {
            try {
               project.touch(monitor);
            } catch (CoreException e) {
               // oh well
            }
         }
      }
   }

   /**
    * Selection in the workbench has been changed.
    * Enable the action iff the new selection is in a Git repository.
    * 
    * @see IWorkbenchWindowActionDelegate#selectionChanged
    */
   @Override
   public void selectionChanged(IAction action, ISelection selection) {
      String repoPath = resolveVariable(EGIT_WORK_TREE_VAR);
      action.setEnabled(!repoPath.isEmpty());
   }

   /**
    * We can use this method to dispose of any system resources we previously
    * allocated.
    * 
    * @see IWorkbenchWindowActionDelegate#dispose
    */
   @Override
   public void dispose() {
      /* Don't think there's anything to dispose here */
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
   
   private final class GitJob
         extends Job {
      private final Launcher launcher;
   
      private GitJob(String name, Launcher launcher) {
         super(name);
         this.launcher = launcher;
      }
   
      @Override
      protected IStatus run(IProgressMonitor monitor) {
         monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
         launcher.launchAndWait(monitor);
         boolean canceled = monitor.isCanceled();
         if (!canceled && touch()) {
            refreshWorkspace(monitor);
         }
         monitor.done();
         return canceled ? Status.CANCEL_STATUS : Status.OK_STATUS;
      }
   }
}