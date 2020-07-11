package util;

import prot.*;
import comm.ComArray;

public class Set extends Container {

    public Set() {
        super();
    }

    public cmp get(int index) {
        return elements[index];
    }

    public ComArray getCA(int index) {
        return (ComArray) elements[index];
    }

    public GProtD getGP(int index) {
        return (GProtD) elements[index];
    }

    public int add(cmp arg) {
        if (!isElement(arg)) {
            super.add(arg);
            return 1;
        }
        return 0;
    }

    public int add(Container cont, boolean compare) {
        if (compare) {
            add(cont);
        } else {
            for (int i = 0; i < cont.getSize(); i++) {
                allocate();
                elements[size] = cont.elements[i];
                size++;
            }
        }
        return 0;
    }

    public int findElement(cmp arg) {
        for (int i = 0; i < size; i++) {
            if (elements[i].compare(arg)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String str = "Size = " + size;

        if (size > 0 && get(0).getClass().equals(new ComArray().getClass())) {
            for (int i = 0; i < size; i++) {
                str += getCA(i).toString() + "\n";
            }
        }

        return str;
    }

}
