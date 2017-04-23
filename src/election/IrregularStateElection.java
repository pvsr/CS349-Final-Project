package election;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A state election that does not distribute all electoral votes to the
 * plurality vote winner.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class IrregularStateElection extends StateElection
{
  private HashMap<Candidate, Integer> evResults;

  /**
   * Explicit value constructor.
   * 
   * @param abbreviation The state's abbreviation.
   * @param electoralVotes The state's number of electoral votes.
   */
  public IrregularStateElection(String abbreviation, int electoralVotes)
  {
    super(abbreviation, electoralVotes);
    evResults = new HashMap<Candidate, Integer>();
  }

  /**
   * Assign the state's electoral votes to candidates.
   */
  public void assignVotes()
  {
    for (HashMap.Entry<Candidate, Integer> result : evResults.entrySet())
      result.getKey().addElectoralVotes(result.getValue());
  }

  @Override
  void parseVotes(String[] split, int offset, ArrayList<Candidate> candidates)
  {
    String[] votes;

    for (int i = 0; i < candidates.size(); i++)
    {
      // handle special notation for non-winner-take-all states
      votes = split[i + offset].split("\\|");
      if (votes.length != 2)
        throw new IllegalArgumentException("malformed vote line");
      addVoteResult(candidates.get(i), Integer.parseInt(votes[0]));
      evResults.put(candidates.get(i), Integer.parseInt(votes[1]));
    }
  }
}
