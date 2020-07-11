package comm;

import util.Container;
import util.IntArray;
import util.Set;

public class CommandSequencer {

    int start, lastSequence;
    Set allCommands;
    boolean reset = false;

    public void resetComLength(int len) {
        if (reset) {
            return;
        }

        lastSequence = (int) Math.pow(allCommands.getSize(), len);
        reset = true;
    }

    public CommandSequencer() {
        init(0, -1);
    }

    public CommandSequencer(int begin) {
        init(begin, -1);
    }

    public CommandSequencer(int begin, int end) {
        init(begin, end);
    }

    public CommandSequencer(CommandSequencer sequencer) {
        init(sequencer.start, sequencer.lastSequence);
        allCommands = sequencer.allCommands;
    }

    public void setAllCommands(Set allComs) {
        allCommands = allComs;
    }

    public int getStart() {
        return start;
    }

    public int getStartLength() {
        return getComArray(start).getSize();
    }

    public String strStart() {
        return "start =" + start + ", length = " + getStartLength();
    }

    static public int getCommand(ComArray sequence) {

        OperatorCommand oc = new OperatorCommand();
        int base = OperatorCommand.getAllCommands().getSize();
        IntArray ary = new IntArray();

        for (int digit = 0; digit < sequence.getSize(); digit++) {
            ary.add(oc.findCommand(sequence.get(digit)));
        }
        ary.reverse();
        return convert(ary, base);
    }

    static public ComArray getComArray(int val) {
        return new CommandSequencer().command(val);
    }

    public boolean isAlive() {
        return (lastSequence == -1) || (start < lastSequence);
    }

    public Set commands(int num) {

        Set set = new Set();
        Container tmp = new Container();
        for (int counter = 0; counter < num; counter++) {
            ComArray ca = command(start + counter);
            if (!ca.containsNullCommand()) {
                tmp.add(ca);
            }
        }
        set.add(tmp, false);
        start += num;

        return set;
    }

////////////////////////////////////////////////////////////////////////////////
//	Private Methods
    private ComArray command(int cn) {

        ComArray sequence = new ComArray();
        IntArray ary = convert(cn, allCommands.getSize());
        for (int i = 0; i < ary.getSize(); i++) {
            sequence.add(allCommands.getCA(ary.get(i)));
        }
        return sequence;
    }

    private static IntArray convert(long val, int base) {
        IntArray ary = new IntArray();
        long div = val;
        int rem;
        do {
            rem = (int) (div % base);
            div /= base;
            ary.add(rem);
        } while (div > 0);
        ary.reverse();
        return ary;
    }

    private static int convert(IntArray ary, int base) {
        int pow = 1, res = 0;

        for (int i = 0; i < ary.getSize(); i++) {
            res += pow * ary.get(i);
            pow *= base;
        }

        return res;
    }

////////////////////////////////////////////////////////////////////////////////
    private void init(int a, int b) {
        start = a;
        lastSequence = b;
        allCommands = new OperatorCommand().getAllCommandsAsSet();
    }

////////////////////////////////////////////////////////////////////////////////
}	// class
