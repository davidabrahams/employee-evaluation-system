package employees;

import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilities.SWTFactory;
import evaluations.Evaluation;
import evaluations.EvaluationDialogue;

/**
 * A Dialog box that allows the user to input data for a new/edited employee,
 * and then returns that employee.
 * 
 * @author David Abrahams
 * @version 2/5/2013
 * 
 */
public class EmployeeDialogue extends Dialog {

	/**
	 * Creates a Text field with a given message that only allows number inputs
	 * of a certain lengths (used for phone number fields or zip codes; which
	 * have preset lengths)
	 * 
	 * @param t
	 * @param limit
	 * @param message
	 */
	private static void createNumLimited(Text t, int limit, String message) {
		SWTFactory.createNumLimited(t, limit, message, 3, 1);
	}

	/**
	 * Creates a Text field with a given message that only allows number inputs.
	 * 
	 * @param t
	 * @param message
	 */
	private static void createNumOnly(Text t, String message) {
		SWTFactory.createNumOnly(t, message, 3, 1);
	}

	/**
	 * Creates a Text field used for phone number inputs
	 * 
	 * @param t
	 * @param limit
	 * @param message
	 */
	private static void createPhoneText(Text t, int limit, String message) {
		SWTFactory.createNumLimited(t, limit, message, 1, 1);
	}

	/**
	 * Creates a Text field with a given message that allows any input
	 * 
	 * @param t
	 * @param message
	 */
	private static void createText(Text t, String message) {
		SWTFactory.createText(t, message, 3, 1);
	}

	private Shell shell;
	private Employee employee;
	private Evaluation evaluation;
	private Text txtEmpNumb;
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtEmail;
	private Text txtStreet;
	private Text txtCity;
	private Text txtZip;
	private Text txtPhone;
	private Text txtPhone1;
	private Text txtPhone2;
	private Text txtCell;
	private Text txtCell1;
	private Text txtCell2;
	private Combo comboState;
	private Group grpEmployeeData;
	private String employerNum;

	/**
	 * Create the dialog with an inputed parent.
	 * 
	 * @param parent
	 * @wbp.parser.constructor
	 */
	public EmployeeDialogue(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Create/Edit Employee");
	}

