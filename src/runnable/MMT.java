package runnable;
import org.eclipse.swt.widgets.Display;

/**
 * The class containing the main method.
 * 
 * @author David Abrahams
 * @version 2/4/2013
 *
 */
public class MMT {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Display display = new Display();
			Window window = new Window();
			window.open(display);
			display.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
