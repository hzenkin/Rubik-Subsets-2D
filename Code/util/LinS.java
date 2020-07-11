package util;

import java.awt.Point;

public class LinS {

    public int width, height, iter;

    public LinS(int w, int h) {
        width = w;
        height = h;
    }

    public int Lindex(int x, int y) {
        return y * width + x;
    }

    public int findX(int index) {
        return index % width;
    }

    public int findY(int index) {
        return index / width;
    }

    public boolean inSurface(int index) {
        return index < size();
    }

    public int size() {
        return width * height;
    }

    public void XYIteration() {
        iter = 0;
    }

    public boolean hasNext() {
        return inSurface(iter);
    }

    public Point curr() {
        return new Point(findX(iter), findY(iter));
    }

    public void next() {
        iter++;
    }

    public String toString() {
        String str = "numbers.LinS";

        str += "[";
        str += "width=" + width + ", ";
        str += "height=" + height;
        str += "]";

        return str;

    }

}
