package kaffee.kompressor;

import java.io.File;

/**
 * .
 * @author ahorvath
 */
class BatchFileJob {

	private static final long MAX_FILESIZE = 1024 * 1024;
	private final File file;
	private final String innerName;
	private final long beforeSize;
	//
	private StringBuilder result;
	private JobResult jobResult;

	BatchFileJob(File file, String innerName) {
		this.file = file;
		if (file.length() > MAX_FILESIZE) {
			throw new UnsupportedOperationException(file.getAbsolutePath() + " is too large to compress.");
		}
		this.innerName = innerName;
		this.beforeSize = file.length();
	}

	public String getInnerName() {
		return innerName;
	}

	public long getBeforeSize() {
		return beforeSize;
	}

	public StringBuilder getResult() {
		return result;
	}

	public void setResult(StringBuilder result) {
		this.result = result;
	}

	public File getFile() {
		return file;
	}

	public JobResult getJobResult() {
		return jobResult;
	}

	public void setJobResult(JobResult jobResult) {
		this.jobResult = jobResult;
	}
}
