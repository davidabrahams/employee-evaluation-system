package otherWindows;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import employees.Employee;
import employers.Employer;
import evaluations.Evaluation;

/**
 * A report window that displays reports of employers and employees
 * 
 * @author David Abrahams
 * @version 2/5/2013
 * 
 */
public class ReportWindow {

	private Shell shell;
	private Employee employeeField;
	private ArrayList<Employee> employeesField;
	private ArrayList<Employer> employersField;
	public static final int INDIVIDUAL = 1;
	public static final int EVALUATION_SCORE = 2;
	public static final int EMPLOYER = 3;
	public static final int EMPLOYEE = 4;
	private Text text;
	private static final String n = System.getProperty("line.separator");

	/**
	 * Open the window. Creates the contents of the window based on the style
	 * input.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open(Shell parent, ArrayList<Employer> employers, int style) {
		if (style == EVALUATION_SCORE) {
			employeesField = new ArrayList<Employee>();
			for (Employer employer : employers) {
				for (Employee employee : employer.getEmployees()) {
					employeesField.add(employee);
				}
			}
			Collections.sort(employeesField, Employee.COMPARE_BY_SCORE);
		} else if (style == EMPLOYER) {
			employersField = employers;
			Collections.sort(employersField, Employer.COMPARE_BY_NUMBER);
		} else if (style == EMPLOYEE) {
			employeesField = new ArrayList<Employee>();
			for (Employer employer : employers) {
				for (Employee employee : employer.getEmployees()) {
					employeesField.add(employee);
				}

			}
			Collections.sort(employeesField, Employee.COMPARE_BY_NUMBER);
		}
		createContents(parent, style);
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Open the window. Creates the contents for a full report
	 */
	public void open(Shell parent, Employee e, int style) {
		employeeField = e;
		createContents(parent, style);
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	private void createContents(Shell parent, int style) {

		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL
				| SWT.RESIZE);
		shell.setSize(800, 800);
		shell.setText("Report");

		// generate an individual report
		if (style == INDIVIDUAL) {
			shell.setLayout(new GridLayout(1, false));
			text = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP
					| SWT.V_SCROLL);
			text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
					1));
			text.setText(fullReport(employeeField));
		}
		// generate a report by evaluation score
		else if (style == EVALUATION_SCORE) {
			shell.setLayout(new GridLayout(1, false));

			text = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP
					| SWT.V_SCROLL);
			text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
					1));
			text.setText(reportByScore(employeesField));

		}
		// generate a report by employer
		else if (style == EMPLOYER) {

			shell.setLayout(new FillLayout(SWT.HORIZONTAL));

			ScrolledComposite scrolledComposite = new ScrolledComposite(shell,
					SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);

			Composite composite = new Composite(scrolledComposite, SWT.NONE);
			composite.setLayout(new GridLayout(1, false));

			for (Employer employer : employersField) {
				Text t = new Text(composite, SWT.BORDER | SWT.READ_ONLY
						| SWT.WRAP);
				t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
						1));
				t.setText(employerReport(employer));
			}
			scrolledComposite.setContent(composite);
			scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
					SWT.DEFAULT));

		}
		// generate a report by employee
		else if (style == EMPLOYEE) {

			shell.setLayout(new FillLayout(SWT.HORIZONTAL));

			ScrolledComposite scrolledComposite = new ScrolledComposite(shell,
					SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);

			Composite composite = new Composite(scrolledComposite, SWT.NONE);
			composite.setLayout(new GridLayout(1, false));

			for (Employee employee : employeesField) {
				Text t = new Text(composite, SWT.BORDER | SWT.READ_ONLY
						| SWT.WRAP);
				t.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
						1, 1));
				t.setText(employeeReport(employee));
			}

			scrolledComposite.setContent(composite);
			scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
					SWT.DEFAULT));
		}

	}

	/**
	 * Returns the employee report.
	 * 
	 * @param employee
	 *            the employee to generate the report for
	 * @return the employee report
	 */
	private static String employeeReport(Employee employee) {

		Evaluation e = employee.getEvaluation();
		String result = "";
		result += "Employee number: " + employee.getEmployeeNumb() + n;
		result += "Employer number: " + e.getEmployerNumb() + n;
		result += "" + n;
		result += "\t" + "First name: " + employee.getFirstName() + n;
		result += "\t" + "Last name: " + employee.getLastName() + n;
		result += "\t" + "E-mail address: " + employee.getEmailAddress() + n;
		result += "\t" + "Phone number: " + employee.getPhoneNumb() + " "
				+ employee.getPhoneNumb1() + " " + employee.getPhoneNumb2() + n;
		result += "\t" + "Cell number: " + employee.getCellNumb() + " "
				+ employee.getCellNumb1() + " " + employee.getCellNumb2() + n;
		result += "\t" + "Street Address: " + employee.getStreetAddress() + n;
		result += "\t" + "City: " + employee.getCity() + n;
		result += "\t" + "State: " + employee.getState() + n;
		result += "\t" + "Zip Code: " + employee.getZipCode() + n;
		result += "\t" + "" + n;
		Calendar next = e.getNextEvalDateCalendar();
		Calendar eval = e.getEvalDateCalendar();
		result += "\t" + "Evaluation number: " + e.getEvaluationNumb() + n;
		result += "\t" + "Evaluation date: "
				+ new DateFormatSymbols().getMonths()[eval.get(Calendar.MONTH)]
				+ " " + eval.get(Calendar.DAY_OF_MONTH) + ", "
				+ eval.get(Calendar.YEAR) + n;
		result += "\t" + "Next evaluation date: "
				+ new DateFormatSymbols().getMonths()[next.get(Calendar.MONTH)]
				+ " " + next.get(Calendar.DAY_OF_MONTH) + ", "
				+ next.get(Calendar.YEAR) + n;
		result += "\t" + "Work quality score: "
				+ removeZero(e.getWorkQualityScore()) + n;
		result += "\t" + "Work habbits score: "
				+ removeZero(e.getWorkHabitsScore()) + n;
		result += "\t" + "Job knowledge score: "
				+ removeZero(e.getJobKnowledgeScore()) + n;
		result += "\t" + "Behavior score: " + removeZero(e.getBehaviorScore())
				+ n;
		result += "\t" + "Average score: " + removeZero(e.getAverageScore())
				+ n;
		result += "\t" + "Overall progress score: "
				+ e.getOverallProgressScore() + n;
		if (e.getEmployeeRecommendation())
			result += "\t" + "Was recommended.";
		else
			result += "\t" + "Was not recommended";
		return result;
	}

	/**
	 * Returns the employer report
	 * 
	 * @param employer
	 *            the employer to generate the report for
	 * @return the employer report
	 */
	private static String employerReport(Employer employer) {
		String result = "";
		result += "Employer info:" + n;
		result += n;
		result += "\t" + "Employer number: " + employer.getEmployerNumb() + n;
		result += "\t" + "Company name: " + employer.getCompanyName() + n;
		result += "\t" + "Street Address: " + employer.getStreetAddress() + n;
		result += "\t" + "City: " + employer.getCity() + n;
		result += "\t" + "State: " + employer.getState() + n;
		result += "\t" + "Zip code: " + employer.getZipCode() + n;
		result += "\t" + "Email address: " + employer.getEmailAddress() + n;
		result += "\t" + "Contact person: " + employer.getContactPerson() + n;
		result += n;
		result += "Employees:" + n;
		result += n;
		ArrayList<Employee> employeeArray = employer.getEmployees();
		Collections.sort(employeeArray, Employee.COMPARE_BY_NUMBER);
		for (Employee employee : employeeArray) {
			Evaluation e = employee.getEvaluation();
			result += "\t" + "Employee #" + employee.getEmployeeNumb() + n;
			result += n;
			result += "\t\t" + "First name: " + employee.getFirstName() + n;
			result += "\t\t" + "Last name: " + employee.getLastName() + n;
			result += "\t\t" + "E-mail address: " + employee.getEmailAddress()
					+ n;
			result += "\t\t" + "Phone number: " + employee.getPhoneNumb() + " "
					+ employee.getPhoneNumb1() + " " + employee.getPhoneNumb2()
					+ n;
			result += "\t\t" + "Cell number: " + employee.getCellNumb() + " "
					+ employee.getCellNumb1() + " " + employee.getCellNumb2()
					+ n;
			result += "\t\t" + "Street Address: " + employee.getStreetAddress()
					+ n;
			result += "\t\t" + "City: " + employee.getCity() + n;
			result += "\t\t" + "State: " + employee.getState() + n;
			result += "\t\t" + "Zip Code: " + employee.getZipCode() + n;
			result += n;
			result += "\t\t" + "Evaluation number: " + e.getEvaluationNumb()
					+ n;
			Calendar next = e.getNextEvalDateCalendar();
			Calendar eval = e.getEvalDateCalendar();
			result += "\t\t"
					+ "Evaluation date: "
					+ new DateFormatSymbols().getMonths()[eval
							.get(Calendar.MONTH)] + " "
					+ eval.get(Calendar.DAY_OF_MONTH) + ", "
					+ eval.get(Calendar.YEAR) + n;
			result += "\t\t"
					+ "Next evaluation date: "
					+ new DateFormatSymbols().getMonths()[next
							.get(Calendar.MONTH)] + " "
					+ next.get(Calendar.DAY_OF_MONTH) + ", "
					+ next.get(Calendar.YEAR) + n;
			result += "\t\t" + "Work quality score: "
					+ removeZero(e.getWorkQualityScore()) + n;
			result += "\t\t" + "Work habbits score: "
					+ removeZero(e.getWorkHabitsScore()) + n;
			result += "\t\t" + "Job knowledge score: "
					+ removeZero(e.getJobKnowledgeScore()) + n;
			result += "\t\t" + "Behavior score: "
					+ removeZero(e.getBehaviorScore()) + n;
			result += "\t\t" + "Average score: "
					+ removeZero(e.getAverageScore()) + n;
			result += "\t\t" + "Overall progress score: "
					+ e.getOverallProgressScore() + n;
			if (e.getEmployeeRecommendation())
				result += "\t\t" + "Was recommended.";
			else
				result += "\t\t" + "Was not recommended";
			result += n;
			result += n;

		}

		return result;
	}

	/**
	 * Returns a full report
	 * 
	 * @param employee
	 *            the employee to generate the report for
	 * @return the full report
	 */
	private static String fullReport(Employee employee) {
		Evaluation e = employee.getEvaluation();
		String result = "";
		result += "Evaluation number: " + e.getEvaluationNumb() + n;
		result += "Employee number: " + employee.getEmployeeNumb() + n;
		result += "Employer number: " + e.getEmployerNumb() + n;
		result += "" + n;
		result += "First name: " + employee.getFirstName() + n;
		result += "Last name: " + employee.getLastName() + n;
		result += "E-mail address: " + employee.getEmailAddress() + n;
		result += "Phone number: " + employee.getPhoneNumb() + " "
				+ employee.getPhoneNumb1() + " " + employee.getPhoneNumb2() + n;
		result += "Cell number: " + employee.getCellNumb() + " "
				+ employee.getCellNumb1() + " " + employee.getCellNumb2() + n;
		result += "Street Address: " + employee.getStreetAddress() + n;
		result += "City: " + employee.getCity() + n;
		result += "State: " + employee.getState() + n;
		result += "Zip Code: " + employee.getZipCode() + n;
		result += "" + n;
		Calendar next = e.getNextEvalDateCalendar();
		Calendar eval = e.getEvalDateCalendar();
		result += "Evaluation date: "
				+ new DateFormatSymbols().getMonths()[eval.get(Calendar.MONTH)]
				+ " " + eval.get(Calendar.DAY_OF_MONTH) + ", "
				+ eval.get(Calendar.YEAR) + n;
		result += "Next evaluation date: "
				+ new DateFormatSymbols().getMonths()[next.get(Calendar.MONTH)]
				+ " " + next.get(Calendar.DAY_OF_MONTH) + ", "
				+ next.get(Calendar.YEAR) + n;
		result += "" + n;
		result += "Work quality score: " + removeZero(e.getWorkQualityScore())
				+ n;
		result += "Work quality comments: " + e.getWorkQualityComments() + n;
		result += "" + n;
		result += "Work habbits score: " + removeZero(e.getWorkHabitsScore())
				+ n;
		result += "Work habbits comments: " + e.getWorkHabitsComments() + n;
		result += "" + n;
		result += "Job knowledge score: "
				+ removeZero(e.getJobKnowledgeScore()) + n;
		result += "Job knowledge comments: " + e.getJobKnowledgeComments() + n;
		result += "" + n;
		result += "Behavior score: " + removeZero(e.getBehaviorScore()) + n;
		result += "Behavior comments: " + e.getBehaviorComments() + n;
		result += "" + n;
		result += "Average score: " + removeZero(e.getAverageScore()) + n;
		result += "Overall progress score: " + e.getOverallProgressScore() + n;
		result += "Overall comments: " + e.getOverallComments() + n;
		result += "" + n;
		if (e.getEmployeeRecommendation())
			result += "Was recommended.";
		else
			result += "Was not recommended";
		return result;
	}

	/**
	 * @param d
	 *            a double
	 * @return an empty String if d == 0, otherwise returns d
	 */
	private static String removeZero(double d) {
		if (d == 0)
			return "";
		else
			return d + "";
	}

	/**
	 * @param i
	 *            an int
	 * @return an empty String if i == 0, otherwise returns i
	 */
	private static String removeZero(int i) {
		if (i == 0)
			return "";
		else
			return i + "";
	}

	/**
	 * Returns a report by evaluation score
	 * 
	 * @param employees
	 *            the employees to generate the report for
	 * @return the report
	 */
	private static String reportByScore(ArrayList<Employee> employees) {
		String result = "";
		for (Employee employee : employees) {
			Evaluation e = employee.getEvaluation();

			result += "Evaluation score: " + e.getOverallProgressScore() + n;
			result += n;
			result += "\t" + "Employee number: " + employee.getEmployeeNumb()
					+ n;
			result += "\t" + "First name: " + employee.getFirstName() + n;
			result += "\t" + "Last name: " + employee.getLastName() + n;
			result += "\t" + "E-mail address: " + employee.getEmailAddress()
					+ n;
			result += "\t" + "Phone number: " + employee.getPhoneNumb() + " "
					+ employee.getPhoneNumb1() + " " + employee.getPhoneNumb2()
					+ n;
			result += "\t" + "Cell number: " + employee.getCellNumb() + " "
					+ employee.getCellNumb1() + " " + employee.getCellNumb2()
					+ n;
			result += "\t" + "Street Address: " + employee.getStreetAddress()
					+ n;
			result += "\t" + "City: " + employee.getCity() + n;
			result += "\t" + "State: " + employee.getState() + n;
			result += "\t" + "Zip Code: " + employee.getZipCode() + n;
			result += n;
			result += "\t" + "Evaluation number: " + e.getEvaluationNumb() + n;
			result += "\t" + "Employer number: " + e.getEmployerNumb() + n;
			Calendar next = e.getNextEvalDateCalendar();
			Calendar eval = e.getEvalDateCalendar();
			result += "\t"
					+ "Evaluation date: "
					+ new DateFormatSymbols().getMonths()[eval
							.get(Calendar.MONTH)] + " "
					+ eval.get(Calendar.DAY_OF_MONTH) + ", "
					+ eval.get(Calendar.YEAR) + n;
			result += "\t"
					+ "Next evaluation date: "
					+ new DateFormatSymbols().getMonths()[next
							.get(Calendar.MONTH)] + " "
					+ next.get(Calendar.DAY_OF_MONTH) + ", "
					+ next.get(Calendar.YEAR) + n;
			result += "\t" + "Work quality score: "
					+ removeZero(e.getWorkQualityScore()) + n;
			result += "\t" + "Work habbits score: "
					+ removeZero(e.getWorkHabitsScore()) + n;
			result += "\t" + "Job knowledge score: "
					+ removeZero(e.getJobKnowledgeScore()) + n;
			result += "\t" + "Behavior score: "
					+ removeZero(e.getBehaviorScore()) + n;
			result += "\t" + "Average score: "
					+ removeZero(e.getAverageScore()) + n;
			result += "\t" + "Overall progress score: "
					+ e.getOverallProgressScore() + n;
			if (e.getEmployeeRecommendation())
				result += "\t" + "Was recommended.";
			else
				result += "\t" + "Was not recommended";
			result += n;
			result += n;

		}
		return result;
	}
}
