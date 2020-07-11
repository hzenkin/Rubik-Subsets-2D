package solvers;

import comm.ComArray;
import comm.CommandSequencer;
import comm.CommandSetMaker;
import prot.ProtConstants;
import prot.ProtD;
import display.Metrics;
import engine.Operator;
import search.ResultTypes;
import util.ClockB;
import util.Container;
import util.IntArray;
import util.LinS;
import util.Set;

public class Algorithm4N {

    Scout scout;
    CommandSequencer cs;

    public Algorithm4N() {
        scout = new Scout(ResultTypes.SINGLE_RESULT);
    }

    public Set moveToMidSquare4(ProtD start) {
        scout.setCommandSequencer(new CommandSequencer());
        return scout.search(start, getMidSquare());
    }

    public Set moveToMidSquare4PlusCorners(ProtD start) {
        scout.setCommandSequencer(new CommandSequencer());
        return scout.search(start, getCornersAndMidSquare());
    }

    public Set fpx(ProtD start, int sc) {
        scout.setCommandSequencer(new CommandSequencer(0, 4000));
        Set set = scout.search(start, getCMX(sc));

        if (set.getSize() == 1) {
            return set;
        }

        /////////////////////////////////////////////////////
        CommandSequencer cseq = new CommandSequencer();
        CommandSetMaker csm = new CommandSetMaker();

        set.add(new ComArray(3259979));
        set.add(new ComArray(3421019));
        set.add(new ComArray(5179148));
        set.add(new ComArray(5018108));

        set.add(new ComArray(3258648));
        set.add(new ComArray(6789668));
        set.add(new ComArray(6950708));
        set.add(new ComArray(3419688));

        set.add(new ComArray(13520));
        set.add(new ComArray(12189));
        set.add(new ComArray(13487));
        set.add(new ComArray(12156));

        csm.addCommandSet(set);
        cseq.setAllCommands(csm.getCommandSet());

        scout.setCommandSequencer(cseq);
        return scout.search(start, getCMX(sc));
    }

    public Set solveSize4(ProtD start) {
        ClockB cl = new ClockB("Transport.solveSize4:Clock");
        Container cont = new Container();
        ComArray com = new ComArray();
        ProtD px = new ProtD(start);

        f1(moveToMidSquare4(px), px, com);
        f1(moveToMidSquare4PlusCorners(px), px, com);

        cont.add(com);
        com = new ComArray();

        for (int i = 2; i < 9; i += 2) {
            f1(fpx(px, i), px, com);
            if (i == 4 || i == 8 || i == 2 || i == 6) {
                cont.add(com);
                com = new ComArray();
            }
        }
        Set set = new Set();
        set.add(cont, false);
        return set;
    }

////////////////////////////////////////////////////////////////////////////////
//	Private Methods
//////////////////////////////////
    private boolean f1(Set set, ProtD p, ComArray com) {
        Operator op = new Operator();

        if (set.getSize() > 0) {
            com.add(set.getCA(0));
            op.execute(p, set.getCA(0));
            return true;
        }
        return false;
    }

    private ProtD getMidSquare() {
        ProtD p = new ProtD();
        p.nullifyComparisonMap();
        LinS lins = Metrics.getLinS();
        for (int y = 1; y < 3; y++) {
            for (int x = 1; x < 3; x++) {
                p.sf[0].comparisonMap[lins.Lindex(x, y)] = ProtConstants.DO_COMPARE;
            }
        }
        return p;
    }

    private ProtD getCornersAndMidSquare() {
        ProtD p = getMidSquare();
        LinS lins = Metrics.getLinS();
        int mx = Metrics.size - 1;
        p.sf[0].comparisonMap[lins.Lindex(0, 0)] = ProtConstants.DO_COMPARE;
        p.sf[0].comparisonMap[lins.Lindex(0, mx)] = ProtConstants.DO_COMPARE;
        p.sf[0].comparisonMap[lins.Lindex(mx, 0)] = ProtConstants.DO_COMPARE;
        p.sf[0].comparisonMap[lins.Lindex(mx, mx)] = ProtConstants.DO_COMPARE;
        return p;
    }

    private ProtD getCMX(int x) {
        ProtD p = getCornersAndMidSquare();
        IntArray ary = getMidSides();
        for (int i = 0; i < x; i++) {
            p.sf[0].comparisonMap[ary.get(i)] = ProtConstants.DO_COMPARE;
        }

        return p;
    }

    private IntArray getMidSides() {
        IntArray ary = new IntArray();
        LinS lins = Metrics.getLinS();

        ary.add(lins.Lindex(1, 0));
        ary.add(lins.Lindex(1, 3));
        ary.add(lins.Lindex(2, 0));
        ary.add(lins.Lindex(2, 3));

        ary.add(lins.Lindex(3, 1));
        ary.add(lins.Lindex(0, 1));

        ary.add(lins.Lindex(0, 2));
        ary.add(lins.Lindex(3, 2));

        return ary;
    }
}
