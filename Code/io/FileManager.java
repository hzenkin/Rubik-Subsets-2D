package io;

import java.io.IOException;
import java.io.File;
import factory.SetFactory;
import search.PermutationSets;
import util.LP;
import util.ClockB;

public class FileManager {

    DSFiles dsf;

    public FileManager() {
        dsf = new DSFiles();
    }

    public void checkFiles() {
        SetFactory usf = new SetFactory();
        PermutationSets us = null;

        dsf.directories();

        // check Files..
        File file = new File(dsf.DataFile);

        if (file.exists() == false) {
            us = usf.buildBasicSet();
            transferDataToBinaryFile(us);
        }

        file = new File(dsf.XMLDataFile);
        if (file.exists() == false) {
            if (us == null) {
                us = usf.buildBasicSet();
            }
            transferDataToXMLFile(us);
        }
    }

    public int transferDataToXMLFile(PermutationSets us) {
        ClockB cl = new ClockB("FileManager Clock");
        XMLDataFileWriter writerA = new XMLDataFileWriter(dsf.XMLDataFile);
        XMLDataFileWriter writerB = new XMLDataFileWriter(dsf.XMLStatisticsFile);

        try {
            cl.print("Begin transfer ");
            writerB.open();
            writerB.makeDataFile(us.toStatisticsXML());
            cl.print("stats complete,");
            writerA.open();
            writerA.makeDataFile(us.toXML());
            cl.print("data transfer to file complete");
        } catch (IOException e) {
            dsf.directories();
            return -1;
        }

        return 0;
    }

    public int transferDataToBinaryFile(PermutationSets us) {
        ClockB cl = new ClockB("FileManager Clock");
        BDataIO io = new BDataIO(dsf.DataFile);
        try {
            cl.print("Begin transfer ");
            io.writeSetGP(us.completeData());
            cl.print("data transfer to binary file complete");
        } catch (IOException e) {
            dsf.directories();
            return -1;
        }

        return 0;
    }

    public PermutationSets buildRubikPermutationSet(String type) {
        ClockB cl = new ClockB("FileManager Clock");
        cl.print("Begin build Rubik Permutation Set ");
        SetFactory usf = new SetFactory();
        PermutationSets us = null;

        try {
            if (type.toLowerCase() == "xml") {
                XMLDataFileReader xr = new XMLDataFileReader(dsf.XMLDataFile);
                us = usf.buildByTableXMLFormat(xr.readDataFile());
                cl.print("usf.buildByTableXMLFormat complete");
            } else {
                BDataIO io = new BDataIO(dsf.DataFile);
                us = usf.buildByTableBinaryFormat(io.readData());
                cl.print("usf.buildByTableBinaryFormat data complete");
            }
        } catch (IOException e) {
            us = usf.buildBasicSet();
            e.printStackTrace();
        } finally {
            if (us.getSize() == 0) {
                us = usf.buildBasicSet();
            }
        }
        return us;
    }

    public PermutationSets buildRubikPermutationSetFromXMLFile() {
        ClockB cl = new ClockB("FileManager Clock");
        cl.print("Begin build Rubik Permutation Set ");
        SetFactory usf = new SetFactory();
        XMLDataFileReader xr = new XMLDataFileReader(dsf.XMLDataFile);
        PermutationSets us = null;

        try {
            us = usf.buildByTableXMLFormat(xr.readDataFile());
            cl.print("usf.buildByTable complete");
        } catch (IOException e) {
            us = usf.buildBasicSet();
            e.printStackTrace();
        } finally {
            if (us == null) {
                us = usf.buildBasicSet();
            }
        }
        return us;
    }

    public PermutationSets buildRubikPermutationSetFromBinaryFile() {
        ClockB cl = new ClockB("FileManager Clock");
        cl.print("Begin build Rubik Permutation Set (binary) ");
        SetFactory usf = new SetFactory();
        BDataIO io = new BDataIO(dsf.DataFile);
        PermutationSets us = null;

        try {
            us = usf.buildByTableBinaryFormat(io.readData());
            cl.print("usf.build by table data complete");
        } catch (IOException e) {
            us = usf.buildBasicSet();
            e.printStackTrace();
        } finally {
            if (us == null) {
                us = usf.buildBasicSet();
            }
        }
        return us;
    }

}
