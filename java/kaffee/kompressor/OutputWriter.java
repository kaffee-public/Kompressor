/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaffee.kompressor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Adam
 */
class OutputWriter {

	private final FileWriter writers[];
	private final int OPEN_COUNT;
	private final ProgramOptions options;

	OutputWriter(int openAtOnce, ProgramOptions options) {
		OPEN_COUNT = openAtOnce;
		this.writers = new FileWriter[OPEN_COUNT];
		this.options = options;
	}

	public void flushOutput(List<BatchFileJob> jobs) {
		if (jobs.size() > OPEN_COUNT) {
			throw new AssertionError("");
			// Uhm, it looks bad.
		}
		int end = jobs.size();
		for (int i = 0; i != end; ++i) {
			try {
				String outputPath = options.getOutputPath() + jobs.get(i).getInnerName();
				outputPath = outputPath.substring(0, outputPath.lastIndexOf('\\'));
				if (createDirectories(outputPath) == false) {
					throw new IOException();
				}
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

	private boolean createDirectories(String outputPath) {
		boolean success = true;
		File directory = new File(outputPath);
		if (!directory.exists()) {
			success = directory.mkdirs();
		}
		return success;
	}
}
