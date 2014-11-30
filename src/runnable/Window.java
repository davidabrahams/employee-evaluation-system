package runnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import otherWindows.FieldPlacement;
import otherWindows.HelpWindow;
import otherWindows.MyDirectoryDialog;
import otherWindows.ReportWindow;
import utilities.FileHandler;
import utilities.SWTFactory;
import employees.Employee;
import employees.EmployeeDialogue;
import employers.Employer;
import employers.EmployerDialogue;
import evaluations.Evaluation;

/**
 * The main window of the program. Provides methods for the user to manage
 * employers, employees, and evaluations. Also allows the user to save or load
 * CSV files into the program.
 * 
 * @author David Abrahams
 * @version 2/4/2013
 */
public class Window {

	protected Shell shell;
	private Text txtSearch;
	private Table tblEmployer;
	private Table tblEmployees;
	private String directory;

	// The field used to store all actual data currently in the program
	private ArrayList<Employer> employers;
	// The field used to store the data currently displayed in the employee
	// table
	private ArrayList<Employee> employees;
	private Menu menuChangeEmployer;
	private TableColumn tblclmnEmployerNumber;
	private TableColumn tblclmnCompanyName;
	private TableColumn tblclmnEmployeeNumber;
	private TableColumn tblclmnFirstName;
	private TableColumn tblclmnLastName;

