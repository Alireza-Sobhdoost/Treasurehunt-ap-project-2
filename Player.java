import java.util.ArrayList;
import java.util.List;

public class Player {

    private static List<Player> PlayerList = new ArrayList<>() ;

    public static int[] SpecialMoveCounter = {3,3,6} ;
    String playername ;
    int playerId ;
    int HpLeft = 5 ;
    int ScoreEarned = 0 ;
    int SpecialMoveLeft  ;

    int[] src ;
    int[] cur_loc ;
    int[] specialMove = {3,3,6};

    public Player (int[] SpecialMoveCounter) {
//        this.playername = playername ;
        this.playerId  = 1 + PlayerList.size() ;
        this.HpLeft = 5 ;
        this.ScoreEarned = 0 ;
        this.SpecialMoveCounter = SpecialMoveCounter;
        this.SpecialMoveLeft = Sum_list(this.SpecialMoveCounter) ;
        this.src = new int [2] ;
        this.cur_loc = new int [2] ;
        this.specialMove = specialMove ;
        PlayerList.add(this) ;
    }

    public static List<Player> getPlayerList() {
        return PlayerList;
    }

    public static int Sum_list (int[] List) {
        int sum = 0 ;
        for (int i : List) {
            sum += i ;
        }
        return sum ;
    }

    public String toString() {
        return "\u001B[47m" + "\u001B[30m" + "PL" + this.playerId + "\u001B[0m";
    }

