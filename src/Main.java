
/**
 * This class includes the entry point for this application
 * 
 * @author woitzik
 *
 */
public class Main {

	/**
	 * The entry point for this application
	 * @param args Arguments given with the start command
	 */
	public static void main(String[] args) {
		// Starts the user interface
		UserInterface ui = new UserInterface();
		ui.startConversation();
	}
}
