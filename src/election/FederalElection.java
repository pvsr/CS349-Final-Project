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
  private String title;

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

  public String getTitle() {
	return title;
  }

  public void setTitle(String title) {
	this.title = title;
  }

  @Override
  public String toString()
  {
	  String result = title + ":\n";

	  for (Candidate c : cands)
	  {
		  result += c.toString() + "\n";
	  }

	  result += states.size() + " states";
	  return result;
  }
}
