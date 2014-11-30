package evaluations;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Represents an employee evaluation. Has fields representing the evaluation
 * information.
 * 
 * @author David Abrahams
 * @version 2/4/2012
 * 
 */
public final class Evaluation {

	private String evaluationNumb;
	private String employeeNumb, employerNumb;
	private int workQualityScore, workHabitsScore, jobKnowledgeScore,
			behaviorScore;
	private double averageScore;
	private double overallProgressScore;
	private String workQualityComments, workHabitsComments,
			jobKnowledgeComments, behaviorComments;
	private String overallComments;
	private boolean employeeRecommendation;
	private Calendar evalDate, nextEvalDate;

	/**
	 * The processor used by CsvBeanReader to read a CSV file into an evaluation
	 */
	public static final CellProcessor[] PROCESSOR_IN = new CellProcessor[] {
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ConvertNullTo(""),
			new ConvertNullTo(""), new ParseInt(), new ConvertNullTo(""),
			new ParseInt(), new ConvertNullTo(""), new ParseInt(),
			new ConvertNullTo(""), new ParseInt(), new ConvertNullTo(""),
			new ParseDouble(), new ParseDouble(), new ConvertNullTo(""),
			new ParseBool() };
	/**
	 * The processor used by CsvBeanWriter to write a CSV file from an Evaluation
	 * object
	 */
	public static final CellProcessor[] PROCESSOR_OUT = new CellProcessor[] {
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull(), new NotNull(), new NotNull(), new NotNull(),
			new NotNull() };
	/**
	 * The header used by CsvBeanWriter and CsvBeanReader to write and read the
	 * header for a CSV file
	 */
	public static final String[] header = new String[] { "evaluationNumb",
			"employeeNumb", "employerNumb", "evalDate", "nextEvalDate",
			"workQualityScore", "workQualityComments", "workHabitsScore",
			"workHabitsComments", "jobKnowledgeScore", "jobKnowledgeComments",
			"behaviorScore", "behaviorComments", "averageScore",
			"overallProgressScore", "overallComments", "employeeRecommendation" };

	/**
	 * No argument constructor; private Object fields are initialized to null, primitives to 0
	 */
	public Evaluation() {
		super();
	}

