public interface GameBoard {
    String[][] game_board = new String [10][20] ;

    public static void print_gameboard (){
        System.out.println("\n________________________________________________________________________________________________________________________");

        for (int rows = 0 ; rows < 10 ; rows++) {
            System.out.println("||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||   ||");
            System.out.print("||");
            for (int column = 0; column < 20; column++) {
                System.out.print(game_board[rows][column]);
                System.out.print("||");
            }
            System.out.print("\n||___||___||___||___||___||___||___||___||___||___||___||___||___||___||___||___||___||___||___||___||");
            System.out.println("\n________________________________________________________________________________________________________________________");
        }
    }

    public static void main(String[] args) {
        for (int rows = 0 ; rows < 10 ; rows++) {
            for (int column = 0; column < 20; column++) {
                game_board[rows][column] = "UWL";
            }
        }
        print_gameboard();
    }
}