import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

    private static final String FILE_PATH = "log.txt";

    public static void writeMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        writeMessage("First message");
//        writeMessage("Second message");
//    }
}