	/**
	 * A listener used to sort the employer table.
	 */
	private Listener employerSortListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			TableColumn selected = (TableColumn) event.widget;
			int dir = tblEmployer.getSortDirection();
			if (tblEmployer.getSortColumn() == selected) {
				dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			} else {
				tblEmployer.setSortColumn(selected);
				dir = SWT.UP;
			}
			tblEmployer.setSortDirection(dir);
			final int sign = dir == SWT.UP ? 1 : -1;
			// Checks which column is selected and sort accordingly
			if (tblEmployer.getSortColumn() == tblclmnEmployerNumber) {
				Collections.sort(employers, new Comparator<Employer>() {
					@Override
					public int compare(Employer o1, Employer o2) {
						return sign
								* o1.getEmployerNumb().compareTo(
										o2.getEmployerNumb());
					}
				});
			} else {
				Collections.sort(employers, new Comparator<Employer>() {
					@Override
					public int compare(Employer o1, Employer o2) {
						return sign
								* o1.getCompanyName().compareTo(
										o2.getCompanyName());
					}
				});
			}
			// updates the tables to display the newly sorted data
			tblEmployer.deselectAll();
			updateEmployerTable();
			updateEmployeesBasedOnSelection();
		}
	};

	/**
	 * A listener used to sort the employee table.
	 */
	private Listener employeeSortListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			TableColumn selected = (TableColumn) event.widget;
			int dir = tblEmployees.getSortDirection();
			if (tblEmployees.getSortColumn() == selected) {
				dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			} else {
				tblEmployees.setSortColumn(selected);
				dir = SWT.UP;
			}
			tblEmployees.setSortDirection(dir);
			final int sign = dir == SWT.UP ? 1 : -1;
			// check which column is selected and sort accordingly
			if (tblEmployees.getSortColumn() == tblclmnEmployeeNumber) {
				Collections.sort(employees, new Comparator<Employee>() {
					@Override
					public int compare(Employee o1, Employee o2) {
						return sign
								* o1.getEmployeeNumb().compareTo(
										o2.getEmployeeNumb());
					}
				});
			} else if (tblEmployees.getSortColumn() == tblclmnFirstName) {
				Collections.sort(employees, new Comparator<Employee>() {
					@Override
					public int compare(Employee o1, Employee o2) {
						return sign
								* o1.getFirstName()
										.compareTo(o2.getFirstName());
					}
				});
			} else {
				Collections.sort(employees, new Comparator<Employee>() {
					@Override
					public int compare(Employee o1, Employee o2) {
						return sign
								* o1.getLastName().compareTo(o2.getLastName());
					}
				});
			}
			// update the employee table to display the newly sorted data
			tblEmployees.deselectAll();
			updateEmployeeTable();
		}
	};

	// declarations for the buttons that allow the user to shift items
	private Button btnUp;
	private Button btnDown;
	private Button btnUp_1;
	private Button btnDown_1;

	/**
	 * Checks if only one employee is selected and notifies the user otherwise
	 * 
	 * @return if only one employee is selected
	 */
	private boolean checkSingleEmployeeSelected() {
		if (tblEmployees.getSelectionCount() > 1)
			multipleEmployeesSelected();
		else if (tblEmployees.getSelectionCount() == 0)
			noEmployeesSelected();
		else
			return true;
		return false;
	}

	/**
	 * Checks if only one employer is selected and notifies the user otherwise
	 * 
	 * @return if only one employer is selected
	 */
	private boolean checkSingleEmployerSelected() {
		if (tblEmployer.getSelectionCount() > 1)
			multipleEmployersSelected();
		else if (tblEmployer.getSelectionCount() == 0)
			noEmployersSelected();
		else
			return true;
		return false;
	}

	/**
	 * Confirms with the user if he would like to delete the items he selected
	 * 
	 * @return whether the user would like to delete the selected items
	 */
	private boolean confirmDelete() {
		return SWTFactory.warningOptionMessage(shell,
				"Are you sure you want to delete the selected item(s)?",
				"Warning: Delete");
	}

	/**
	 * Create contents of the window.
	 * 
	 * @param display
	 *            The current display in use.
	 */
	private void createContents(Display display) {
		shell = new Shell(display);
		shell.setSize(700, 500);
		shell.setText("Employee Management Suite");
		GridLayout gl_shell = new GridLayout(2, false);
		gl_shell.marginWidth = 0;
		gl_shell.marginHeight = 0;
		shell.setLayout(gl_shell);

		Composite compositeSearch = new Composite(shell, SWT.NONE);
		compositeSearch.setLayout(new GridLayout(2, false));
		compositeSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));

		Label lblSearch = new Label(compositeSearch, SWT.NONE);
		lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblSearch.setText("Search:");

		txtSearch = new Text(compositeSearch, SWT.BORDER | SWT.H_SCROLL
				| SWT.SEARCH | SWT.CANCEL);
		txtSearch.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				search();
			}
		});
		txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		txtSearch.setBounds(0, 0, 64, 19);

		Group grpEmployerSelection = new Group(shell, SWT.NONE);
		GridLayout gl_grpEmployerSelection = new GridLayout(2, false);
		gl_grpEmployerSelection.marginWidth = 0;
		gl_grpEmployerSelection.marginHeight = 0;
		grpEmployerSelection.setLayout(gl_grpEmployerSelection);
		grpEmployerSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		grpEmployerSelection.setText("Employer Selection");

		Composite compositeTable = new Composite(grpEmployerSelection, SWT.NONE);
		compositeTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		GridLayout gl_compositeTable = new GridLayout(2, false);
		compositeTable.setLayout(gl_compositeTable);

		tblEmployer = new Table(compositeTable, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		tblEmployer.addSelectionListener(new SelectionAdapter() {
			// if the table is selected, updates both the employee data field
			// and table
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateEmployeesBasedOnSelection();
			}
		});
		tblEmployer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 2));
		tblEmployer.setHeaderVisible(true);
		tblEmployer.setLinesVisible(true);

		tblclmnEmployerNumber = new TableColumn(tblEmployer, SWT.NONE);
		tblclmnEmployerNumber.setWidth(150);
		tblclmnEmployerNumber.setText("Employer Number");

		tblclmnCompanyName = new TableColumn(tblEmployer, SWT.NONE);
		tblclmnCompanyName.setWidth(150);
		tblclmnCompanyName.setText("Company Name");

		tblclmnEmployerNumber.addListener(SWT.Selection, employerSortListener);
		tblclmnCompanyName.addListener(SWT.Selection, employerSortListener);
		tblEmployer.setSortDirection(SWT.UP);

		btnUp = new Button(compositeTable, SWT.ARROW | SWT.UP);
		btnUp.addSelectionListener(new SelectionAdapter() {
			// checks if an employer is selected and if a shift is possible, and
			// performs the shift
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployer.getSelectionIndices().length == 0) {
					noEmployersSelected();
				} else if (tblEmployer.getSelectionIndices()[0] == 0) {
					unableToShift();
				} else {
					moveEmployersUp();
				}
			}
		});
		btnUp.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));

		btnDown = new Button(compositeTable, SWT.ARROW | SWT.DOWN);
		btnDown.addSelectionListener(new SelectionAdapter() {
			// checks if an employer is selected and if a shift is possible, and
			// performs the shift
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployer.getSelectionIndices().length == 0) {
					noEmployersSelected();
				} else if (tblEmployer.getSelectionIndices()[tblEmployer
						.getSelectionIndices().length - 1] == tblEmployer
						.getItemCount() - 1) {
					unableToShift();
				} else {
					moveEmployersDown();
				}
			}
		});
		btnDown.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1,
				1));

		Button btnEdit = new Button(grpEmployerSelection, SWT.NONE);
		btnEdit.addSelectionListener(new SelectionAdapter() {
			// checks that a single employer is selected, then prompts the user
			// to edit it
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkSingleEmployerSelected()) {
					editEmployer((Employer) tblEmployer.getSelection()[0]
							.getData());
				}
			}
		});
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		btnEdit.setText("Edit");

		Button btnDelete = new Button(grpEmployerSelection, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			// checks if an employer is selected, and deletes it
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployer.getSelectionIndices().length == 0)
					noEmployersSelected();
				else if (confirmDelete()) {
					deleteEmployers();
				}
			}
		});
		btnDelete.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		btnDelete.setText("Delete");

		Button btnNewEmployer = new Button(grpEmployerSelection, SWT.NONE);
		btnNewEmployer.addSelectionListener(new SelectionAdapter() {
			// creates a new employer and updates the table selection
			@Override
			public void widgetSelected(SelectionEvent evt) {
				if (createNewEmployer()) {
					tblEmployer.deselectAll();
					tblEmployer.select(tblEmployer.getItemCount() - 1);
					updateEmployeesBasedOnSelection();
				}
			}
		});
		btnNewEmployer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		btnNewEmployer.setText("New Employer");

		Group grpEmployees = new Group(shell, SWT.NONE);
		GridLayout gl_grpEmployees = new GridLayout(4, false);
		gl_grpEmployees.marginWidth = 0;
		gl_grpEmployees.marginHeight = 0;
		grpEmployees.setLayout(gl_grpEmployees);
		grpEmployees.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		grpEmployees.setText("Employees");

		Composite composite = new Composite(grpEmployees, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4,
				1));
		composite.setLayout(new GridLayout(2, false));

		tblEmployees = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		tblEmployees.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 2));
		tblEmployees.setHeaderVisible(true);
		tblEmployees.setLinesVisible(true);

		tblclmnEmployeeNumber = new TableColumn(tblEmployees, SWT.NONE);
		tblclmnEmployeeNumber.setWidth(150);
		tblclmnEmployeeNumber.setText("Employee Number");

		tblclmnFirstName = new TableColumn(tblEmployees, SWT.NONE);
		tblclmnFirstName.setWidth(150);
		tblclmnFirstName.setText("First Name");

		tblclmnLastName = new TableColumn(tblEmployees, SWT.NONE);
		tblclmnLastName.setWidth(150);
		tblclmnLastName.setText("Last Name");

		// add the sort listeners to all the table columns
		tblclmnEmployeeNumber.addListener(SWT.Selection, employeeSortListener);
		tblclmnFirstName.addListener(SWT.Selection, employeeSortListener);
		tblclmnLastName.addListener(SWT.Selection, employeeSortListener);
		tblEmployees.setSortDirection(SWT.UP);

		btnUp_1 = new Button(composite, SWT.ARROW | SWT.UP);
		btnUp_1.addSelectionListener(new SelectionAdapter() {
			// checks if a shift is possible, and then shifts the employees
			// and updates the selections.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployees.getSelectionIndices().length == 0) {
					noEmployeesSelected();
				} else if (tblEmployees.getSelectionIndices()[0] == 0) {
					unableToShift();
				} else {
					moveEmployeesUp();
				}
			}
		});
		btnUp_1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1,
				1));
		btnUp_1.setText("Up");

		btnDown_1 = new Button(composite, SWT.ARROW | SWT.DOWN);
		btnDown_1.addSelectionListener(new SelectionAdapter() {
			// checks if a shift is possible, and then shifts the employees and
			// updates the selections.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployees.getSelectionIndices().length == 0) {
					noEmployeesSelected();
				} else if (tblEmployees.getSelectionIndices()[tblEmployees
						.getSelectionIndices().length - 1] == tblEmployees
						.getItemCount() - 1) {
					unableToShift();
				} else {
					moveEmployeesDown();
				}
			}
		});
		btnDown_1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true,
				1, 1));
		btnDown_1.setText("Down");

		Button btnViewFullReport = new Button(grpEmployees, SWT.NONE);
		btnViewFullReport.addSelectionListener(new SelectionAdapter() {
			// checks if a single employee is selected, then displays the full
			// report.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkSingleEmployeeSelected())
					fullReport();
			}
		});
		btnViewFullReport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		btnViewFullReport.setText("View Full Report");

		Button btnEdit_1 = new Button(grpEmployees, SWT.NONE);
		btnEdit_1.addSelectionListener(new SelectionAdapter() {
			// checks if a single employee is selected, then prompts the user to
			// edit it.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkSingleEmployeeSelected()) {
					editEmployee();
				}
			}
		});
		btnEdit_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		btnEdit_1.setText("Edit");

		Button btnChangeEmployer = new Button(grpEmployees, SWT.NONE);
		btnChangeEmployer.addSelectionListener(new SelectionAdapter() {
			// opens the menu to change employer
			@Override
			public void widgetSelected(SelectionEvent e) {
				menuChangeEmployer.setVisible(true);
			}
		});
		btnChangeEmployer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		btnChangeEmployer.setText("Change Employer");

		menuChangeEmployer = new Menu(btnChangeEmployer);
		menuChangeEmployer.addMenuListener(new MenuAdapter() {
			// if the menu is shown, the menu items are generated
			@Override
			public void menuShown(MenuEvent e) {
				createMenu();
			}
		});
		btnChangeEmployer.setMenu(menuChangeEmployer);

		Button btnDelete_1 = new Button(grpEmployees, SWT.NONE);
		btnDelete_1.addSelectionListener(new SelectionAdapter() {
			// checks if a single employee is selected, and then asks the user
			// for confirmation and deletes it.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployees.getSelectionIndices().length == 0)
					noEmployeesSelected();
				else if (confirmDelete()) {
					deleteEmployees();
				}
			}
		});
		btnDelete_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		btnDelete_1.setText("Delete");

		Button btnNewEmployee = new Button(grpEmployees, SWT.NONE);
		btnNewEmployee.addSelectionListener(new SelectionAdapter() {
			// makes sure there is a valid employer field placement selected,
			// then creates a new employee in that placement.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tblEmployer.getSelectionCount() == 0) {
					noEmployersSelected();
				} else if (tblEmployer.getSelectionCount() > 1) {
					multipleEmployersSelected();
				} else {
					if (createNewEmployee()) {
						tblEmployees.deselectAll();
						tblEmployees.select(tblEmployees.getItemCount() - 1);
					}
				}
			}
		});
		btnNewEmployee.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 4, 1));
		btnNewEmployee.setText("New Employee");

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmfile = new MenuItem(menu, SWT.CASCADE);
		mntmfile.setText("&File");

		Menu menuFile = new Menu(mntmfile);
		mntmfile.setMenu(menuFile);

		MenuItem mntmload = new MenuItem(menuFile, SWT.NONE);
		mntmload.addSelectionListener(new SelectionAdapter() {
			// loads data from text files
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				load();
			}
		});
		mntmload.setAccelerator(SWT.MOD1 + 'O');
		mntmload.setText("&Load");

		new MenuItem(menuFile, SWT.SEPARATOR);

		MenuItem mntmsave = new MenuItem(menuFile, SWT.NONE);
		mntmsave.addSelectionListener(new SelectionAdapter() {
			// saves the data into CSV text files
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
		mntmsave.setAccelerator(SWT.MOD1 + 'S');
		mntmsave.setText("&Save");

		MenuItem mntmsaveAs = new MenuItem(menuFile, SWT.NONE);
		mntmsaveAs.addSelectionListener(new SelectionAdapter() {
			// prompts the user for a directory, and saves the CSV files into
			// it.
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveAs();
			}
		});
		mntmsaveAs.setAccelerator(SWT.MOD1 + SWT.SHIFT + 'S');
		mntmsaveAs.setText("&Save As");

		new MenuItem(menuFile, SWT.SEPARATOR);

		MenuItem mntmGenerateReports_1 = new MenuItem(menuFile, SWT.CASCADE);
		mntmGenerateReports_1.setText("Generate Reports");

		Menu menu_3 = new Menu(mntmGenerateReports_1);
		mntmGenerateReports_1.setMenu(menu_3);

		MenuItem mntmFullEmployeeReport = new MenuItem(menu_3, SWT.NONE);
		mntmFullEmployeeReport.addSelectionListener(new SelectionAdapter() {
			// Checks if a single employee is selected, then displays a full
			// report.
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkSingleEmployeeSelected())
					fullReport();
			}
		});
		mntmFullEmployeeReport.setText("Full Employee Report");

		new MenuItem(menu_3, SWT.SEPARATOR);

		MenuItem mntmByEvaluation = new MenuItem(menu_3, SWT.NONE);
		mntmByEvaluation.addSelectionListener(new SelectionAdapter() {
			// displays a report of the employees sorted by score
			@Override
			public void widgetSelected(SelectionEvent e) {
				scoreReport();
			}
		});
		mntmByEvaluation.setText("By Evaluation");

		MenuItem mntmByEmployer = new MenuItem(menu_3, SWT.NONE);
		mntmByEmployer.addSelectionListener(new SelectionAdapter() {
			// displays a report of the employees sorted by employer
			@Override
			public void widgetSelected(SelectionEvent e) {
				employerReport();
			}
		});
		mntmByEmployer.setText("By Employer");

		MenuItem mntmByEmployee = new MenuItem(menu_3, SWT.NONE);
		mntmByEmployee.addSelectionListener(new SelectionAdapter() {
			// displays a report of the employees, sorted by employee number
			@Override
			public void widgetSelected(SelectionEvent e) {
				employeeReport();
			}
		});
		mntmByEmployee.setText("By Employee");

		MenuItem mntmhelp = new MenuItem(menu, SWT.CASCADE);
		mntmhelp.setText("&Help");

		Menu menuHelp = new Menu(mntmhelp);
		mntmhelp.setMenu(menuHelp);
		
		MenuItem mntmInstructions = new MenuItem(menuHelp, SWT.NONE);
		mntmInstructions.addSelectionListener(new SelectionAdapter() {
			//Bring up the instruction menu
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new HelpWindow().open(shell);
			}
		});
		mntmInstructions.setText("Instructions");

	}

	/**
	 * Creates the menu used to move the employee to another employer
	 */
	private void createMenu() {

		// Deletes the items already in the menu
		for (MenuItem mi : menuChangeEmployer.getItems())
			mi.dispose();
		for (int i = 0; i < employers.size(); i++) {
			final MenuItem temp = new MenuItem(menuChangeEmployer, SWT.NONE);
			temp.setText(employers.get(i).getEmployerNumb() + ": "
					+ employers.get(i).getCompanyName());
			final int k = i;
			temp.addSelectionListener(new SelectionAdapter() {
				// checks if there is/are employee(s) selected, and moves them
				@Override
				public void widgetSelected(SelectionEvent evt) {
					// if no employees are selected, alerts the user
					if (tblEmployees.getSelection().length == 0)
						noEmployeesSelected();
					// otherwise moves the employees
					else {
						// stores the selections in a temporary ArrayList
						ArrayList<Employee> temp = new ArrayList<Employee>();
						for (TableItem ti : tblEmployees.getSelection())
							temp.add((Employee) ti.getData());
						// deletes the selected employees
						deleteEmployees(temp);
						// adds the selected employees to the selected employer
						for (Employee e : temp)
							e.getEvaluation().setEmployerNumb(
									employers.get(k).getEmployerNumb());
						employers.get(k).getEmployees().addAll(temp);
						// updates the employee table
						updateEmployeeTable();
					}
				}
			});
		}
	}

	/**
	 * Prompts the user to create a new employee
	 * 
	 * @return if the employee was created
	 */
	private boolean createNewEmployee() {
		Employee e;
		if ((e = new EmployeeDialogue(shell).open(null, ((Employer) tblEmployer
				.getSelection()[0].getData()).getEmployerNumb())) != null) {
			if (isDuplicate(e)) {
				// if the user enters something invalid and chooses to modify
				// their input, the method calls itself with the invalid input
				// as a parameter.
				if (duplicateMessage()) {
					return createNewEmployee(e);
				}
			} else {
				employees.add(e);
				updateEmployeeTable();
				tblEmployees.setSortColumn(null);
				return true;
			}
		}
		return false;
	}

	/**
	 * Prompts the user to create a new employee after a previously created
	 * employee caused an error.
	 * 
	 * @param employee
	 *            the previously created invalid employee
	 * @return if the employee was created
	 */
	private boolean createNewEmployee(Employee employee) {
		Employee e;
		if ((e = new EmployeeDialogue(shell).open(employee,
				((Employer) tblEmployer.getSelection()[0].getData())
						.getEmployerNumb())) != null) {
			if (isDuplicate(e)) {
				if (duplicateMessage()) {
					return createNewEmployee(e);
				}
			} else {
				employees.add(e);
				updateEmployeeTable();
				tblEmployees.setSortColumn(null);
				return true;
			}
		}
		return false;
	}

	/**
	 * Prompts the user to create a new employer
	 * 
	 * @return if the employer was created
	 */
	private boolean createNewEmployer() {

		Employer e;
		if ((e = new EmployerDialogue(shell).open(null)) != null) {
			if (isDuplicate(e)) {
				// if the user enters something invalid and chooses to modify
				// their input, the method calls itself with the invalid input
				// as a parameter.
				if (duplicateMessage()) {
					return createNewEmployer(e);
				}
			} else {
				employers.add(e);
				updateEmployerTable();
				tblEmployer.setSortColumn(null);
				return true;
			}
		}
		return false;
	}

	/**
	 * Prompts the user to create a new employer after a previously created
	 * employee caused an error.
	 * 
	 * @param employer
	 *            the previously created invalid employer
	 * @return if the employer was created
	 */
	private boolean createNewEmployer(Employer employer) {
		Employer e;
		if ((e = new EmployerDialogue(shell).open(employer)) != null) {
			if (isDuplicate(e)) {
				if (duplicateMessage()) {
					return createNewEmployer(e);
				}
			} else {
				employers.add(e);
				updateEmployerTable();
				tblEmployer.setSortColumn(null);
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes the selected employees
	 */
	private void deleteEmployees() {
		// loop through and delete the selected employees from the employee
		// fields by searching for the deleted employee number
		for (Employer employer : employers) {
			ArrayList<Employee> employees = employer.getEmployees();
			for (int i = 0; i < employees.size(); i++) {
				for (TableItem ti : tblEmployees.getSelection()) {
					if (((Employee) ti.getData()).getEmployeeNumb().equals(
							employees.get(i).getEmployeeNumb())) {
						employees.remove(i);
						i--;
						break;
					}
				}
			}
		}
		// updates the employee table
		updateEmployeesBasedOnSelection();
	}

	/**
	 * Deletes the inputed employees
	 * 
	 * @param emps
	 *            the employees to delete
	 */
	private void deleteEmployees(ArrayList<Employee> emps) {
		// loops through the employers and checks if any of their employees have
		// matching identifiers to the employees to delete, and if they do,
		// deletes them
		for (Employer employer : employers) {
			ArrayList<Employee> employees = employer.getEmployees();
			for (int i = 0; i < employees.size(); i++) {
				for (Employee e : emps) {
					if (e.getEmployeeNumb().equals(
							employees.get(i).getEmployeeNumb())) {
						employees.remove(i);
						i--;
						break;
					}
				}
			}
		}
	}

	/**
	 * Deletes the selected employers
	 */
	private void deleteEmployers() {
		// loop through and delete the selected employers from the employer
		// fields by searching for the deleted employee number
		for (int i = 0; i < employers.size(); i++) {
			Employer temp = employers.get(i);
			for (TableItem ti : tblEmployer.getSelection()) {
				if (temp.getEmployerNumb().equals(
						((Employer) ti.getData()).getEmployerNumb())) {
					employers.remove(i);
					i--;
					break;
				}
			}
		}
		// updates the tables
		updateEmployerTable();
		updateEmployeesBasedOnSelection();
	}

	/**
	 * Alerts the user an item was created with an invalid duplicate identifier
	 * 
	 * @return if the user would like to edit the invalid item
	 */
	private boolean duplicateMessage() {
		return SWTFactory
				.errorOptionMessage(
						shell,
						"You created an item with a duplicate identifier. Would you like to re-open the dialog?",
						"Error: Duplicate");
	}

	/**
	 * Calls the recursive helper method of the same name
	 * 
	 * @param employee
	 *            the employee to edit
	 */
	private void editEmployee() {
		Employee employee = (Employee) tblEmployees.getSelection()[0].getData();
		editEmployee(employee, employee);
		updateEmployeeTable();
	}

	/**
	 * Prompts the user to edit an employee
	 * 
	 * @param employee
	 *            the employee to edit
	 * @param inTable
	 *            the employee originally in the table when the edit sequence
	 *            began
	 */
	private void editEmployee(Employee employee, Employee inTable) {
		Employee e;
		if ((e = new EmployeeDialogue(shell).open(employee, employee
				.getEvaluation().getEmployerNumb())) != null) {
			if (occurrencesOf(e)
					+ (e.getEmployeeNumb().equals(inTable.getEmployeeNumb())
							|| e.getEvaluation()
									.getEvaluationNumb()
									.equals(inTable.getEvaluation()
											.getEvaluationNumb()) ? 0 : 1) > 1) {
				if (duplicateMessage()) {
					String temp = e.getEmployeeNumb();
					editEmployee(e, inTable);
					if (!e.getEmployeeNumb().equals(temp)) {
						employee.setTo(e);
						tblEmployees.setSortColumn(null);
					}
				}
			} else {
				employee.setTo(e);
				tblEmployees.setSortColumn(null);
			}
		}
	}

	/**
	 * Calls the recursive helper method of the same name
	 * 
	 * @param employer
	 *            the employer to edit
	 */
	private void editEmployer(Employer employer) {
		editEmployer(employer, employer);
		updateEmployerTable();
	}

	/**
	 * Prompts the user to edit an employer
	 * 
	 * @param employer
	 *            the employer to edit
	 * @param inTable
	 *            the employer originally in the table when the edit sequence
	 *            began
	 */
	private void editEmployer(Employer employer, Employer inTable) {
		Employer e;
		if ((e = new EmployerDialogue(shell).open(employer)) != null) {
			if (occurrencesOf(e)
					+ (e.getEmployerNumb().equals(inTable.getEmployerNumb()) ? 0
							: 1) > 1) {
				if (duplicateMessage()) {
					String temp = e.getEmployerNumb();
					editEmployer(e, inTable);
					if (!e.getEmployerNumb().equals(temp)) {
						employer.setTo(e);
						tblEmployer.setSortColumn(null);
					}
				}
			} else {
				employer.setTo(e);
				tblEmployer.setSortColumn(null);
			}
		}
	}

	/**
	 * generates a report for all the employees sorted by employee number
	 */
	private void employeeReport() {
		ReportWindow reportWindow = new ReportWindow();
		reportWindow.open(shell, employers, ReportWindow.EMPLOYEE);
	}

	/**
	 * generates a report for all the employees sorted by employer
	 */
	private void employerReport() {
		ReportWindow reportWindow = new ReportWindow();
		reportWindow.open(shell, employers, ReportWindow.EMPLOYER);
	}

	/**
	 * generates a full report for the selected employee
	 */
	private void fullReport() {
		ReportWindow reportWindow = new ReportWindow();
		reportWindow.open(shell,
				(Employee) tblEmployees.getSelection()[0].getData(),
				ReportWindow.INDIVIDUAL);
	}

	/**
	 * Checks if an employee already exists
	 * 
	 * @param employee
	 * @return if the employee exists
	 */
	private boolean isDuplicate(Employee employee) {
		for (Employer emp : employers) {
			for (Employee e : emp.getEmployees()) {
				if (employee.getEmployeeNumb().equals(e.getEmployeeNumb())
						|| employee.getEvaluation().getEvaluationNumb()
								.equals(e.getEvaluation().getEvaluationNumb())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if an employer already exists
	 * 
	 * @param employer
	 * @return if the employer exists
	 */
	private boolean isDuplicate(Employer employer) {
		for (Employer e : employers) {
			if (employer.getEmployerNumb().equals(e.getEmployerNumb())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * loads employee and employer data from the program's directory
	 */
	private void load() {
		// prompts the user for a directory
		if (setDirectory()) {
			ArrayList<Employer> current = employers;
			try {
				String separator = System.getProperty("file.separator");
				// reads the four data files into temporary variables
				FileHandler handler = new FileHandler();
				ArrayList<Employer> tempEmployers = handler
						.readEmployers(directory + separator + "EMPLOYER.txt");
				ArrayList<Employee> tempEmployees = handler
						.readEmployees(directory + separator + "EMPLOYEES.txt");
				ArrayList<Evaluation> tempEvaluations = handler
						.readEvaluations(directory + separator
								+ "EVALUATION RESULTS.txt");
				ArrayList<FieldPlacement> tempPlacements = handler
						.readFieldPlacements(directory + separator
								+ "FIELD PLACEMENTS.txt");
				if (tempEmployers == null || tempEmployees == null
						|| tempEvaluations == null || tempPlacements == null) {
					SWTFactory
							.errorMessage(
									shell,
									"An error occurred while reading the files. No data was imported.\nCheck to make sure no files have been moved or modified since the last save.",
									"Error");
				} else {
					// assigns the employer field, and then loops through and
					// assigns the employees and evaluations based on the other
					// data fields
					employers = tempEmployers;
					for (int i = 0; i < employers.size(); i++) {
						for (int j = 0; j < tempEmployees.size(); j++) {
							if (tempPlacements.get(j).getEmployerNumb()
									.equals(employers.get(i).getEmployerNumb())) {
								tempEmployees.get(j).setEvaluation(
										tempEvaluations.get(j));
								employers.get(i).getEmployees()
										.add(tempEmployees.get(j));
							}
						}
					}
					// after data has been loaded, updates the tables
					updateEmployerTable();
					updateEmployeesBasedOnSelection();
					//alert the user the load was successful
					SWTFactory.notificationMessage(shell,
							"The files were loaded", "Success");
				}
			}
			// if an exception occurs, reset all data in the table and alert the
			// user
			catch (Exception e) {
				employers = current;
				updateEmployerTable();
				updateEmployeesBasedOnSelection();
				SWTFactory
						.errorMessage(
								shell,
								"An error occurred while reading the files. No data was imported.\nCheck to make sure no files have been moved or modified since the last save.",
								"Error");
			}

		}
		// if the user does not chose a directory, alerts them that the load
		// operation was canceled
		else {
			SWTFactory.errorMessage(shell, "Load operation canceled",
					"Load Canceled");
		}
	}

	/**
	 * Shifts the selected employees down
	 */
	private void moveEmployeesDown() {
		int[] selections = tblEmployees.getSelectionIndices();
		// swaps all selected employees with the employee below it
		for (int i : selections) {
			Employee temp = employees.get(i);
			employees.set(i, employees.get(i + 1));
			employees.set(i + 1, temp);
		}
		updateEmployeeTable();
		tblEmployees.deselectAll();
		int[] temp = new int[selections.length];
		// reselects the originally selected employees
		for (int i = 0; i < selections.length; i++)
			temp[i] = selections[i] + 1;
		tblEmployees.select(temp);
		tblEmployees.setSortColumn(null);
	}

	/**
	 * Shifts the selected employees up
	 */
	private void moveEmployeesUp() {
		int[] selections = tblEmployees.getSelectionIndices();
		// swaps all selected employees with the employee above it
		for (int i : selections) {
			Employee temp = employees.get(i);
			employees.set(i, employees.get(i - 1));
			employees.set(i - 1, temp);
		}
		updateEmployeeTable();
		tblEmployees.deselectAll();
		int[] temp = new int[selections.length];
		// reselects the originally selected employees
		for (int i = 0; i < selections.length; i++)
			temp[i] = selections[i] - 1;
		tblEmployees.select(temp);
		tblEmployees.setSortColumn(null);
	}

	/**
	 * Shifts the selected employers down
	 */
	private void moveEmployersDown() {
		int[] selections = tblEmployer.getSelectionIndices();
		// swaps all selected employers with the employer below it
		for (int i : selections) {
			Employer temp = employers.get(i);
			employers.set(i, employers.get(i + 1));
			employers.set(i + 1, temp);
		}
		updateEmployerTable();
		tblEmployer.deselectAll();
		int[] temp = new int[selections.length];
		// reselects the originally selected employers
		for (int i = 0; i < selections.length; i++)
			temp[i] = selections[i] + 1;
		tblEmployer.select(temp);
		tblEmployer.setSortColumn(null);
	}

	/**
	 * Shifts the selected employers up
	 */
	private void moveEmployersUp() {
		int[] selections = tblEmployer.getSelectionIndices();
		// swaps all selected employers with the employer above it
		for (int i : selections) {
			Employer temp = employers.get(i);
			employers.set(i, employers.get(i - 1));
			employers.set(i - 1, temp);
		}
		updateEmployerTable();
		tblEmployer.deselectAll();
		int[] temp = new int[selections.length];
		// reselects the originally selected employers
		for (int i = 0; i < selections.length; i++)
			temp[i] = selections[i] - 1;
		tblEmployer.select(temp);
		tblEmployer.setSortColumn(null);
	}

	/**
	 * Alerts the user there are multiple employees selected
	 */
	private void multipleEmployeesSelected() {
		SWTFactory.errorMessage(shell, "Please select only one employee",
				"Error: Multiple Selected");
	}

	/**
	 * Alerts the user there are multiple employers selected
	 */
	private void multipleEmployersSelected() {
		SWTFactory.errorMessage(shell, "Please select only one employer",
				"Error: Multiple Selected");
	}

	/**
	 * Alerts the user there are no employees selected
	 */
	private void noEmployeesSelected() {
		SWTFactory.errorMessage(shell, "Please select an employee",
				"Error: None Selected");
	}

	/**
	 * Alerts the user there are no employers selected
	 */
	private void noEmployersSelected() {
		SWTFactory.errorMessage(shell, "Please select an employer",
				"Error: None Selected");
	}

	/**
	 * Checks how many equivalent employees are stored
	 * 
	 * @param employee
	 * @return the number of equivalent employees stored
	 */
	private int occurrencesOf(Employee employee) {
		int result = 0;
		for (Employer emp : employers) {
			for (Employee e : emp.getEmployees()) {
				if (employee.getEmployeeNumb().equals(e.getEmployeeNumb())
						|| employee.getEvaluation().getEvaluationNumb()
								.equals(e.getEvaluation().getEvaluationNumb())) {
					result++;
				}
			}
		}
		return result;
	}

	/**
	 * Checks how many equivalent employers are stored
	 * 
	 * @param employer
	 * @return the number of equivalent employers stored
	 */
	private int occurrencesOf(Employer employer) {
		int result = 0;
		for (Employer e : employers) {
			if (employer.getEmployerNumb().equals(e.getEmployerNumb())) {
				result++;
			}
		}
		return result;
	}

	/**
	 * Open the window.
	 * 
	 * @param display
	 *            The current display in use.
	 * 
	 * @wbp.parser.entryPoint
	 * 
	 */
	public void open(Display display) {
		// initialize the data fields
		employers = new ArrayList<Employer>();
		employees = new ArrayList<Employee>();
		// create the contents of the window, and then open it.
		createContents(display);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Saves the files into the program's directory or prompts the user for a
	 * directory if one does not exist
	 */
	private void save() {
		// if the program directory has not been set yet, this method is
		// equivalent to the saveAs() method
		if (directory == null) {
			saveAs();
		} else {
			String separator = System.getProperty("file.separator");
			FileHandler handler = new FileHandler();
			// tries writing the files, catches any Exception that may be
			// thrown, and then cancels the save operation
			try {
				// creates temporary variables to store data in
				ArrayList<Employee> tempEmployees = new ArrayList<Employee>();
				ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
				ArrayList<FieldPlacement> placements = new ArrayList<FieldPlacement>();
				// loops through the employers file and creates objects in the
				// temporary variables
				for (Employer e : employers) {
					for (Employee e0 : e.getEmployees()) {
						tempEmployees.add(e0);
						evaluations.add(e0.getEvaluation());
						placements.add(new FieldPlacement(e0.getEmployeeNumb(),
								e.getEmployerNumb()));
					}
				}
				// writes each of the four data files, and store whether or not
				// they were successes in booleans
				boolean success1 = handler.writeEmployers(employers, directory
						+ separator + "EMPLOYER.txt");
				boolean success2 = handler.writeEmployees(tempEmployees,
						directory + separator + "EMPLOYEES.txt");
				boolean success3 = handler.writeEvaluations(evaluations,
						directory + separator + "EVALUATION RESULTS.txt");
				boolean success4 = handler.writeFieldPlacements(placements,
						directory + separator + "FIELD PLACEMENTS.txt");
				// if the save was a success, alerts the user, otherwise, alerts
				// the user
				if (success1 && success2 && success3 && success4) {
					SWTFactory.notificationMessage(shell,
							"The files were written", "Success");
				} else {
					SWTFactory
							.errorMessage(
									shell,
									"An error occurred while writting the files. The files may be corrupted",
									"Error");
				}
			}
			// alert the user there was an error writing the files
			catch (Exception e) {
				SWTFactory
						.errorMessage(
								shell,
								"An error occurred while writting the files. The files may be corrupted",
								"Error");
			}
		}
	}

	/**
	 * Prompts the user for a directory and then saves the files into that
	 * directory
	 */
	private void saveAs() {
		// prompts the user for a directory, and then saves the files
		if (setDirectory()) {
			save();
		}
		// if no directory was chosen, alerts the user the operation was
		// canceled
		else {
			SWTFactory.errorMessage(shell, "Save operation canceled",
					"Save Canceled");
		}
	}

	/**
	 * generates a report for all the employees sorted by score
	 */
	private void scoreReport() {
		ReportWindow reportWindow = new ReportWindow();
		reportWindow.open(shell, employers, ReportWindow.EVALUATION_SCORE);
	}

	/**
	 * removes all items in the tables that do not match the search text
	 */
	private void search() {
		// resets the tables
		tblEmployer.deselectAll();
		updateEmployerTable();
		updateEmployeesBasedOnSelection();
		// removes all items that do not contain the search field
		searchEmployers();
		searchEmployees();
		// disables the shift buttons when text is entered in the search field
		if (txtSearch.getText().length() != 0) {
			btnUp.setEnabled(false);
			btnUp_1.setEnabled(false);
			btnDown.setEnabled(false);
			btnDown_1.setEnabled(false);
		} else {
			btnUp.setEnabled(true);
			btnUp_1.setEnabled(true);
			btnDown.setEnabled(true);
			btnDown_1.setEnabled(true);
		}
	}

	/**
	 * removes all items from the employee table that do not match the search
	 * text
	 */
	private void searchEmployees() {
		if (txtSearch.getText().length() != 0)
			for (TableItem ti : tblEmployees.getItems()) {
				if (!((Employee) ti.getData()).tableDataContains(txtSearch
						.getText())) {
					ti.dispose();
				}
			}
	}

	/**
	 * removes all items from the employer table that do not match the search
	 * text
	 */
	private void searchEmployers() {
		if (txtSearch.getText().length() != 0)
			for (TableItem ti : tblEmployer.getItems()) {
				if (!((Employer) ti.getData()).tableDataContains(txtSearch
						.getText())) {
					ti.dispose();
				}
			}
	}

	/**
	 * sets file directory for the program
	 * 
	 * @return whether the directory was altered
	 */
	private boolean setDirectory() {
		String tempString = new MyDirectoryDialog(shell).open(directory);
		if (tempString != null) {
			directory = tempString;
			return true;
		}
		return false;
	}

	/**
	 * Alerts the user an illegal shift has been performed
	 */
	private void unableToShift() {
		SWTFactory.errorMessage(shell, "The shift could not be performed",
				"Error: Could Not Shift");
	}

	/**
	 * Extracts the employees from the employers based on which employers are
	 * selected
	 */
	private void updateEmployeesBasedOnSelection() {
		// If only one employer is selected, import all the employees from that
		// employer
		if (tblEmployer.getSelectionCount() == 1) {
			employees = employers.get(tblEmployer.getSelectionIndices()[0])
					.getEmployees();
			updateEmployeeTable();
		}
		// If none are selected, import all employees
		else if (tblEmployer.getSelectionCount() == 0) {
			employees = new ArrayList<Employee>();
			for (Employer e : employers) {
				employees.addAll(e.getEmployees());
			}
			updateEmployeeTable();
		}
		// Otherwise, import the employees from the selected employers
		else {
			employees = new ArrayList<Employee>();
			for (int i : tblEmployer.getSelectionIndices()) {
				employees.addAll(employers.get(i).getEmployees());
			}
			updateEmployeeTable();
		}
		tblEmployees.deselectAll();
		tblEmployees.setSortColumn(null);
	}

	/**
	 * Updates the employee table based on the employees stored in the window.
	 */
	private void updateEmployeeTable() {
		int employeeSize = employees.size();
		int tableSize = tblEmployees.getItems().length;
		// if there are less employees stored than in the table, loops through
		// and updates the selections, then removes the excess table items
		if (employeeSize < tableSize) {
			for (int i = 0; i < employeeSize; i++) {
				tblEmployees.getItem(i).setData(employees.get(i));
				tblEmployees.getItem(i)
						.setText(employees.get(i).getTableData());
			}
			tblEmployees.remove(employeeSize, tableSize - 1);
		}
		// otherwise, loops through and updates the selections, then adds
		// additional table items
		else {
			for (int i = 0; i < tableSize; i++) {
				tblEmployees.getItem(i).setData(employees.get(i));
				tblEmployees.getItem(i)
						.setText(employees.get(i).getTableData());
			}
			for (int i = tableSize; i < employeeSize; i++) {
				TableItem ti = new TableItem(tblEmployees, SWT.NONE);
				ti.setData(employees.get(i));
				ti.setText(employees.get(i).getTableData());
			}
		}
		// removes the employees that do not fit the search criteria
		searchEmployees();
	}

	/**
	 * Updates the employer table based on the employers stored in the window.
	 */
	private void updateEmployerTable() {
		int employerSize = employers.size();
		int tableSize = tblEmployer.getItems().length;
		// if there are less employers than currently displayed, loops through
		// and updates all table items, then removes the excess ones.
		if (employerSize < tableSize) {
			for (int i = 0; i < employerSize; i++) {
				tblEmployer.getItem(i).setData(employers.get(i));
				tblEmployer.getItem(i).setText(employers.get(i).getTableData());
			}
			tblEmployer.remove(employerSize, tableSize - 1);
		}
		// otherwise, loops through and updates the table items, then adds
		// additional items.
		else {
			for (int i = 0; i < tableSize; i++) {
				tblEmployer.getItem(i).setData(employers.get(i));
				tblEmployer.getItem(i).setText(employers.get(i).getTableData());
			}
			for (int i = tableSize; i < employerSize; i++) {
				TableItem ti = new TableItem(tblEmployer, SWT.NONE);
				ti.setData(employers.get(i));
				ti.setText(employers.get(i).getTableData());
			}
		}
		// removes the table items that do not currently fit the search criteria
		searchEmployers();
	}
}
