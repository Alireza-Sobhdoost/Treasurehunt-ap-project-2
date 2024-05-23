import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class TextFileSaveAndLoad {

    private static boolean pl1Found = false;
    private static boolean pl2Found = false;
    private static boolean pl3Found = false;
    private static boolean pl4Found = false;

    public static List<String> saved_files = new ArrayList<>();
    private static int number_of_saves = 1;

    public static void SaveGame(Object[][] data , int number_of_players , int who_is_going_to_play , Player[] players) {
        String txtFilePath = "game" + String.valueOf(number_of_saves) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
            for (Object[] row : data) {
                if (row != null) {
                    for (int i = 0; i < row.length; i++) {
                        if (row[i] != null) {
                            writer.write(row[i].toString());
                        } else {
                            writer.write("null");  // Or any other default value for null elements
                        }
                        if (i < row.length - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");
                }
            }

            writer.write(String.valueOf(number_of_players));
            writer.write("\n");
            writer.write(String.valueOf(who_is_going_to_play));
            writer.write("\n");

            for (Player player : players){
                writer.write(
                        String.valueOf(player.specialMove[0]) + "," +
                                String.valueOf(player.specialMove[1]) + "," +
                                String.valueOf(player.specialMove[2]) + "," +
                                String.valueOf(player.HpLeft) + "," +
                                String.valueOf(player.ScoreEarned) + "," +
                                String.valueOf(player.src[0]) + "," +
                                String.valueOf(player.src[1]) + "," +
                                String.valueOf(player.hasWon) + "," +
                                String.valueOf(player.haslose)
                );
                writer.write("\n");
            }
            System.out.println("Data saved to " + txtFilePath);
        } catch (IOException e) {
            System.out.println("Error writing to text file: " + e.getMessage());
        }
    }

    public static Object[][] LoadGame(String txtFilePath) {
        Object[][] data = new Object[10][20];

        try (BufferedReader reader = new BufferedReader(new FileReader(txtFilePath))) {
            String line;
            int rowIdx = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int colIdx = 0; colIdx < values.length; colIdx++) {
                    data[rowIdx][colIdx] = parseValue(values[colIdx]);
                }
                rowIdx++;
            }
        } catch (IOException e) {
            System.out.println("Error reading text file: " + e.getMessage());
        }

        number_of_saves += 1;

        return data;
    }

    public static List<String> coordofplayerss = new ArrayList<>() ;
    private static Object parseValue(String item) {
        if (item.equals("\u001B[47m" + "\u001B[30m" + "PL1" + "\u001B[0m")) {
           coordofplayerss.add(" 1 ") ;
            return 1;
        }
        if (item.equals("\u001B[47m" + "\u001B[30m" + "PL2" + "\u001B[0m")) {
            coordofplayerss.add(" 2 ") ;
            return 2;
        }
        if (item.equals("\u001B[47m" + "\u001B[30m" + "PL3" + "\u001B[0m")) {
            coordofplayerss.add(" 3 ") ;
            return 3;
        }
        if (item.equals("\u001B[47m" + "\u001B[30m" + "PL4" + "\u001B[0m")) {
            coordofplayerss.add(" 4 ") ;
            return 4;
        }
        if (item.equals("\u001B[32m" + "TRS" + "\u001B[0m")) {
            Treasure treasure = new Treasure();
            return treasure;
        }
        if (item.equals("\u001B[31m" + "MST" + "\u001B[0m")) {
            Trap trap = new Trap(TrapType.MouseTrap, true, 1, 5);
            return trap;
        }
        if (item.equals("\u001B[31m" + "BMB" + "\u001B[0m")) {
            Trap trap = new Trap(TrapType.Bomb, true, 2, 10);
            return trap;
        }
        if (item.equals("\u001B[31m" + "TNT" + "\u001B[0m")) {
            Trap trap = new Trap(TrapType.TNT, false, 3, 15);
            return trap;
        }
        if (item.equals("\u001B[33m" + "BWL" + "\u001B[0m")) {
            Wall wall = new Wall(WallType.BreakableWall, true);
            return wall;
        }
        if (item.equals("\u001B[33m" + "UWL" + "\u001B[0m")) {
            Wall wall = new Wall(WallType.UnbreakableWall, false);
            return wall;
        }
        else {
            return "   ";
        }
    }

    public static int[] findElement(Object[][] array, Object element) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == element) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}


