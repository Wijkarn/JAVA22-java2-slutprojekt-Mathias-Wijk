package logger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private PrintWriter writer;

    private Logger() {
        try {
            // Create a log file with a timestamp in the filename
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String logFileName = "log_" + dateFormat.format(new Date()) + ".txt";
            writer = new PrintWriter(new FileWriter(logFileName), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logText(String message) {
        String logEntry = "[" + getCurrentTimestamp() + "] " + message;
        writer.println(logEntry);
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
