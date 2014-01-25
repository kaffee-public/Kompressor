package kaffee.kompressor;

/**
 * .
 * @author ahorvath
 */
public class ProgramOptions {

	private boolean quiet;
	private boolean verbose;
	private boolean abrevate;
	private String baseDir = "";

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getBaseDir() {
		return baseDir;
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
}
