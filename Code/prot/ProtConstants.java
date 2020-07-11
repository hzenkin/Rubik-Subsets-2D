package prot;

import display.Metrics;
import util.LinS;

public class ProtConstants {
////////////////////////////////////////////////////////////////////////////////
//	Statics Constants

    static final public int CONFIG_SINGLE = 101,
            CONFIG_PARRALEL = 102,
            CONFIG_STANDARD = 106,
            ////////////////////////////////////////////////////////////////////
            DO_COMPARE = 200,
            DO_NOT_COMPARE = 201;
////////////////////////////////////////////////////////////////////////////////

    static public ProtD makeProtType1() {
        ProtD p = new ProtD();
        LinS lins = Metrics.getLinS();
        p.sf[0].cls[lins.Lindex(1, 0)] = 2;
        p.sf[1].cls[lins.Lindex(1, Metrics.size - 1)] = 1;

        return p;
    }

    static public ProtD makeProtType2() {
        ProtD p = new ProtD();
        LinS lins = Metrics.getLinS();
        p.sf[0].cls[lins.Lindex(2, 0)] = 2;
        p.sf[1].cls[lins.Lindex(2, Metrics.size - 1)] = 1;

        return p;
    }

    static public ProtD makeProtType12() {
        ProtD p = new ProtD();
        LinS lins = Metrics.getLinS();
        p.sf[0].cls[lins.Lindex(2, 0)] = 2;
        p.sf[1].cls[lins.Lindex(2, Metrics.size - 1)] = 1;
        p.sf[0].cls[lins.Lindex(1, 0)] = 2;
        p.sf[1].cls[lins.Lindex(1, Metrics.size - 1)] = 1;

        return p;
    }

}
