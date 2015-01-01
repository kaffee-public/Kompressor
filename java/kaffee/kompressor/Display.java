package kaffee.kompressor;

import java.util.List;

/**
 * .
 * @author ahorvath
 */
class Display {

	public static void about() {
		System.out.println("Kompressor v0.1 - Compress PHP files to smaller size");
		System.out.println("(c) 2014 Kaffee");
		System.out.println();
	}

	static void infoInputDirectory(String string) {
		System.out.println("Input directory is " + string);
	}

	public static void listResults(List<BatchFileJob> jobs) {
		for (BatchFileJob job : jobs) {
			System.out.println(job.getJobResult().name() + " " + job.getInnerName());
		}
	}

	public static void listResultsVerbose(List<BatchFileJob> jobs) {
		for (BatchFileJob job : jobs) {
			double originalSize = job.getBeforeSize();
			double newSize = job.getResult().length();
			String ratioText;
			double ratio = newSize / originalSize * 100;
			if (ratio == 0) {
				ratioText = " 0%";
			} else if (ratio < 10) {
				ratioText = " " + (new Double(ratio).intValue() + 1) + "%";
			} else {
				ratioText = (new Double(ratio).intValue()) + "%";
			}
			StringBuilder builder = new StringBuilder();
			builder.append(job.getJobResult().name());
			builder.append(ratioText);
			builder.append(job.getInnerName());
			builder.append(" [");
			builder.append(originalSize);
			builder.append("->");
			builder.append(newSize);
			builder.append(']');
			System.out.println(builder.toString());
		}
	}
}
