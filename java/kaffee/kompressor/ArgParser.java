package kaffee.kompressor;

/**
 * .
 * @author ahorvath
 */
public class ArgParser {

	private static final String QUIET = "q";
	private static final String VERBOSE = "v";
	private static final String ABREVATION = "abr";
	private static final String BASEDIR = "basedir";

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
				if (ABREVATION.equals(params[i])) {
					po.setAbrevate(true);
				}
				if (BASEDIR.equals(params[i]) && i + 1 != params.length) {
					po.setBaseDir(params[i] + 1);
				}
			}
		}
		return po;
	}
}
