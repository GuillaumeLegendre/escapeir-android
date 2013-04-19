package fr.umlv.escape.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.escape.Objects;

/** Supplies some functions to parse a text file. Creating a ParseFile Object just initialize the stream
 *  on the file path given. All the content file is loaded in memory on the first use of a function. 
 *  When the file content is loaded, the stream is closed. Data will be accessed directly in memory. DO NOT use
 *  a ParseFile object with big files.
 */
public class ParseFile {
	private final String fileName;
	private final BufferedReader in;
	private final ArrayList<String> fileContent;
	private int currentLine;
	private boolean isInitialased;
	
	 /** Create a ParseFile object.
	  * 
	  * @param fileName Path of the file.
	  * @throws  FileNotFoundException
	  */
	public ParseFile(String fileName) throws FileNotFoundException{
		Objects.requireNonNull(fileName);
		this.fileName=fileName;
		this.in=new BufferedReader(new FileReader(fileName));
		this.fileContent=new ArrayList<String>();
		this.isInitialased=false;
	}

	/** Load all the file content in memory.
	 * @throws IOException
	 */
	private void loadFileContent() throws IOException{
		String buffer;
		while((buffer=in.readLine())!=null){
			this.fileContent.add(buffer);
		}
		this.currentLine = 0;
		in.close();
		this.isInitialased=true;
	}
	
	/** Get the next line of data of the file.
	 * @return The next line of the file or null if the file is ended.
	 * @throws IOException
	 */
	public String getNextLine() throws IOException{
		if(!this.isInitialased){
			this.loadFileContent();
		}
		if(currentLine < fileContent.size()){
			String str = fileContent.get(currentLine);
			currentLine++;
			return str;
		}
		return null;
	}
	
	/** Reset to the beginning. The next call to the {@link #getNextLine()} function will
	 * return the first line of the file.
	 */
	public void reset(){
		currentLine = 0;
	}
	
	/** Return the name of the file parsed.
	 * @return the name of the file parsed.
	 */
	public String getFileName() {
		return fileName;
	}
}
