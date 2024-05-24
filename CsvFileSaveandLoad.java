import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class CsvFileSaveandLoad {

    public static List<String> saved_files = new ArrayList<>();
    public static int number_of_saves ;
    public static int turn = 0;


    public static void SaveGame(Object[][] data , int number_of_players , int who_is_going_to_play , Player[] players) {
        String csvFilePath = "game" + String.valueOf(number_of_saves ) + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
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
            writer.write("\n");
            writer.write(String.valueOf(number_of_players));
            writer.write(",");
            writer.write(String.valueOf(who_is_going_to_play));
            writer.write("\n");

            // Update the loop to handle potential null Player objects
            for (Player player : players) {
                if (player != null) {
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
            }
            System.out.println("Data saved to " + csvFilePath);
            saved_files.add(csvFilePath) ;
//            SaveStatus() ;
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public static void SaveStatus () {
        String csvFilePath = "gamestatus.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (String item : saved_files) {
                if (item != null) {
                    writer.write(item);
                    writer.write(",");
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public static int LoadStatus() {
        String csvFilePath = "gamestatus.csv";
        String[] values = new String[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            System.out.print("\033[H\033[2J");
            String line = reader.readLine();
            values = line.split(",");
            number_of_saves = values.length ;
            System.out.println("==Trasure==");
            System.out.println("which game ddo you waant to load ?");
            for (int i = 0 ; i < values.length ; i ++) {
                if (values[i] != null) {
                    System.out.println("[" + String.valueOf(i + 1) + "] " + values[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return number_of_saves ;
    }

    public static int getNumber_of_saves () {
        String csvFilePath = "gamestatus.csv";
        String[] values = new String[0];


        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
//            System.out.print("\033[H\033[2J");
            String line = reader.readLine();
            if (line != null) {
                values = line.split(",");
                number_of_saves = values.length;
            }
            else {
                number_of_saves = 0 ;
            }
        } catch (IOException e) {
//            System.out.println("Error reading CSV file: " + e.getMessage());
            number_of_saves = 1;
        }
        return number_of_saves ;
    }
    public static void getSavedFiles() {
        String csvFilePath = "gamestatus.csv";
        List<String> listValues = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    listValues.addAll(Arrays.asList(values));
                    saved_files=listValues ;
                }
            }

        } catch (IOException e) {
//            System.out.println("Error reading CSV file: " + e.getMessage());
        }

    }
    public static void LoadGame(String csvFilePath , Object[][] data ) {
//        Object[][] data = new Object[10][20];
        Object[] lastThreeLines = new Object[9];


        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int rowIdx = 0;
            while ((line = reader.readLine()) != null && rowIdx < 10) {
                String[] values = line.split(",");
                if (rowIdx < 10){
                    for (int colIdx = 0; colIdx < values.length; colIdx++) {
                        data[rowIdx][colIdx] = parseValue(values[colIdx]);
                    }
                }
//                if (rowIdx == 10) {
//                    Main.number_of_players = Integer.parseInt(values[0]); // Assuming the value in line 11 is an integer
//                }
//                if (rowIdx == 11) {
//                    turn = Integer.parseInt(values[0]); // Assuming the value in line 12 is an integer
//                    System.out.println(turn);
//                }
                rowIdx++;
            }
            line = reader.readLine() ;
            String[] x = line.split(",");
            Main.number_of_players = Integer.parseInt(x[0]) ;
            turn = Integer.parseInt(x[1]); // Assuming the value in line 12 is an integer
//            System.out.println(turn);
//            System.out.println(Main.number_of_players);

            // Read the last 3 lines
            int lastThreeLinesIdx = 0;
            int found = 0 ;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
//                System.out.println(values.length);
                int[] coord = findElement(data,found+1) ;
                int [] specialMove = {Integer.parseInt(values[0]) , Integer.parseInt(values[1]), Integer.parseInt(values[2])} ;
                int [] src = {Integer.parseInt(values[5]) , Integer.parseInt(values[6])} ;
                boolean won = Boolean.valueOf(values[7]) ;
                boolean lose = Boolean.valueOf(values[8]) ;
                Player player = new Player(specialMove , Integer.parseInt(values[3]) , Integer.parseInt(values[4]) , src , won , lose) ;
                data[coord[0]][coord[1]] = player ;
                player.cur_loc = coord ;
                found += 1 ;
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        number_of_saves += 1;

//        return data;
    }

    public static int getTurn(){
        return turn ;
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
        if (item.equals("\u001B[34m" + "SPN" + "\u001B[0m")) {
            Spin spin = new Spin();
            return spin;
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