	/**
	 * Constructs an Evaluation object with the given parameters
	 * 
	 * @param evaluationNumb
	 * @param employeeNumb
	 * @param employerNumb
	 * @param evalDate
	 * @param nextEvalDate
	 * @param workQualityScore
	 * @param workQualityComments
	 * @param workHabitsScore
	 * @param workHabitsComments
	 * @param jobKnowledgeScore
	 * @param jobKnowledgeComments
	 * @param behaviorScore
	 * @param behaviorComments
	 * @param averageScore
	 * @param overallProgressScore
	 * @param overallComments
	 * @param employeeRecommendation
	 */
	public Evaluation(String evaluationNumb, String employeeNumb,
			String employerNumb, Calendar evalDate, Calendar nextEvalDate,
			int workQualityScore, String workQualityComments,
			int workHabitsScore, String workHabitsComments,
			int jobKnowledgeScore, String jobKnowledgeComments,
			int behaviorScore, String behaviorComments, double averageScore,
			double overallProgressScore, String overallComments,
			boolean employeeRecommendation) {
		super();
		this.evaluationNumb = evaluationNumb;
		this.employeeNumb = employeeNumb;
		this.employerNumb = employerNumb;
		this.workQualityScore = workQualityScore;
		this.workHabitsScore = workHabitsScore;
		this.jobKnowledgeScore = jobKnowledgeScore;
		this.behaviorScore = behaviorScore;
		this.averageScore = averageScore;
		this.overallProgressScore = overallProgressScore;
		this.workQualityComments = workQualityComments;
		this.workHabitsComments = workHabitsComments;
		this.jobKnowledgeComments = jobKnowledgeComments;
		this.behaviorComments = behaviorComments;
		this.overallComments = overallComments;
		this.employeeRecommendation = employeeRecommendation;
		this.evalDate = new GregorianCalendar(evalDate.get(Calendar.YEAR),
				evalDate.get(Calendar.MONTH),
				evalDate.get(Calendar.DAY_OF_MONTH));
		this.nextEvalDate = new GregorianCalendar(
				nextEvalDate.get(Calendar.YEAR),
				nextEvalDate.get(Calendar.MONTH),
				nextEvalDate.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @return the average score
	 */
	public double getAverageScore() {
		return averageScore;
	}

	/**
	 * @return the behavior comments
	 */
	public String getBehaviorComments() {
		return behaviorComments;
	}

	/**
	 * @return the behavior score
	 */
	public int getBehaviorScore() {
		return behaviorScore;
	}

	/**
	 * @return the employee number
	 */
	public String getEmployeeNumb() {
		return employeeNumb;
	}

	/**
	 * @return if the employee is recommended
	 */
	public boolean getEmployeeRecommendation() {
		return employeeRecommendation;
	}

	/**
	 * @return the employer number
	 */
	public String getEmployerNumb() {
		return employerNumb;
	}

	/**
	 * @return the evaluation date in form mm/dd/yyyy
	 */
	public String getEvalDate() {
		return evalDate.get(Calendar.MONTH) + "/"
				+ evalDate.get(Calendar.DAY_OF_MONTH) + "/"
				+ evalDate.get(Calendar.YEAR);
	}

	/**
	 * @return the evaluation date
	 */
	public Calendar getEvalDateCalendar() {
		return new GregorianCalendar(evalDate.get(Calendar.YEAR),
				evalDate.get(Calendar.MONTH),
				evalDate.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @return the evaluation number
	 */
	public String getEvaluationNumb() {
		return evaluationNumb;
	}

	/**
	 * @return the job knowledge comments
	 */
	public String getJobKnowledgeComments() {
		return jobKnowledgeComments;
	}

	/**
	 * @return the job knowledge score
	 */
	public int getJobKnowledgeScore() {
		return jobKnowledgeScore;
	}

	/**
	 * @return the next evaluation date in form mm/dd/yyyy
	 */
	public String getNextEvalDate() {
		return nextEvalDate.get(Calendar.MONTH) + "/"
				+ nextEvalDate.get(Calendar.DAY_OF_MONTH) + "/"
				+ nextEvalDate.get(Calendar.YEAR);
	}

	/**
	 * @return the next evaluation date
	 */
	public Calendar getNextEvalDateCalendar() {
		return new GregorianCalendar(nextEvalDate.get(Calendar.YEAR),
				nextEvalDate.get(Calendar.MONTH),
				nextEvalDate.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @return the overall comments
	 */
	public String getOverallComments() {
		return overallComments;
	}

	/**
	 * @return the overall progress score
	 */
	public double getOverallProgressScore() {
		return overallProgressScore;
	}

	/**
	 * @return the work habits comments
	 */
	public String getWorkHabitsComments() {
		return workHabitsComments;
	}

	/**
	 * @return the work habits score
	 */
	public int getWorkHabitsScore() {
		return workHabitsScore;
	}

	/**
	 * @return the work quality comments
	 */
	public String getWorkQualityComments() {
		return workQualityComments;
	}

	/**
	 * @return the work quality score
	 */
	public int getWorkQualityScore() {
		return workQualityScore;
	}

	/**
	 * sets the average score
	 * 
	 * @param averageScore
	 */
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	/**
	 * sets the behavior comments
	 * 
	 * @param behaviorComments
	 */
	public void setBehaviorComments(String behaviorComments) {
		this.behaviorComments = behaviorComments;
	}

	/**
	 * sets the behavior score
	 * 
	 * @param behaviorScore
	 */
	public void setBehaviorScore(int behaviorScore) {
		this.behaviorScore = behaviorScore;
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
	 * sets the employment recommendation
	 * 
	 * @param employeeRecommendation
	 */
	public void setEmployeeRecommendation(boolean employeeRecommendation) {
		this.employeeRecommendation = employeeRecommendation;
	}

	/**
	 * sets the employer number
	 * 
	 * @param employerNumb
	 */
	public void setEmployerNumb(String employerNumb) {
		this.employerNumb = employerNumb;
	}

	/**
	 * sets the evaluation date
	 * 
	 * @param evalDate in the form mm/dd/yyyy
	 */
	public void setEvalDate(String evalDate) {
		String[] date = evalDate.split("/");
		this.evalDate = new GregorianCalendar(Integer.parseInt(date[2]),
				Integer.parseInt(date[0]), Integer.parseInt(date[1]));
	}

	/**
	 * @param evaluationNumb
	 */
	public void setEvaluationNumb(String evaluationNumb) {
		this.evaluationNumb = evaluationNumb;
	}

	/**
	 * @param jobKnowledgeComments
	 */
	public void setJobKnowledgeComments(String jobKnowledgeComments) {
		this.jobKnowledgeComments = jobKnowledgeComments;
	}

	/**
	 * @param jobKnowledgeScore
	 */
	public void setJobKnowledgeScore(int jobKnowledgeScore) {
		this.jobKnowledgeScore = jobKnowledgeScore;
	}

	/**
	 * sets the next evaluation date
	 * 
	 * @param nextEvalDate in the form mm/dd/yyyy
	 */
	public void setNextEvalDate(String nextEvalDate) {
		String[] date = nextEvalDate.split("/");
		this.nextEvalDate = new GregorianCalendar(Integer.parseInt(date[2]),
				Integer.parseInt(date[0]), Integer.parseInt(date[1]));
	}

	/**
	 * sets the overall comments
	 * 
	 * @param overallComments
	 */
	public void setOverallComments(String overallComments) {
		this.overallComments = overallComments;
	}

	/**
	 * sets the overall progress score
	 * 
	 * @param overallProgressScore
	 */
	public void setOverallProgressScore(double overallProgressScore) {
		this.overallProgressScore = overallProgressScore;
	}

	/**
	 * sets the work habits comments
	 * 
	 * @param workHabitsComments
	 */
	public void setWorkHabitsComments(String workHabitsComments) {
		this.workHabitsComments = workHabitsComments;
	}

	/**
	 * sets the work habits score
	 * 
	 * @param workHabitsScore
	 */
	public void setWorkHabitsScore(int workHabitsScore) {
		this.workHabitsScore = workHabitsScore;
	}

	/**
	 * sets the work quality comments
	 * 
	 * @param workQualityComments
	 */
	public void setWorkQualityComments(String workQualityComments) {
		this.workQualityComments = workQualityComments;
	}

	/**
	 * sets the work quality score
	 * 
	 * @param workQualityScore
	 */
	public void setWorkQualityScore(int workQualityScore) {
		this.workQualityScore = workQualityScore;
	}

}
