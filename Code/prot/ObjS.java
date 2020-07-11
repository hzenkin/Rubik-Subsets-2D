package prot;

import display.Metrics;
import util.LinS;

public class ObjS {

    public int cls[], comparisonMap[];
    int dimension;

    public ObjS() {
        dimension = Metrics.size;
        cls = new int[dimension * dimension];
        comparisonMap = new int[dimension * dimension];
    }

    public ObjS(int d) {
        dimension = d;
        cls = new int[d * d];
        comparisonMap = new int[dimension * dimension];
    }

    public ObjS(ObjS cp) {
        dimension = cp.dimension;
        cls = new int[dimension * dimension];
        comparisonMap = new int[dimension * dimension];
        setSurface(cp.cls);
    }

    public String toString() {
        String str = "[";
        LinS i = new LinS(dimension, dimension);
        i.XYIteration();
        while (i.hasNext()) {
            str += cls[i.iter] + "";
            i.next();
            if (i.hasNext()) {
                str += ", ";
            }
        }

        str += "]";
        return str;
    }

    public void resetSurface(int c) {
        LinS i = new LinS(dimension, dimension);
        i.XYIteration();
        while (i.hasNext()) {
            cls[i.iter] = c;
            i.next();
        }
    }

    public void setSurface(int c[]) {
        LinS i = new LinS(dimension, dimension);
        i.XYIteration();
        while (i.hasNext()) {
            cls[i.iter] = c[i.iter];
            i.next();
        }
    }

    public boolean compare(ObjS m) {
        if (dimension != m.dimension) {
            return false;
        }

        LinS i = new LinS(dimension, dimension);
        i.XYIteration();
        while (i.hasNext()) {
            if (comparisonMap[i.iter] == ProtConstants.DO_COMPARE) {
                if (cls[i.iter] != m.cls[i.iter]) {
                    return false;
                }
            }
            i.next();
        }

        return true;
    }

}
