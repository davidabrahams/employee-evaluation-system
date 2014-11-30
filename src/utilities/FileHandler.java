package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import otherWindows.FieldPlacement;

import employees.Employee;
import employers.Employer;
import evaluations.Evaluation;

/**
 * Represents a hile handler. Has methods to read and write different files.
 * 
 * @author David Abrahams
 * @version 2/5/2103
 *
 */
public class FileHandler {

	/**
	 * reads a CSV text file and returns an ArrayList<Employee> of employees contained in the file.
	 * 
	 * @param filename
	 * @return an ArrayList of employees in the file
	 */
	public ArrayList<Employee> readEmployees(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		CsvBeanReader beanReader = null;
		ArrayList<Employee> result = new ArrayList<Employee>();
		try {
			br = new BufferedReader(new FileReader(file));
			beanReader = new CsvBeanReader(br,
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = beanReader.getHeader(true);
			Employee employee;
			while ((employee = beanReader.read(Employee.class, header,
					Employee.PROCESSOR_IN)) != null) {
				result.add(employee);
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * reads a CSV text file and returns an ArrayList<Employer> of employers contained in the file.
	 * 
	 * @param filename
	 * @return an ArrayList of employers in the file
	 */
	public ArrayList<Employer> readEmployers(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		CsvBeanReader beanReader = null;
		ArrayList<Employer> result = new ArrayList<Employer>();
		try {
			br = new BufferedReader(new FileReader(file));
			beanReader = new CsvBeanReader(br,
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = beanReader.getHeader(true);
			Employer employer;
			while ((employer = beanReader.read(Employer.class, header,
					Employer.PROCESSOR_IN)) != null) {
				result.add(employer);
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * reads a CSV text file and returns an ArrayList<Evaluations> of employees contained in the file.
	 * 
	 * @param filename
	 * @return an ArrayList of evaluations in the file
	 */
	public ArrayList<Evaluation> readEvaluations(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		CsvBeanReader beanReader = null;
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();
		try {
			br = new BufferedReader(new FileReader(file));
			beanReader = new CsvBeanReader(br,
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = beanReader.getHeader(true);
			Evaluation evaluation;
			while ((evaluation = beanReader.read(Evaluation.class, header,
					Evaluation.PROCESSOR_IN)) != null) {
				result.add(evaluation);
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * reads a CSV text file and returns an ArrayList<FieldPlacement> of placements contained in the file.
	 * 
	 * @param filename
	 * @return an ArrayList of placements in the file
	 */
	public ArrayList<FieldPlacement> readFieldPlacements(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		CsvBeanReader beanReader = null;
		ArrayList<FieldPlacement> result = new ArrayList<FieldPlacement>();
		try {
			br = new BufferedReader(new FileReader(file));
			beanReader = new CsvBeanReader(br,
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = beanReader.getHeader(true);
			FieldPlacement placement;
			while ((placement = beanReader.read(FieldPlacement.class, header,
					FieldPlacement.PROCESSOR_IN)) != null) {
				result.add(placement);
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * reads a text file and returns the text in it as a string
	 * 
	 * @param filename
	 * @return the text in the file
	 */
	public String readFile(String filename) {
		File f = new File(filename);
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		String line = null;
		if (f.exists()) {
			try {
				reader = new BufferedReader(new FileReader(f));
				while ((line = reader.readLine()) != null) {
					builder.append(line);
					builder.append(ls);
				}
			} catch (FileNotFoundException e1) {
				return null;
			} catch (IOException e) {
				return null;
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					return null;
				}
			}
		} else {
			return null;
		}

		return builder.toString();
	}

	/**
	 * Writes an ArrayList<Employee> into a CSV file
	 * 
	 * @param employees
	 * @param filename
	 * @return if the write was successful
	 */
	public boolean writeEmployees(ArrayList<Employee> employees, String filename) {

		File file = new File(filename);
		BufferedWriter bf = null;
		CsvBeanWriter beanWriter = null;
		try {
			bf = new BufferedWriter(new FileWriter(file));
			beanWriter = new CsvBeanWriter(bf,
					CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(Employee.header);
			for (Employee e : employees) {
				beanWriter.write(e, Employee.header, Employee.PROCESSOR_OUT);
			}

		} catch (IOException e) {
			return false;
		} finally {
			if (beanWriter != null)
				try {
					beanWriter.close();
				} catch (IOException e) {
					return false;
				}
		}
		return true;
	}

	/**
	 * Writes an ArrayList<Employer> into a CSV file
	 * 
	 * @param employers
	 * @param filename
	 * @return if the write was successful
	 */
	public boolean writeEmployers(ArrayList<Employer> employers, String filename) {

		File file = new File(filename);
		BufferedWriter bf = null;
		CsvBeanWriter beanWriter = null;
		try {
			bf = new BufferedWriter(new FileWriter(file));
			beanWriter = new CsvBeanWriter(bf,
					CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(Employer.header);
			for (Employer e : employers) {
				beanWriter.write(e, Employer.header, Employer.PROCESSOR_OUT);
			}

		} catch (IOException e) {
			return false;
		} finally {
			if (beanWriter != null)
				try {
					beanWriter.close();
				} catch (IOException e) {
					return false;
				}
		}
		return true;
	}

	/**
	 * Writes an ArrayList<Evaluation> into a CSV file
	 * 
	 * @param evaluations
	 * @param filename
	 * @return if the write was successful
	 */
	public boolean writeEvaluations(ArrayList<Evaluation> evaluations,
			String filename) {

		File file = new File(filename);
		BufferedWriter bf = null;
		CsvBeanWriter beanWriter = null;
		try {
			bf = new BufferedWriter(new FileWriter(file));
			beanWriter = new CsvBeanWriter(bf,
					CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(Evaluation.header);
			for (Evaluation e : evaluations) {
				beanWriter
						.write(e, Evaluation.header, Evaluation.PROCESSOR_OUT);
			}

		} catch (IOException e) {
			return false;
		} finally {
			if (beanWriter != null)
				try {
					beanWriter.close();
				} catch (IOException e) {
					return false;
				}
		}
		return true;
	}

	/**
	 * Writes an ArrayList<FieldPlacement> into a CSV file
	 * 
	 * @param placements
	 * @param filename
	 * @return if the write was successful
	 */
	public boolean writeFieldPlacements(ArrayList<FieldPlacement> placements,
			String filename) {

		File file = new File(filename);
		BufferedWriter bf = null;
		CsvBeanWriter beanWriter = null;
		try {
			bf = new BufferedWriter(new FileWriter(file));
			beanWriter = new CsvBeanWriter(bf,
					CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(FieldPlacement.header);
			for (FieldPlacement e : placements) {
				beanWriter.write(e, FieldPlacement.header,
						FieldPlacement.PROCESSOR_OUT);
			}

		} catch (IOException e) {
			return false;
		} finally {
			if (beanWriter != null)
				try {
					beanWriter.close();
				} catch (IOException e) {
					return false;
				}
		}
		return true;
	}

}
