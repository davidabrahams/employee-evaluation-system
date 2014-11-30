package evaluations;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilities.SWTFactory;

/**
 * A Dialog box that allows the user to input data for an employee evaluation,
 * and then returns that evaluation.
 * 
 * @author David Abrahams
 * @version 2/5/2013
 * 
 */
public class EvaluationDialogue extends Dialog {

	private Evaluation evaluation;
	private Shell shell;
	private Text txtEmployee;
	private Text txtEval;
	private Text txtEmployer;
	private Text txtQualityOfWork;
	private Text txtWorkHabbits;
	private Text txtJobKnowlege;
	private Text txtBehaviorRelations;
	private Text txtOverallProgress;
	/**
	 * The max number of characters allowed in comment boxes
	 */
	private final static int MAX_CHARACTERS = 256;
	private Label charRemain;
	private Label charRemain1;
	private Label charRemain2;
	private Label charRemain3;
	private Combo comboQualityOfWork;
	private Combo comboWorkHabbits;
	private Combo comboJobKnowledge;
	private Combo comboBehaviorRelations;
	private Combo comboOverallProgress;
	private Button btnYes;
	private Button btnNo;
	private DateTime dateTimeCurrent;
	private DateTime dateTimeNext;
	private Label charRemain4;
	private Label lblAverage;
	private Group grpEvaluation;
	private String employerNum, employeeNum;

	/**
	 * Create the dialog with an inputed parent.
	 * 
	 * @param parent
	 */
	public EvaluationDialogue(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		setText("Evaluation");
	}

