package egitex.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Launcher {
	private File output;
	private final ProcessBuilder builder;

	Launcher(File repoRoot, String...args) {
		builder = new ProcessBuilder(args);
		builder.directory(repoRoot);
		try {
			output = File.createTempFile("egit-ex", ".txt");
			builder.redirectOutput(output);
			builder.redirectError(output);
		} catch (IOException e) {
			// fuck you
		}
	}

	void launchAndWait() {
		Process process;
		try {
			process = builder.start();
		} catch (IOException e) {
			throw new IllegalStateException("Failed to start process");
		}
		while (true) {
			try {
				process.exitValue();
				return;
			} catch (IllegalThreadStateException e) {
				// keep waiting
			}
		}
	}
	
	public String getOutput() {
		if (output == null) {
			return "no output";
		}
		StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(output))) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (IOException e) {
			return "";
		} finally {
			output.delete();
		}
	}
}
