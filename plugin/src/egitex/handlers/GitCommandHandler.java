package egitex.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import egit_ex.util.ConsoleWriter;
import egit_ex.util.Launcher;
import egit_ex.util.MissingRequiredParameterException;
import egit_ex.util.ParameterSet;
import egit_ex.util.PromptCancelledException;
import egit_ex.util.SimpleInputDialog;

abstract class GitCommandHandler
      extends AbstractHandler {
   private static final String GIT_EXEC_VAR = "git_exec";
   private static final String EGIT_WORK_TREE_VAR = "git_work_tree";
   private static final String NO_GIT_PROJECT_IS_SELECTED_MSG = "No git project is selected";
   private static final String NO_GIT_EXEC_VAR_MSG = "You must define the String Substitution variable '" + GIT_EXEC_VAR + "'";

   private ConsoleWriter console;
   private Shell shell;

   GitCommandHandler() {
      /* TODO: register with some selection event manager */
   }
   
   /**
    * the command has been executed, so extract extract the needed information
    * from the application context.
    */
   @Override
   public Object execute(ExecutionEvent event)
         throws ExecutionException {
      IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
      this.console = new ConsoleWriter(window);
      this.shell = window.getShell();
      String gitExec = resolveVariable(GIT_EXEC_VAR);
      if (gitExec.isEmpty()) {
         console.displayLine(NO_GIT_EXEC_VAR_MSG);
         return null;
      }

      String repoPath = resolveVariable(EGIT_WORK_TREE_VAR);
      if (repoPath.isEmpty()) {
         console.displayLine(NO_GIT_PROJECT_IS_SELECTED_MSG);
         return null;
      }

      File repo = new File(repoPath);
      String[] gitArgs;
      try {
         gitArgs = getArgs();
         String[] fullArgs = new String[gitArgs.length + 1];
         fullArgs[0] = gitExec;
         System.arraycopy(gitArgs, 0, fullArgs, 1, gitArgs.length);

         Launcher launcher;
         File output = getOutputFile();
         if (output != null) {
            launcher = new Launcher(repo, output, fullArgs);
         } else {
            launcher = new Launcher(repo, console, fullArgs);
         }
         Job job = new GitJob(getJobName(), launcher);
         job.schedule();
      } catch (PromptCancelledException e) {
         /* User cancelled out of prompt dialog */
         return null;
      } catch (MissingRequiredParameterException e) {
         console.displayLine(e.getMessage());
      }

      return null;
   }

   /**
    * 
    * @return the arguments to the Git command
    * @throws PromptCancelledException
    */
   abstract String[] getArgs()
         throws PromptCancelledException, MissingRequiredParameterException;

   Shell getShell() {
      return shell;
   }

   /**
    * 
    * @return the name shown in the progress area.
    */
   abstract String getJobName();
   
   @Override
   public boolean isEnabled() {
      return !resolveVariable(EGIT_WORK_TREE_VAR).isEmpty();
   }

   /**
    * Override to return false if no refresh is required
    * 
    * @return
    */
   boolean touch() {
      return true;
   }

   /**
    * Override to have process output go to a given file
    * 
    * @return
    */
   File getOutputFile() {
      return null;
   }

   void promptForParameters(ParameterSet parameters, String[] args)
         throws PromptCancelledException, MissingRequiredParameterException {
      SimpleInputDialog dialog = new SimpleInputDialog(shell, parameters);
      dialog.create();
      int status = dialog.open();
      if (status == Window.CANCEL) {
         throw new PromptCancelledException();
      }

      parameters.splice(args);
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
    * FIXME this will only reliably refresh one project, though it works fine in
    * debug mode.
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
