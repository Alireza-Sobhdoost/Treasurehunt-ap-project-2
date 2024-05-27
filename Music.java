import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
public class Music {

    protected boolean exit = false;
    private static long len ;

    public void stop()
    {
        exit = true;
    }
    public static void Play_misc () {
//        the code's idea is started with my dear friend Ali Bozorg !
        try {
            File soundFile = new File("/home/sepehr_sobhdoost/Desktop/Ap projects/2/Treasure hunt/src/gary_moore_-_spanish_guitar.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            // Wait for the sound to finish playing
            len = clip.getMicrosecondLength() / 1000 ;
            Thread.sleep(len);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean stopMusic = new AtomicBoolean(false);

        // Create a new thread to play music continuously
        Thread musicThread = new Thread(() -> {
            while (!stopMusic.get()) {
                Music.Play_misc();
            }
        });
        musicThread.start();

        System.out.println("Enter 'stop' to stop the music and exit the program.");
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("stop")) {
                stopMusic.set(true);
                len=0;
                musicThread.stop();

                break;
            }

            // Additional program logic
            System.out.println("You entered: " + input);
        }
    }
}


