import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Log {

    private static String FILE_PATH = "log"+String.valueOf(CsvFileSaveandLoad.number_of_saves)+".txt";

    public static void writeMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readLinesFromFile(String filePath, Player caller) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                String[] splitLine = line.split(" "); // Assuming words are separated by space
                String firstWord = splitLine[0];
//                String caller_id = firstWord.substring(0, 2);
                if (firstWord.equals("PL" + caller.playerId)) {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//    public static void main(String[] args) {
//        writeMessage("First message");
//        writeMessage("Second message");
//    }