package engine;

import display.Metrics;
import util.ClockB;
import util.IntArray;
import comm.OperatorCommand;

public class Router {

    ClockB clock;
    public IntArray mouseData;
    Engine engine;

    public Router(ClockB c) {
        clock = c;
        engine = new Engine();
    }

    public void processClicked() {
        OperatorCommand oc = new OperatorCommand();
        int clicked = engine.painter.getClicked();

        if ((clicked == -1) || (Metrics.bc < clicked)) {
            return;
        }

        switch (clicked) {
            case 1:
                engine.random();
                break;

            case 2:
                clock.print("Find Solution");
                engine.solve();
                break;

            default:
                engine.executeMove(oc.getCommand(clicked - 2));
        }
    }
}
