package util;

public class IntTable {

    protected IntArray[] column;

    public IntTable() {
        column = new IntArray[4];
        for (int i = 0; i < 4; i++) {
            column[i] = new IntArray();
        }
    }

    public IntTable(IntTable t4x) {
        column = new IntArray[4];
        for (int i = 0; i < 4; i++) {
            column[i] = t4x.column[i].replicate();
        }
    }

    public void add(int v1, int v2, int v3, int v4) {
        column[0].add(v1);
        column[1].add(v2);
        column[2].add(v3);
        column[3].add(v4);
    }

    public void add(IntArray vs) {
        column[0].add(vs.get(0));
        column[1].add(vs.get(1));
        column[2].add(vs.get(2));
        column[3].add(vs.get(3));
    }

    public void add(IntTable table) {
        for (int i = 0; i < table.size(); i++) {
            IntArray row = table.row(i);
            add(row);
        }
    }

    public void delete(int index) {
        column[0].delete(index);
        column[1].delete(index);
        column[2].delete(index);
        column[3].delete(index);
    }

    public void set(int index, int v1, int v2, int v3, int v4) {
        column[0].array[index] = v1;
        column[1].array[index] = v2;
        column[2].array[index] = v3;
        column[3].array[index] = v4;
    }

    public void set(int index, IntArray vs) {
        column[0].array[index] = vs.get(0);
        column[1].array[index] = vs.get(1);
        column[2].array[index] = vs.get(2);
        column[3].array[index] = vs.get(3);
    }

    public void update(int field, int index, int v) {
        column[field - 1].array[index] = v;
    }

    public int get(int field, int index) {
        return column[field - 1].get(index);
    }

    public IntArray column(int field) {
        return column[field - 1].replicate();
    }

    public IntArray row(int index) {
        IntArray ary = new IntArray();

        if (index < size()) {
            ary.add(column[0].get(index));
            ary.add(column[1].get(index));
            ary.add(column[2].get(index));
            ary.add(column[3].get(index));
        }

        return ary;
    }

    public IntTable findIndexes(int field, int val) {
        IntTable tmp = new IntTable();

        for (int i = 0; i < size(); i++) {
            if (val == column[field - 1].array[i]) {
                tmp.add(get(1, i), get(2, i), get(3, i), get(4, i));
            }
        }
        return tmp;
    }

    public int findIndex(int field, int val) {
        for (int i = 0; i < size(); i++) {
            if (val == column[field - 1].array[i]) {
                return i;
            }
        }

        return -1;
    }

    public int size() {
        return column[1].size;
    }

    @Override
    public String toString() {
        String str = "lp.IntTable4" + LP.newln;

        str += "index   field  field  field  field  " + LP.newln;

        for (int i = 0; i < column[0].getSize(); i++) {
            str += i + "      " + get(1, i) + "     " + get(2, i) + "     "
                    + get(3, i) + "     " + get(4, i) + LP.newln;
        }

        return str + "end of table" + LP.newln;
    }

}
