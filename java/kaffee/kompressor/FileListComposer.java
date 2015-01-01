package kaffee.kompressor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * .
 * @author ahorvath
 */
class FileListComposer {

	private final boolean recursive;
	private final List<BatchFileJob> jobList = new LinkedList<BatchFileJob>();
	private final String inputPath;

	FileListComposer(String inputPath, boolean recursive) throws FileNotFoundException {
		this.recursive = recursive;
		this.inputPath = inputPath;
		composeList();
	}

	private void composeList() throws FileNotFoundException {
		File target = new File(inputPath);
		if (target.exists()) {
			if (target.isDirectory()) {
				listFiles(target);
			} else if (target.isFile()) {
				BatchFileJob bfj = new BatchFileJob(target, target.getName());
				jobList.add(bfj);
			} else {
				throw new IllegalArgumentException(inputPath + " is not a file nor a directory.");
			}
		} else {
			throw new FileNotFoundException(inputPath);
		}
	}

	private void listFiles(File path) {
		String[] directoryListing = path.list();
		if (directoryListing != null) {
			for (String item : directoryListing) {
				File child = new File(path.getAbsolutePath() + '\\' + item);
				if (child.isFile()) {
					if (child.getPath().endsWith(".php")) {
						int i = child.getAbsolutePath().lastIndexOf('\\' + inputPath + '\\') + inputPath.length() + 1;
						String innerName = child.getAbsolutePath().substring(i);
						BatchFileJob bfj = new BatchFileJob(child, innerName);
						jobList.add(bfj);
					}
				} else if (recursive) {
					listFiles(child);
				}
			}
		}
	}

	public List<BatchFileJob> getJobList() {
		return jobList;
	}
}
