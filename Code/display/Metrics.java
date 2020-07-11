package display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import prot.ProtConstants;
import util.IntArray;
import util.LinS;

public class Metrics {
////////////////////////////////////////////////////////////////////////////////
//	Static Fields

    static public int size = 4, // N cube's size
            config = ProtConstants.CONFIG_PARRALEL,
            spc = 5,
            surfaceCount = 2,
            bc = 2 * size + 4;

////////////////////////////////////////////////////////////////////////////////
    Graphics gm;
    Dimension dm;
    public int wsp;
    private IntArray ary = new IntArray();

    public Metrics(Graphics g, Dimension d) {
        gm = g;
        dm = new Dimension(d);
        init();
        initButtons();
    }

    public Graphics getDrawArea(int n) {
        return gm.create(ary.get(1 + 4 * n), ary.get(2 + 4 * n),
                ary.get(3 + 4 * n), ary.get(4 + 4 * n));
    }

    public Rectangle getButtonAreaRect() {
        Rectangle r = new Rectangle();
        r.x = ary.get(13);
        r.y = ary.get(14);
        r.width = ary.get(15);
        r.height = ary.get(16);

        return r;
    }

    public int getLoc(int l) {
        return l * wsp + spc;
    }

    public int getSW() {
        return ary.get(0);
    }

    public int getWS() {
        return ary.get(17);
    }

    public Rectangle getButton(int n) {
        Rectangle rn = new Rectangle();

        rn.x = ary.get(18 + n * 4);
        rn.width = ary.get(19 + n * 4) - rn.x;

        rn.y = ary.get(20 + n * 4);
        rn.height = ary.get(21 + n * 4) - rn.y;

        return rn;
    }

////////////////////////////////////////////////////////////////////////////////
//	Static Methods	
////////////////////////////////////////////////////////////////////////////////
    static public long getCommandCountOfLength(int length) {
        long count = (long) Math.round((Math.pow(2 * Metrics.size + 2, length)));
        return count;
    }

////////////////////////////////////////////////////////////////////////////////
    static public long getCommandCountOfLengthWithNulls(int length) {
        long count = (long) Math.round((Math.pow(2 * Metrics.size + 3, length)));
        return count;
    }

////////////////////////////////////////////////////////////////////////////////
    static public long getCommandCountOfSmallerLengthsWithNulls(int length) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += getCommandCountOfLengthWithNulls(i);
        }
        return sum;
    }

////////////////////////////////////////////////////////////////////////////////
    static public LinS getLinS() {
        LinS lins = new LinS(Metrics.size, Metrics.size);
        return lins;
    }

////////////////////////////////////////////////////////////////////////////////
//	Private Methods
    private void init() {
        wsp = (dm.width - 16 * spc) / (2 * size);
        int dxB = 4 * spc,
                dxL = dm.width - 4 * spc,
                dx2 = wsp * size + 5 * spc,
                baX1 = dxB,
                baX2 = dxL,
                baY1 = dx2 + 4 * spc,
                baY2 = baY1 + dm.height - (wsp * size + 13 * spc);

        ary.add(size * wsp + spc);      //		ObjS total width

        ary.add(dxB);
        ary.add(dxB);
        ary.add(size * wsp + 5 * spc);
        ary.add(wsp * size + 5 * spc);	//		area1 [1..4]

        ary.add(dm.width - dx2);
        ary.add(dxB);
        ary.add(dxL);
        ary.add(dx2);			//		area2  [5..8]

        ary.add(baX1);
        ary.add(baY1);
        ary.add(baX2);
        ary.add(baY2);			//		Button area  [9..12]

        ary.add(baX1);
        ary.add(baY1);
        ary.add(baX2 - baX1);
        ary.add(baY2 - baY1);		//		Button area R [13..16]

        ary.add(wsp - spc);		//		ws	[17]

    }

////////////////////////////////////////////////////////////////////////////////
    private void initButtons() {
        Rectangle r = getButtonAreaRect();

        ary.add(r.height - 2 * spc);			//	heightA	[18]
        ary.add((r.width - (bc - 1) * spc) / (bc - 2)); //	width	[19]

        ary.add((r.height - 3 * spc) / 2);		//	heightB	[20]
        ary.add(ary.get(19));				//	width	[21]

        int x1, x2, y1, y2;

        for (int i = 1; i < 3; i++) //	22..25; 26..29
        {
            x1 = r.x + spc;
            x2 = x1 + ary.get(19);
            y1 = r.y + i * spc + (i - 1) * ary.get(20);
            y2 = y1 + ary.get(20);
            addToArray(x1, x2, y1, y2);
        }

        for (int i = 3; i < (bc - 1); i++) {
            x1 = r.x + (i - 2) * ary.get(19) + (i - 1) * spc;
            x2 = x1 + ary.get(19);
            y1 = r.y + spc;
            y2 = y1 + ary.get(18);
            addToArray(x1, x2, y1, y2);
        }

        for (int i = bc - 1; i < (bc + 1); i++) {
            x1 = r.x + (bc - 3) * ary.get(19) + (bc - 2) * spc;
            x2 = x1 + ary.get(19);
            y1 = r.y + (i - bc + 2) * spc + (i - bc + 1) * ary.get(20);
            y2 = y1 + ary.get(20);
            addToArray(x1, x2, y1, y2);
        }
    }

////////////////////////////////////////////////////////////////////////////////
    private void addToArray(int x1, int x2, int y1, int y2) {
        ary.add(x1);
        ary.add(x2);
        ary.add(y1);
        ary.add(y2);
    }
}
