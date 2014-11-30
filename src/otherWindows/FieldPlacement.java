package otherWindows;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Represents a field placement pair
 * 
 * @author David Abrahams
 * @version 2/5/2013
 * 
 */
public class FieldPlacement {

	private String employeeNumb;
	private String employerNumb;
	public static final CellProcessor[] PROCESSOR_IN = new CellProcessor[] {
			new ConvertNullTo(""), new ConvertNullTo("") };
	public static final CellProcessor[] PROCESSOR_OUT = new CellProcessor[] {
			new NotNull(), new NotNull() };
	public static final String[] header = new String[] { "employeeNumb",
			"employerNumb" };

	/**
	 * Creates a FieldPlacement with null fields
	 */
	public FieldPlacement() {
		super();
	}

	/**
	 * Creates a Field Placement with inputed parameters
	 * 
	 * @param employeeNumb
	 * @param employerNumb
	 */
	public FieldPlacement(String employeeNumb, String employerNumb) {
		super();
		this.employeeNumb = employeeNumb;
		this.employerNumb = employerNumb;
	}

	/**
	 * @return the employee number
	 */
	public String getEmployeeNumb() {
		return employeeNumb;
	}

	/**
	 * @return the employer number
	 */
	public String getEmployerNumb() {
		return employerNumb;
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
	 * sets the employer number
	 * 
	 * @param employerNumb
	 */
	public void setEmployerNumb(String employerNumb) {
		this.employerNumb = employerNumb;
	}

}