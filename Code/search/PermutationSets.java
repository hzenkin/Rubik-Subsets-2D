package search;

import comm.ComArray;
import comm.OperatorCommand;
import prot.GProtD;
import prot.ProtD;
import util.Container;
import util.LP;
import util.Set;

public class PermutationSets {

    private static String ch01 = "\"";
    Set sets[] = new Set[1000];
    int size = 0;

    public PermutationSets() {
        size = 0;
        for (int i = 0; i < 1000; i++) {
            sets[i] = null;
        }
    }

    public int includeSet(int setID, Set set) {
        if (sets[setID] == null) {
            sets[setID] = new Set();
        }
        sets[setID].add(set, false);
        if (size <= setID) {
            size = setID + 1;
        }
        return 0;
    }

    public String toXML() {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += setToXML(sets[i]);
        }
        return str;
    }

    public String toStatisticsXML() {
        String str = "";

        str = setToStatisticsXML(sets[0], new Set());

        for (int i = 1; i < size; i++) {
            str += setToStatisticsXML(sets[i], sets[i - 1]);
        }

        return str;
    }

    public Set getLastSet() {
        Set res = new Set();
        if (size > 1) {
            for (int p = 0; p < sets[size - 1].getSize(); p++) {
                res.add(new GProtD(sets[size - 1].getGP(p)));
            }
        }
        return res;
    }

    public Set completeData() {
        Set set = new Set();
        Container cx = new Container();

        for (int i = 0; i < size; i++) {
            for (int p = 0; p < sets[i].getSize(); p++) {
                cx.add(new GProtD(sets[i].getGP(p)));
            }
        }

        set.add(cx, false);
        return set;
    }

    public int getSize() {
        return size;
    }

    public Set getSet(int index) {
        return sets[index];
    }

    public Set getOuterRim() {
        return sets[size - 2];
    }

    public GProtD getMain() {
        return sets[0].getGP(0);
    }

////////////////////////////////////////////////////////////////////////////////
// 		Search Section
    public GProtD search(ProtD gp) {
        for (int i = 0; i < size; i++) {
            for (int p = 0; p < sets[i].getSize(); p++) {
                GProtD tmp = sets[i].getGP(p);
                if (tmp.compare(gp)) {
                    GProtD ret = new GProtD(tmp);
                    ret.setID(countLowerSets(tmp.findSetID()) + tmp.getID());
                    return ret;
                }
            }
        }
        return null;
    }

////////////////////////////////////////////////////////////////////////////////
//		Private Methods
///////////////////////////	
    private int countLowerSets(int setID) {
        int ret = 0;

        for (int i = 0; i < setID; i++) {
            ret += sets[i].getSize();
        }

        return ret;
    }

    private String setToXML(Set set) {
        String str = "	<Set id = ";

        str += ch01 + set.getGP(0).findSetID();
        str += ch01 + " size = ";
        str += ch01 + set.getSize();
        str += ch01 + ">" + LP.newln;

        for (int i = 0; i < set.getSize(); i++) {
            str += set.getGP(i).toXML();
        }

        str += "	</Set>" + LP.newln;
        return str;
    }

    private String setToStatisticsXML(Set set, Set setP) {
        String str = "	<Set id = ";
        int setID = set.getGP(0).findSetID();
        float a = set.getSize(), b = setP.getSize(), c = a / b;

        str += ch01 + setID;
        str += ch01 + " size = ";
        str += ch01 + set.getSize();
        str += ch01 + " slope = ";
        str += ch01 + c;
        str += ch01 + ">" + LP.newln;
        str += MoveReachStatisticsXML(set);

        str += "	</Set>" + LP.newln;
        return str;
    }

    private String MoveReachStatisticsXML(Set idSet) {
        String str = "";
        ComArray coms = OperatorCommand.getAllCommands();

        int setSize = idSet.getSize();
        int comReaCount, comSolCount;

        for (int c = 0; c < coms.getSize(); c++) {
            OperatorCommand oc = new OperatorCommand(coms.get(c));
            comReaCount = 0;
            comSolCount = 0;

            for (int i = 0; i < idSet.getSize(); i++) {
                Set reachers = idSet.getGP(i).getReachers();
                for (int r = 0; r < reachers.getSize(); r++) {
                    OperatorCommand frc = new OperatorCommand(reachers.getCA(r).get(0));
                    if (frc.equals(oc)) {
                        comReaCount++;
                        break;
                    }
                }
                Set solvers = idSet.getGP(i).getSolvers();
                for (int s = 0; s < solvers.getSize(); s++) {
                    OperatorCommand frc = new OperatorCommand(solvers.getCA(s).get(0));
                    if (frc.equals(oc)) {
                        comSolCount++;
                        break;
                    }
                }
            }
            float fr = comReaCount * 1000 / setSize, fs = comSolCount * 1000 / setSize;
            fr /= 10.0f;
            fs /= 10.0f;

            str += "<" + oc + "> "
                    + fr + "% / " + fs
                    + "% </" + oc + ">" + LP.newln;
        }

        return str;
    }

}
