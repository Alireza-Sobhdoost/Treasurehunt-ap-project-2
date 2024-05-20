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

    public void BackToSrc () {

    }


    
}
