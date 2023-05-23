import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

    public class Music {
        private Clip bgm;
        private Clip fail;
        private Clip eat;

        public Music() {
            try {
                // initial bgm music
                bgm = AudioSystem.getClip();
                URL url = Music.class.getResource("/sound/bgm.wav");
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                bgm.open(ais);

                // initial dead music
                fail = AudioSystem.getClip();
                url = Music.class.getResource("/sound/fail.wav");
                ais = AudioSystem.getAudioInputStream(url);
                fail.open(ais);

                // initial eat music
                eat = AudioSystem.getClip();
                url = Music.class.getResource("/sound/eat.wav");
                ais = AudioSystem.getAudioInputStream(url);
                eat.open(ais);

            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // playing bgm
        public void playBgm() {
            bgm.loop(Clip.LOOP_CONTINUOUSLY); // loop - repeat endlessly
        }

        // stop bgm play dead
        public void playDead() {
            // play dead music
            fail.loop(1); // only repeat once
            bgm.stop();
        }

        // play eat music
        public void playEat() {
            // play eat
            eat.loop(1);
        }
    }


