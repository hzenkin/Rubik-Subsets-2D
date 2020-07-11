package factory;

import comm.ComArray;
import prot.GProtD;
import search.PermutationSets;
import engine.Operator;
import search.Generator;
import util.*;

public class SetFactory {

    public SetFactory() {
    }

    public PermutationSets buildBasicSet() {
        Generator g = new Generator(Generator.Type.StartUp);
        g.generate(20);
        PermutationSets us = buildBySequences(g.totalSet);
        return us;
    }

    public PermutationSets buildBySequences(Set seqSets) {
        PermutationSets us = new PermutationSets();

        for (int ID = 0; ID < 1000; ID++) {
            Set sub = makeSet(ID, seqSets);

            if (sub.getSize() > 0) {
                us.includeSet(ID, sub);
            } else {
                break;
            }
        }

        return us;
    }

    public PermutationSets buildByTableBinaryFormat(IntTable table) {
        return buildBySequences(makeSetFromTableData(table));
    }

    public PermutationSets buildByTableXMLFormat(IntTable table) {
        PermutationSets us = new PermutationSets();
        int currSetID = -1;
        Set subSet = new Set();

        for (int index = 0; index < table.size();) {
            if (currSetID < table.get(1, index)) {
                currSetID = table.get(1, index);
                subSet = new Set();
            }
            while ((index < table.size()) && (currSetID == table.get(1, index))) {
                boolean reacherExecuted = false;
                GProtD px = new GProtD();
                px.setID(table.get(2, index));

                while ((index < table.size())
                        && (currSetID == table.get(1, index))
                        && (px.getID() == table.get(2, index))) {
                    int ui = 0;
                    while ((index < table.size())
                            && (currSetID == table.get(1, index))
                            && (table.get(4, index) == -1)) {
                        int val = table.get(3, index);
                        while (px.table.size() <= ui) {
                            px.table.add(0, 0, 0, 0);
                        }
                        px.table.update(2, ui, val);
                        px.table.update(1, ui, new ComArray(val).getSize());
                        index++;
                        ui++;
                        if (reacherExecuted); else {
                            (new Operator()).execute(px, new ComArray(val));
                            reacherExecuted = true;
                        }
                    }
                    ui = 0;
                    while ((index < table.size())
                            && (currSetID == table.get(1, index))
                            && (table.get(3, index) == -1)) {
                        while (px.table.size() <= ui) {
                            px.table.add(0, 0, 0, 0);
                        }
                        int val = table.get(4, index);
                        px.table.update(3, ui, val);
                        px.table.update(4, ui, new ComArray(val).getSize());
                        index++;
                        ui++;
                    }
                }
                subSet.add(px);
            }
            us.includeSet(currSetID, subSet);
        }
        return us;
    }

    private Set makeSetFromTableData(IntTable table) {
        int gpID = 0;
        Set set = new Set();
        Container cont = new Container();
        GProtDFactory gf = new GProtDFactory();

        for (int i = 0; i < table.size(); i++) {
            int arySize = table.get(1, i);
            if (arySize > 0) {
                IntArray reachers = new IntArray(), solvers = new IntArray();
                for (int k = i + 1; k < i + arySize + 1; k++) {
                    reachers.add(table.get(2, k));
                    solvers.add(table.get(3, k));
                }
                GProtD gp = gf.makeGProt(reachers, solvers);
                gp.setID(gpID++);
                cont.add(gp);
            }
        }
        set.add(cont, false);
        return set;
    }

    public static Set makeSet(int ID, Set set) {
        Set res = new Set();
        for (int i = 0; i < set.getSize(); i++) {
            int setID = set.getGP(i).findSetID();
            if (setID == ID) {
                res.add(set.getGP(i));
            }
        }
        res = sortByMinReacher(res);
        return res;
    }

    public static Set sortByMinReacher(Set set) {
        IntArray reachers = new IntArray();
        for (int i = 0; i < set.getSize(); i++) {
            GProtD gp = set.getGP(i);
            reachers.add(gp.getMinimumReacher());
        }
        reachers.sort();

        Set res = new Set();
        int id = 1;
        for (int r : reachers.getArrayCopy()) {
            for (int i = 0; i < set.getSize(); i++) {
                GProtD gp = set.getGP(i);
                if (gp.getMinimumReacher() == r) {
                    gp.setID(id++);
                    res.add(gp);
                    break;
                }
            }
        }

        return res;
    }

}
