package kaffee.kompressor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * .
 * @author vszakonyi
 */
public class Kompressor {

	private static OutputWriter writer;
	private static ProgramOptions options;
	private static final int OPEN_COUNT = 10;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Display.about();
		options = ArgParser.parseOptions(args);
		if (options.getInputFile() == null) {
			System.err.println("No input file or path specified.");
			System.exit(1);
		}
		if (options.getOutputPath() == null) {
			System.err.println("No output path specified.");
			System.exit(2);
		}
		FileListComposer composer;
		try {
			composer = new FileListComposer(options.getInputFile(), options.isRecursive());
		} catch (FileNotFoundException ex) {
			System.err.println("Could not open specified input path.");
			return;
		}
		processFiles(composer);
	}

	private static void processFiles(FileListComposer composer) {
		List<BatchFileJob> jobsDone = new ArrayList<BatchFileJob>();
		writer = new OutputWriter(OPEN_COUNT, options);
		Lexer lexer = new Lexer();
		for (BatchFileJob toCompress : composer.getJobList()) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(toCompress.getFile()));
			} catch (FileNotFoundException ex) {
				toCompress.setJobResult(JobResult.NOT_FOUND);
				continue;
			}
			StringBuilder output = new StringBuilder();
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					StringBuilder s = lexer.compressLine(line);
					output.append(s);
					output.append('\n');
				}
			} catch (IOException ex) {
				toCompress.setJobResult(JobResult.COULD_NOT_COMPRESS);
			}
			toCompress.setResult(output);
			jobsDone.add(toCompress);
			if (jobsDone.size() == OPEN_COUNT) {
				writer.flushOutput(jobsDone);
			}
		}
		writer.flushOutput(jobsDone);
	}
}