	/**
	 * Open the dialog
	 * 
	 * @param e
	 *            the employee to be edited, or null if this dialog is for a new
	 *            employee
	 * @param employerNum
	 *            the number of the employer where this employee is being placed
	 * @return the resulting employee
	 */
	public Employee open(Employee e, String employerNum) {
		this.employerNum = employerNum;
		createContents();
		if (e != null)
			updateFields(e);
		shell.open();
		shell.layout();
		shell.pack();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return employee;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(407, 395);
		shell.setText("New Employee");
		shell.setLayout(new GridLayout(1, false));

		grpEmployeeData = new Group(shell, SWT.NONE);
		grpEmployeeData.setLayout(new GridLayout(4, false));
		grpEmployeeData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));
		grpEmployeeData.setText("Employee Data");

		Label lblEmployeeNumber = new Label(grpEmployeeData, SWT.NONE);
		lblEmployeeNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEmployeeNumber.setText("Employee Number");

		txtEmpNumb = new Text(grpEmployeeData, SWT.BORDER);
		createNumOnly(txtEmpNumb, "12345");

		Label lblFirstName = new Label(grpEmployeeData, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblFirstName.setText("First Name");

		txtFirstName = new Text(grpEmployeeData, SWT.BORDER);
		createText(txtFirstName, "John");

		Label lblLastName = new Label(grpEmployeeData, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblLastName.setText("Last Name");

		txtLastName = new Text(grpEmployeeData, SWT.BORDER);
		createText(txtLastName, "Doe");

		Label lblEmailAddress = new Label(grpEmployeeData, SWT.NONE);
		lblEmailAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEmailAddress.setText("E-mail Address");

		txtEmail = new Text(grpEmployeeData, SWT.BORDER);
		createText(txtEmail, "johndoe@gmail.com");

		Label lblPhoneNumber = new Label(grpEmployeeData, SWT.NONE);
		lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPhoneNumber.setText("Phone Number");

		txtPhone = new Text(grpEmployeeData, SWT.BORDER);
		createPhoneText(txtPhone, 3, "555");

		txtPhone1 = new Text(grpEmployeeData, SWT.BORDER);
		createPhoneText(txtPhone1, 3, "555");

		txtPhone2 = new Text(grpEmployeeData, SWT.BORDER);
		createPhoneText(txtPhone2, 4, "5555");

		Label lblCellNumber = new Label(grpEmployeeData, SWT.NONE);
		lblCellNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCellNumber.setText("Cell Number");

		txtCell = new Text(grpEmployeeData, SWT.BORDER);
		createPhoneText(txtCell, 3, "555");

		txtCell1 = new Text(grpEmployeeData, SWT.BORDER);
		createPhoneText(txtCell1, 3, "555");

		txtCell2 = new Text(grpEmployeeData, SWT.BORDER);
		createPhoneText(txtCell2, 4, "5555");

		Label lblStreetAddress = new Label(grpEmployeeData, SWT.NONE);
		lblStreetAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblStreetAddress.setText("Street Address");

		txtStreet = new Text(grpEmployeeData, SWT.BORDER);
		createText(txtStreet, "123 Sesame Street");

		Label lblCity_1 = new Label(grpEmployeeData, SWT.NONE);
		lblCity_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCity_1.setText("City");

		txtCity = new Text(grpEmployeeData, SWT.BORDER);
		createText(txtCity, "New York");

		Label lblState_1 = new Label(grpEmployeeData, SWT.NONE);
		lblState_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblState_1.setText("State");

		comboState = new Combo(grpEmployeeData, SWT.READ_ONLY);
		comboState.setItems(new String[] { "", "AL", "AK", "AZ", "AR", "CA",
				"CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN",
				"IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
				"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
				"OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
				"VT", "VA", "WA", "WV", "WI", "WY" });
		comboState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 3, 1));

		Label lblZipCode_1 = new Label(grpEmployeeData, SWT.NONE);
		lblZipCode_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblZipCode_1.setText("Zip Code");

		txtZip = new Text(grpEmployeeData, SWT.BORDER);
		createNumLimited(txtZip, 5, "10000");

		Composite composite = new Composite(grpEmployeeData, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				4, 1));

		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		group.setLayout(new GridLayout(3, false));

		Button btnSaveEmployee = new Button(group, SWT.NONE);
		btnSaveEmployee.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		btnSaveEmployee.addSelectionListener(new SelectionAdapter() {
			// Saves the employee by creating the employee field and disposing
			// of the shell, unless there is an empty shell, in which case
			// alerts the user there is an empty shell, and asks if they would
			// still like to create the employee
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!hasEmptyField()
						|| SWTFactory
								.warningOptionMessage(
										shell,
										"One or more data fields are blank. Would you still like to create the employee?",
										"Error: Blank field")) {
					createEmployee();
					shell.dispose();
				}
			}
		});
		btnSaveEmployee.setText("Save Employee");

		Button btnEnterEvaluationData = new Button(group, SWT.NONE);
		btnEnterEvaluationData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		btnEnterEvaluationData.addSelectionListener(new SelectionAdapter() {
			// opens the EvaluationDialog and assigns this employee's evaluation
			// to the resulting evaluation.
			@Override
			public void widgetSelected(SelectionEvent e) {
				Evaluation temp = new EvaluationDialogue(shell).open(
						evaluation, employerNum, txtEmpNumb.getText());
				if (temp != null) {
					evaluation = temp;
				}
			}
		});
		btnEnterEvaluationData.setText("Edit Evaluation Data");

		Button btnCancel = new Button(group, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			// close the Dialog; the resulting employee is null
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCancel.setText("Cancel");

		shell.setDefaultButton(btnSaveEmployee);
	}

	/**
	 * takes the values from the input fields and creates an employee with those
	 * specified values
	 */
	private void createEmployee() {
		// if the evaluation has not been created, assigns this employee's
		// evaluation to an empty evaluation.
		if (evaluation == null) {
			evaluation = new Evaluation("", txtEmpNumb.getText(),
					this.employerNum, new GregorianCalendar(),
					new GregorianCalendar(), 0, "", 0, "", 0, "", 0, "", 0, 0,
					"", false);
		} // otherwise, updates the created evaluation with the new employee
			// number
		else {
			evaluation.setEmployeeNumb(txtEmpNumb.getText());
		}

		employee = new Employee(txtEmpNumb.getText(), txtFirstName.getText(),
				txtLastName.getText(), txtEmail.getText(), txtPhone.getText(),
				txtPhone1.getText(), txtPhone2.getText(), txtCell.getText(),
				txtCell1.getText(), txtCell2.getText(), txtStreet.getText(),
				txtCity.getText(), comboState.getText(), txtZip.getText(),
				evaluation);
	}

	/**
	 * @return if any data fields are empty
	 */
	private boolean hasEmptyField() {
		Text[] stringArray = new Text[] { txtEmpNumb, txtFirstName,
				txtLastName, txtEmail, txtPhone, txtPhone1, txtPhone2, txtCell,
				txtCell1, txtCell2, txtStreet, txtCity, txtZip };
		for (Text t : stringArray) {
			if (t.getText().length() == 0)
				return true;
		}
		return comboState.getText().length() == 0;
	}

	/**
	 * Assigns data entry fields to the values in the input employee
	 * 
	 * @param e
	 */
	private void updateFields(Employee e) {
		txtEmpNumb.setText(e.getEmployeeNumb());
		txtFirstName.setText(e.getFirstName());
		txtLastName.setText(e.getLastName());
		txtEmail.setText(e.getEmailAddress());
		txtStreet.setText(e.getStreetAddress());
		txtCity.setText(e.getCity());
		txtZip.setText(e.getZipCode());
		txtPhone.setText(e.getPhoneNumb());
		txtPhone1.setText(e.getPhoneNumb1());
		txtPhone2.setText(e.getPhoneNumb2());
		txtCell.setText(e.getCellNumb());
		txtCell1.setText(e.getCellNumb1());
		txtCell2.setText(e.getCellNumb2());
		if (!e.getState().equals(""))
			comboState.setText(e.getState());
		evaluation = e.getEvaluation();
	}

}
