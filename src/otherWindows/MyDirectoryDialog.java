package otherWindows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilities.SWTFactory;

/**
 * A Dialog box that prompts the user the chose a file directory to save to/load
 * from.
 * 
 * @author David Abrahams
 * @version 2/5/2013
 * 
 */
public class MyDirectoryDialog extends Dialog {

	private String result;
	private Shell shell;
	private Text txtPath;
	private Button btnbrowse;
	private Button btnSelect;
	private Button btnCancel;

	/**
	 * Create the dialog with an inputed parent.
	 * 
	 * @param parent
	 * 
	 */
	public MyDirectoryDialog(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Choose Folder to Load from/Save To");
	}

	/**
	 * Open the dialog
	 * 
	 * @param directory
	 *            the current directory of the application (null if none
	 *            selected)
	 * @return the new directory
	 */
	public String open(String directory) {
		createContents(directory);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param directory
	 *            the current directory of the application (null if none
	 *            selected)
	 */
	private void createContents(String directory) {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(457, 101);
		shell.setText(getText());
		shell.setLayout(new GridLayout(2, false));

		txtPath = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		if (directory != null)
			txtPath.setText(directory);
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		btnbrowse = new Button(shell, SWT.NONE);
		btnbrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dd = new DirectoryDialog(shell);
				String path = dd.open();
				if (path != null) {
					txtPath.setText(path);
				}
			}
		});
		btnbrowse.setText("&Browse");

		btnSelect = new Button(shell, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			// Selects the directory and closes the window. Prompts the user if
			// there is no directory.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txtPath.getText().length() != 0) {
					result = txtPath.getText();
					shell.dispose();
				} else {
					SWTFactory.errorMessage(shell, "No directory selected",
							"Error: No Directory selected");
				}
			}
		});
		btnSelect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true,
				1, 1));
		btnSelect.setText("&Select");

		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCancel.addSelectionListener(new SelectionAdapter() {
			// closes the dialog, return null as a directory
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
		});
		btnCancel.setText("&Cancel");
	}
}
