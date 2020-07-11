package engine;

import comm.ComArray;
import comm.OperatorCommand;
import display.Metrics;
import prot.ObjS;
import prot.ProtD;
import util.*;

public class Operator {

    private ProtD prot;

    public Operator() {
        prot = null;
    }

    public Container execute(ProtD start, ComArray coms) {
        Container list = new Container();
        prot = start;
        list.add(new ProtD(start));
        for (int i = 0; i < coms.getSize(); i++) {
            if (single(new OperatorCommand(coms.get(i))) == 1) {
                ProtD tmp = new ProtD(prot);
                list.add(tmp);
            } else {
                System.out.println("Invalid command " + coms.get(i));
            }
        }
        prot = null;
        return list;
    }

/////////////////////////////////////////////////////////////////////////////////////////////
//		Private Methods
/////////////////////////////////////////////////////////////////////////////////////////////	
    private int single(int op, int ln) {
        switch (op) {
            case 1000000:
                return 1;
            case 1001000:
                operX(ln - 1);
                return 1;
            case 1002000:
                operY(ln - 1);
                return 1;
            case 1003000:
                operZ(ln - 1);
                return 1;
        }
        return -1;
    }

/////////////////////////////////////////////////////////////////////////////////////////////
    private int single(OperatorCommand oc) {
        int command = oc.getCommand();
        int ret = single(oc.getCommand(), oc.getAxis());
        if (command != 1003000) {
            single(oc.getCommand(), oc.getAxis());
        }
        return ret;
    }

/////////////////////////////////////////////////////////////////////////////////////////////
    private void operX(int ln) {
        int size = Metrics.size;
        int maxs = size * size;

////////////////////////////////////////////////////////////////////////////////////////////
//wheeler section
        //  store top in tmp 
        ObjS tmp = new ObjS(prot.sf[0]);

        //  move 3 -> 0 
        for (int i = 0; i < size; i++) {
            prot.sf[0].cls[ln + i * size] = prot.sf[3].cls[ln + i * size]; // same index!
        }
        //  move 1 -> 3 
        for (int i = 0; i < size; i++) {
            prot.sf[3].cls[ln + i * size] = prot.sf[1].cls[maxs - size - i * size + ln]; // reverse index!
        }
        //  move 5 -> 1
        for (int i = 0; i < size; i++) {
            prot.sf[1].cls[ln + i * size] = prot.sf[5].cls[ln + i * size]; // same index!
        }
        //  move 0 -> 5 
        for (int i = 0; i < size; i++) {
            prot.sf[5].cls[ln + i * size] = tmp.cls[maxs - size - i * size + ln]; // reverse index!
        }//////////////////////////////////////////////////////////////////////////////////////////////////////

        // side surfaces 2,4
        if (ln == 0) {
            rotator(prot.sf[2], true);
        } else if (ln == size - 1) {
            rotator(prot.sf[4], true);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
    private void operY(int ln) {
        int size = Metrics.size;
        int maxs = size * size;

//////////////////////////////////////////////////////////////////////////////////////////////////////
        // side surfaces 3,5
        if (ln == 0) {
            rotator(prot.sf[3], true);
        } else if (ln == size - 1) {
            rotator(prot.sf[5], true);
        }

////////////////////////////////////////////////////////////////////////////////////////////
//wheeler section
        //  store top in tmp 
        ObjS tmp = new ObjS(prot.sf[0]);

        //  move 2 -> 0 
        for (int i = 0; i < size; i++) {
            prot.sf[0].cls[maxs - 1 - i - ln * size] = prot.sf[2].cls[i * size + ln];
        }

        //  move 1 -> 2
        for (int i = 0; i < size; i++) {
            prot.sf[2].cls[i * size + ln] = prot.sf[1].cls[i - ln * size + maxs - size]; //maxs-1-i-ln*size
        }
        //  move 4 -> 1 
        for (int i = 0; i < size; i++) {
            prot.sf[1].cls[maxs - 1 - i - ln * size] = prot.sf[4].cls[i * size + ln];
        }

        //  move 0 -> 4 
        for (int i = 0; i < size; i++) {
            prot.sf[4].cls[i * size + ln] = tmp.cls[maxs - size - ln * size + i];
        }

    }

/////////////////////////////////////////////////////////////////////////////////////////////		
    private void operZ(int ln) {
        int size = Metrics.size;
        int maxs = size * size;

//////////////////////////////////////////////////////////////////////////////////////////////////////
        // side surfaces 0,1
        if (ln == 0) {
            rotator(prot.sf[1], true);
        } else if (ln == size - 1) {
            rotator(prot.sf[0], true);
        }

////////////////////////////////////////////////////////////////////////////////////////////
//wheeler section
        //  store top in tmp 
        ObjS tmp = new ObjS(prot.sf[3]);

        //  move 4 -> 3 
        for (int i = 0; i < size; i++) {
            prot.sf[3].cls[i - ln * size + maxs - size] = prot.sf[4].cls[i - ln * size + maxs - size];
        }

        //  move 5 -> 4 
        for (int i = 0; i < size; i++) {
            prot.sf[4].cls[i - ln * size + maxs - size] = prot.sf[5].cls[maxs - 1 - i - ln * size];
        }

        //  move 2 -> 5 
        for (int i = 0; i < size; i++) {
            prot.sf[5].cls[i - ln * size + maxs - size] = prot.sf[2].cls[i - ln * size + maxs - size];
        }

        //  move 3 -> 2
        for (int i = 0; i < size; i++) {
            prot.sf[2].cls[i - ln * size + maxs - size] = tmp.cls[maxs - 1 - i - ln * size];
        }

    }

/////////////////////////////////////////////////////////////////////////////////////////////
    private void rotator(ObjS os, boolean clockwise) {
        ObjS cpy = new ObjS(os);

        LinS lins = Metrics.getLinS();
        while (lins.hasNext()) {
            os.cls[lins.Lindex(Metrics.size - 1 - lins.curr().y, lins.curr().x)] = cpy.cls[lins.iter];
            lins.next();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////		
}	// class

/////////////////////////////////////////////////////////////////////////////////////////////		
