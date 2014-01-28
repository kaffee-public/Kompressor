package kaffee.kompressor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * .
 * @author vszakonyi
 */
public class Kompressor {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Display.about();
		ProgramOptions options = ArgParser.parseOptions(args);
		Lexer l = new Lexer();
		List<String> output = new LinkedList<String>();
		File file = new File(options.getBaseDir() + "fibonacci.php");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			String s = l.compressLine(line);
			output.add(s);

		}
		int i = 1;
	}
}
