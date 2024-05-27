import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Music {

    private static long len;

    public static void Play_misc() {
        try {
            File soundFile = new File("/home/sepehr_sobhdoost/Desktop/Ap projects/2/Treasure hunt/src/gary_moore_-_spanish_guitar.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            len = clip.getMicrosecondLength() / 1000;
            Thread.sleep(len);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AtomicBoolean stopMusic = new AtomicBoolean(false);

        // Create a daemon thread to play music continuously
        Thread musicThread = new Thread(() -> {
            while (!stopMusic.get()) {
                Play_misc();
            }
        });
        musicThread.setDaemon(true);
        musicThread.start();

        System.out.println("Enter 'stop' to stop the music and exit the program.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("stop")) {
                stopMusic.set(true);
                break;
            }

            // Additional program logic
            System.out.println("You entered: " + input);
        }
    }
}