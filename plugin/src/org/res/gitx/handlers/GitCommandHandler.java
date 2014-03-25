package org.res.gitx.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.res.gitx.parameter.MissingRequiredParameterException;
import org.res.gitx.parameter.Parameter;
import org.res.gitx.parameter.ParameterSet;
import org.res.gitx.parameter.ParametersDialog;
import org.res.gitx.parameter.PromptCancelledException;
import org.res.gitx.util.ConsoleWriter;
import org.res.gitx.util.Launcher;
import org.res.gitx.util.Resolver;

abstract class GitCommandHandler
      extends AbstractHandler {
   private static final String GIT_EXEC_VAR = "git_exec";
   private static final String EGIT_WORK_TREE_VAR = "git_work_tree";
   private static final String NO_GIT_PROJECT_IS_SELECTED_MSG = "Current selection is not in a repository managed by EGit";
   private static final String NO_GIT_EXEC_VAR_MSG = "You must define the String Substitution variable '" + GIT_EXEC_VAR + "'";

   private ConsoleWriter console;
   private Shell shell;
   private final List<String> args = new ArrayList<>();

   /**
    * 
    * @throws PromptCancelledException
    */
   abstract void getArgs()
         throws PromptCancelledException, MissingRequiredParameterException;

   GitCommandHandler append(String... arguments) {
      for (String arg : arguments) {
         args.add(arg);
      }
      return this;
   }

   GitCommandHandler append(ParameterSet params, Parameter... arguments) {
      for (Parameter param : arguments) {
         args.add(params.getParameterValue(param));
      }
      return this;
   }

   @Override
   public boolean isEnabled() {
      return !Resolver.resolveVariable(EGIT_WORK_TREE_VAR).isEmpty();
   }

   @Override
   public Object execute(ExecutionEvent event)
         throws ExecutionException {
      ensureConsole(event);
      console.clear();
      String gitExec = Resolver.resolveVariable(GIT_EXEC_VAR);
      if (gitExec.isEmpty()) {
         console.displayLine(NO_GIT_EXEC_VAR_MSG);
         return null;
      }

      String repoPath = Resolver.resolveVariable(EGIT_WORK_TREE_VAR);
      if (repoPath.isEmpty()) {
         console.displayLine(NO_GIT_PROJECT_IS_SELECTED_MSG);
         return null;
      }

      File repo = new File(repoPath);
      args.clear();
      args.add(gitExec);
      try {
         getArgs();
         console.displayCommand(args);
         String[] processArgs = new String[args.size()];
         args.toArray(processArgs);

         Launcher launcher;
         File output = getOutputFile();
         if (output != null) {
            launcher = new Launcher(repo, output, processArgs);
         } else {
            launcher = new Launcher(repo, console, processArgs);
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

   private void ensureConsole(ExecutionEvent event)
         throws ExecutionException {
      if (console == null) {
         IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
         console = new ConsoleWriter(window);
         shell = window.getShell();
      }
   }

   /**
    * 
    * @return the name shown in the progress area.
    */
   abstract String getJobName();

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

   void promptForParameters(ParameterSet parameters)
         throws PromptCancelledException, MissingRequiredParameterException {
      ParametersDialog dialog = new ParametersDialog(shell, parameters);
      dialog.create();
      int status = dialog.open();
      if (status == Window.CANCEL) {
         throw new PromptCancelledException();
      }
      parameters.checkRequirements();
   }

   /*
    * Refresh open projects. Possibly a new progress monitor is needed for each?
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
