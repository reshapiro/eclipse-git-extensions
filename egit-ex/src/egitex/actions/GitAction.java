package egitex.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import egit_ex.Utils;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
abstract class GitAction implements IWorkbenchWindowActionDelegate {
	GitAction() {
	}
	
	abstract String getOperationName();
	abstract String[] getArgs();

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@Override
	public void run(IAction action) {
		String gitExec = Utils.resolveVariable("git_exec");
		String op = getOperationName();
		if (gitExec.isEmpty()) {
			Utils.displayErrorMessage(op, "git_exec is not defined");
			return;
		}
		
		String repoPath = Utils.resolveVariable("git_work_tree");
		if (repoPath.isEmpty()) {
			Utils.displayErrorMessage(op, "No git project is selected");
			return;
		}
		
		File repo = new File(repoPath);
		String[] gitArgs = getArgs();
		String[] fullArgs = new String[gitArgs.length + 1];
		fullArgs[0] = gitExec;
		System.arraycopy(gitArgs, 0, fullArgs, 1, gitArgs.length);
		Launcher launcher = new Launcher(repo, fullArgs);
		launcher.launchAndWait();
		String message = launcher.getOutput();
		Utils.displayInfoMessage(op, message);
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	@Override
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		Utils.window = window;
	}
}