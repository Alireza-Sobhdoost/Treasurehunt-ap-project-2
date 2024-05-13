import java.util.ArrayList;
import java.util.List;

public class Wall {
    private static List<Wall> WallList = new ArrayList<>();

    WallType wall;
    public static WallType[] WallTypeList = {WallType.BreakableWall, WallType.UnbreakableWall};
    boolean isBreakable;
    public Wall (WallType wall, boolean isBreakable) {
        this.wall = wall;
        this.isBreakable = isBreakable ;
        WallList.add(this);
    }

    public static List<Wall> getWallList() {
        return WallList;
    }

    public static String type () {
        return "Wall" ;
    }

    public String toString() {
        if (this.wall  == WallType.BreakableWall) {
            return "\u001B[33m" + "BWL" + "\u001B[0m" ;
        }
        else {
            return "\u001B[33m" + "UWL" + "\u001B[0m" ;
        }
    }
}
