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
public class IrregularStateElection extends StateElection
{
  private HashMap<Candidate, Integer> evResults;

  /**
   * @param abbreviation
   * @param electoralVotes
   */
  public IrregularStateElection(String abbreviation, int electoralVotes)
  {
    super(abbreviation, electoralVotes);
    evResults = new HashMap<Candidate, Integer>();
  }

  public void addElectoralVoteResult(Candidate cand, Integer eVotes)
  {
    evResults.put(cand, eVotes);
  }

  /**
   * Assign the state's electoral votes
   */
  public void assignVotes()
  {
    for (HashMap.Entry<Candidate, Integer> result : evResults.entrySet())
      result.getKey().addElectoralVotes(result.getValue());
  }

  @Override void parseVotes(String[] split, int offset, ArrayList<Candidate> candidates)
  {
    String[] votes;

    for (int i = 0; i < candidates.size(); i++)
    {
      votes = split[i + offset].split("\\|");
      if (votes.length != 2)
        throw new IllegalArgumentException("malformed vote line");
      addVoteResult(candidates.get(i), Integer.parseInt(votes[0]));
      addElectoralVoteResult(candidates.get(i), Integer.parseInt(votes[1]));
    }
  }
}
