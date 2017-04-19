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
  private Candidate winner;

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
   * @return the winner
   */
  public Candidate getWinner()
  {
    return winner;
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
    HashMap.Entry<Candidate, Integer> max;
    StateElection result;
    String abbreviation;
    String[] split;
    int i = 0;

    split = line.split(",");
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
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new IllegalArgumentException(
          "malformed state entry: " + e.toString());
    }

    max = null;

    for (HashMap.Entry<Candidate, Integer> state : result.results.entrySet())
    {
      if (max == null || state.getValue() > max.getValue())
        max = state;
    }

    result.winner = max.getKey();
    result.winner.addElectoralVotes(result.electoralVotes);

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
