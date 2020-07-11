package io;

import java.io.IOException;
import display.Metrics;

public class XMLDataFileWriter extends LogFile {

    private static String ch01 = "\"";

    public XMLDataFileWriter(String file) {
        super(file);
    }

    public void makeDataFile(String sets) throws IOException {
        makeFile(sets);
    }

//////////////////////////////////////////////////////////////////
////	Private Methods
/////////////////////////
    private void makeFile(String body) throws IOException {
        writeLogLn(getVersion());
        writeLogLn("");
        writeLogLn(getRootTag());
        writeLogLn(body);
        writeLog("</RubikPermutations>");

        bw.close();
    }

    private String getVersion() {
        String str = "<?xml version=";
        str += ch01 + "1.0" + ch01 + " encoding=";
        str += ch01 + "UTF-8" + ch01 + "?>";

        return str;
    }

    private String getRootTag() {
        String co = ch01;
        String str = "<RubikPermutations id=" + co + Metrics.size + co + ">";

        return str;
    }

}
