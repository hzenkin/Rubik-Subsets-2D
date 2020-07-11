package solvers;

import comm.ComArray;
import comm.CommandSequencer;
import factory.GProtDFactory;
import prot.GProtD;
import prot.ProtD;
import search.ResultTypes;
import engine.Operator;
import util.ClockB;
import util.Set;

public class Scout implements Solver {

    private int config;
    CommandSequencer sequencer;
    public int resultCount = 5;
    float timeout = 25;
    ClockB clock;

    public Scout(int conf) {
        config = conf;
    }

    @Override
    public void setCommandSequencer(CommandSequencer sequencer) {
        this.sequencer = sequencer;
    }

    @Override
    public ComArray solve(ProtD begin) {
        int tmp = config;
        config = ResultTypes.SINGLE_RESULT;
        Set res = search(begin, new ProtD());
        config = tmp;

        return res.getCA(0);
    }

    public Set search(ProtD start) {
        ProtD b = new ProtD();
        return search(start, b);
    }

    @Override
    public Set search(ProtD begin, ProtD end) {
        clock = new ClockB("searchClock");
        clock.setAlarm(timeout);
        Set result = new Set();
        CASetPrControl progress = new CASetPrControl(config, resultCount);

        while (sequencer.isAlive() && clock.alarmTimeNotPassed()) {
            Set coms = sequencer.commands(400);
            if (coms.isEmpty() == false) {
                result.add(matcher(begin, coms, end));
            }
            if (progress.conditionsMet(result, sequencer)) {
                return progress.trim(result);
            }
        }
        return result;
    }

    @Override
    public GProtD Complete(ProtD begin, ProtD end) {
        GProtDFactory factory = new GProtDFactory(end);
        CommandSequencer secondSequencer = new CommandSequencer(sequencer);
        Set sol = search(begin, end);
        sequencer = new CommandSequencer(secondSequencer);
        Set rea = search(end, begin);

        return factory.makeGProt(rea, sol);
    }

    public Set matcher(ProtD start, Set set, ProtD end) {
        Set resultSet = new Set();
        for (int index = 0; index < set.getSize(); index++) {
            ComArray ca = set.getCA(index);
            if (match(start, ca, end)) {
                resultSet.add(ca);
            }
        }
        return resultSet;
    }

    private boolean match(ProtD start, ComArray ca, ProtD end) {
        ProtD tmp = new ProtD(start);
        Operator op = new Operator();
        op.execute(tmp, ca);
        return (end.compare(tmp));
    }

    public static Set Solver(ProtD prot) {
        Set set = new Set();
        Scout scout = new Scout(ResultTypes.SINGLE_RESULT);
        scout.setCommandSequencer(new CommandSequencer());
        ComArray res = scout.solve(prot);
        if (res != null) {
            set.add(res);
        }
        return set;
    }

    @Override
    public void setTimeLimit(float t) {
        timeout = t;
    }

}
