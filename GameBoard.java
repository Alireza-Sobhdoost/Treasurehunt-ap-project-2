import static java.lang.Math.abs;

public interface GameBoard {
     Object[][] game_board = new Object[10][20] ;

    public static void print_gameboard (int[] cur_loc){
        System.out.print("\033[H\033[2J");
        System.out.println("==Treasure Hunt==");

        System.out.println("\n╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");

        for (int rows = 0 ; rows < 10 ; rows++) {
            System.out.print("║");
            for (int column = 0; column < 20; column++) {
//                my version
//                if (((((rows - cur_loc[0])*(rows - cur_loc[0])) + ((column - cur_loc[1])*(column - cur_loc[1]))) <= 16) || game_board[rows][column].equals("\u001B[47m" + "\u001B[30m" + "PL1" + "\u001B[0m") || game_board[rows][column].equals("\u001B[47m" + "\u001B[30m" + "PL2" + "\u001B[0m") || game_board[rows][column].equals("\u001B[47m" + "\u001B[30m" + "PL3" + "\u001B[0m") || game_board[rows][column].equals("\u001B[47m" + "\u001B[30m" + "PL4" + "\u001B[0m")){
//                    System.out.print(game_board[rows][column]);
//                }
//                what Ta asked !
                if ((((((rows - cur_loc[0])*(rows - cur_loc[0])) + ((column - cur_loc[1])*(column - cur_loc[1]))) <= 32) && (abs(rows - cur_loc[0]) <= 4) && (abs(column - cur_loc[1]) <= 4))|| game_board[rows][column].toString().equals("\u001B[47m" + "\u001B[30m" + "PL1" + "\u001B[0m") || game_board[rows][column].toString().equals("\u001B[47m" + "\u001B[30m" + "PL2" + "\u001B[0m") || game_board[rows][column].toString().equals("\u001B[47m" + "\u001B[30m" + "PL3" + "\u001B[0m") || game_board[rows][column].toString().equals("\u001B[47m" + "\u001B[30m" + "PL4" + "\u001B[0m")){
                    System.out.print(game_board[rows][column]);
                }
                else {
                    System.out.print(" ? ");
//                    System.out.print(game_board[rows][column]);

                }
                System.out.print("║");
            }

            if (rows < 9) {
                System.out.println("\n╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
            }
            else {
                System.out.println("\n╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝");
            }

        }
    }

    public static void main(String[] args) {
//        for (int rows = 0 ; rows < 10 ; rows++) {
//            for (int column = 0; column < 20; column++) {
//                game_board[rows][column] = "   ";
//            }
//        }
//        print_gameboard();
    }
}