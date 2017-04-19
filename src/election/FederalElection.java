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
  private ArrayList<ElectionListener> listeners;
  private String title;
  private int totalVotes;
  private int totalElectoralVotes;

  public FederalElection()
  {
    cands = new ArrayList<Candidate>();
    states = new ArrayList<StateElection>();
    listeners = new ArrayList<ElectionListener>();
  }

  public void addCandidate(Candidate candidate)
  {
    cands.add(candidate);
  }

  public void addState(StateElection state)
  {
    for (ElectionListener l : listeners)
    {
      l.handleState(state);
    }
    states.add(state);
  }

  public void addStateListener(ElectionListener l)
  {
    listeners.add(l);
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

  /**
   * @return the number of electoral votes
   */
  public int getTotalElectoralVotes()
  {
    return totalElectoralVotes;
  }

  /**
   * @return the number of votes
   */
  public int getTotalVotes()
  {
    return totalVotes;
  }

  public void setTitle(String title) {
	this.title = title;
  }

  /**
   * @param totalElectoralVotes the number of electoral votes
   */
  public void setTotalElectoralVotes(int totalElectoralVotes)
  {
    this.totalElectoralVotes = totalElectoralVotes;
  }

  /**
   * @param totalVotes the number of votes
   */
  public void setTotalVotes(int totalVotes)
  {
    this.totalVotes = totalVotes;
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
