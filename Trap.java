import java.util.ArrayList;
import java.util.List;

public class Trap {

    private static List<Trap> TrapList = new ArrayList<>();

    TrapType trap;
    public static TrapType[] TrapTypeList = {TrapType.MouseTrap, TrapType.Bomb, TrapType.TNT};
    boolean isDestroiable;
    int HpLost;
    int ScoreLost;

    public Trap(TrapType trap, boolean isDestroiable, int HpLost, int ScoreLost) {
        this.trap = trap;
        this.isDestroiable = isDestroiable;
        this.HpLost = HpLost;
        this.ScoreLost = ScoreLost;
        TrapList.add(this);
    }

    public static List<Trap> getTrapList() {
        return TrapList;
    }

    public static String type () {
        return "Trap" ;
    }

    public String toString() {
        if (this.trap == TrapType.MouseTrap) {
            return "\u001B[31m" +"MST" +  "\u001B[0m" ;
        }
        else if (this.trap == TrapType.Bomb) {
            return "\u001B[31m" +"BMB" +  "\u001B[0m";
        }
        else  { //(this.trap  == TrapType.TNT)
            return "\u001B[31m" +"TNT" +  "\u001B[0m" ;
        }
    }

}