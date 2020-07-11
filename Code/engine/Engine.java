package engine;

import display.Painter;
import prot.ProtD;
import comm.ComArray;
import comm.CommandSequencer;
import search.ResultTypes;
import solvers.Algorithm4N;
import solvers.Cx00;
import display.Metrics;
import factory.SetFactory;
import io.FileManager;
import search.Generator;
import search.Generator.Type;
import search.PermutationSets;
import solvers.Scout;
import util.*;

public class Engine {

    public Painter painter;
    Cx00 cx;
    ProtD prototype;
    Operator operator;
    Algorithm4N alg4N;
    int rand;

    public Engine() {
        new FileManager().checkFiles();
        prototype = new ProtD();
        cx = new Cx00(ResultTypes.SINGLE_RESULT);
        operator = new Operator();
        alg4N = new Algorithm4N();
        rand = 0;

        Expand();
    }

    public void drawStart() {
        painter.paintToBuffer(prototype);
    }

    final public void Expand() {
        FileManager fm = new FileManager();
        fm.checkFiles();
        int mark = cx.us.completeData().getSize();

        LP.println("mark=" + mark);

        switch (Metrics.size) {
            case 1:
                if (mark == 2) {
                    return;
                }
                break;
            case 2:
                if (69 < mark) {
                    return;
                }
                break;
            case 3:
                if (788 < mark) {
                    return;
                }
                break;
            case 4:
                if (1234 < mark) {
                    return;
                }
                break;
            default:
                if (2345 < mark) {
                    return;
                }
        }
        SetFactory sf = new SetFactory();
        Generator gen = new Generator(Type.Expansions);
        gen.setMaxComLength(5);
        gen.generate(4000);
        PermutationSets P = sf.buildBySequences(gen.totalSet);
        LP.println("Expansion complete, new size=" + P.getSize());
        fm.transferDataToBinaryFile(P);
        fm.transferDataToXMLFile(P);
    }

    public void random() {
        ComArray com = new ComArray();
        com.generateRandSequenceNoNulls(28, rand++);
        LP.println("Executing..:" + com.toString());
        executeMoves(com, 0.01, false);
    }

    public void solve() {
        Set set = new Set();
        switch (Metrics.size) {
            case 2:
            case 3:
                set = Cx00_Solve(20);
                break;
            case 4:
                set = Case4_Solver();
                break;

            default:
                set = Scout.Solver(prototype);
        }
        executeMoves(set, 0.5, .75, true);
    }

    public Set Case4_Solver() {
        Set set = Cx00_Solve(1.4f);
        if (set.isEmpty()) {
            set = alg4N.solveSize4(prototype);
        }
        return set;
    }

    public Set Cx00_Solve(float timeout) {
        ComArray com = null;
        Set set = new Set();
        cx.setCommandSequencer(new CommandSequencer());
        cx.setTimeLimit(timeout);
        com = cx.solve(prototype);
        if (com != null) {
            set.add(com);
        }
        return set;
    }

    public void executeMove(ComArray com) {
        operator.execute(prototype, com);
        drawStart();

        LP.println("Executed command:" + com.toString());
        LP.println(prototype.toString());
    }

    public void executeMoves(ComArray com, double delay, boolean displayCom) {
        if (displayCom) {
            LP.println(com.toString());
        }
        painter.drawList(operator.execute(prototype, com), delay);
    }

    public void executeMoves(Set set, double delay, double delaySE, boolean display) {
        for (int i = 0; i < set.getSize(); i++) {
            executeMoves(set.getCA(i), delay, display);
            if (i < set.getSize() - 1) {
                ClockB.Sleep(delaySE);
            }
        }
    }

}
