package Raycaster.Display.UI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MakeSound {


        private File soundFile;
        private AudioInputStream audioStream;
        private SourceDataLine sourceLine;

        private ArrayList<String> listPlayingSounds = new ArrayList<String>();

        public boolean isPlaying(String fileName){
            while (true) {
                try {
                    return listPlayingSounds.contains(fileName);
                } catch (ConcurrentModificationException ignore) {

                }
            }
        }

        public void playSound(String filename){

            Thread music = new Thread(()->{
                while (true) {
                    try {
                        listPlayingSounds.add(filename);
                        break;
                    } catch (ConcurrentModificationException ignore) {

                    }
                }


                playInsideSound(filename);

                while (true) {
                    try {
                        listPlayingSounds.remove(filename);
                        break;
                    } catch (ConcurrentModificationException ignore) {

                    }
                }

            });

            music.start();
        }

    /**
     * @param filename the name of the file that is going to be played
     */

        private void playInsideSound(String filename){

            // https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java

            try {
                soundFile = new File(filename);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            try {
                audioStream = AudioSystem.getAudioInputStream(soundFile);
            } catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }

            AudioFormat audioFormat = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            try {
                sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceLine.open(audioFormat);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            sourceLine.start();

            int nBytesRead = 0;
            int BUFFER_SIZE = 128000;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    @SuppressWarnings("unused")
                    int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                }
            }

            sourceLine.drain();
            sourceLine.close();
        }

}
