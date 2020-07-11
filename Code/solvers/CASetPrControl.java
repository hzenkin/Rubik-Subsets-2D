package solvers;

import search.ResultTypes;
import util.Set;
import comm.ComArray;
import comm.CommandSequencer;

public class CASetPrControl {

    int config, resultCount;

    public CASetPrControl(int conf) {
        config = conf;
        resultCount = 1;
    }

    public CASetPrControl(int conf, int numRes) {
        config = conf;
        resultCount = numRes;
    }

    public boolean conditionsMet(Set set, CommandSequencer cs) {
        int sz = set.getSize();

        switch (config) {
            case ResultTypes.SINGLE_RESULT:
                if (sz > 0) {
                    return true;
                }
                break;
            case ResultTypes.MINIMUM_LENGTH_RESULTS:
                if (sz > 0 && cs.isAlive() == false) {
                    return true;
                }
                if (sz > 2) {
                    ComArray c0 = set.getCA(0);
                    ComArray clast = set.getCA(sz - 1);
                    if (clast.getSize() > c0.getSize()) {
                        return true;
                    }

                }
                break;
            case ResultTypes.FIXED_NUMBER_OF_RESULTS:
                if (sz >= resultCount) {
                    return true;
                }
                break;
        }
        return false;
    }

    public Set trim(Set set) {
        Set tset = new Set();

        switch (config) {
            case ResultTypes.SINGLE_RESULT:
                tset.add(set.get(0));
                break;
            case ResultTypes.MINIMUM_LENGTH_RESULTS:
                int minLen = set.getCA(0).getSize();
                for (int i = 0; i < set.getSize(); i++) {
                    if (minLen == set.getCA(i).getSize()) {
                        tset.add(set.get(i));
                    }
                }
                break;
            case ResultTypes.FIXED_NUMBER_OF_RESULTS:
                for (int i = 0; i < resultCount; i++) {
                    tset.add(set.get(i));
                }
                break;
        }
        return tset;
    }

}
