package employers;

import java.util.ArrayList;
import java.util.Comparator;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import employees.Employee;

/**
 * Represents an employer. Has fields representing the employer's information.
 * 
 * @author David Abrahams
 * @version 2/5/2013
 * 
 */
public class Employer {

	private String employerNumb, companyName;
	private String streetAddress, city, state, zipCode;
	private String phoneNumb, phoneNumb1, phoneNumb2;
	private String emailAddress;
	private String contactPerson;
	private ArrayList<Employee> employees;

	/**
	 * The processor used by CsvBeanReader to read a CSV file into an employer
	 */
	public static final CellProcessor[] PROCESSOR_IN = new CellProcessor[] {
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""), new ConvertNullTo("") };

	/**
	 * The processor used by CsvBeanWriter to write a CSV file from an Employer
	 * object
	 */
	public static final CellProcessor[] PROCESSOR_OUT = new CellProcessor[] {
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull() };

	/**
	 * The header used by CsvBeanWriter and CsvBeanReader to write and read the
	 * header for a CSV file
	 */
	public static final String[] header = new String[] { "employerNumb",
			"companyName", "streetAddress", "city", "state", "zipCode",
			"phoneNumb", "phoneNumb1", "phoneNumb2", "emailAddress",
			"contactPerson" };

	/**
	 * a Comparator that compares employers by their employer numbers
	 */
	public static Comparator<Employer> COMPARE_BY_NUMBER = new Comparator<Employer>() {

		@Override
		public int compare(Employer o1, Employer o2) {
			return o1.employerNumb.compareTo(o2.employerNumb);

		}

	};

	/**
	 * No argument constructor; private fields are initialized to null except
	 * the list of employees, which is initialized to an empty
	 * ArrayList<Employee>
	 */
	public Employer() {
		super();
		employees = new ArrayList<Employee>();
	}

	/**
	 * Constructs an Employer object with the given parameters
	 * 
	 * @param employerNumb
	 * @param companyName
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param phoneNumb
	 * @param phoneNumb1
	 * @param phoneNumb2
	 * @param emailAddress
	 * @param contactPerson
	 * @param employees
	 */
	public Employer(String employerNumb, String companyName,
			String streetAddress, String city, String state, String zipCode,
			String phoneNumb, String phoneNumb1, String phoneNumb2,
			String emailAddress, String contactPerson,
			ArrayList<Employee> employees) {
		super();
		this.employerNumb = employerNumb;
		this.companyName = companyName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNumb = phoneNumb;
		this.phoneNumb1 = phoneNumb1;
		this.phoneNumb2 = phoneNumb2;
		this.emailAddress = emailAddress;
		this.contactPerson = contactPerson;
		if (employees == null)
			this.employees = new ArrayList<Employee>();
		else
			this.employees = employees;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @return the contact person
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @return the e-mail address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the ArrayList<Employee> used to store the employees assigned to
	 *         this employer.
	 */
	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @return the employer number
	 */
	public String getEmployerNumb() {
		return employerNumb;
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
	 * @return a String[] array to be displayed in the table
	 */
	public String[] getTableData() {
		return new String[] { employerNumb, companyName };
	}

	/**
	 * @return the zip code
	 */
	public String getZipCode() {
		return zipCode;
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
	 * sets the company name
	 * 
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * sets the contact person
	 * 
	 * @param contactPerson
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * sets the email address
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * sets the assigned employees
	 * 
	 * @param employees
	 */
	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	/**
	 * sets the employerNumb
	 * 
	 * @param employerNumb
	 */
	public void setEmployerNumb(String employerNumb) {
		this.employerNumb = employerNumb;
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
	 * sets this employer equivalent to the input
	 * 
	 * @param e
	 */
	public void setTo(Employer e) {
		employerNumb = e.employerNumb;
		companyName = e.companyName;
		streetAddress = e.streetAddress;
		city = e.city;
		state = e.state;
		zipCode = e.zipCode;
		phoneNumb = e.phoneNumb;
		phoneNumb1 = e.phoneNumb1;
		phoneNumb2 = e.phoneNumb2;
		emailAddress = e.emailAddress;
		contactPerson = e.contactPerson;
		employees = e.employees;
		for (Employee emp : employees) {
			emp.getEvaluation().setEmployerNumb(employerNumb);
		}
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
		return employerNumb.toLowerCase().indexOf(lowerCase) != -1
				|| companyName.toLowerCase().indexOf(lowerCase) != -1;
	}

}
