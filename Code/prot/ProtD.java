package prot;

import display.Metrics;
import util.cmp;
import util.LinS;

public class ProtD implements cmp {

    public ObjS sf[];

    public ProtD() {
        init();
    }

    public ProtD(int configuration) {
        init();
    }

    public ProtD(ProtD p) {
        init(p);
    }

    public ObjS getSurface(int n) {
        return sf[n];
    }

    public String toString() {
        String s = "ProtD = {";
        for (int i = 0; i < Metrics.surfaceCount; i++) {
            s += sf[i].toString();
        }
        s += "}";

        return s;
    }

    @Override
    public boolean compare(cmp arg) {
        ProtD p = (ProtD) arg;
        for (int i = 0; i < Metrics.surfaceCount; i++) {
            if (sf[i].compare(p.sf[i]) == false) {
                return false;
            }
        }
        return true;
    }

    public void nullifyComparisonMap() {
        for (int surf = 0; surf < Metrics.surfaceCount; surf++) {
            LinS lx = Metrics.getLinS();
            lx.XYIteration();
            while (lx.hasNext()) {
                sf[surf].comparisonMap[lx.iter] = ProtConstants.DO_NOT_COMPARE;
                lx.next();
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////
//	Private Methods
////////////////////////////////////
    private void init() {
        sf = new ObjS[6];

        for (int i = 0; i < 6; i++) {
            sf[i] = new ObjS();
            LinS lins = Metrics.getLinS();
            lins.XYIteration();
            while (lins.hasNext()) {
                sf[i].comparisonMap[lins.iter] = ProtConstants.DO_COMPARE;
                sf[i].cls[lins.iter] = i + 1;
                lins.next();
            }
        }
    }

    private void init(ProtD p) {
        sf = new ObjS[6];

        for (int i = 0; i < 6; i++) {
            sf[i] = new ObjS();
            LinS lins = Metrics.getLinS();
            lins.XYIteration();
            while (lins.hasNext()) {
                sf[i].comparisonMap[lins.iter] = p.sf[i].comparisonMap[lins.iter];
                sf[i].cls[lins.iter] = p.sf[i].cls[lins.iter];
                lins.next();
            }
        }
    }
}
