package kaffee.kompressor;

/**
 * .
 * @author ahorvath
 */
class ProgramOptions {

	private boolean quiet;
	private boolean verbose;
	private boolean abrevate;
	private boolean recursive;
	private String inputFile;
	private String outputPath;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public boolean isQuiet() {
		return quiet;
	}

	public void setQuiet(boolean quiet) {
		this.quiet = quiet;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public boolean isAbrevate() {
		return abrevate;
	}

	public void setAbrevate(boolean abrevate) {
		this.abrevate = abrevate;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public boolean isRecursive() {
		return recursive;
	}
}
