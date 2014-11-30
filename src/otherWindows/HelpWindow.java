package otherWindows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilities.FileHandler;

public class HelpWindow {

	private Shell shell;
	private Text text;

	/**
	 * Open the window with an inputed parent.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open(Shell parent) {
		Display display = Display.getDefault();
		createContents(parent);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	private void createContents(Shell parent) {
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE
				| SWT.APPLICATION_MODAL);
		shell.setSize(600, 500);
		shell.setText("Instructions");
		shell.setLayout(new GridLayout(2, false));
		Tree tree = new Tree(shell, SWT.BORDER);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		TreeItem trtmManagingEmployers = new TreeItem(tree, SWT.NONE);
		trtmManagingEmployers.setText("Managing Employers");

		TreeItem trtmCreatingANew = new TreeItem(trtmManagingEmployers,
				SWT.NONE);
		trtmCreatingANew.setText("Creating a New Employer");

		TreeItem trtmModifyingAnExisting = new TreeItem(trtmManagingEmployers,
				SWT.NONE);
		trtmModifyingAnExisting.setText("Modifying an Existing Employer");

		TreeItem trtmReorganizingEmployers = new TreeItem(
				trtmManagingEmployers, SWT.NONE);
		trtmReorganizingEmployers.setText("Reorganizing Employers");

		TreeItem trtmFindingAnEmployer = new TreeItem(trtmManagingEmployers,
				SWT.NONE);
		trtmFindingAnEmployer.setText("Finding an Employer");

		TreeItem trtmDeletingAnEmployer = new TreeItem(trtmManagingEmployers,
				SWT.NONE);
		trtmDeletingAnEmployer.setText("Deleting an Employer");
		trtmManagingEmployers.setExpanded(true);

		TreeItem trtmManagingEmployees = new TreeItem(tree, SWT.NONE);
		trtmManagingEmployees.setText("Managing Employees");

		TreeItem trtmCreatingANew_1 = new TreeItem(trtmManagingEmployees,
				SWT.NONE);
		trtmCreatingANew_1.setText("Creating a New Employee");

		TreeItem trtmModifyingAnExisting_1 = new TreeItem(
				trtmManagingEmployees, SWT.NONE);
		trtmModifyingAnExisting_1.setText("Modifying an Existing Employee");

		TreeItem trtmReorganizingEmployees = new TreeItem(
				trtmManagingEmployees, SWT.NONE);
		trtmReorganizingEmployees.setText("Reorganizing Employees");

		TreeItem trtmFindingAnEmployee = new TreeItem(trtmManagingEmployees,
				SWT.NONE);
		trtmFindingAnEmployee.setText("Finding an Employee");

		TreeItem trtmDeletingAnEmployee = new TreeItem(trtmManagingEmployees,
				SWT.NONE);
		trtmDeletingAnEmployee.setText("Deleting an Employee");

		TreeItem trtmChangingEmployer = new TreeItem(trtmManagingEmployees,
				SWT.NONE);
		trtmChangingEmployer.setText("Changing Employer");
		trtmManagingEmployees.setExpanded(true);

		TreeItem trtmViewingReports = new TreeItem(tree, SWT.NONE);
		trtmViewingReports.setText("Viewing Reports");

		TreeItem trtmFullReport = new TreeItem(trtmViewingReports, SWT.NONE);
		trtmFullReport.setText("Full Report");

		TreeItem trtmByEmployer = new TreeItem(trtmViewingReports, SWT.NONE);
		trtmByEmployer.setText("By Employer");

		TreeItem trtmByEvaluationScore = new TreeItem(trtmViewingReports,
				SWT.NONE);
		trtmByEvaluationScore.setText("By Evaluation Score");

		TreeItem trtmByEmployee = new TreeItem(trtmViewingReports, SWT.NONE);
		trtmByEmployee.setText("By Employee");
		trtmViewingReports.setExpanded(true);

		TreeItem trtmSavingFiles = new TreeItem(tree, SWT.NONE);
		trtmSavingFiles.setText("Saving Files");

		TreeItem trtmSave = new TreeItem(trtmSavingFiles, SWT.NONE);
		trtmSave.setText("Save");

		TreeItem trtmSaveAs = new TreeItem(trtmSavingFiles, SWT.NONE);
		trtmSaveAs.setText("Save As");
		trtmSavingFiles.setExpanded(true);

		TreeItem trtmLoadingFiles = new TreeItem(tree, SWT.NONE);
		trtmLoadingFiles.setText("Loading Files");

		TreeItem trtmLoad = new TreeItem(trtmLoadingFiles, SWT.NONE);
		trtmLoad.setText("Load");
		trtmLoadingFiles.setExpanded(true);

		tree.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// When a table item is selected, read the corresponding txt
				// file and set the text of the text box to that String.
				TreeItem ti = (TreeItem) event.item;
				String s;
				if ((s = (new FileHandler().readFile("resources" + System.getProperty("file.separator") + ti.getText() + ".txt"))) != null) {
					text.setText(s);
				}
				// If the read fails, empty the text box.
				else {
					text.setText("");
				}
			}
		});

		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

	}
}