	/**
	 * Open the dialog
	 * 
	 * @param e
	 *            the evaluation to be edited, or null if this dialog is for a
	 *            new evaluation
	 * @param employerNum
	 *            the number of the employer where this evaluation is being
	 *            placed
	 * @param employeeNum
	 *            the number of the employee where this evaluation is being
	 *            placed
	 * @return the resulting evaluation
	 */
	public Evaluation open(Evaluation e, String employerNum, String employeeNum) {
		this.employerNum = employerNum;
		this.employeeNum = employeeNum;
		createContents();
		if (e != null) {
			updateFields(e);
		}
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return evaluation;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(700, 850);
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, false));

		Group grpIdentification = new Group(shell, SWT.NONE);
		grpIdentification.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));
		grpIdentification.setText("Identification");
		grpIdentification.setLayout(new GridLayout(2, false));

		Label lblEvaluationNumber = new Label(grpIdentification, SWT.NONE);
		lblEvaluationNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvaluationNumber.setText("Evaluation Number");

		txtEval = new Text(grpIdentification, SWT.BORDER);
		SWTFactory.createNumOnly(txtEval, "Enter evaluation number", 1, 1);

		Label lblEmployeeNumber = new Label(grpIdentification, SWT.NONE);
		lblEmployeeNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEmployeeNumber.setText("Employee Number");

		txtEmployee = new Text(grpIdentification, SWT.BORDER | SWT.READ_ONLY);
		txtEmployee.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtEmployee
				.setMessage("Automatically copied from the Employee window.");
		txtEmployee.setText(employeeNum);

		Label lblEmployerNumber = new Label(grpIdentification, SWT.NONE);
		lblEmployerNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEmployerNumber.setText("Employer Number");

		txtEmployer = new Text(grpIdentification, SWT.BORDER | SWT.READ_ONLY);
		txtEmployer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtEmployer
				.setMessage("Automatically copied from the field placement.");
		txtEmployer.setText(employerNum);

		grpEvaluation = new Group(shell, SWT.NONE);
		GridLayout gl_grpEvaluation = new GridLayout(3, false);
		grpEvaluation.setLayout(gl_grpEvaluation);
		grpEvaluation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		grpEvaluation.setText("Evaluation");
		new Label(grpEvaluation, SWT.NONE);

		Label lblRating = new Label(grpEvaluation, SWT.NONE);
		lblRating.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblRating.setText("Rating");

		Label lblComments = new Label(grpEvaluation, SWT.NONE);
		lblComments.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblComments.setText("Comments");

		Label label = new Label(grpEvaluation, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3,
				1));

		Label lblQualityOfWork = new Label(grpEvaluation, SWT.NONE);
		lblQualityOfWork.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 2));
		lblQualityOfWork.setText("Quality of Work");

		comboQualityOfWork = new Combo(grpEvaluation, SWT.READ_ONLY);
		createCombo(comboQualityOfWork);

		txtQualityOfWork = new Text(grpEvaluation, SWT.BORDER | SWT.WRAP);
		SWTFactory.createLimitedFill(txtQualityOfWork, MAX_CHARACTERS,
				"Comments here.", 1, 1);

		charRemain = new Label(grpEvaluation, SWT.NONE);
		charRemain.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false,
				1, 1));
		setCharacterCounter(txtQualityOfWork, charRemain);

		Label lblWorkHabbits = new Label(grpEvaluation, SWT.NONE);
		lblWorkHabbits.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 2));
		lblWorkHabbits.setText("Work Habbits");

		comboWorkHabbits = new Combo(grpEvaluation, SWT.READ_ONLY);
		createCombo(comboWorkHabbits);

		txtWorkHabbits = new Text(grpEvaluation, SWT.BORDER | SWT.WRAP);
		SWTFactory.createLimitedFill(txtWorkHabbits, MAX_CHARACTERS,
				"Comments here.", 1, 1);

		charRemain1 = new Label(grpEvaluation, SWT.NONE);
		charRemain1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1));
		setCharacterCounter(txtWorkHabbits, charRemain1);

		Label lblJobKnowledge = new Label(grpEvaluation, SWT.NONE);
		lblJobKnowledge.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 2));
		lblJobKnowledge.setText("Job Knowledge");

		comboJobKnowledge = new Combo(grpEvaluation, SWT.READ_ONLY);
		createCombo(comboJobKnowledge);

		txtJobKnowlege = new Text(grpEvaluation, SWT.BORDER | SWT.WRAP);
		SWTFactory.createLimitedFill(txtJobKnowlege, MAX_CHARACTERS,
				"Comments here.", 1, 1);

		charRemain2 = new Label(grpEvaluation, SWT.NONE);
		charRemain2.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1));
		setCharacterCounter(txtJobKnowlege, charRemain2);

		Label lblBehaviorRelations = new Label(grpEvaluation, SWT.NONE);
		lblBehaviorRelations.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 2));
		lblBehaviorRelations.setText("Behavior/Relations");

		comboBehaviorRelations = new Combo(grpEvaluation, SWT.READ_ONLY);
		createCombo(comboBehaviorRelations);

		txtBehaviorRelations = new Text(grpEvaluation, SWT.BORDER | SWT.WRAP);
		SWTFactory.createLimitedFill(txtBehaviorRelations, MAX_CHARACTERS,
				"Comments here.", 1, 1);

		charRemain3 = new Label(grpEvaluation, SWT.NONE);
		charRemain3.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1));
		setCharacterCounter(txtBehaviorRelations, charRemain3);

		Label label_1 = new Label(grpEvaluation, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				3, 1));

		Label lblEvaluationDate = new Label(grpEvaluation, SWT.NONE);
		lblEvaluationDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblEvaluationDate.setText("Evaluation Date");

		dateTimeCurrent = new DateTime(grpEvaluation, SWT.BORDER | SWT.CALENDAR);

		Composite composite_1 = new Composite(grpEvaluation, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.marginWidth = 0;
		gl_composite_1.marginHeight = 0;
		composite_1.setLayout(gl_composite_1);

		Label lblNextEvaluationDate = new Label(composite_1, SWT.NONE);
		lblNextEvaluationDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblNextEvaluationDate.setText("Next Evaluation Date");

		dateTimeNext = new DateTime(composite_1, SWT.BORDER | SWT.CALENDAR);

		Label lblOverallProgress = new Label(grpEvaluation, SWT.NONE);
		lblOverallProgress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 2));
		lblOverallProgress.setText("Overall Progress");

		comboOverallProgress = new Combo(grpEvaluation, SWT.NONE);
		comboOverallProgress.setItems(new String[] { "5.0", "4.0", "3.0",
				"2.0", "1.0" });
		comboOverallProgress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 2));

		txtOverallProgress = new Text(grpEvaluation, SWT.BORDER | SWT.WRAP);
		SWTFactory.createLimitedFill(txtOverallProgress, MAX_CHARACTERS,
				"Comments here.", 1, 1);

		charRemain4 = new Label(grpEvaluation, SWT.NONE);
		charRemain4.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1));
		setCharacterCounter(txtOverallProgress, charRemain4);

		Label lblAverageScore = new Label(grpEvaluation, SWT.NONE);
		lblAverageScore.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblAverageScore.setText("Average Score:");

		lblAverage = new Label(grpEvaluation, SWT.NONE);
		lblAverage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		lblAverage.setText(getAverage() + "");

		Composite composite = new Composite(grpEvaluation, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);

		Label lblEmploymentRecommendation = new Label(composite, SWT.NONE);
		lblEmploymentRecommendation.setText("Employment Recommendation:");

		btnYes = new Button(composite, SWT.RADIO);
		btnYes.setText("Yes");

		btnNo = new Button(composite, SWT.RADIO);
		btnNo.setSelection(true);
		btnNo.setText("No");

		Composite composite_2 = new Composite(grpEvaluation, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));
		GridLayout gl_composite_2 = new GridLayout(1, false);
		gl_composite_2.marginWidth = 0;
		gl_composite_2.marginHeight = 0;
		composite_2.setLayout(gl_composite_2);

		Group groupButtons = new Group(shell, SWT.NONE);
		groupButtons.setLayout(new GridLayout(2, false));
		groupButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));

		Button btnNewButton = new Button(groupButtons, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			// Saves the evaluation by creating the evaluation and field and
			// disposing of the shell. If there are any blank fields, alerts the
			// user and asks if they would like to create the evaluation anyway.
			// If the overall progress score is invalid, the user is prompted,
			// and if they chose to continue, it is reset to 0.0.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isValid()) {
					createEvaluation();
					shell.dispose();
				}
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		btnNewButton.setText("Create Evaluation");

		Button btnCancel = new Button(groupButtons, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			// closes the Dialog, causes it to return null
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCancel.setText("Cancel");

		shell.setDefaultButton(btnNewButton);

	}

	/**
	 * Makes the input combo have items 5, 4, 3, 2, and 1, and whenever the
	 * input is modified, updates the average text label.
	 * 
	 * @param combo
	 */
	private void createCombo(Combo combo) {
		combo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (lblAverage != null)
					lblAverage.setText(getAverage() + "");
			}
		});
		combo.setItems(new String[] { "", "5", "4", "3", "2", "1" });
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
	}

	/**
	 * takes the values from the input fields and creates an evaluation with
	 * those specified values
	 */
	private void createEvaluation() {

		evaluation = new Evaluation(txtEval.getText(), txtEmployee.getText(),
				txtEmployer.getText(), new GregorianCalendar(
						dateTimeCurrent.getYear(), dateTimeCurrent.getMonth(),
						dateTimeCurrent.getDay()), new GregorianCalendar(
						dateTimeNext.getYear(), dateTimeNext.getMonth(),
						dateTimeNext.getDay()),
				getIntFromCombo(comboQualityOfWork),
				txtQualityOfWork.getText(), getIntFromCombo(comboWorkHabbits),
				txtWorkHabbits.getText(), getIntFromCombo(comboJobKnowledge),
				txtJobKnowlege.getText(),
				getIntFromCombo(comboBehaviorRelations),
				txtBehaviorRelations.getText(), getAverage(),
				getDoubleFromCombo(comboOverallProgress),
				txtOverallProgress.getText(), btnYes.getSelection());
	}

	/**
	 * @return the average of all specified scores (does not take blank scores
	 *         into account)
	 */
	private double getAverage() {
		Combo[] comboArray = new Combo[] { comboQualityOfWork,
				comboWorkHabbits, comboJobKnowledge, comboBehaviorRelations };
		int numerator = 0;
		int denominator = 0;
		for (Combo c : comboArray) {
			if (c != null && c.getText().length() != 0) {
				numerator += Integer.parseInt(c.getText());
				denominator++;
			}
		}
		if (denominator == 0)
			return 0;
		return ((int) (100.0 * numerator / denominator + 0.5)) / 100.0;
	}

	/**
	 * @return a Combo[] of all combos in the Dialog
	 */
	private Combo[] getCombos() {
		return new Combo[] { comboQualityOfWork, comboWorkHabbits,
				comboJobKnowledge, comboBehaviorRelations,
				comboOverallProgress, };
	}

	/**
	 * @param c
	 *            a combo
	 * @return the decimal value of that combo; 0 if the combo is empty
	 */
	private double getDoubleFromCombo(Combo c) {
		if (c.getText().length() == 0)
			return 0;
		else
			return Double.parseDouble(c.getText());
	}

	/**
	 * @param c
	 *            a combo
	 * @return the integer value of that combo; 0 if the combo is empty
	 */
	private int getIntFromCombo(Combo c) {
		try {
			return Integer.parseInt(c.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * @return all Text fields in the dialog
	 */
	private Text[] getTexts() {
		return new Text[] { txtEval, txtEmployer, txtQualityOfWork,
				txtWorkHabbits, txtJobKnowlege, txtBehaviorRelations,
				txtOverallProgress };
	}

	/**
	 * @return if any of the Text or Combo fields are empty
	 */
	private boolean hasEmptyField() {
		for (Text t : getTexts()) {
			if (t.getText().length() == 0)
				return true;
		}
		for (Combo c : getCombos())
			if (c.getText().length() == 0)
				return true;
		return false;
	}

	/**
	 * @return if the overall progress combo could not be parsed to a double.
	 */
	private boolean hasInvalidDecimal() {
		try {
			String temp = comboOverallProgress.getText();
			Double.parseDouble(temp);
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	/**
	 * @return if all input fields are valid, prompting the user if any are
	 *         blank, and setting the overall progress combo to 0.0 if the user
	 *         chooses not to change it if it is invalid.
	 */
	private boolean isValid() {
		if (hasInvalidDecimal()) {
			MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK
					| SWT.CANCEL);
			mb.setMessage("Overall progress is either invalid or blank. It will be set to 0.");
			mb.setText("Error: Invalid Decimal");
			if (mb.open() == SWT.CANCEL)
				return false;
			else {
				comboOverallProgress.setText("0.0");
			}
		}
		if (hasEmptyField()) {
			if (SWTFactory
					.warningOptionMessage(
							shell,
							"One or more data fields are blank. Would you still like to create the evaluation?",
							"Error: Blank field") == false)
				return false;
		}
		return true;
	}

	/**
	 * Links a text field to a characters remaining counter.
	 * 
	 * @param t
	 * @param l
	 */
	private void setCharacterCounter(final Text t, final Label l) {
		l.setText("Characters Remaining: "
				+ (MAX_CHARACTERS - t.getText().length()));
		t.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				l.setText("Characters Remaining: "
						+ (MAX_CHARACTERS - t.getText().length()));
			}
		});
	}

	/**
	 * Assigns data entry fields to the values in the input evaluation
	 * 
	 * @param input
	 */
	private void updateFields(Evaluation input) {

		txtEval.setText(input.getEvaluationNumb());
		txtQualityOfWork.setText(input.getWorkQualityComments());
		txtWorkHabbits.setText(input.getWorkHabitsComments());
		txtJobKnowlege.setText(input.getJobKnowledgeComments());
		txtBehaviorRelations.setText(input.getBehaviorComments());
		txtOverallProgress.setText(input.getOverallComments());
		comboQualityOfWork.setText(input.getWorkQualityScore() + "");
		comboWorkHabbits.setText(input.getWorkHabitsScore() + "");
		comboJobKnowledge.setText(input.getJobKnowledgeScore() + "");
		comboBehaviorRelations.setText(input.getBehaviorScore() + "");
		comboOverallProgress.setText(input.getOverallProgressScore() + "");
		btnYes.setSelection(input.getEmployeeRecommendation());
		btnNo.setSelection(!input.getEmployeeRecommendation());
		Calendar evalDate = input.getEvalDateCalendar();
		dateTimeCurrent.setDate(evalDate.get(Calendar.YEAR),
				evalDate.get(Calendar.MONTH),
				evalDate.get(Calendar.DAY_OF_MONTH));
		Calendar nextEvalDate = input.getNextEvalDateCalendar();
		dateTimeNext.setDate(nextEvalDate.get(Calendar.YEAR),
				nextEvalDate.get(Calendar.MONTH),
				nextEvalDate.get(Calendar.DAY_OF_MONTH));
		lblAverage.setText(getAverage() + "");
	}
}
