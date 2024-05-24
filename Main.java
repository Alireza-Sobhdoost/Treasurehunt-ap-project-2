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
    public static int randomIndexsp ;
//    public static int who_is_going_to_play ;
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

    public static String randomSpin (Player[] players , int turn , List<Trap> traplist ){
         String chanceMessage ;
        String[] messagelist = {" and added one of his/her special movements" , " ans backed to his/her source " , " and make other players to bach to their source " , " and removed 3 traps from the map " , " and added 3 traps to the map "} ;

        Random random = new Random();
        int randomIndexsp = random.nextInt(5) + 1;
        System.out.println(randomIndexsp);
        if (randomIndexsp == 1){
            int random_ability = random.nextInt(3);
            players[turn].specialMove[random_ability] += 1 ;
            return messagelist[0];

        }
        else if (randomIndexsp == 2){
            players[turn].BackToSrc(GameBoard.game_board);

            return messagelist[1];
        }
        else if (randomIndexsp== 3){
//            for (Player player : players){
//                if (player != players[turn]) {
//                    player.BackToSrc(GameBoard.game_board);
//                }
//            }
            if (Main.number_of_players == 2) {
                players[(turn+1)%2].BackToSrc(GameBoard.game_board);
            }
            else if (Main.number_of_players == 4) {
                players[(turn+1)%4].BackToSrc(GameBoard.game_board);
                players[(turn+2)%4].BackToSrc(GameBoard.game_board);
                players[(turn+3)%4].BackToSrc(GameBoard.game_board);

            }
            return  messagelist[2];
        }
        else if (randomIndexsp == 4){
            for (int i =0 ; i < 3 ; i++) {
                int randomtrap = random.nextInt(traplist.size());
                traplist.remove(randomtrap) ;
            }
            return messagelist[3];
        }
        else if (randomIndexsp == 5){
            for (int i = 0 ; i < 3 ; i++) {
                Trap mytrap ;
                int randomtrap = random.nextInt(Trap.TrapTypeList.length);
                if (Trap.TrapTypeList[randomtrap] == TrapType.MouseTrap) {
                    mytrap = new Trap(TrapType.MouseTrap , true ,1, 5) ;
                }
                else if (Trap.TrapTypeList[randomtrap] == TrapType.Bomb) {
                    mytrap = new Trap(TrapType.Bomb , true ,2, 10) ;
                }
                else {
                    mytrap = new Trap(TrapType.TNT , false ,3, 15) ;
                }
                while (true) {
                    int row = random.nextInt(10);
                    int column = random.nextInt(20);
                    if (GameBoard.game_board[row][column].equals("   ")) {
                        GameBoard.game_board[row][column] = mytrap;
                        break;
                    }
                }

            }
            return messagelist[4];
        }

        else {
            return null ;
        }
    }

    public static Trap Spwan_trap() {
        Random random = new Random();
        int randomIndex = random.nextInt(Trap.TrapTypeList.length);
        Trap trap;

        if (Trap.TrapTypeList[randomIndex] == TrapType.MouseTrap) {
            trap = new Trap(TrapType.MouseTrap, true, 1, 5);
        } else if (Trap.TrapTypeList[randomIndex] == TrapType.Bomb) {
            trap = new Trap(TrapType.Bomb, true, 2, 10);
        } else if (Trap.TrapTypeList[randomIndex] == TrapType.TNT) {
            trap = new Trap(TrapType.TNT, false, 3, 15);
        } else {
            // Handle the case when none of the Trap types match
            trap = null; // Or provide default Trap instance based on your requirements
        }

        return trap;
    }


     public static void initial_set (int numberOfPlayers , Player[] players ,List<Wall> Our_wall , List<Trap> Our_trap , Portal[] portals){
         for (int rows = 0 ; rows < 10 ; rows++) {
             for (int column = 0; column < 20; column++) {
                 game_board[rows][column] = "   ";
             }

         }
         if (numberOfPlayers == 2) {
             GameBoard.game_board [9][0] = players[0] ;
             players[0].src[0] = 9 ;  players[0].src[1] = 0 ;
             players[0].cur_loc[0] = 9 ;  players[0].cur_loc[1] = 0 ;
             GameBoard.game_board [0][19] = players[1] ;
             players[1].src[0] = 0 ;  players[1].src[1] = 19 ;
             players[1].cur_loc[0] = 0 ;  players[1].cur_loc[1] = 19 ;


         }
         else if (numberOfPlayers == 4) {
             GameBoard.game_board [9][0] = players[0] ;
             players[0].src[0] = 9 ;  players[0].src[1] = 0 ;
             players[0].cur_loc[0] = 9 ;  players[0].cur_loc[1] = 0 ;
             GameBoard.game_board [0][19] = players[1] ;
             players[1].src[0] = 0 ;  players[1].src[1] = 19 ;
             players[1].cur_loc[0] = 0 ;  players[1].cur_loc[1] = 19 ;
             GameBoard.game_board [0][0] = players[2];
             players[2].src[0] = 0 ;  players[2].src[1] = 0 ;
             players[2].cur_loc[0] = 0 ;  players[2].cur_loc[1] = 0 ;
             GameBoard.game_board [9][19] = players[3] ;
             players[3].src[0] = 9 ;  players[3].src[1] = 19 ;
             players[3].cur_loc[0] = 9 ;  players[3].cur_loc[1] = 19 ;

         }
//         System.out.println(portals[0].loc[0] + portals[0].loc[1] );
         GameBoard.game_board[portals[0].loc[0]][portals[0].loc[1]] = portals[0] ;
         GameBoard.game_board[portals[1].loc[0]][portals[1].loc[1]] = portals[1] ;

         for (Wall wall : Our_wall) {
             Random random = new Random();
             while (true) {
                 int row = random.nextInt(10);
                 int column = random.nextInt(20);
                 if (GameBoard.game_board[row][column].equals("   ")) {
                     GameBoard.game_board[row][column] = wall;
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
                     GameBoard.game_board[row][column] = trap;
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
             if (GameBoard.game_board[row][column].toString().equals("   ")) {
                 GameBoard.game_board[row][column] = treasure;
                 break;
             }
         }
     }
    public static void randomLocSPN (Spin spin) {
        Random random = new Random();
        while (true) {
            int row = random.nextInt(10);
            int column = random.nextInt(20);
            if (GameBoard.game_board[row][column].toString().equals("   ")) {
                GameBoard.game_board[row][column] = spin;
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
             GameBoard.game_board[player.cur_loc[0] - dst][player.cur_loc[1]] = player;
             GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
             player.cur_loc[0] -= dst  ;
         }
        if (move == 'd') {
            GameBoard.game_board[player.cur_loc[0] + dst][player.cur_loc[1]] = player;
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
            player.cur_loc[0] += dst  ;
        }
        if (move == 'l') {
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1] - dst] = player;
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
            player.cur_loc[1] -= dst  ;
        }
        if (move == 'r') {
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1] + dst] = player;
            GameBoard.game_board[player.cur_loc[0]][player.cur_loc[1]] = "   " ;
            player.cur_loc[1] += dst  ;
        }
    }
    public static void moveWithPortal (Player[] players , int who_is_going_to_play, char move , int[] src, List<Trap> Our_trap , List<Spin> spinList){
         String message = null ;
         boolean flagspeqto2 = false ;
        if (move == 'u') {
            if (GameBoard.game_board[src[0] -1][src[1]] instanceof Trap) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).trap ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[src[0]-1][src[1]]).HpLost ;
                players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[src[0]-1][src[1]]).ScoreLost ;
                Our_trap.remove(((Trap) GameBoard.game_board[src[0]-1][src[1]])) ;

            }
            else if (GameBoard.game_board[src[0]-1][src[1]] instanceof Treasure) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[src[0]-1][src[1]]).Score_add ;
                randomLocTRS(TRSList.get(0)); ;

            }
            else if (GameBoard.game_board[src[0]-1][src[1]] instanceof Spin) {

                String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                if (randomIndexsp == 2){
                    flagspeqto2 = true;
                }
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
//                Log.writeMessage(message);
                randomLocSPN(spinList.get(0));
            }

            GameBoard.game_board[src[0] - 1][src[1]] = players[who_is_going_to_play];
            GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]] = "   " ;
            players[who_is_going_to_play].cur_loc[0] -= 1 ;
            if (flagspeqto2){
                players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
            }
        }
        if (move == 'd') {
            if (GameBoard.game_board[src[0] +1][src[1]] instanceof Trap) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).trap ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[src[0]+1][src[1]]).HpLost ;
                players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[src[0]+1][src[1]]).ScoreLost ;
                Our_trap.remove(((Trap) GameBoard.game_board[src[0]-1][src[1]])) ;

            }
            else if (GameBoard.game_board[src[0]+1][src[1]] instanceof Treasure) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[src[0]+1][src[1]]).Score_add ;
                randomLocTRS(TRSList.get(0)); ;

            }
            else if (GameBoard.game_board[src[0]+1][src[1]] instanceof Spin) {

                String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                if (randomIndexsp == 2){
                    flagspeqto2 = true;
                }
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
//                Log.writeMessage(message);
                randomLocSPN(spinList.get(0));
            }
            GameBoard.game_board[src[0] + 1][src[1]] = players[who_is_going_to_play];
            GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]] = "   " ;
            players[who_is_going_to_play].cur_loc[0] += 1 ;
            if (flagspeqto2){
                players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
            }
        }
        if (move == 'l') {
            if (GameBoard.game_board[src[0] ][src[1]-1] instanceof Trap) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).trap ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[src[0]][src[1]-1]).HpLost ;
                players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[src[0]][src[1]-1]).ScoreLost ;
                Our_trap.remove(((Trap) GameBoard.game_board[src[0]][src[1]-1])) ;

            }
            else if (GameBoard.game_board[src[0]][src[1]-1] instanceof Treasure) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[src[0]][src[1]-1]).Score_add ;
                randomLocTRS(TRSList.get(0)); ;

            }
            else if (GameBoard.game_board[src[0]][src[1]-1] instanceof Spin) {

                String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                if (randomIndexsp == 2){
                    flagspeqto2 = true;
                }
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
//                Log.writeMessage(message);
                randomLocSPN(spinList.get(0));
            }
            GameBoard.game_board[src[0]][src[1] - 1] = players[who_is_going_to_play];
            GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]] = "   " ;
            players[who_is_going_to_play].cur_loc[1] -= 1  ;
            if (flagspeqto2){
                players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
            }
        }
        if (move == 'r') {
            if (GameBoard.game_board[src[0] ][src[1]+1] instanceof Trap) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).trap ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[src[0]][src[1]+1]).HpLost ;
                players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[src[0]][src[1]+1]).ScoreLost ;
                Our_trap.remove(((Trap) GameBoard.game_board[src[0]][src[1]-1])) ;

            }
            else if (GameBoard.game_board[src[0]][src[1]+1] instanceof Treasure) {
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
//                Log.writeMessage(message);
                players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[src[0]][src[1]+1]).Score_add ;
                randomLocTRS(TRSList.get(0)); ;

            }
            else if (GameBoard.game_board[src[0]][src[1]+1] instanceof Spin) {

                String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                if (randomIndexsp == 2){
                    flagspeqto2 = true;
                }
//                message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
//                Log.writeMessage(message);
                randomLocSPN(spinList.get(0));
            }
            GameBoard.game_board[src[0]][src[1] + 1] = players[who_is_going_to_play];
            GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]] = "   " ;
            players[who_is_going_to_play].cur_loc[1] += 1  ;
            if (flagspeqto2){
                players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
            }
        }
    }

    public static void andTheWinnerIs (Player player){
         System.out.println("==Trasure Hunt==");
         System.out.println("And the winner iiiissssss PL"+String.valueOf(player.playerId));
         System.out.println("Congratulation PL"+String.valueOf(player.playerId) + " you were grate ! \uD83D\uDE0E");
         System.out.println("Other players dont be sad ! maybe another day ! \uD83D\uDE0A");
         player.hasWon = true ;


    }
    public static void someoneHaslost (Player player){
        System.out.println("==Trasure Hunt==");
        System.out.println("Sorry ! PL"+String.valueOf(player.playerId));
        System.out.println("you are one of your losers ! maybe it wasn't your lucky day");
        player.haslose = true ;


    }
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_Yellow = "\u001B[33m";



  public static void main(String[] args)  {
      Scanner scanner = new Scanner(System.in);
      List<Wall> Our_wall = new ArrayList<>() ;
      List<Trap> Our_trap = new ArrayList<>();
      List<Treasure> Our_treasure = new ArrayList<>();
      List<Spin> spinList = new ArrayList<>() ;
      Portal[] portals = new Portal[2] ;
      int who_is_going_to_play = 0 ;

//      System.out.println(ANSI_RED + "Red text" + ANSI_RESET);
//      System.out.println(ANSI_BLUE + "Blue text" + ANSI_RESET);
//      System.out.println(ANSI_GREEN + "Green text" + ANSI_RESET);
      char choice ;
//      List<Wall> Our_wall ;
//      List<Trap> Our_trap ;
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
                  CsvFileSaveandLoad.number_of_saves  = CsvFileSaveandLoad.getNumber_of_saves() ;
                  if (CsvFileSaveandLoad.number_of_saves != 0) {
                      CsvFileSaveandLoad.getSavedFiles();
                  }
                  while (true) {
                      number_of_players = scanner.nextInt();
                      if ((number_of_players == 2) || (number_of_players == 4)) {
                          break;
                      } else
                          System.out.println("the number of players should be 2 or 4 players");
                  }

                  int[] portal1 = {1,1} ;
                  int[] portal2 = {8,18} ;
                  Portal Prt1 = new Portal(portal1 , false) ;
                  Portal Prt2 = new Portal(portal2, false) ;
                  portals = Portal.getPortals();
                  generate_random_wall();
                  generate_random_trap();
                  Our_wall = Wall.getWallList();
                  Our_trap = Trap.getTrapList();
                  Treasure TRS = new Treasure();
                  TRSList = Treasure.getTreasureList();
                  Spin spin = new Spin();
                  spinList = Spin.getSpinList() ;

                  if (number_of_players == 2) {
                      int[] src1 = {9,0};
                      int [] spm1 = {3,3,6};
                      new Player(spm1 , 5, 0 , src1 ,false,false);
                      int[] src2 = {0,19};
                      int [] spm2 = {3,3,6};
                      new Player(spm2 , 5, 0 , src2 ,false,false);
                  }
                  else if (number_of_players == 4) {
                      int[] src1 = {9,0};
                      int [] spm1 = {3,3,6};
                      new Player(spm1 , 5, 0 , src1 ,false,false);
                      int[] src2 = {0,19};
                      int [] spm2 = {3,3,6};
                      new Player(spm2 , 5, 0 , src2 ,false,false);
                      int[] src3 = {0,0};
                      int [] spm3 = {3,3,6};
                      new Player( spm3, 5, 0 , src3 ,false,false);
                      int[] src4 = {9,19};
                      int [] spm4 = {3,3,6};
                      new Player(spm4 , 5, 0 , src4 ,false,false);

                  }

                  players = Player.getPlayerList().toArray(new Player[4]);
                  initial_set(number_of_players, players, Our_wall, Our_trap , portals);
                  randomLocTRS(TRS);
                  randomLocSPN(spin);
                  who_is_going_to_play = 0 ;
                  CsvFileSaveandLoad.SaveGame(GameBoard.game_board , number_of_players , who_is_going_to_play ,players);
                  CsvFileSaveandLoad.SaveStatus();
//                  int[] a = {4, 10};
                  break;
              }
              if (choice == 'l'){
                  while (true) {
                      CsvFileSaveandLoad.number_of_saves = CsvFileSaveandLoad.LoadStatus();
                      CsvFileSaveandLoad.getSavedFiles() ;
                      CsvFileSaveandLoad.SaveStatus();
//                      System.out.println(numberOfSaves);
                      int fileToLoad = scanner.nextInt();
                      if (fileToLoad  <= CsvFileSaveandLoad.number_of_saves ) {

                          CsvFileSaveandLoad.LoadGame("game"+String.valueOf(fileToLoad)+".csv" , GameBoard.game_board );
                          who_is_going_to_play = CsvFileSaveandLoad.getTurn() ;
//                          number_of_players = CsvFileSaveandLoad.numberplayers ;
//                          who_is_going_to_play = CsvFileSaveandLoad.turn ;
                          int[] a = {0,0} ;
                          Our_wall = Wall.getWallList();
                          Our_trap = Trap.getTrapList();
                          TRSList = Treasure.getTreasureList();
                          spinList = Spin.getSpinList();
                          portals = Portal.getPortals();
                          players = Player.getPlayerList().toArray(new Player[4]);
//                          GameBoard.print_gameboard(a);
//                          System.out.println(players[0].HpLeft);
                          break;

                      }
                  }
                  CsvFileSaveandLoad.SaveGame(GameBoard.game_board , number_of_players , who_is_going_to_play ,players);
                  CsvFileSaveandLoad.SaveStatus();


                  break;

              }
          }

      }

      String message = "" ;

      if (choice == 'p' || choice == 'l') {
//          System.out.println(number_of_players);
//          System.out.println(who_is_going_to_play);
//          System.out.println(players[who_is_going_to_play].specialMove[0]);


          while (true) {

              if (players[who_is_going_to_play].haslose){
                  who_is_going_to_play += 1 ;
                  who_is_going_to_play %= 4 ;
              }
//              GameBoard.print_gameboard(players[who_is_going_to_play].cur_loc);
//              status(players);
              boolean[] allowedToMove;
              BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
              try {


                  while (true) {
                      boolean flagspeqto2 = false ;
                      boolean isportalused = false ;
                      boolean[] portalallowd = new boolean[4];
                      GameBoard.print_gameboard(players[who_is_going_to_play].cur_loc);
                      status(players);
                      System.out.println(message);
//                      System.out.println(who_is_going_to_play);
                      allowedToMove = players[who_is_going_to_play].gettosides();
                      System.out.println("[h] see log's history of your movments");
                      int input = System.in.read();
                      int allowtouseportal = 0 ;
                      if (input == 27) { // Escape sequence for arrow keys
                          System.in.read(); // Consume [
                          int arrowKey = System.in.read();

                          if (arrowKey == 65 && allowedToMove[0]) {
                              if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Trap) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).trap ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).HpLost ;
                                  players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).ScoreLost ;
                                  Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]])) ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Treasure) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).Score_add ;
                                  randomLocTRS(TRSList.get(0)); ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Spin) {

                                  String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                  if (randomIndexsp == 2){
                                      flagspeqto2 = true;
                                  }
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
                                  Log.writeMessage(message);
                                  randomLocSPN(spinList.get(0));

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Portal) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get into a Portal " ;
                                  Log.writeMessage(message);
                                  portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).checksides() ;
                                  for (boolean i : portalallowd){
                                      if (i) {
                                          allowtouseportal += 1 ;
                                      }
                                  }
                                  isportalused = true ;

                              }

                              else {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) ;
                                  Log.writeMessage(message);
                              }
                              if (!isportalused) {
                                  move(players[who_is_going_to_play], 'u', 1);
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              if (allowtouseportal != 0){
                                  char[] moveopt = {'u','d','l','r'} ;
                                  Random random = new Random();
                                  int randomportal = 0 ;
                                  while (true) {
                                      randomportal= random.nextInt(4);
                                      if (portalallowd[randomportal]){
                                          break;
                                      }
                                  }
                                  moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).id + 1)%2].loc , Our_trap, spinList) ;
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
//                              else {continue;}


                          } else if (arrowKey == 66 && allowedToMove[1]) {
                              if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]] instanceof Trap) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).trap ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).HpLost ;
                                  players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).ScoreLost ;
                                  Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]])) ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]] instanceof Treasure) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).Score_add ;
                                  randomLocTRS(TRSList.get(0)); ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]] instanceof Spin) {
                                  String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                  if (randomIndexsp == 2){
                                      flagspeqto2 = true;
                                  }
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
                                  Log.writeMessage(message);
                                  randomLocSPN(spinList.get(0));

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]] instanceof Portal) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get into a Portal " ;
                                  Log.writeMessage(message);
                                  portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).checksides() ;
                                  for (boolean i : portalallowd){
                                      if (i) {
                                          allowtouseportal += 1 ;
                                      }
                                  }
                                  isportalused = true ;

                              }
                              else {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) ;
                                  Log.writeMessage(message);
                              }

                              if (!isportalused) {
                                  move(players[who_is_going_to_play], 'd', 1);
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              if (allowtouseportal != 0){
                                  char[] moveopt = {'u','d','l','r'} ;
                                  Random random = new Random();
                                  int randomportal = 0 ;
                                  while (true) {
                                      randomportal= random.nextInt(4);
                                      if (portalallowd[randomportal]){
                                          break;
                                      }
                                  }
                                  moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).id + 1)%2].loc , Our_trap, spinList) ;
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              else {continue;}

                          } else if (arrowKey == 68 && allowedToMove[2]) {
                              if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1] instanceof Trap) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).trap ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).HpLost ;
                                  players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).ScoreLost ;
                                  Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1])) ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1] instanceof Treasure) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]) +" and get the Treasure " ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).Score_add ;
                                  randomLocTRS(TRSList.get(0)); ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1] instanceof Spin) {
                                  String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]) +" and used the spin of chance" + chance ;
                                  Log.writeMessage(message);
                                  randomLocSPN(spinList.get(0));

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1] instanceof Portal) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]) +" and get into a Portal " ;
                                  Log.writeMessage(message);
                                  portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).checksides() ;
                                  for (boolean i : portalallowd){
                                      if (i) {
                                          allowtouseportal += 1 ;
                                      }
                                  }
                                  isportalused = true ;

                              }
                              else {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]) ;
                                  Log.writeMessage(message);
                              }
                              if (!isportalused) {
                                  move(players[who_is_going_to_play], 'l', 1);
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              if (allowtouseportal != 0){
                                  char[] moveopt = {'u','d','l','r'} ;
                                  Random random = new Random();
                                  int randomportal = 0 ;
                                  while (true) {
                                      randomportal= random.nextInt(4);
                                      if (portalallowd[randomportal]){
                                          break;
                                      }
                                  }
                                  moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).id + 1)%2].loc , Our_trap, spinList) ;
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              else {continue;}
                          } else if (arrowKey == 67 && allowedToMove[3]) {
                              if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1] instanceof Trap) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).trap ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).HpLost ;
                                  players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).ScoreLost ;
                                  Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1])) ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1] instanceof Treasure) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2) +" and get the Treasure " ;
                                  Log.writeMessage(message);
                                  players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).Score_add ;
                                  randomLocTRS(TRSList.get(0)); ;

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1] instanceof Spin) {
                                  String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                  if (randomIndexsp == 2){
                                      flagspeqto2 = true;
                                  }
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2) +" and used the spin of chance" + chance ;
                                  Log.writeMessage(message);
                                  randomLocSPN(spinList.get(0));

                              }
                              else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1] instanceof Portal) {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2) +" and get into a Portal " ;
                                  Log.writeMessage(message);
                                  portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).checksides() ;
                                  for (boolean i : portalallowd){
                                      if (i) {
                                          allowtouseportal += 1 ;
                                      }
                                  }
                                  isportalused = true ;

                              }
                              else {
                                  message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2) ;
                                  Log.writeMessage(message);
                              }
                              if (!isportalused) {
                                  move(players[who_is_going_to_play], 'r', 1);
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              if (allowtouseportal != 0){
                                  char[] moveopt = {'u','d','l','r'} ;
                                  Random random = new Random();
                                  int randomportal = 0 ;
                                  while (true) {
                                      randomportal= random.nextInt(4);
                                      if (portalallowd[randomportal]){
                                          break;
                                      }
                                  }
                                  moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).id + 1)%2].loc , Our_trap, spinList) ;
                                  if (flagspeqto2){
                                      players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                  }
                                  break;
                              }
                              else {continue;}
                          }
                      }

                      if (input == 'd' && allowedToMove[4]) {
                          boolean back_opt = false ;
                          GameBoard.print_gameboard(players[who_is_going_to_play].cur_loc);
                          boolean[] allowedToDestroy = players[who_is_going_to_play].Destruction();
                          try {

                              while (true && !back_opt) {
                                  int input_D = System.in.read();
                                  if (input_D == 27) { // Escape sequence for arrow keys
                                      System.in.read(); // Consume [
                                      int arrowKey = System.in.read();

                                      if (arrowKey == 65 && allowedToDestroy[0]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).trap +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1)  ;
                                              Log.writeMessage(message);
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]])) ;

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Wall) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).wall +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1)  ;
                                              Log.writeMessage(message);
                                              Our_wall.remove(((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]])) ;

                                          }
