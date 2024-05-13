public interface GameBoard {
    String[][] game_board = new String [10][20] ;

    public static void initial_set(){

    }
    public static void print_gameboard (){


        System.out.println("\n╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");

        for (int rows = 0 ; rows < 10 ; rows++) {
            System.out.print("║");
            for (int column = 0; column < 20; column++) {
                System.out.print(game_board[rows][column]);
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
        print_gameboard();
    }
}