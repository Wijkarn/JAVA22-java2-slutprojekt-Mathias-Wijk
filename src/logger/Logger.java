package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class Logger {
	private static Logger instance;
	private PrintWriter writer;
	private final String logDirectory = "logs";

	private Logger() {
		createLogDirectory();

		try {
			// Create a log file with a timestamp in the filename in a folder
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String logFileName = logDirectory + File.separator + "log_" + dateFormat.format(new Date()) + ".txt";
			writer = new PrintWriter(new FileWriter(logFileName), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	public synchronized void logText(String message) {
		String logEntry = "[" + getCurrentTimestamp() + "] " + message;
		writer.println(logEntry);
	}

	private String getCurrentTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	public synchronized void close() {
		if (writer != null) {
			writer.close();
		}
	}

	private synchronized void createLogDirectory() {
		File directory = new File(logDirectory);
		if (!directory.exists()) {
			if (directory.mkdir()) {
				System.out.println("Log directory created.");
			} else {
				System.err.println("Failed to create log directory.");
			}
		}
	}
}
