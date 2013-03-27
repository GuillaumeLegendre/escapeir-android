package fr.umlv.escape.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Supplies some functions to parse a text file. Creating a ParseFile Object just initialize the stream
 *  on the file path given. All the content file is loaded in memory on the first use of a function. 
 *  When the file content is loaded, the stream is closed. Data will be accessed directly in memory. DO NOT use
 *  a ParseFile object with big files.
 */
public class ParseFile {
	private final String fileName;
	private final BufferedReader in;
	private final List<String> fileContent;
	private Iterator<String> iterList;
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
		this.fileContent=new LinkedList<>();
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
		this.iterList=this.fileContent.iterator();
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
		if(this.iterList.hasNext()){
			return this.iterList.next();
		}
		return null;
	}
	
	/** Reset to the beginning. The next call to the {@link #getNextLine()} function will
	 * return the first line of the file.
	 */
	public void reset(){
		this.iterList=this.fileContent.iterator();
	}
	
	/** Return the name of the file parsed.
	 * @return the name of the file parsed.
	 */
	public String getFileName() {
		return fileName;
	}
}
