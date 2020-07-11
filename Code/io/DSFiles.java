package io;

import java.io.File;
import display.Metrics;
import util.LP;

public class DSFiles {

    public String DataFile, XMLDataFile, XMLStatisticsFile;

    public DSFiles() {
        String str = "020" + Metrics.size;
        String ax = str + ".xml";

        XMLDataFile = "./data/xdf" + ax;
        XMLStatisticsFile = "./data/xsf" + ax;
        DataFile = "./data/bdf" + str + ".dat";
    }

    public void directories() {
        setDirectory("./data");
    }

    private void setDirectory(String str) {
        File directory = new File(str);
        if ((directory.exists() == false) && (directory.mkdirs())) {
            LP.println("Directories = " + str);
        }
    }

}
