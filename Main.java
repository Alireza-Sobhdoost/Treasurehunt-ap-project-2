import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.awt.* ;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;


public class Main implements GameBoard {
  // ANSI color codes
    public static int number_of_players ;
    public static List<Treasure> TRSList ;

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
             players[0].cur_loc[0] = 9 ;  players[0].cur_loc[1] = 0 ;
             GameBoard.game_board [0][19] = players[1].toString() ;
             players[1].src[1] = 0 ;  players[1].src[1] = 19 ;
             players[1].cur_loc[0] = 0 ;  players[1].cur_loc[1] = 19 ;


         }
         else if (numberOfPlayers == 4) {
             GameBoard.game_board [9][0] = players[0].toString() ;
             players[0].src[0] = 9 ;  players[0].src[1] = 0 ;
             players[0].cur_loc[0] = 9 ;  players[0].cur_loc[1] = 0 ;
             GameBoard.game_board [0][19] = players[1].toString() ;
             players[1].src[1] = 0 ;  players[1].src[1] = 19 ;
             players[1].cur_loc[0] = 0 ;  players[1].cur_loc[1] = 19 ;
             GameBoard.game_board [0][0] = players[2].toString() ;
             players[2].src[0] = 0 ;  players[2].src[1] = 0 ;
             players[2].cur_loc[0] = 0 ;  players[2].cur_loc[1] = 0 ;
             GameBoard.game_board [9][19] = players[3].toString() ;
             players[3].src[0] = 9 ;  players[3].src[1] = 19 ;
             players[3].cur_loc[0] = 9 ;  players[3].cur_loc[1] = 19 ;

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
     public static void randomLocTRS (Treasure treasure) {
         Random random = new Random();
         while (true) {
             int row = random.nextInt(10);
             int column = random.nextInt(20);
             if (GameBoard.game_board[row][column].equals("   ")) {
                 GameBoard.game_board[row][column] = treasure.toString();
                 break;
             }
         }
     }
     public static void init_menu () {
         System.out.println("==Treasure Hunt==");
         System.out.println("Welcome to our game ! what do you want to do ?");
         System.out.println("[p] play a new game");
         System.out.println("[l] load a saved game");
         System.out.println("[e] exit");

     }
     public static void play_init_menu (){
         System.out.println("==Treasure Hunt==");
         System.out.println("Welcome again ! How many friends are going to play this game ?");
     }
    public static void exitMenu() {
        System.out.println("==Exit==");
        System.out.println("Do you want to leave this game that soon?");
        System.out.println("[y] Yes, I'll be back another time!");
        System.out.println("[n] No, I want to stay!");
    }
    public static void status (Player[] players) {
        System.out.println("==Treasure Hunt==");
        for (int i = 0 ; i < number_of_players ; i ++){
            System.out.print("PL"+String.valueOf(i+1)+" Score: " + String.valueOf(players[i].ScoreEarned));
            if (i != number_of_players - 1)
                System.out.print(" | ");
        }
        System.out.println("");
        for (int i = 0 ; i < number_of_players ; i ++){
            System.out.print("PL"+String.valueOf(i+1)+" HP: " + String.valueOf(players[i].HpLeft));
            if (i != number_of_players - 1)
                System.out.print(" | ");
        }
        System.out.println("");
        for (int i = 0 ; i < number_of_players ; i ++){
            System.out.println("PL"+String.valueOf(i+1)+" Abilities -> Destruction: " + String.valueOf(players[i].specialMove[0]) + " | Long Jump: " + String.valueOf(players[i].specialMove[1] + " | Spwan Trap: " + String.valueOf(players[i].specialMove[2])));
        }
    }

    public static void move (Player player , char move , int dst){
         if (move == 'u') {
             GameBoard.game_board[player.cur_loc[0] - dst][player.cur_loc[1]] = player.toString();
             GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
             player.cur_loc[0] -= dst  ;
         }
        if (move == 'd') {
            GameBoard.game_board[player.cur_loc[0] + dst][player.cur_loc[1]] = player.toString();
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
            player.cur_loc[0] += dst  ;
        }
        if (move == 'l') {
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1] - dst] = player.toString();
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
            player.cur_loc[1] -= dst  ;
        }
        if (move == 'r') {
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1] + dst] = player.toString();
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
            player.cur_loc[1] += dst  ;
        }
    }
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_Yellow = "\u001B[33m";



  public static void main(String[] args)  {
      Scanner scanner = new Scanner(System.in);

//      System.out.println(ANSI_RED + "Red text" + ANSI_RESET);
//      System.out.println(ANSI_BLUE + "Blue text" + ANSI_RESET);
//      System.out.println(ANSI_GREEN + "Green text" + ANSI_RESET);
      char choice ;
      List<Wall> Our_wall ;
      List<Trap> Our_trap ;
      Player[] players = new Player[0];
      while (true) {
          init_menu();
          choice = scanner.nextLine().charAt(0);
          System.out.print("\033[H\033[2J");
          if (choice == 'e') {
              exitMenu();
              char choice_exit;
              while (true) {
                  choice_exit = scanner.nextLine().charAt(0);
                  if (choice_exit == 'y' || choice_exit == 'n') {
                      break;
                  }
              }
              if (choice_exit == 'y') {
                  System.out.println("So goodbye !");
                  break;
              }
              if (choice_exit == 'n') {
                  continue;
              }
          }
          else if (choice == 'p' || choice == 'l'){
              if (choice == 'p') {
                  play_init_menu();
                  while (true) {
                      number_of_players = scanner.nextInt();
                      if ((number_of_players == 2) || (number_of_players == 4)) {
                          break;
                      } else
                          System.out.println("the number of players should be 2 or 4 players");
                  }

                  generate_random_wall();
                  generate_random_trap();
                  Our_wall = Wall.getWallList();
                  Our_trap = Trap.getTrapList();
                  Treasure TRS = new Treasure();
                  TRSList = Treasure.getTreasureList();

                  for (int i = 0; i < number_of_players; i++) {
                      new Player(Player.SpecialMoveCounter);
                  }
                  players = Player.getPlayerList().toArray(new Player[4]);
                  initial_set(number_of_players, players, Our_wall, Our_trap);
                  randomLocTRS(TRS);
//                  int[] a = {4, 10};
                  break;
              }
              if (choice == 'l'){
                  break;
              }
          }

      }
      int who_is_going_to_play = 0 ;
      if (choice == 'p' || choice == 'l') {
          while (true) {
              GameBoard.print_gameboard(players[who_is_going_to_play].cur_loc);
              status(players);
              boolean[] allowedToMove = players[who_is_going_to_play].gettosides();
              BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
              while (true) {
                  char choice_player = scanner.next().charAt(0);
                  if (choice_player == 'u' && allowedToMove[0]){
                      move(players[who_is_going_to_play] , 'u',1);
                      break;
                  }
                  if (choice_player == 'd' && allowedToMove[1]){
                      move(players[who_is_going_to_play] , 'd',1);
                      break;
                  }
                  if (choice_player == 'l' && allowedToMove[2]){
                      move(players[who_is_going_to_play] , 'l',1);
                      break;
                  }
                  if (choice_player == 'r' && allowedToMove[3]){
                      move(players[who_is_going_to_play] , 'r',1);
                      break;
                  }

              }

              who_is_going_to_play += 1;
              who_is_going_to_play %= number_of_players;
          }
      }



  }
 }
