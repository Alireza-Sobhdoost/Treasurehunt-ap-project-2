public class Portal {
    private static boolean is_destroyable = false ;
    private static Portal[] portals = new Portal[2] ;
    int id ;
    int[] loc = new int[2] ;

    private static int countportals = 0 ;
    public Portal(int[] loc ,boolean is_destroyable){
        this.is_destroyable = is_destroyable ;
        this.id = countportals ;
        this.loc = loc ;
        portals[countportals] = this ;
        countportals += 1 ;
    }

    public static Portal[] getPortals(){
        return portals ;
    }

    public String toString (){
        return  "\u001B[35m" + "PRT" + "\u001B[0m" ;
    }

    public boolean[] checksides (){
        boolean[] allowed_to_move = {false,false,false,false};
        Portal p = portals[(this.id + 1)%2];  // Fixed syntax error here
        if ( p.loc[0] != 0 ){
            if (!(GameBoard.game_board[p.loc[0] -1][p.loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[p.loc[0]-1][p.loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                allowed_to_move[0] = true ;
            }
        }
        if (p.loc[0] != 9){
            if (!(GameBoard.game_board[p.loc[0]+1][p.loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[p.loc[0]+1][p.loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                allowed_to_move[1] = true ;
            }
        }
        if (this.loc[1] != 0){
            if (!(GameBoard.game_board[p.loc[0]][p.loc[1]-1].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[p.loc[0]][p.loc[1]-1].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                allowed_to_move[2] = true ;
            }
        }
        if (this.loc[1] != 19){
            if (!(GameBoard.game_board[p.loc[0]][p.loc[1]+1].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[p.loc[0]][p.loc[1]+1].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                allowed_to_move[3] = true ;
            }
        }

        return allowed_to_move ;
    }
}