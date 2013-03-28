package fr.umlv.escape.file;

/** Specific Exception that should be thrown when the content of a file is corrupted
 */
public class IllegalFormatContentFile extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1384489293398935858L;

	/** Constructor.
	 * @param message the message to print in the err stream.
	 */
	public IllegalFormatContentFile(String message){
		System.err.println(message);
	}
}
