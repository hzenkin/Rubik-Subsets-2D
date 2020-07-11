package solvers;

import prot.GProtD;
import prot.ProtD;
import search.PermutationSets;
import engine.Operator;
import util.ClockB;
import comm.ComArray;
import comm.CommandSequencer;
import factory.GProtDFactory;
import io.FileManager;
import util.Set;

public class Cx00 implements Solver {

    int config;
    public PermutationSets us;
    CommandSequencer sequencer;
    float timeout = 25;
    int outerRimIndex;

    public Cx00(int conf) {
        init();
        config = conf;
    }

    @Override
    public void setTimeLimit(float timeout) {
        this.timeout = timeout;
    }

    public void setOuterRimIndex(int ORIndex) {
        outerRimIndex = ORIndex;
    }

    public ComArray searchUniversalSet(ProtD protd) {

        GProtD gp = us.search(protd);
        ComArray com = null;

        if (gp != null) {
            com = gp.getSolvers().getCA(0);
            return com;
        }
        return null;
    }

    @Override
    public void setCommandSequencer(CommandSequencer sequencer) {
        this.sequencer = sequencer;
    }

    @Override
    public Set search(ProtD begin, ProtD end) {
        GProtD gp = Complete(begin, end);
        if (gp != null) {
            return gp.getSolvers();
        }
        return new Set();
    }

    @Override
    public ComArray solve(ProtD begin) {
        ClockB clock = new ClockB();

        ComArray com = searchUniversalSet(begin);

        if (com == null) {
            Set sol = search(begin, new ProtD());

            if (sol.getSize() > 0) {
                com = sol.getCA(0);
                clock.print("Configuration solved by Cx00;");
            } else {
                clock.print("Unable to solve configuration");
            }
        }
        return com;
    }

    @Override
    public GProtD Complete(ProtD begin, ProtD end) {
        if (end.compare(us.getMain())) {
            return CompleteCont(new GProtD(begin));
        }
        return null;
    }

///////////////////////////////////////////////////////////////////////////////
//		Private Methods
//////////////////////////////////////////////    
    private GProtD CompleteCont(GProtD begin) {
        if (us.search(begin) != null) {
            return us.search(begin);
        }
        ClockB clock = new ClockB("Cx00 Clock");
        clock.setAlarm(timeout);

        GProtDFactory factory = new GProtDFactory(us.getMain());
        CASetPrControl progress = new CASetPrControl(config);
        Set outerRim = us.getSet(outerRimIndex);
        Operator op = new Operator();
        Set solvers = new Set();
        Set reachers = new Set();

        while (sequencer.isAlive() && clock.alarmTimeNotPassed()) {
            Set coms = sequencer.commands(1);
            for (int i = 0; i < coms.getSize(); i++) {
                ComArray ca = coms.getCA(i);

                //solvers ////////////////////////////////////////				
                ProtD tmp = new ProtD(begin);
                op.execute(tmp, ca);
                if (outerRim.isElement(tmp)) {
                    GProtD gp = outerRim.getGP(outerRim.findElement(tmp));
                    solvers.add(append(ca, gp.getSolvers()));
                    sequencer.resetComLength(ca.getSize());
                }

                //reachers ///////////////////////////////////////////
                for (int k = 0; k < outerRim.getSize(); k++) {
                    ProtD px = new ProtD(outerRim.getGP(k));
                    op.execute(px, ca);
                    if (begin.compare(px)) {
                        reachers.add(append(outerRim.getGP(k).getReachers(), ca));
                    }
                }
            }

            if (progress.conditionsMet(reachers, sequencer)
                    && progress.conditionsMet(solvers, sequencer)) {
                reachers = progress.trim(reachers);
                solvers = progress.trim(solvers);
                GProtD res = factory.makeGProt(reachers, solvers);
                return res;
            }
        }
        clock.print("Search unsuccessful");
        return null;
    }

    private void init() {
        FileManager fm = new FileManager();
        us = fm.buildRubikPermutationSetFromBinaryFile();
        outerRimIndex = 0;

        if (2 < us.getSize()) {
            outerRimIndex = us.getSize() - 2;
        }
    }

    private Set append(ComArray ca, Set solvers) {
        Set set = new Set();

        for (int i = 0; i < solvers.getSize(); i++) {
            ComArray com = new ComArray();
            com.add(ca);
            com.add(solvers.getCA(i));
            set.add(com);
        }
        return set;
    }

    private Set append(Set reachers, ComArray ca) {
        Set set = new Set();

        for (int i = 0; i < reachers.getSize(); i++) {
            ComArray com = new ComArray();
            com.add(reachers.getCA(i));
            com.add(ca);
            set.add(com);
        }
        return set;
    }

}
