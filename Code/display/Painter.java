package display;

import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import static java.awt.Color.gray;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import prot.ObjS;
import prot.ProtD;
import util.ClockB;
import util.Container;
import util.IntArray;
import util.LinS;

public class Painter {

    public IntArray mouse;
    BufferStrategy buffer;
    Graphics graphics;
    Metrics metrics;
    Dimension dim;
    static Color palet[]
            = {
                gray,
                new Color(255, 144, 144),
                new Color(144, 255, 144),
                new Color(144, 144, 255),
                new Color(255, 255, 144),
                new Color(144, 255, 255),
                new Color(255, 144, 255)
            };

    public Painter(BufferStrategy strategy, Dimension dimension) {
        buffer = strategy;
        dim = dimension;
        mouse = new IntArray();
        graphics = buffer.getDrawGraphics();
        metrics = new Metrics(graphics, dim);
    }

    public void drawList(Container list, double delay) {
        for (int i = 0; i < list.getSize(); i++) {
            ProtD prot = (ProtD) list.elements[i];
            paintToBuffer(prot);
            ClockB.Sleep(delay);
        }
    }

    public void paintToBuffer(ProtD prot) {
        ////////////////////////////
        graphics = buffer.getDrawGraphics();// imported code
        /////////////////////////////

        drawProt(prot);
        drawButtons();

        //////////////////////////////
        graphics.dispose();
        buffer.show();	// imported code
        /////////////////////////////
    }

    public int getClicked() {
        if (mouse.get(0) == MouseEvent.MOUSE_CLICKED) {
            return getContainerButton();
        }
        return -1;
    }

////////////////////////////////////////////////////////////////////////////////
//		Private Methods
////////////////////////////////////////////
    private void drawProt(ProtD prot) {
        for (int i = 0; i < Metrics.surfaceCount; i++) {
            Graphics tmp = metrics.getDrawArea(i);
            drawObjS(tmp, prot.getSurface(i));
            tmp.dispose();
        }
    }

    private void drawObjS(Graphics g, ObjS s) {
        LinS lins = new LinS(Metrics.size, Metrics.size);
        lins.XYIteration();
        while (lins.hasNext()) {
            int colIndex = s.cls[lins.iter];
            g.setColor(palet[colIndex]);
            Point p = lins.curr();
            g.fillRect(metrics.getLoc(p.x), metrics.getLoc(p.y),
                    metrics.getWS(), metrics.getWS());
            lins.next();
        }
        int w = metrics.getSW() - 1;
        g.setColor(Color.lightGray);
        g.drawRect(0, 0, w, w);
    }

    private void drawButtons() {
        Rectangle rect = metrics.getButtonAreaRect();
        Color buttonColors[] = setButtonColorsByMouseState();

        graphics.setColor(new Color(100, 130, 140));
        graphics.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);

        for (int i = 1; i < Metrics.bc + 1; i++) {
            graphics.setColor(buttonColors[i]);
            Rectangle rx = metrics.getButton(i);
            graphics.fillRect(rx.x, rx.y, rx.width, rx.height);
        }

    }

    private Color[] setButtonColorsByMouseState() {
        Color tmp[] = new Color[1000];
        Color colM = new Color(200, 244, 210),
                colP = new Color(255, 220, 200),
                cXn = new Color(123, 168, 123),
                cYn = new Color(123, 123, 168),
                cZn = new Color(123, 168, 168);

        tmp[1] = new Color(155, 130, 170);
        tmp[2] = new Color(140, 155, 170);
        int nx = 3 + Metrics.size;

        for (int i = 3; i < nx; i++) {
            tmp[i] = cXn;
        }
        for (int i = nx; i < (nx + Metrics.size); i++) {
            tmp[i] = cYn;
        }
        nx += Metrics.size;
        for (int i = nx; i < (Metrics.bc + 1); i++) {
            tmp[i] = cZn;
        }

        if (mouse.getSize() > 2) {
            int cb = getContainerButton();
            if (cb > -1) {
                switch (mouse.get(0)) {
                    case MouseEvent.MOUSE_CLICKED:
                        tmp[cb] = (colM);
                        break;
                    case MouseEvent.MOUSE_MOVED:
                        tmp[cb] = (tmp[cb].brighter());
                        break;
                    case MouseEvent.MOUSE_PRESSED:
                        tmp[cb] = (colP);
                        break;
                    case MouseEvent.MOUSE_RELEASED:
                        tmp[cb] = (colM);
                }
            }
        }
        return tmp;
    }

    private int getContainerButton() {
        for (int i = 1; i < Metrics.bc + 1; i++) {
            Point px = new Point(mouse.get(1), mouse.get(2));
            if (metrics.getButton(i).contains(px)) {
                return i;
            }
        }
        return -1;
    }

}
