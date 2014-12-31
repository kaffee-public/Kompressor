package kaffee.kompressor;

import java.io.BufferedReader;
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
		Lexer l = new Lexer();
		FileListComposer composer;
		try {
			composer = new FileListComposer(options.getInputFile(), options.isRecursive());
		} catch (FileNotFoundException ex) {
			System.err.println("Could not open specified input-path.");
			return;
		}
		processFiles(composer, l);
	}

	private static void processFiles(FileListComposer composer, Lexer l) {
		List<BatchFileJob> jobsDone = new ArrayList<BatchFileJob>();
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
					StringBuilder s = l.compressLine(line);
					output.append(s);
					output.append('\n');
				}
			} catch (IOException ex) {
				toCompress.setJobResult(JobResult.COULD_NOT_COMPRESS);
			}
			toCompress.setResult(output);
			jobsDone.add(toCompress);
			if (jobsDone.size() == OPEN_COUNT) {
				flushOutput(jobsDone);
			}
		}
		flushOutput(jobsDone);
	}

	private static final FileWriter writers[] = new FileWriter[OPEN_COUNT];

	private static void flushOutput(List<BatchFileJob> jobs) {
		if (jobs.size() > OPEN_COUNT) {
			throw new AssertionError("");
			// Uhm, it looks bad.
		}
		int end = jobs.size();
		for (int i = 0; i != end; ++i) {
			try {
				writers[i] = new FileWriter(options.getOutputPath() + jobs.get(i).getInnerName());
			} catch (IOException ex) {
				jobs.get(i).setJobResult(JobResult.COULD_NOT_WRITE);
				writers[i] = null;
			}
		}
		for (int i = 0; i != end; ++i) {
			if (writers[i] != null) {
				try {
					writers[i].write(jobs.get(i).getResult().toString());
					writers[i].close();
					jobs.get(i).setJobResult(JobResult.COMPRESSED);
				} catch (IOException ioe) {
					jobs.get(i).setJobResult(JobResult.COULD_NOT_WRITE);
				}
			}
		}
		if (options.isVerbose()) {
			Display.listResultsVerbose(jobs);
		} else {
			Display.listResults(jobs);
		}
		jobs.clear();
	}
}
