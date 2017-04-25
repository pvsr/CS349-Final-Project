package election;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An election in one state.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class StateElection
{
  private boolean assigned;
  private Candidate winner;
  private int electoralVotes;
  private int totalVotes;
  private HashMap<Candidate, Integer> results;
  private State state;

  /**
   * Explicit value constructor.
   * 
   * @param abbreviation The abbreviation of the state
   * @param electoralVotes The number of electoral votes at stake
   */
  public StateElection(String abbreviation, int electoralVotes)
  {
    state = State.fromAbbreviation(abbreviation);
    this.electoralVotes = electoralVotes;

    results = new HashMap<Candidate, Integer>();
    assigned = false;
  }

  /**
   * Add a record of votes for a candidate.
   * 
   * @param cand The candidate
   * @param votes The number of votes
   */
  public void addVoteResult(Candidate cand, Integer votes)
  {
    results.put(cand, votes);
  }

  /**
   * Calculate a winner and assign the state's electoral votes.
   */
  public void calculateWinner()
  {
    HashMap.Entry<Candidate, Integer> max;

    max = null;

    // if votes were previously assigned, unassign them
    if (assigned)
    {
      winner.addElectoralVotes(-1 * electoralVotes);
    }

    for (HashMap.Entry<Candidate, Integer> entry : results.entrySet())
    {
      if (max == null || entry.getValue() > max.getValue())
        max = entry;
    }

    winner = max.getKey();
    winner.addElectoralVotes(electoralVotes);
  }

  /**
   * Getter for electoral votes.
   *
   * @return The number of electoral votes
   */
  public int getElectoralVotes()
  {
    return electoralVotes;
  }

  /**
   * Getter for results.
   *
   * @return The results
   */
  public HashMap<Candidate, Integer> getResults()
  {
    return results;
  }

  /**
   * Getter for state.
   * 
   * @return The state
   */
  public State getState()
  {
    return state;
  }

  /**
   * Getter for totalVotes.
   * @return The total number of votes
   */
  public int getTotalVotes()
  {
    return totalVotes;
  }

  /**
   * Getter for winner.
   * 
   * @return The winner
   */
  public Candidate getWinner()
  {
    return winner;
  }

  /**
   * Parse a line from the input csv into a StateElection.
   * 
   * @param line The input line
   * @param candidates The candidates running
   * @return The result
   */
  public static StateElection parseStateElection(String line,
      ArrayList<Candidate> candidates)
  {
    StateElection result;
    String abbreviation;
    String[] split;
    int i = 0;

    split = line.split(",");
    // expected width of the row
    if (split.length != 3 + candidates.size())
      throw new IllegalArgumentException("malformed vote line: " + line);

    try
    {
      abbreviation = split[i++];
      if (abbreviation.endsWith("*"))
      {
        result = new IrregularStateElection(abbreviation.substring(0, 2),
            Integer.parseInt(split[i++]));
      }
      else
      {
        result = new StateElection(abbreviation, Integer.parseInt(split[i++]));
      }

      result.parseVotes(split, i, candidates);
      
      i+= candidates.size();
      result.totalVotes = Integer.parseInt(split[i]);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new IllegalArgumentException(
          "malformed state entry: " + e.toString());
    }

    result.calculateWinner();

    return result;
  }

  /**
   * Parse a row of votes into the results. Exists so child classes can parse
   * votes differently.
   * 
   * @param split The vote numbers, in the same order as candidates
   * @param offset The offset into the row where votes start
   * @param candidates The list of candidates
   */
  void parseVotes(String[] split, int offset, ArrayList<Candidate> candidates)
  {
    for (int i = 0; i < candidates.size(); i++)
    {
      addVoteResult(candidates.get(i), Integer.parseInt(split[i + offset]));
    }
  }
}
