package factory;

import prot.GProtD;
import prot.ProtD;
import engine.Operator;
import util.IntArray;
import util.Set;
import comm.ComArray;
import comm.CommandSequencer;

public class GProtDFactory {

    GProtD begin;

    public GProtDFactory() {
        begin = new GProtD();
    }

    public GProtDFactory(ProtD start) {
        begin = new GProtD(start);
    }

    public GProtD makeGProt(IntArray reachers, IntArray solvers) {
        if (reachers.getSize() == 0) {
            return null;
        }

        GProtD gp = new GProtD(begin);
        (new Operator()).execute(gp, new ComArray(reachers.get(0)));

        int max = Math.max(reachers.getSize(), solvers.getSize());
        for (int i = 0; i < max; i++) {
            int rl = 0, rea = -1, sol = -1, sl = 0;

            if (i < reachers.getSize()) {
                rea = reachers.get(i);
                rl = new ComArray(rea).getSize();
            }
            if (i < solvers.getSize()) {
                sol = solvers.get(i);
                sl = new ComArray(sol).getSize();
            }
            gp.table.add(rl, rea, sol, sl);
        }

        return gp;
    }

    public GProtD makeGProt(Set reachers, Set solvers) {
        IntArray rary = new IntArray(), sary = new IntArray();
        int size = Math.max(reachers.getSize(), solvers.getSize());

        for (int i = 0; i < size; i++) {
            if (reachers.getSize() > i) {
                rary.add(CommandSequencer.getCommand(reachers.getCA(i)));
            }
            if (solvers.getSize() > i) {
                sary.add(CommandSequencer.getCommand(solvers.getCA(i)));
            }
        }

        rary.sort();
        sary.sort();

        return makeGProt(rary, sary);
    }

}
