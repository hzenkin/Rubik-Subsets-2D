package windows;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.HeadlessException;

public class AppFrame extends Frame {

    public WListener wl;
    public Canvas ca;

    public AppFrame() throws HeadlessException {
        Constructor();
    }

    public AppFrame(String arg0) throws HeadlessException {
        super(arg0);
        Constructor();
    }

    private int Constructor() {
        wl = new WListener(this);
        ca = new AppCanvas();
        setVisible(true);
        add(ca);
        addWindowListener(wl);
        setSize(555, 400);
        setLocationRelativeTo(null);
        return 0;
    }
}
