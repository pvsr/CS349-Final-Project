package election;

import java.util.ArrayList;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class FederalElection
{
  private ArrayList<Candidate> cands;
  private ArrayList<StateElection> states;

  public FederalElection()
  {
    cands = new ArrayList<Candidate>();
    states = new ArrayList<StateElection>();
  }

  public void addCandidate(Candidate candidate)
  {
    cands.add(candidate);
  }

  public void addState(StateElection state)
  {
    states.add(state);
  }

  public void countVotes()
  {
    for (StateElection se : states)
    {
      se.assignVotes();
    }
  }

  /**
   * @return the list of candidates
   */
  public ArrayList<Candidate> getCandidates()
  {
    return cands;
  }

  /**
   * @return the list of states
   */
  public ArrayList<StateElection> getStates()
  {
    return states;
  }
}
