package util;

import java.util.LinkedList;

public class IntArray {

    protected int array[];
    protected int alloc, size;

    public IntArray() {
        size = 0;
        alloc = 0;
        allocate();
    }

    public IntArray(int[] ary) {
        size = 0;
        alloc = 0;
        while (alloc < ary.length) {
            allocate();
        }
        System.arraycopy(ary, 0, array, 0, ary.length);
        size = ary.length;
    }

    public IntArray replicate() {
        return copy(0, size - 1);
    }

    public LinkedList replicateAsLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int n : getArrayCopy()) {
            list.add(n);
        }
        return list;
    }

    public IntArray copy(int begin, int end) {
        IntArray cpy = new IntArray();
        for (int i = begin; i <= end; i++) {
            cpy.add(array[i]);
        }
        return cpy;
    }

    public int getSize() {
        return size;
    }

    public int[] getArrayCopy() {
        int res[] = new int[size];
        System.arraycopy(array, 0, res, 0, size);
        return res;
    }

    public void delete(int index) {
        size--;
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }
    }

    public void add(int val) {
        if (size == alloc) {
            allocate();
        }
        array[size++] = val;
    }

    public void add(IntArray ary) {
        for (int i = 0; i < ary.getSize(); i++) {
            add(ary.get(i));
        }
    }

    public void set(int index, int value) {
        array[index] = value;
    }

    public int get(int index) {
        return array[index];
    }

    public int findIndex(int value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public IntArray findIndexes(int value) {
        IntArray indexes = new IntArray();

        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public int min() {
        int tmp = array[0];

        for (int i = 0; i < size; i++) {
            tmp = Math.min(tmp, array[i]);
        }

        return tmp;
    }

    public int max() {
        int tmp = array[0];

        for (int i = 0; i < size; i++) {
            tmp = Math.max(tmp, array[i]);
        }

        return tmp;
    }

    public int avg() {
        return (int) (sum() / Math.max(1, size));
    }

    public float avarageAsFloat() {
        float sum = sum();

        return (sum / Math.max(1, size));
    }

    public long sum() {
        long tmp = 0;
        for (int i = 0; i < size; i++) {
            tmp += array[i];
        }

        return tmp;
    }

    public void sort() {
        int numUnsorted = size, index, max;
        while (numUnsorted > 0) {
            max = 0;
            for (index = 1; index < numUnsorted; index++) {
                if (array[max] < array[index]) {
                    max = index;
                }
            }
            swap(max, --numUnsorted);
        }
    }

    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            swap(i, size - i - 1);
        }
    }

    @Override
    public String toString() {
        return "IntArray{" + "size=" + size + ", array=" + array + '}';
    }

////////////////////////////////////////////////////////////////////////////////
//      Private Methods
////////////////////////////////
    private void allocate() {
        alloc = 2 * alloc + 8;
        int tmp[] = array;
        array = new int[alloc];
        if (size > 0) {
            System.arraycopy(tmp, 0, array, 0, size);
        }
    }

    private void swap(int indexA, int indexB) {
        int tmp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = tmp;
    }

}
