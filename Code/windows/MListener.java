package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import util.IntArray;

public class MListener extends MouseAdapter {

    AppCanvas canvas;

    public MListener(AppCanvas arg) {
        canvas = arg;
    }

    public void inputMouseData(MouseEvent mev) {
        IntArray ia = new IntArray();

        ia.add(mev.getID());
        ia.add(mev.getX());
        ia.add(mev.getY());
        ia.add(mev.getClickCount());
        ia.add(mev.getButton());

        canvas.board.mouse = ia;
    }

    @Override
    public void mouseClicked(MouseEvent mev) {
        inputMouseData(mev);
        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mev) {
        inputMouseData(mev);
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mev) {
        inputMouseData(mev);
        canvas.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mev) {
        inputMouseData(mev);
        canvas.repaint();
    }

}
