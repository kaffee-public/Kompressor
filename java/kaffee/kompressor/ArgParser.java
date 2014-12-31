package kaffee.kompressor;

/**
 * .
 * @author ahorvath
 */
class ArgParser {

	private static final String QUIET = "-q";
	private static final String VERBOSE = "-v";
	private static final String ABBREVATION = "-abr";
	private static final String OUTPUT = "-o";
	private static final String RECURSIVE = "-r";

	public static ProgramOptions parseOptions(String[] params) {
		ProgramOptions po = new ProgramOptions();
		if (params != null) {
			for (int i = 0; i != params.length; ++i) {
				if (QUIET.equals(params[i])) {
					po.setQuiet(true);
				}
				if (VERBOSE.equals(params[i])) {
					po.setVerbose(true);
				}
				if (ABBREVATION.equals(params[i])) {
					po.setAbrevate(true);
				}
				if (RECURSIVE.equals(params[i])) {
					po.setRecursive(true);
				}
				if (OUTPUT.equals(params[i]) && i + 1 != params.length) {
					po.setOutputPath(params[++i]);
				}
				if (i == params.length - 1) {
					po.setInputFile(params[i]);
				}
			}
		}
		return po;
	}
}
