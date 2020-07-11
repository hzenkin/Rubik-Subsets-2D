package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import util.LP;

public class LogFile {

    File logf = new File("./log.txt");
    public FileWriter filewriter;
    BufferedWriter bw;

    public LogFile() {
    }

    public LogFile(String file) {
        logf = new File(file);
    }

    protected void initFileWriter() throws IOException {
        filewriter = new FileWriter(logf);
        bw = new BufferedWriter(filewriter);
    }

    public void open() throws IOException {
        initFileWriter();
    }

    public void writeLog(String logstr) throws IOException {
        bw.write(logstr);
    }

    public void writeLogLn(String logstr) throws IOException {
        bw.write(logstr + LP.newln);
    }

    public void close() throws IOException {
        bw.write(LP.newln + "__________________________________________________"
                + LP.newln + "End of log... :");
        bw.close();
    }

    public static void Logging(String lgs) {
        LogFile lg = new LogFile();
        try {
            lg.open();
            lg.filewriter.write(lgs);
            lg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
