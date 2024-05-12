import java.util.ArrayList;
import java.util.List;
import java.awt.* ;
import java.util.List;
import java.util.Random;
 public class Main {
  // ANSI color codes
     public static void generate_random_trap (){
         for (int i = 0 ; i < 30 ; i++) {
             Random random = new Random();
             int randomIndex = random.nextInt(Trap.TrapTypeList.length);
             if (Trap.TrapTypeList[randomIndex] == TrapType.MouseTrap) {
                 new Trap(TrapType.MouseTrap , true ,1, 5) ;
             }
             else if (Trap.TrapTypeList[randomIndex] == TrapType.Bomb) {
                 new Trap(TrapType.Bomb , true ,2, 10) ;
             }
             else if (Trap.TrapTypeList[randomIndex] == TrapType.TNT) {
                 new Trap(TrapType.TNT , false ,3, 15) ;
             }

         }
     }

     public static void generate_random_wall (){
         for (int i = 0 ; i < 20 ; i++) {
             Random random = new Random();
             int randomIndex = random.nextInt(Wall.WallTypeList.length);
             if (Wall.WallTypeList[randomIndex] == WallType.BreakableWall) {
                 new Wall(WallType.BreakableWall , true ) ;
             }
             else if (Wall.WallTypeList[randomIndex] == WallType.UnbreakableWall) {
                 new Wall(WallType.UnbreakableWall , false ) ;
             }

         }
     }
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_GREEN = "\u001B[32m";

  public static void main(String[] args) {
//      System.out.println(ANSI_RED + "Red text" + ANSI_RESET);
//      System.out.println(ANSI_BLUE + "Blue text" + ANSI_RESET);
//      System.out.println(ANSI_GREEN + "Green text" + ANSI_RESET);

      generate_random_wall();
      List<Wall> Our_list = Wall.getWallList() ;
      for (Wall wall : Our_list) {
          System.out.println(wall);
      }

      
  }
 }
