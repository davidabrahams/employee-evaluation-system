package employees;

import java.util.Comparator;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import evaluations.Evaluation;

/**
 * Represents an employee. Has fields representing the employee's information,
 * and an evaluation Object field.
 * 
 * @author David Abrahams
 * @version 2/4/2012
 * 
 */
public class Employee {

	private String phoneNumb, phoneNumb1, phoneNumb2, cellNumb, cellNumb1,
			cellNumb2;

	private String employeeNumb, zipCode;
	private String firstName, lastName, emailAddress, streetAddress, city,
			state;
	private Evaluation evaluation;

	/**
	 * The processor used by CsvBeanReader to read a CSV file into an employee
	 */
	public static final CellProcessor[] PROCESSOR_IN = new CellProcessor[] {
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""), };
	/**
	 * The processor used by CsvBeanWriter to write a CSV file from an Employee
	 * object
	 */
	public static final CellProcessor[] PROCESSOR_OUT = new CellProcessor[] {
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull() };

	/**
	 * The header used by CsvBeanWriter and CsvBeanReader to write and read the
	 * header for a CSV file
	 */
	public static final String[] header = new String[] { "employeeNumb",
			"firstName", "lastName", "emailAddress", "phoneNumb", "phoneNumb1",
			"phoneNumb2", "cellNumb", "cellNumb1", "cellNumb2",
			"streetAddress", "city", "state", "zipCode" };

	/**
	 * a Comparator that compares employees by their overall progress score in
	 * their evaluation files.
	 */
	public static Comparator<Employee> COMPARE_BY_SCORE = new Comparator<Employee>() {

		@Override
		public int compare(Employee o1, Employee o2) {
			if (o1.evaluation.getOverallProgressScore() > o2.evaluation
					.getOverallProgressScore())
				return -1;
			else if (o1.evaluation.getOverallProgressScore() < o2.evaluation
					.getOverallProgressScore())
				return 1;
			else
				return 0;
		}

	};

	/**
	 * a Comparator that compares employees by their employer number
	 */
	public static Comparator<Employee> COMPARE_BY_NUMBER = new Comparator<Employee>() {

		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.employeeNumb.compareTo(o2.employeeNumb);
		}

	};

	/**
	 * No argument constructor; private fields are initialized to null
	 */
	public Employee() {
		super();
	}

	/**
	 * Constructs an Employee object with the given parameters
	 * 
	 * @param employeeNumb
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param phoneNumb
	 * @param phoneNumb1
	 * @param phoneNumb2
	 * @param cellNumb
	 * @param cellNumb1
	 * @param cellNumb2
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param evaluation
	 */
	public Employee(String employeeNumb, String firstName, String lastName,
			String emailAddress, String phoneNumb, String phoneNumb1,
			String phoneNumb2, String cellNumb, String cellNumb1,
			String cellNumb2, String streetAddress, String city, String state,
			String zipCode, Evaluation evaluation) {
		super();
		this.phoneNumb = phoneNumb;
		this.phoneNumb1 = phoneNumb1;
		this.phoneNumb2 = phoneNumb2;
		this.cellNumb = cellNumb;
		this.cellNumb1 = cellNumb1;
		this.cellNumb2 = cellNumb2;
		this.employeeNumb = employeeNumb;
		this.zipCode = zipCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.evaluation = evaluation;
	}

	/**
	 * @return the first 3 digits of the cell number
	 */
	public String getCellNumb() {
		return cellNumb;
	}

	/**
	 * @return the second 3 digits of the cell number
	 */
	public String getCellNumb1() {
		return cellNumb1;
	}

	/**
	 * @return the last 4 digits of the cell number
	 */
	public String getCellNumb2() {
		return cellNumb2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the employee number
	 */
	public String getEmployeeNumb() {
		return employeeNumb;
	}

	/**
	 * @return the Evaluation
	 */
	public Evaluation getEvaluation() {
		return evaluation;
	}

	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the first 3 digits of the phone number
	 */
	public String getPhoneNumb() {
		return phoneNumb;
	}

	/**
	 * @return the second 3 digits of the phone number
	 */
	public String getPhoneNumb1() {
		return phoneNumb1;
	}

	/**
	 * @return the last 4 digits of the phone number
	 */
	public String getPhoneNumb2() {
		return phoneNumb2;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the street address
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @return a String[] to be displayed in the table
	 */
	public String[] getTableData() {
		return new String[] { employeeNumb, firstName, lastName };
	}

	/**
	 * @return the zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * sets the first 3 digits of the cell number
	 * 
	 * @param cellNumb
	 */
	public void setCellNumb(String cellNumb) {
		this.cellNumb = cellNumb;
	}

	/**
	 * sets the second 3 digits of the cell number
	 * 
	 * @param cellNumb1
	 */
	public void setCellNumb1(String cellNumb1) {
		this.cellNumb1 = cellNumb1;
	}

	/**
	 * sets the last 4 digits of the cell number
	 * 
	 * @param cellNumb2
	 */
	public void setCellNumb2(String cellNumb2) {
		this.cellNumb2 = cellNumb2;
	}

	/**
	 * sets the city
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * sets the e-mail address
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * sets the employee number
	 * 
	 * @param employeeNumb
	 */
	public void setEmployeeNumb(String employeeNumb) {
		this.employeeNumb = employeeNumb;
	}

	/**
	 * sets the evaluation
	 * 
	 * @param evaluation
	 */
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * sets the first name
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * sets the last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * sets the first 3 digits of the phone number
	 * 
	 * @param phoneNumb
	 */
	public void setPhoneNumb(String phoneNumb) {
		this.phoneNumb = phoneNumb;
	}

	/**
	 * sets the second 3 digits of the phone number
	 * 
	 * @param phoneNumb1
	 */
	public void setPhoneNumb1(String phoneNumb1) {
		this.phoneNumb1 = phoneNumb1;
	}

	/**
	 * sets the last 4 digits of the phone number
	 * 
	 * @param phoneNumb2
	 */
	public void setPhoneNumb2(String phoneNumb2) {
		this.phoneNumb2 = phoneNumb2;
	}

	/**
	 * sets the state
	 * 
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * sets the street address
	 * 
	 * @param streetAddress
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * sets this employee equivalent to the input
	 * 
	 * @param e
	 */
	public void setTo(Employee e) {
		phoneNumb = e.phoneNumb;
		phoneNumb1 = e.phoneNumb1;
		phoneNumb2 = e.phoneNumb2;
		cellNumb = e.cellNumb;
		cellNumb1 = e.cellNumb1;
		cellNumb2 = e.cellNumb2;
		employeeNumb = e.employeeNumb;
		zipCode = e.zipCode;
		firstName = e.firstName;
		lastName = e.lastName;
		emailAddress = e.emailAddress;
		streetAddress = e.streetAddress;
		city = e.city;
		state = e.state;
		evaluation = e.evaluation;
	}

	/**
	 * sets the zip code
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * checks if the table data contains an input string
	 * 
	 * @param s
	 * @return
	 */
	public boolean tableDataContains(String s) {
		String lowerCase = s.toLowerCase();
		return firstName.toLowerCase().indexOf(lowerCase) != -1
				|| lastName.toLowerCase().indexOf(lowerCase) != -1
				|| employeeNumb.toLowerCase().indexOf(lowerCase) != -1;
	}

}