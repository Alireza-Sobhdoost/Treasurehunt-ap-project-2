import java.util.ArrayList;
import java.util.List;

public class Treasure {

    private static List<Treasure> TreasureList = new ArrayList<>();
    private boolean isBreakable = false ;
    int Score_add = 10 ;
    public Treasure () {
        this.isBreakable = isBreakable ;
        this.Score_add = Score_add ;
        TreasureList.add(this) ;
    }

    public static List<Treasure> getTreasureList() {
        return TreasureList;
    }

    public static void randomLocTRS (){

    }

    public String toString() {
        return  "\u001B[32m" + "TRS" + "\u001B[0m" ;
    }
}
