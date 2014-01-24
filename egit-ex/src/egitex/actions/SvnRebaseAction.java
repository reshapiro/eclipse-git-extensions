package egitex.actions;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SvnRebaseAction
		extends GitAction
		
		implements IWorkbenchWindowActionDelegate {
	private static final String OPERATION_NAME = "pull from svn";
	private static final String[] ARGS = new String[] {
		"svn", "rebase"
	};

	@Override
	String getOperationName() {
		return OPERATION_NAME;
	}

	@Override
	String[] getArgs() {
		return ARGS;
	}
}