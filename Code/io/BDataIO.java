package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import prot.GProtD;
import display.Metrics;
import util.*;

public class BDataIO {

    File dfile;

    public BDataIO(String file) {
        dfile = new File(file);
    }

    public int writeSetGP(Set set) throws IOException {
        DataOutputStream dos
                = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dfile)));

        dos.writeUTF("Data File a=");
        dos.writeInt(Metrics.size);

        for (int i = 0; i < set.getSize(); i++) {
            GProtD gp = set.getGP(i);
            dos.writeInt(gp.table.size());
            for (int ti = 0; ti < gp.table.size(); ti++) {
                dos.writeInt(gp.table.get(2, ti));
                dos.writeInt(gp.table.get(3, ti));
            }
        }
        dos.writeInt(0);
        dos.writeInt(0);
        dos.close();
        return 0;
    }

    public IntTable readData() throws IOException {
        DataInputStream dis
                = new DataInputStream(new BufferedInputStream(new FileInputStream(dfile)));
        IntTable table = new IntTable();
        dis.readUTF();
        dis.readInt();
        int tableSize = dis.readInt();
        while (tableSize > 0) {
            table.add(tableSize, 0, 0, 0);
            for (int ti = 0; ti < tableSize; ti++) {
                int v2 = dis.readInt(), v3 = dis.readInt();
                table.add(0, v2, v3, 0);
            }
            tableSize = dis.readInt();
        }
        dis.close();
        return table;
    }

}
