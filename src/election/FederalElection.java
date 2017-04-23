package election;

import java.util.ArrayList;

/**
 * A record of a federal election.
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
  private int totalVotes;
  private int totalElectoralVotes;

  /**
   * Default constructor.
   */
  public FederalElection()
  {
    cands = new ArrayList<Candidate>();
    states = new ArrayList<StateElection>();
  }

  /**
   * Add a new candidate.
   * 
   * @param candidate The candidate to add
   */
  public void addCandidate(Candidate candidate)
  {
    cands.add(candidate);
  }

  /**
   * Add a new state election.
   * 
   * @param state The state election to add
   */
  public void addState(StateElection state)
  {
    states.add(state);
  }

  /**
   * Getter for candidates.
   * 
   * @return The list of candidates
   */
  public ArrayList<Candidate> getCandidates()
  {
    return cands;
  }

  /**
   * Getter for states.
   * 
   * @return The list of state elections
   */
  public ArrayList<StateElection> getStates()
  {
    return states;
  }

  /**
   * Getter for title.
   * 
   * @return The title of the election
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Getter for totalElectoralVotes.
   * 
   * @return The number of electoral votes
   */
  public int getTotalElectoralVotes()
  {
    return totalElectoralVotes;
  }

  /**
   * Getter for totalVotes.
   * 
   * @return The number of votes
   */
  public int getTotalVotes()
  {
    return totalVotes;
  }

  /**
   * Setter for title.
   * 
   * @param title The new title
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * Setter for totalElectoralVotes.
   * 
   * @param totalElectoralVotes The new number of electoral votes
   */
  public void setTotalElectoralVotes(int totalElectoralVotes)
  {
    this.totalElectoralVotes = totalElectoralVotes;
  }

  /**
   * Setter for totalVotes.
   * 
   * @param totalVotes The new number of votes
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
