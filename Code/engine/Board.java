package engine;

import display.Painter;
import java.awt.event.MouseEvent;
import util.*;

public class Board {

    public Router router;
    ClockB clock;

    public IntArray mouse;

    public Board() {
        clock = new ClockB();
        router = new Router(clock);
        mouse = new IntArray();
        clock.print("Board initialized");
    }

    public void setPainter(Painter px) {
        router.engine.painter = px;
        router.engine.painter.mouse = mouse;
    }

    public void process() {
        router.engine.drawStart();
        if (mouse.get(0) == MouseEvent.MOUSE_CLICKED) {
            router.processClicked();
        }
        ClockB.Sleep(0.17);
    }

}