//                                          move(players[who_is_going_to_play], 'u', 1);
                                          players[who_is_going_to_play].specialMove[0] -= 1 ;
                                          break;
                                      } else if (arrowKey == 66 && allowedToDestroy[1]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).trap +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1)  ;
                                              Log.writeMessage(message);
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]])) ;

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]] instanceof Wall) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).wall +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1)  ;
                                              Log.writeMessage(message);
                                              Our_wall.remove(((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]])) ;

                                          }
//                                          move(players[who_is_going_to_play], 'd', 1);
                                          players[who_is_going_to_play].specialMove[0] -= 1 ;
                                          break;
                                      } else if (arrowKey == 68 && allowedToDestroy[2]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).trap +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1])  ;
                                              Log.writeMessage(message);
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1])) ;

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1] instanceof Wall) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).wall +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1])  ;
                                              Log.writeMessage(message);
                                              Our_wall.remove(((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]-1])) ;

                                          }
//                                          move(players[who_is_going_to_play], 'l', 1);
                                          players[who_is_going_to_play].specialMove[0] -= 1 ;
                                          break;
                                      } else if (arrowKey == 67 && allowedToDestroy[3]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).trap +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2)  ;
                                              Log.writeMessage(message);
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1])) ;

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1] instanceof Wall) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has used one of his/her Destructions and destroyed a " + ((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).wall +" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2)  ;
                                              Log.writeMessage(message);
                                              Our_wall.remove(((Wall) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]-1])) ;

                                          }
