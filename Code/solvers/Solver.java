package solvers;

import prot.GProtD;
import prot.ProtD;
import util.Set;
import comm.ComArray;
import comm.CommandSequencer;

public interface Solver {

    public ComArray solve(ProtD config);

    /*
	 * Solve to ZERO position
	 * returns single solution as ComArray
     */
    public Set search(ProtD begin, ProtD end);

    /*
	 * search for solutions based on setting 
	 * usually minimum length solutions
     */
    public GProtD Complete(ProtD begin, ProtD end);

    /*
	 * returns a GProtD(end) with filled table
	 * that connects begin to end 
     */
    public void setCommandSequencer(CommandSequencer sequencer);

    /*
	 * set sequencer prior to search
     */

    public void setTimeLimit(float timeout);
    /*
	 * set time limit, solver will stop searching after time out 
     */

}
