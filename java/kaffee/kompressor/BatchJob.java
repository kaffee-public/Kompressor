package kaffee.kompressor;

/**
 * .
 * @author ahorvath
 */
public class BatchFileJob implements Iterable<File> {
	public void addFile(String fileName);
	
	public File nextFile();
	public int size();
	
}