//                                          move(players[who_is_going_to_play], 'r', 1);
                                          players[who_is_going_to_play].specialMove[0] -= 1 ;
                                          break;
                                      }
                                  }
                                  if (input_D == 'b') {
                                      back_opt = true ;
//                                      break;
                                  }
                              }
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                        if (!back_opt) {
                            break;
                        }
                      }
                      if (input == 'l'){
                          boolean back_opt = false ;
                          GameBoard.print_gameboard(players[who_is_going_to_play].cur_loc);
                          boolean[] allowedToJump = players[who_is_going_to_play].LongJump();
                          try {
                              while (true && !back_opt) {
                                  int input_L = System.in.read();

                                  if (input_L == 27) { // Escape sequence for arrow keys
                                      System.in.read(); // Consume [
                                      int arrowKey = System.in.read();

                                      if (arrowKey == 65 && allowedToJump[0]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 2][players[who_is_going_to_play].cur_loc[1]] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-2][players[who_is_going_to_play].cur_loc[1]]).trap ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 2][players[who_is_going_to_play].cur_loc[1]]).HpLost;
                                              players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 2][players[who_is_going_to_play].cur_loc[1]]).ScoreLost;
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 2][players[who_is_going_to_play].cur_loc[1]]));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 2][players[who_is_going_to_play].cur_loc[1]] instanceof Treasure) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 2][players[who_is_going_to_play].cur_loc[1]]).Score_add;
                                              randomLocTRS(TRSList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-2][players[who_is_going_to_play].cur_loc[1]] instanceof Spin) {

                                              String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                              if (randomIndexsp ==2){
                                                  flagspeqto2 = true;
                                              }
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
                                              Log.writeMessage(message);
                                              randomLocSPN(spinList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-2][players[who_is_going_to_play].cur_loc[1]] instanceof Portal) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get into a Portal " ;
                                              Log.writeMessage(message);
                                              portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-2][players[who_is_going_to_play].cur_loc[1]]).checksides() ;
                                              for (boolean i : portalallowd){
                                                  if (i) {
                                                      allowtouseportal += 1 ;
                                                  }
                                              }
                                              isportalused = true ;

                                          }
                                          else {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) ;
                                              Log.writeMessage(message);
                                          }
                                          if (!isportalused) {
                                              move(players[who_is_going_to_play], 'u', 2);
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          if (allowtouseportal != 0){
                                              char[] moveopt = {'u','d','l','r'} ;
                                              Random random = new Random();
                                              int randomportal = 0 ;
                                              while (true) {
                                                  randomportal= random.nextInt(4);
                                                  if (portalallowd[randomportal]){
                                                      break;
                                                  }
                                              }
                                              moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-2][players[who_is_going_to_play].cur_loc[1]]).id + 1)%2].loc , Our_trap, spinList) ;
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          else {continue;}

                                      } else if (arrowKey == 66 && allowedToJump[1]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 2][players[who_is_going_to_play].cur_loc[1]] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+2][players[who_is_going_to_play].cur_loc[1]]).trap ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 2][players[who_is_going_to_play].cur_loc[1]]).HpLost;
                                              players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 2][players[who_is_going_to_play].cur_loc[1]]).ScoreLost;
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 2][players[who_is_going_to_play].cur_loc[1]]));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 2][players[who_is_going_to_play].cur_loc[1]] instanceof Treasure) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get the Treasure " ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 2][players[who_is_going_to_play].cur_loc[1]]).Score_add;
                                              randomLocTRS(TRSList.get(0));
                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+2][players[who_is_going_to_play].cur_loc[1]] instanceof Spin) {
                                              String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                              if (randomIndexsp ==2){
                                                  flagspeqto2 = true;
                                              }
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and used the spin of chance" + chance ;
                                              Log.writeMessage(message);
                                              randomLocSPN(spinList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+2][players[who_is_going_to_play].cur_loc[1]] instanceof Portal) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) +" and get into a Portal " ;
                                              Log.writeMessage(message);
                                              portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+2][players[who_is_going_to_play].cur_loc[1]]).checksides() ;
                                              for (boolean i : portalallowd){
                                                  if (i) {
                                                      allowtouseportal += 1 ;
                                                  }
                                              }
                                              isportalused = true ;

                                          }

                                          else {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+2].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) ;
                                              Log.writeMessage(message);
                                          }
                                          if (!isportalused) {
                                              move(players[who_is_going_to_play], 'd', 2);
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          if (allowtouseportal != 0){
                                              char[] moveopt = {'u','d','l','r'} ;
                                              Random random = new Random();
                                              int randomportal = 0 ;
                                              while (true) {
                                                  randomportal= random.nextInt(4);
                                                  if (portalallowd[randomportal]){
                                                      break;
                                                  }
                                              }
                                              moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+2][players[who_is_going_to_play].cur_loc[1]]).id + 1)%2].loc , Our_trap, spinList) ;
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          else {continue;}

                                      } else if (arrowKey == 68 && allowedToJump[2]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] - 2] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]-1) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-2]).trap ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] -2]).HpLost;
                                              players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] - 2]).ScoreLost;
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] - 2]));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] - 2] instanceof Treasure) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]-1) +" and get the Treasure " ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] - 2]).Score_add;
                                              randomLocTRS(TRSList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-2] instanceof Spin) {
                                              String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                              if (randomIndexsp ==2){
                                                  flagspeqto2 = true;
                                              }
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]-1) +" and used the spin of chance" + chance ;
                                              Log.writeMessage(message);
                                              randomLocSPN(spinList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-2] instanceof Portal) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]-1) +" and get into a Portal " ;
                                              Log.writeMessage(message);
                                              portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-2]).checksides() ;
                                              for (boolean i : portalallowd){
                                                  if (i) {
                                                      allowtouseportal += 1 ;
                                                  }
                                              }
                                              isportalused = true ;

                                          }
                                          else {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]-1) ;
                                              Log.writeMessage(message);
                                          }
                                          if (!isportalused) {
                                              move(players[who_is_going_to_play], 'l', 2);
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          if (allowtouseportal != 0){
                                              char[] moveopt = {'u','d','l','r'} ;
                                              Random random = new Random();
                                              int randomportal = 0 ;
                                              while (true) {
                                                  randomportal= random.nextInt(4);
                                                  if (portalallowd[randomportal]){
                                                      break;
                                                  }
                                              }
                                              moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-2]).id + 1)%2].loc , Our_trap, spinList) ;
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          else {continue;}

                                      } else if (arrowKey == 67 && allowedToJump[3]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 2] instanceof Trap) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+3) +" and damaged by a " + ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+2]).trap ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].HpLeft -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 2]).HpLost;
                                              players[who_is_going_to_play].ScoreEarned -= ((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 2]).ScoreLost;
                                              Our_trap.remove(((Trap) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 2]));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 2] instanceof Treasure) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+3) +" and get the Treasure " ;
                                              Log.writeMessage(message);
                                              players[who_is_going_to_play].ScoreEarned += ((Treasure) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 2]).Score_add;
                                              randomLocTRS(TRSList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+2] instanceof Spin) {
                                              String chance = randomSpin(players,who_is_going_to_play,Our_trap) ;
                                              if (randomIndexsp ==2){
                                                  flagspeqto2 = true;
                                              }
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+3) +" and used the spin of chance" + chance ;
                                              Log.writeMessage(message);
                                              randomLocSPN(spinList.get(0));

                                          }
                                          else if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+2] instanceof Portal) {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has moved to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+3) +" and get into a Portal " ;
                                              Log.writeMessage(message);
                                              portalallowd = ((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+2]).checksides() ;
                                              for (boolean i : portalallowd){
                                                  if (i) {
                                                      allowtouseportal += 1 ;
                                                  }
                                              }
                                              isportalused = true ;

                                          }
                                          else {
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has jumped to " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+3) ;
                                              Log.writeMessage(message);
                                          }
                                          if (!isportalused) {
                                              move(players[who_is_going_to_play], 'r', 2);
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          if (allowtouseportal != 0){
                                              char[] moveopt = {'u','d','l','r'} ;
                                              Random random = new Random();
                                              int randomportal = 0 ;
                                              while (true) {
                                                  randomportal= random.nextInt(4);
                                                  if (portalallowd[randomportal]){
                                                      break;
                                                  }
                                              }
                                              moveWithPortal(players ,who_is_going_to_play,moveopt[randomportal],portals[(((Portal) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+2]).id + 1)%2].loc , Our_trap, spinList) ;
                                              if (flagspeqto2){
                                                  players[who_is_going_to_play].BackToSrc(GameBoard.game_board);
                                              }
                                              players[who_is_going_to_play].specialMove[1] -= 1 ;
                                              break;
                                          }
                                          else {continue;}
                                      }
                                  }
                                  if (input_L == 'b') {
                                      back_opt = true ;
                                  }
                              }
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                        if (!back_opt) {
                            break;
                        }
                      }
                      if (input == 's'){
                          boolean back_opt = false ;
                          GameBoard.print_gameboard(players[who_is_going_to_play].cur_loc);
                          boolean[] allowedToJump = players[who_is_going_to_play].SpwanTrap();
                          try {
                              while (true && !back_opt) {
                                  int input_S = System.in.read();

                                  if (input_S == 27) { // Escape sequence for arrow keys
                                      System.in.read(); // Consume [
                                      int arrowKey = System.in.read();

                                      if (arrowKey == 65 && allowedToJump[0]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 1][players[who_is_going_to_play].cur_loc[1]].toString().equals("   ")) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1)  ;
                                              Log.writeMessage(message);
                                              GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 1][players[who_is_going_to_play].cur_loc[1]] = spwaned_trap ;
                                          }
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] - 1][players[who_is_going_to_play].cur_loc[1]] instanceof Player) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " and damaged the PL"+ players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].playerId;
                                              Log.writeMessage(message);
                                              String message2 = "PL"+players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].playerId +" you have damaged by a " +spwaned_trap.trap +" which was spwaned by PL"+String.valueOf(players[who_is_going_to_play].playerId) + " and you've backed to your source in " + GameBoard.row_log[players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].src[0]].toString() + String.valueOf(players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].src[1] + 1);
                                              Log.writeMessage(message2);
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].HpLeft -= spwaned_trap.HpLost ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].ScoreEarned -= spwaned_trap.ScoreLost ;
                                              Our_trap.remove(spwaned_trap) ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].BackToSrc(GameBoard.game_board);

                                          }
                                          players[who_is_going_to_play].specialMove[2] -= 1 ;
                                          break;
                                      } else if (arrowKey == 66 && allowedToJump[1]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+ 1][players[who_is_going_to_play].cur_loc[1]].toString().equals("   ")) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]+1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1)  ;
                                              Log.writeMessage(message);
                                              GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 1][players[who_is_going_to_play].cur_loc[1]] = spwaned_trap ;
                                          }
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] + 1][players[who_is_going_to_play].cur_loc[1]] instanceof Player) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]-1].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " and damaged the PL"+ players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]-1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].playerId;
                                              Log.writeMessage(message);
                                              String message2 = "PL"+players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].playerId +" you have damaged by a " +spwaned_trap.trap +" which was spwaned by PL"+String.valueOf(players[who_is_going_to_play].playerId) + " and you've backed to your source in " + GameBoard.row_log[players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].src[0]].toString() + String.valueOf(players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].src[1] + 1);
                                              Log.writeMessage(message2);
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].HpLeft -= spwaned_trap.HpLost ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].ScoreEarned -= spwaned_trap.ScoreLost ;
                                              Our_trap.remove(spwaned_trap) ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]+1][players[who_is_going_to_play].cur_loc[1]]).playerId - 1].BackToSrc(GameBoard.game_board);

                                          }
                                          players[who_is_going_to_play].specialMove[2] -= 1 ;
                                          break;
                                      } else if (arrowKey == 68 && allowedToJump[2]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1].toString().equals("   ")) {
                                          Trap spwaned_trap = Spwan_trap();
                                          Our_trap = Trap.getTrapList() ;
                                          message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1])  ;
                                          Log.writeMessage(message);
                                          GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] - 1] = spwaned_trap ;
                                      }
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] ][players[who_is_going_to_play].cur_loc[1]- 1] instanceof Player) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]) + " and damaged the PL"+ players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].playerId;
                                              Log.writeMessage(message);
                                              String message2 = "PL"+players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].playerId +" you have damaged by a " +spwaned_trap.trap +" which was spwaned by PL"+String.valueOf(players[who_is_going_to_play].playerId) + " and you've backed to your source in " + GameBoard.row_log[players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].src[0]].toString() + String.valueOf(players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].src[1] + 1);
                                              Log.writeMessage(message2);
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].HpLeft -= spwaned_trap.HpLost ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].ScoreEarned -= spwaned_trap.ScoreLost ;
                                              Our_trap.remove(spwaned_trap) ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]-1]).playerId - 1].BackToSrc(GameBoard.game_board);

                                          }
                                          players[who_is_going_to_play].specialMove[2] -= 1 ;
                                          break;
                                      } else if (arrowKey == 67 && allowedToJump[3]) {
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1].toString().equals("   ")) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2)  ;
                                              Log.writeMessage(message);
                                              GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1] + 1] = spwaned_trap ;
                                          }
                                          if (GameBoard.game_board[players[who_is_going_to_play].cur_loc[0] ][players[who_is_going_to_play].cur_loc[1]+ 1] instanceof Player) {
                                              Trap spwaned_trap = Spwan_trap();
                                              Our_trap = Trap.getTrapList() ;
                                              message = "PL"+String.valueOf(players[who_is_going_to_play].playerId)+" in " + GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+1) + " has spwaned a " +spwaned_trap.trap+" in " +GameBoard.row_log[players[who_is_going_to_play].cur_loc[0]].toString() + String.valueOf(players[who_is_going_to_play].cur_loc[1]+2) + " and damaged the PL"+ players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].playerId;
                                              Log.writeMessage(message);
                                              String message2 = "PL"+players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].playerId +" you have damaged by a " +spwaned_trap.trap +" which was spwaned by PL"+String.valueOf(players[who_is_going_to_play].playerId) + " and you've backed to your source in " + GameBoard.row_log[players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].src[0]].toString() + String.valueOf(players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].src[1] + 1);
                                              Log.writeMessage(message2);
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].HpLeft -= spwaned_trap.HpLost ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].ScoreEarned -= spwaned_trap.ScoreLost ;
                                              Our_trap.remove(spwaned_trap) ;
                                              players[((Player) GameBoard.game_board[players[who_is_going_to_play].cur_loc[0]][players[who_is_going_to_play].cur_loc[1]+1]).playerId - 1].BackToSrc(GameBoard.game_board);

                                          }
                                          players[who_is_going_to_play].specialMove[2] -= 1 ;
                                          break;
                                      }
                                  }
                                  if (input_S == 'b') {
                                      back_opt = true ;
                                  }
                              }
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                          if (!back_opt) {
                              break;
                          }
                      }
                      if (input == 'h') {
                          Log.readLinesFromFile("log"+String.valueOf(CsvFileSaveandLoad.number_of_saves)+".txt",players[who_is_going_to_play]);
                          System.out.println("[b] back");
                          while (true){
                              char choise_in_history = scanner.next().charAt(0);
                              if (choise_in_history == 'b'){
                                  break;
                              }
                          }
//                          break;
                      }
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              }

