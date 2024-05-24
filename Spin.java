import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spin {

    private static String spinMessage = null ;
    private static List<Spin> SpinList = new ArrayList<>();
    private String[] messagelist = {" and added one of his/her special movements" , " ans backed to his/her source " , " and make other players to bach to their source " , " and removed 3 traps from the map " , " and added 3 traps to the map "} ;

    public static String chanceMessage = null ;
    private boolean isBreakable = false ;

    public Spin (){
        this.isBreakable = false ;
        SpinList.add(this);
    }

    public void randomSpin (Player[] players , int turn , List<Trap> traplist ){
        Random random = new Random();
        int randomIndex = random.nextInt(5) + 1;
        System.out.println(randomIndex);
        if (randomIndex == 1){
            int random_ability = random.nextInt(3);
            players[turn].specialMove[random_ability] += 1 ;
            chanceMessage = messagelist[0];

        }
        else if (randomIndex == 2){
            players[turn].BackToSrc(GameBoard.game_board);
            chanceMessage = messagelist[1];
        }
        else if (randomIndex == 3){
//            for (Player player : players){
//                if (player != players[turn]) {
//                    player.BackToSrc(GameBoard.game_board);
//                }
//            }
            if (Main.number_of_players == 2) {
                players[(turn+1)%2].BackToSrc(GameBoard.game_board);
            }
            chanceMessage = " and make other players to bach to their source ";
        }
        else if (randomIndex == 4){
            for (int i =0 ; i < 3 ; i++) {
                int randomtrap = random.nextInt(traplist.size());
                traplist.remove(randomtrap) ;
            }
            chanceMessage = messagelist[3];
        }
        else if (randomIndex == 5){
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
            chanceMessage = messagelist[4];
        }
    }

    public static List<Spin> getSpinList(){
        return  SpinList ;
    }

    public String toString () {
        return  "\u001B[34m" + "SPN" + "\u001B[0m" ;
    }


}
