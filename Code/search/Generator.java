package search;

import prot.GProtD;
import prot.ProtD;
import solvers.Cx00;
import solvers.Scout;
import solvers.Solver;
import engine.Operator;
import util.ClockB;
import util.Set;
import comm.ComArray;
import comm.CommandSequencer;
import util.LP;

public class Generator {

    public enum Type {
        StartUp, Expansions
    }

    ProtD target = new ProtD();
    Type generator;
    public Set totalSet;
    int config, mark, maxComLength;
    Solver method;
    CommandSequencer cs;

    public Generator(Type type) {
        generator = type;
        init();
    }

    public void setMaxComLength(int maxComLength) {
        this.maxComLength = maxComLength;
    }

    public void setStart(ProtD initalPos) {
        target = new ProtD(initalPos);
    }

    public boolean generate(int ct) {
        Set comset = cs.commands(ct);
        Operator op = new Operator();
        int size = totalSet.getSize();

        for (int n = 0; n < comset.getSize(); n++) {
            GProtD tmp = new GProtD(target);
            ComArray ca = comset.getCA(n);
            op.execute(tmp, ca);

            if (totalSet.add(tmp) == 1) {
                LP.println("New probability:" + totalSet.getSize() + ", com#"
                        + CommandSequencer.getCommand(ca) + "  " + ca.toString());
                int last = totalSet.getSize() - 1;
                method.setCommandSequencer(new CommandSequencer());
                tmp = method.Complete(tmp, target);
                if (tmp == null) {
                    LP.println("Solver returned null,");
                    System.exit(0);
                }
                tmp.setID(last);
                totalSet.elements[last] = tmp;

                if (ca.getSize() == maxComLength) {
                    break;
                }
            }
        }
        return size == totalSet.getSize();
    }

    public boolean generate(int ct, double timeOut) {
        ClockB clock = new ClockB("Generator Clock");
        clock.setAlarm((float) timeOut);
        clock.print("Begin generator");
        boolean res = true;
        while (clock.alarmTimeNotPassed()) {
            res = res && generate(ct);
        }
        clock.print("End generator");
        return res;
    }

/////////////////////////////////////////////////////////////////////////////////////
////Private Methods	
/////////////////////////////
    private void init() {
        config = ResultTypes.MINIMUM_LENGTH_RESULTS;
        setMaxComLength(5);
        totalSet = new Set();
        target = new ProtD();

        switch (generator) {
            case StartUp:
                method = new Scout(config);
                cs = new CommandSequencer();
                break;

            case Expansions:
                method = new Cx00(config);
                totalSet = ((Cx00) method).us.completeData();
                cs = new CommandSequencer(totalSet.getGP(totalSet.lastIndex()).table.get(2, 0));
                mark = totalSet.getSize();
        }

    }

}