//              while (true) {
//                  char choice_player = scanner.next().charAt(0);
//                  if (choice_player == 'u' && allowedToMove[0]){
//                      move(players[who_is_going_to_play] , 'u',1);
//                      break;
//                  }
//                  if (choice_player == 'd' && allowedToMove[1]){
//                      move(players[who_is_going_to_play] , 'd',1);
//                      break;
//                  }
//                  if (choice_player == 'l' && allowedToMove[2]){
//                      move(players[who_is_going_to_play] , 'l',1);
//                      break;
//                  }
//                  if (choice_player == 'r' && allowedToMove[3]){
//                      move(players[who_is_going_to_play] , 'r',1);
//                      break;
//                  }
//
//              }

              if (players[who_is_going_to_play].ScoreEarned >= 100) {
                    andTheWinnerIs(players[who_is_going_to_play]);
                    break;
              }
              if (players[who_is_going_to_play].HpLeft <= 0) {
                  someoneHaslost(players[who_is_going_to_play]);
                  if (number_of_players == 2){
                      andTheWinnerIs(players[(who_is_going_to_play+1)%2]);
                      break;
                  }
              }
              who_is_going_to_play += 1;
              who_is_going_to_play %= number_of_players;
              CsvFileSaveandLoad.SaveGame(GameBoard.game_board , number_of_players , who_is_going_to_play ,players);

          }
      }



  }
 }
