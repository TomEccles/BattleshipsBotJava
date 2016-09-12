package BattleshipsExamplePlayer;

import java.io.*;

/**
 * Created by TEE on 11/09/2016.
 */
public class Logger {

    private Writer log;
    private int gameIndex = 0;

    public void log(String s) {
        try {
            log.write(s);
            log.write("\r\n");
        } catch (Exception e) {

        }
    }

    public final void openNext(String s) {
        gameIndex++;
        String fileName = s+ "_personalLogs_Game" + gameIndex + ".log";
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
        }
        log = new OutputStreamWriter(out);
    }

    public void close() {
        try {
            log.close();
        } catch (Exception e) {
        }
    }
}
