package windows;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WListener extends WindowAdapter {

    Frame fr;

    public WListener(Frame frame) {
        fr = frame;
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        super.windowClosing(arg0);
        fr.dispose();
    }

}
