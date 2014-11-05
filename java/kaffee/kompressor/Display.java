package kaffee.kompressor;

/**
 * .
 * @author ahorvath
 */
public class Display {

	public static void about() {
		System.out.println("Kompressor v0.1 - Compress PHP files to smaller size");
		System.out.println("(c) 2014 Kaffee");
	}
	
	public void compressed(String fileName) {
		System.out.println("compressed: " + fileName);
	}
	
	public void compressedVerbose(String fileName, int originalSize, int newSize) {
		System.out.println("compressed: " + fileName + " [" + originalSize + "->" + newSize + ']');
	}
	
	public void error(String fileName) {
		System.out.println("Error while compressing: " + fileName + "at line: ");
		// Verbose
		// Line count
		System.out.println("File left in original state. Proceeding to next file.");
	}
}