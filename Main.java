import java.util.*;
import java.awt.* ;
import java.util.List;

public class Main implements GameBoard {
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


     public static void initial_set (int numberOfPlayers , Player[] players ,List<Wall> Our_wall , List<Trap> Our_trap){
         for (int rows = 0 ; rows < 10 ; rows++) {
             for (int column = 0; column < 20; column++) {
                 game_board[rows][column] = "   ";
             }

         }
         if (numberOfPlayers == 2) {
             GameBoard.game_board [9][0] = players[0].toString() ;
             players[0].src[0] = 9 ;  players[0].src[1] = 0 ;
             GameBoard.game_board [0][19] = players[1].toString() ;
             players[0].src[1] = 0 ;  players[1].src[1] = 19 ;

         }
         else if (numberOfPlayers == 4) {
             GameBoard.game_board [9][0] = players[0].toString() ;
             players[0].src[0] = 9 ;  players[0].src[1] = 0 ;
             GameBoard.game_board [0][19] = players[1].toString() ;
             players[0].src[1] = 0 ;  players[1].src[1] = 19 ;
             GameBoard.game_board [0][0] = players[2].toString() ;
             players[0].src[0] = 0 ;  players[2].src[1] = 0 ;
             GameBoard.game_board [9][19] = players[3].toString() ;
             players[3].src[0] = 9 ;  players[3].src[1] = 19 ;
         }
         for (Wall wall : Our_wall) {
             Random random = new Random();
             while (true) {
                 int row = random.nextInt(10);
                 int column = random.nextInt(20);
                 if (GameBoard.game_board[row][column].equals("   ")) {
                     GameBoard.game_board[row][column] = wall.toString();
                     break;
                 }
             }

         }

         for (Trap trap : Our_trap ) {
             Random random = new Random();
             while (true) {
                 int row = random.nextInt(10);
                 int column = random.nextInt(20);
                 if (GameBoard.game_board[row][column].equals("   ")) {
                     GameBoard.game_board[row][column] = trap.toString();
                     break;
                 }
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
  public static final String ANSI_Yellow = "\u001B[33m";



  public static void main(String[] args)  {
//      System.out.println(ANSI_RED + "Red text" + ANSI_RESET);
//      System.out.println(ANSI_BLUE + "Blue text" + ANSI_RESET);
//      System.out.println(ANSI_GREEN + "Green text" + ANSI_RESET);

      generate_random_wall();
      generate_random_trap();
      List<Wall> Our_wall = Wall.getWallList() ;
      List<Trap> Our_trap = Trap.getTrapList() ;
//      String[] nameList  = {"Ali" , "Hassan" , "Taghi" , "Naghi"}  ;

      for (int i = 0 ; i < 4 ; i++) {
          new Player(Player.SpecialMoveCounter) ;
      }

      Player[] our = Player.getPlayerList().toArray(new Player[4]) ;
      our[2].SpecialMoveCounter[0] += 1 ;
      our[2].SpecialMoveLeft = Player.Sum_list(our[2].SpecialMoveCounter) ;
      System.out.println(our[2].SpecialMoveCounter[0]);
      System.out.println(our[2].SpecialMoveLeft);
      System.out.println(our[2]);

      initial_set(4 , our ,Our_wall , Our_trap);


      GameBoard.print_gameboard();
      
  }
 }
