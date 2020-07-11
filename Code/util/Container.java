package util;

public class Container {

    public cmp elements[];
    protected int alloc, size;

    public Container() {
        alloc = 24;
        size = 0;
        elements = new cmp[24];
    }

    public int getSize() {
        return size;
    }

    public int lastIndex() {
        return size - 1;
    }

    public int add(cmp arg) {
        allocate();
        elements[size] = arg;
        size++;
        return 0;
    }

    public cmp get(int index) {
        return elements[index];
    }

    public int add(Container arg) {
        for (int i = 0; i < arg.getSize(); i++) {
            add(arg.elements[i]);
        }

        return 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isElement(cmp arg) {
        for (int i = 0; i < size; i++) {
            if (elements[i].compare(arg)) {
                return true;
            }
        }
        return false;
    }

    protected void allocate() {
        if (size == alloc) {
            cmp tmp[] = elements;
            alloc = 2 * alloc;
            elements = new cmp[alloc];

            for (int i = 0; i < size; i++) {
                elements[i] = tmp[i];
            }
        }
    }

}