    public boolean[] gettosides (){
        boolean[] alowed_to_move = {false,false,false,false,false,false,false};
        System.out.println("----------------"+this.toString()+"'s turn . Please make a move or use one of ypu special moves if you have some ----------------");
        if (this.cur_loc[0] != 0 ){
            if (!(GameBoard.game_board[cur_loc[0]-1][cur_loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]-1][cur_loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[↑] move Up");
                alowed_to_move[0] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]-1][cur_loc[1]].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[↑] move Up to get treasure");
                alowed_to_move[0] = true ;
            }
        }
        if (this.cur_loc[0] != 9){
            if (!(GameBoard.game_board[cur_loc[0]+1][cur_loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]+1][cur_loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[↓] move down");
                alowed_to_move[1] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]+1][cur_loc[1]].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[↓] move down to get treasure");
                alowed_to_move[1] = true ;
            }
        }
        if (this.cur_loc[1] != 0){
            if (!(GameBoard.game_board[cur_loc[0]][cur_loc[1]-1].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]][cur_loc[1]-1].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[←] move left");
                alowed_to_move[2] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]][cur_loc[1]-1].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[←] move left to get treasure");
                alowed_to_move[2] = true ;
            }

        }
        if (this.cur_loc[1] != 19){
            if (!(GameBoard.game_board[cur_loc[0]][cur_loc[1]+1].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]][cur_loc[1]+1].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[→] move right");
                alowed_to_move[3] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]][cur_loc[1]+1].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[→] move right to get treasure");
                alowed_to_move[3] = true ;
            }
        }
        if (this.specialMove[0] != 0){
            System.out.println("[d] Destruction");
            alowed_to_move[4] = true ;
        }
        if (this.specialMove[1] != 0){
            System.out.println("[l] Long Jump");
            alowed_to_move[5] = true ;
        }
        if (this.specialMove[2] != 0){
            System.out.println("[s] Spwan Trap");
            alowed_to_move[6] = true ;
        }

        return alowed_to_move ;
    }
    public  boolean[] Destruction (){
        System.out.println("----------------"+this.toString()+"'s turn . Please choose a entity yo destroy ----------------");
        boolean[] alowed_to_destroy = {false,false,false,false} ;
        if (this.cur_loc[0] != 0 ){
            if (GameBoard.game_board[cur_loc[0]-1][cur_loc[1]] instanceof Wall) {
                if (((Wall) GameBoard.game_board[cur_loc[0]-1][cur_loc[1]]).isBreakable)
                    System.out.println("[↑] to Break your upper wall");
                    alowed_to_destroy[0] = true ;
            }
            if (GameBoard.game_board[cur_loc[0]-1][cur_loc[1]] instanceof Trap) {
                if (((Trap) GameBoard.game_board[cur_loc[0]-1][cur_loc[1]]).isDestroiable)
                    System.out.println("[↑] to Break your upper trap");
                    alowed_to_destroy[0] = true ;
            }

        }
        if (this.cur_loc[0] != 9 ){
            if (GameBoard.game_board[cur_loc[0]+1][cur_loc[1]] instanceof Wall) {
                if (((Wall) GameBoard.game_board[cur_loc[0]+1][cur_loc[1]]).isBreakable)
                    System.out.println("[↓] to Break your under wall");
                    alowed_to_destroy[1] = true ;
            }
            if (GameBoard.game_board[cur_loc[0]+1][cur_loc[1]] instanceof Trap) {
                if (((Trap) GameBoard.game_board[cur_loc[0]+1][cur_loc[1]]).isDestroiable)
                    System.out.println("[↓] to Break your under trap");
                    alowed_to_destroy[1] = true ;
            }
        }
        if (this.cur_loc[1] != 0 ){
            if (GameBoard.game_board[cur_loc[0]][cur_loc[1]-1] instanceof Wall) {
                if (((Wall) GameBoard.game_board[cur_loc[0]][cur_loc[1]-1]).isBreakable)
                    System.out.println("[←] to Break your left wall");
                    alowed_to_destroy[2] = true ;
            }
            if (GameBoard.game_board[cur_loc[0]][cur_loc[1]-1] instanceof Trap) {
                if (((Trap) GameBoard.game_board[cur_loc[0]][cur_loc[1]-1]).isDestroiable)
                    System.out.println("[←] to Break your left trap");
                    alowed_to_destroy[2] = true ;
            }
        }
        if (this.cur_loc[1] != 19 ){
            if (GameBoard.game_board[cur_loc[0]][cur_loc[1]+1] instanceof Wall) {
                if (((Wall) GameBoard.game_board[cur_loc[0]][cur_loc[1]+1]).isBreakable)
                    System.out.println("[→] to Break your right wall");
                    alowed_to_destroy[3] = true ;
            }
            if (GameBoard.game_board[cur_loc[0]][cur_loc[1]+1] instanceof Trap) {
                if (((Trap) GameBoard.game_board[cur_loc[0]][cur_loc[1]+1]).isDestroiable)
                    System.out.println("[→] to Break your right trap");
                    alowed_to_destroy[3] = true ;
            }
        }
        System.out.println("[b] back");


        return alowed_to_destroy ;
    }

    public boolean[] LongJump () {
        System.out.println("----------------"+this.toString()+"'s turn . Please make a long jump ----------------");

        boolean [] alowed_to_spwan = {false,false,false,false} ;
        if (this.cur_loc[0] > 1 ){
            if (!(GameBoard.game_board[cur_loc[0]-2][cur_loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]-2][cur_loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[↑] Jump Up");
                alowed_to_move[0] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]-2][cur_loc[1]].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[↑] Jump Up to get treasure");
                alowed_to_move[0] = true ;
            }
        }
        if (this.cur_loc[0] < 8){
            if (!(GameBoard.game_board[cur_loc[0]+2][cur_loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]+2][cur_loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[↓] Jump down");
                alowed_to_move[1] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]+2][cur_loc[1]].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[↓] Jump down to get treasure");
                alowed_to_move[1] = true ;
            }
        }
        if (this.cur_loc[1] > 1){
            if (!(GameBoard.game_board[cur_loc[0]][cur_loc[1]-2].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]][cur_loc[1]-2].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[←] Jump left");
                alowed_to_move[2] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]][cur_loc[1]-2].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[←] Jump left to get treasure");
                alowed_to_move[2] = true ;
            }

        }
        if (this.cur_loc[1] < 18){
            if (!(GameBoard.game_board[cur_loc[0]][cur_loc[1]+2].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]][cur_loc[1]+2].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[→] Jump right");
                alowed_to_move[3] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]][cur_loc[1]+2].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[→] Jump right to get treasure");
                alowed_to_move[3] = true ;
            }


        }

        System.out.println("[b] back");

        return alowed_to_move ;
    }

    public boolean[] SpwanTrap (){
        System.out.println("----------------"+this.toString()+"'s turn . Please make a long jump ----------------");

        boolean [] alowed_to_move = {false,false,false,false} ;
        if (this.cur_loc[0] > 1 ){
            if (!(GameBoard.game_board[cur_loc[0]-2][cur_loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]-2][cur_loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[↑] Jump Up");
                alowed_to_move[0] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]-2][cur_loc[1]].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[↑] Jump Up to get treasure");
                alowed_to_move[0] = true ;
            }
        }
        if (this.cur_loc[0] < 8){
            if (!(GameBoard.game_board[cur_loc[0]+2][cur_loc[1]].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]+2][cur_loc[1]].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[↓] Jump down");
                alowed_to_move[1] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]+2][cur_loc[1]].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[↓] Jump down to get treasure");
                alowed_to_move[1] = true ;
            }
        }
        if (this.cur_loc[1] > 1){
            if (!(GameBoard.game_board[cur_loc[0]][cur_loc[1]-2].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]][cur_loc[1]-2].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[←] Jump left");
                alowed_to_move[2] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]][cur_loc[1]-2].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[←] Jump left to get treasure");
                alowed_to_move[2] = true ;
            }

        }
        if (this.cur_loc[1] < 18){
            if (!(GameBoard.game_board[cur_loc[0]][cur_loc[1]+2].toString().equals("\u001B[33m" + "UWL" + "\u001B[0m")) && !(GameBoard.game_board[cur_loc[0]][cur_loc[1]+2].toString().equals("\u001B[33m" + "BWL" + "\u001B[0m"))) {
                System.out.println("[→] Jump right");
                alowed_to_move[3] = true ;
            }
            if ((GameBoard.game_board[cur_loc[0]][cur_loc[1]+2].toString().equals( "\u001B[32m" + "TRS" + "\u001B[0m" ))) {
                System.out.println("[→] Jump right to get treasure");
                alowed_to_move[3] = true ;
            }


        }

        System.out.println("[b] back");

        return alowed_to_spwan ;
    }
    public void BackToSrc () {

    }


    
}
