package comm;

import display.Metrics;
import util.Set;

public class OperatorCommand {

    final public static int nullCommand = 1000000;
    int command;

    public OperatorCommand() {
        command = 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.command;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OperatorCommand other = (OperatorCommand) obj;
        if (this.command != other.command) {
            return false;
        }
        return true;
    }

    public OperatorCommand(int com) {

        command = com;

        if (com == 0) {
            command = nullCommand;
        }
    }

    public void setCommand(int com) {
        command = com;
        if (com == 0) {
            command = nullCommand;
        }
    }

    public int getCommand() {
        return command - getAxis();
    }

    public int getAxis() {
        return command % 1000;
    }

    static public ComArray getAllCommands() {
        ComArray ca = new ComArray();
        ca.add(nullCommand); // null command
        for (int i = 1000; i < 2300; i += 1000) {
            int com = nullCommand + i;
            for (int axis = 1; axis <= Metrics.size; axis++) {
                ca.add(com + axis);
            }
        }
        int zcoms = nullCommand + 3000;
        ca.add(zcoms + 1);
        ca.add(zcoms + Metrics.size);

        return ca;
    }

    public static Set getAllCommandsAsSet() {
        Set set = new Set();
        ComArray coms = getAllCommands();

        for (int i = 0; i < coms.getSize(); i++) {
            ComArray ca = new ComArray();
            ca.add(coms.get(i));
            set.add(ca);
        }

        return set;
    }

    public ComArray getCommand(int id) {
        ComArray ca = new ComArray();
        ca.add(getAllCommands().get(id));
        return ca;
    }

    public int findCommand(int command) {
        ComArray ca = getAllCommands();

        for (int i = 0; i < ca.getSize(); i++) {
            if (ca.get(i) == command) {
                return i;
            }
        }

        return -1;
    }

    public String toString() {
        return Notation(command);
    }

    public static String Notation(int com) {
        if (com == nullCommand) {
            return "Null";
        }

        OperatorCommand oc = new OperatorCommand(com);
        int ax = oc.getAxis();

        switch (oc.getCommand() - nullCommand) {
            case 1000:
                return "X" + ax;

            case 2000:
                return "Y" + ax;

            case 3000:
                return "Z" + ax;

        }

        return "";
    }

}
