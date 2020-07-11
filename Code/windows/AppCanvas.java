package windows;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferStrategy;
import java.awt.Dimension;
import engine.Board;
import display.Painter;

public class AppCanvas extends Canvas {

    BufferStrategy strategy;
    Dimension dimension;
    public Board board;

    public AppCanvas() {
        setBackground(Color.darkGray);
        dimension = new Dimension();
        board = new Board();
        addMouseListener(new MListener(this));
        addMouseMotionListener(new MListener(this));
    }

    public AppCanvas(GraphicsConfiguration arg0) {
        super(arg0);
        setBackground(Color.darkGray);
        dimension = new Dimension();
    }

    public void taskBuffer() {
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    @Override
    public void paint(Graphics arg0) {
        if (strategy == null) {
            taskBuffer();
        }
        dimension.height = getHeight();
        dimension.width = getWidth();

        board.setPainter(new Painter(strategy, dimension));
        board.process();
    }

    @Override
    public void update(Graphics arg0) {
        dimension.height = getHeight();
        dimension.width = getWidth();
        if (strategy == null) {
            taskBuffer();
        }
        super.update(arg0);
    }
}
