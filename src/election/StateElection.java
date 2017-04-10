package election;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class StateElection
{
  private State state;
  private int electoralVotes;

  private HashMap<Candidate, Integer> results;

  /**
   * @param votes
   * @param evResults
   * @param electoralVotes
   */
  public StateElection(String abbreviation, int electoralVotes)
  {
    state = State.fromAbbreviation(abbreviation);
    this.electoralVotes = electoralVotes;

    results = new HashMap<Candidate, Integer>();
  }

  public void addVoteResult(Candidate cand, Integer votes)
  {
    results.put(cand, votes);
  }

  /**
   * Assign the state's electoral votes
   */
  public void assignVotes()
  {
    HashMap.Entry<Candidate, Integer> max = null;

    for (HashMap.Entry<Candidate, Integer> result : results.entrySet())
    {
      if (max == null || result.getValue() > max.getValue())
        max = result;
    }
    max.getKey().addElectoralVotes(electoralVotes);
  }

  /**
   * @return the state
   */
  public State getState()
  {
    return state;
  }

  public static StateElection parseStateElection(String line,
      ArrayList<Candidate> candidates)
  {
    StateElection result;
    String abbreviation;
    String[] split;
    int i = 0;

    split = line.split(",");
    if (split.length != 3 + candidates.size())
      throw new IllegalArgumentException("malformed vote line");

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
        result = new StateElection(abbreviation,
            Integer.parseInt(split[i++]));
      }

      result.parseVotes(split, i, candidates);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new IllegalArgumentException(
          "malformed state entry: " + e.toString());
    }

    return result;
  }

  void parseVotes(String[] split, int offset, ArrayList<Candidate> candidates)
  {

    for (int i = 0; i < candidates.size(); i++)
    {
      addVoteResult(candidates.get(i), Integer.parseInt(split[i + offset]));
    }
  }
}
