package comm;

import util.Set;

public class CommandSetMaker {

    Set allCommands;

    public CommandSetMaker() {
        reset();
    }

    public Set getCommandSet() {
        return allCommands;
    }

    public void reset() {
        allCommands = (new OperatorCommand()).getAllCommandsAsSet();
    }

    public int addCommandSet(Set newComs) {
        allCommands.add(newComs);
        return allCommands.getSize();
    }

    public int setCommandSet(Set newComs) {
        allCommands = new Set();
        allCommands.add(newComs);
        return allCommands.getSize();
    }

    public int removeCommands(Set comsToGo) {
        Set set = new Set();

        for (int i = 0; i < allCommands.getSize(); i++) {
            ComArray tmp = allCommands.getCA(i);

            if (!comsToGo.isElement(tmp)) {
                set.add(tmp);
            }
        }
        allCommands = set;
        return set.getSize();
    }

}
