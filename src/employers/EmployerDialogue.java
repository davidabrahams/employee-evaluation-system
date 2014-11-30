package employers;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilities.SWTFactory;
import employees.Employee;

public class EmployerDialogue extends Dialog {

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

	private static void createPhoneText(Text t, int limit, String message) {
		SWTFactory.createNumLimited(t, limit, message, 1, 1);
	}

	private static void createText(Text t, String message) {
		SWTFactory.createText(t, message, 3, 1);
	}

	private Employer employer;
	private ArrayList<Employee> employees;
	private Shell shell;
	private Text txtEmpNumb;
	private Text txtCompName;
	private Text txtStreetAddress;
	private Text txtCity;
	private Text txtZip;
	private Text txtPhone;
	private Text txtPhone1;
	private Text txtPhone2;
	private Text txtEmail;
	private Text txtContactPerson;
	private Combo comboState;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public EmployerDialogue(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Create/Edit Employer");
	}

	/**
	 * Open the dialog.
	 * 
	 * @param e
	 *            the employer to be edited, null if this dialogue is for a new
	 *            employee
	 * @return the resulting employer
	 */
	public Employer open(Employer e) {
		createContents();
		if (e != null) {
			updateFields(e);
		}
		shell.open();
		shell.layout();
		shell.pack();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return employer;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 364);
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, false));

		Group grpEmployerData = new Group(shell, SWT.NONE);
		grpEmployerData.setLayout(new GridLayout(4, false));
		grpEmployerData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		grpEmployerData.setText("Employer Data");

		Label lblEmployerNumber = new Label(grpEmployerData, SWT.NONE);
		lblEmployerNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEmployerNumber.setText("Employer Number");

		txtEmpNumb = new Text(grpEmployerData, SWT.BORDER);
		createNumOnly(txtEmpNumb, "12345");

		Label lblCompanyName = new Label(grpEmployerData, SWT.NONE);
		lblCompanyName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCompanyName.setText("Company Name");

		txtCompName = new Text(grpEmployerData, SWT.BORDER);
		createText(txtCompName, "Cisco Systems");

		Label lblStreetAddress = new Label(grpEmployerData, SWT.NONE);
		lblStreetAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblStreetAddress.setText("Street Address");

		txtStreetAddress = new Text(grpEmployerData, SWT.BORDER);
		createText(txtStreetAddress, "170 W Tasman Dr");

		Label lblCity = new Label(grpEmployerData, SWT.NONE);
		lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblCity.setText("City");

		txtCity = new Text(grpEmployerData, SWT.BORDER);
		createText(txtCity, "San Jose");

		Label lblState = new Label(grpEmployerData, SWT.NONE);
		lblState.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblState.setText("State");

		comboState = new Combo(grpEmployerData, SWT.READ_ONLY);
		comboState.setItems(new String[] { "", "AL", "AK", "AZ", "AR", "CA",
				"CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN",
				"IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
				"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
				"OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
				"VT", "VA", "WA", "WV", "WI", "WY" });
		comboState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));

		Label lblZipcode = new Label(grpEmployerData, SWT.NONE);
		lblZipcode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblZipcode.setText("Zip Code");

		txtZip = new Text(grpEmployerData, SWT.BORDER);
		createNumLimited(txtZip, 5, "95134");

		Label lblPhoneNumber = new Label(grpEmployerData, SWT.NONE);
		lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPhoneNumber.setText("Phone Number");

		txtPhone = new Text(grpEmployerData, SWT.BORDER);
		createPhoneText(txtPhone, 3, "555");

		txtPhone1 = new Text(grpEmployerData, SWT.BORDER);
		createPhoneText(txtPhone1, 3, "555");

		txtPhone2 = new Text(grpEmployerData, SWT.BORDER);
		createPhoneText(txtPhone2, 4, "5555");

		Label lblEmailAddress = new Label(grpEmployerData, SWT.NONE);
		lblEmailAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEmailAddress.setText("E-mail Address");

		txtEmail = new Text(grpEmployerData, SWT.BORDER);
		createText(txtEmail, "johndoe@cisco.com");

		Label lblContactPerson = new Label(grpEmployerData, SWT.NONE);
		lblContactPerson.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblContactPerson.setText("Contact Person");

		txtContactPerson = new Text(grpEmployerData, SWT.BORDER);
		createText(txtContactPerson, "John Doe");

		Group group = new Group(shell, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1));

		Button btnSaveEmployer = new Button(group, SWT.NONE);
		btnSaveEmployer.addSelectionListener(new SelectionAdapter() {
			// Saves the employer by creating the employer field and disposing
			// of the shell, unless there is an empty shell, in which case
			// alerts the user there is an empty shell, and asks if they would
			// still like to create the employer
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isValid()) {
					createEmployer();
					shell.dispose();
				}
			}
		});
		btnSaveEmployer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		btnSaveEmployer.setText("Save Employer");

		Button btnCancel = new Button(group, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			// Closes the Dialog; the result of the Dialog will be null
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCancel.setText("Cancel");

		shell.setDefaultButton(btnSaveEmployer);

	}

	/**
	 * takes the values from the input fields and creates an employer with those
	 * specified values
	 */
	private void createEmployer() {
		employer = new Employer(this.txtEmpNumb.getText(),
				this.txtCompName.getText(), this.txtStreetAddress.getText(),
				this.txtCity.getText(), this.comboState.getText(),
				this.txtZip.getText(), this.txtPhone.getText(),
				this.txtPhone1.getText(), this.txtPhone2.getText(),
				this.txtEmail.getText(), this.txtContactPerson.getText(),
				employees);
	}

	/**
	 * @return if any data fields are empty
	 */
	private boolean hasEmptyField() {
		Text[] stringArray = new Text[] { txtEmpNumb, txtCompName,
				txtStreetAddress, txtCity, txtZip, txtPhone, txtPhone1,
				txtPhone2, txtEmail, txtContactPerson };
		for (Text t : stringArray) {
			if (t.getText().length() == 0)
				return true;
		}
		return comboState.getText().length() == 0;
	}

	/**
	 * Checks if there are any empty fields, if there are, prompts the user if
	 * they would still like to create the employer.
	 * 
	 * @return whether all data fields are valid
	 */
	public boolean isValid() {
		if (hasEmptyField()) {
			return SWTFactory
					.warningOptionMessage(
							shell,
							"One or more data fields are blank. Would you still like to create the employer?",
							"Error: Blank field");
		}
		return true;
	}

	/**
	 * Assigns data entry fields to the values in the input employee
	 * 
	 * @param e
	 */
	private void updateFields(Employer e) {
		employees = e.getEmployees();
		txtEmpNumb.setText(e.getEmployerNumb());
		txtCompName.setText(e.getCompanyName());
		txtStreetAddress.setText(e.getStreetAddress());
		txtCity.setText(e.getCity());
		txtZip.setText(e.getZipCode());
		txtPhone.setText(e.getPhoneNumb());
		txtPhone1.setText(e.getPhoneNumb1());
		txtPhone2.setText(e.getPhoneNumb2());
		txtEmail.setText(e.getEmailAddress());
		txtContactPerson.setText(e.getContactPerson());
		if (!e.getState().equals(""))
			comboState.setText(e.getState());
		employees = e.getEmployees();
	}

}
