package comm;

import java.util.Random;
import util.cmp;
import util.IntArray;

public class ComArray extends IntArray implements cmp {

    public ComArray() {
        super();
    }

    public ComArray(int val) {
        super();
        add(CommandSequencer.getComArray(val));
    }

    public void add(ComArray ca) {
        for (int i = 0; i < ca.getSize(); i++) {
            add(ca.get(i));
        }
    }

    public boolean compare(cmp arg) {
        ComArray ca = (ComArray) arg;
        if (ca.getSize() != getSize()) {
            return false;
        }
        for (int i = 0; i < ca.getSize(); i++) {
            if (get(i) != ca.get(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsNullCommand() {
        if (size < 2) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (get(i) == 1000000) {
                return true;
            }
        }

        return false;
    }

    public int getSize() {
        if (size == 1) {
            if (get(0) == 1000000) {
                return 0;
            }
        }
        return size;
    }

    public void generateRandSequence(int k) {
        Random random = new Random();
        ComArray ac = OperatorCommand.getAllCommands();
        for (int i = 0; i < k; i++) {
            add(ac.get(random.nextInt(ac.getSize())));
        }
    }

    public static ComArray ZgenerateRandSequence(int k) {
        Random random = new Random();
        ComArray res = new ComArray();
        ComArray ac = OperatorCommand.getAllCommands();
        for (int i = 0; i < k; i++) {
            int r = random.nextInt(2) + ac.getSize() - 2;
            res.add(ac.get(r));
        }
        return res;
    }

    public void generateRandSequenceNoNulls(int k, int rand) {
        Random random = new Random(rand);
        ComArray ac = OperatorCommand.getAllCommands();
        int sz = ac.getSize() - 1;

        for (int i = 0; i < k; i++) {
            add(ac.get(random.nextInt(sz) + 1));
        }
    }

    public String toString() {
        int s = getSize();
        String str = "ComArray[" + s + "] = {";
        for (int i = 0; i < s; i++) {
            str += OperatorCommand.Notation(get(i));
            if (i < s - 1) {
                str += ", ";
            }
        }
        if (s == 0) {
            str += " Null Comm";
        }
        str += " }";
        return str;
    }

}
