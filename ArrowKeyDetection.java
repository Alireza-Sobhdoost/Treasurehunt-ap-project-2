 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;

 public class ArrowKeyDetection {

     public static void main(String[] args) {
         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

         System.out.println("Use arrow keys to move (Press 'q' to quit):");

         try {
             while (true) {
                 int input = System.in.read();

                 if (input == 27) { // Escape sequence for arrow keys
                     System.in.read(); // Consume [
                     switch (System.in.read()) {
                         case 65:
                             System.out.println("Up key pressed");
                             // Add your code for up key action here
                             break;
                         case 66:
                             System.out.println("Down key pressed");
                             // Add your code for down key action here
                             break;
                         case 68:
                             System.out.println("Left key pressed");
                             // Add your code for left key action here
                             break;
                         case 67:
                             System.out.println("Right key pressed");
                             // Add your code for right key action here
                             break;
                     }
                 }

                 if (input == 'q') {
                     System.out.println("Exiting program...");
                     break;
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }

//         try {
//             while (true) {
//                 int input = System.in.read();
//
//                 if (input == 27) { // Escape sequence for arrow keys
//                     System.in.read(); // Consume [
//                     switch (System.in.read()) {
//                         case 65:
//                             System.out.println(allowedToMove[0]);
//                             if (allowedToMove[0]){
//                                 move(players[who_is_going_to_play] , 'u',1);
//                                 break;
//                             }
//                         case 66:
//                             System.out.println("Down key pressed");
//                             // Add your code for down key action here
//                             break;
//                         case 68:
//                             System.out.println("Left key pressed");
//                             // Add your code for left key action here
//                             break;
//                         case 67:
//                             System.out.println("Right key pressed");
//                             // Add your code for right key action here
//                             break;
//                     }
//                 }
//
//                 if (input == 'd') {
//                     System.out.println("Exiting program...");
//                     break;
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
     }
 }