package prot;

import comm.ComArray;
import util.*;

public class GProtD extends ProtD {

    private int id;
    public IntTable table;

    public GProtD() {
        table = new IntTable();
    }

    public GProtD(ProtD p) {
        super(p);
        table = new IntTable();
    }

    public GProtD(GProtD gp) {
        super(gp);
        table = new IntTable(gp.table);
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        id = ID;
    }

    public int findSetID() {
        return table.get(4, 0);
    }

    public Set getSolvers() {
        int index = 0;
        Set set = new Set();
        Container con = new Container();

        while ((index < table.size()) && (table.get(3, index) != -1)) {
            ComArray ca = new ComArray(table.get(3, index++));
            con.add(ca);
        }
        set.add(con, false);
        return set;
    }

    public Set getReachers() {
        int index = 0;
        Set set = new Set();
        Container con = new Container();

        while ((index < table.size()) && (table.get(2, index) != -1)) {
            ComArray ca = new ComArray(table.get(2, index++));
            con.add(ca);
        }
        set.add(con, false);
        return set;
    }

    public int getMinimumReacher() {
        return table.column(2).min();
    }

    public String toXML() {
        String sxml = "		<Prototype_Configuration  id=\"" + id + "\">" + "\n";

        sxml += toXML_Configuration_Sequences("ReacherSequence", 2);
        sxml += toXML_Configuration_Sequences("_SolverSequence", 3);

        sxml += toXML_Configuration("Reacher", 2);
        sxml += toXML_Configuration("_Solver", 3);

        return sxml + "		</Prototype_Configuration>" + LP.newln;
    }

    public String toString() {
        String str = "GProtD::";

        str += LP.newln + "id=" + id + LP.newln;
        str += super.toString() + LP.newln;
        str += "Reachers:" + LP.newln + getComs(2);
        str += "Solvers: " + LP.newln + getComs(3);
        str += "End Of GProtD." + LP.newln;

        return str;
    }

///////////////////////////////////////////////////////////////////////////////
//  Protected Methods
/////////////////////////
    protected String toXML_Configuration(String tagName, int tableField) {
        String strXML = "			";

        for (int i = 0; i < table.size(); i++) {
            strXML += "<" + tagName + ">" + (table.get(tableField, i))
                    + "</" + tagName + ">" + "\n";
        }

        return strXML;
    }

    protected String toXML_Configuration_Sequences(String tagName, int tableField) {
        String strXML = "			";
        ComArray ca = new ComArray((table.get(tableField, 0)));
        strXML += "<" + tagName + ">" + ca.toString() + "</" + tagName + ">" + "\n";

        return strXML;
    }

////////////////////////////////////////////////////////////
//Private Methods
/////////////////////
    private String getComs(int field) {
        String coms = "";
        int size = table.size(), line;

        for (int index = 0; index < size; index++) {
            if (table.get(field, index) != -1) {
                line = index + 1;
                ComArray com = new ComArray(table.get(field, index));
                coms += "Line" + line + "  " + com.toString() + LP.newln;
            }
        }
        return coms;
    }

}
