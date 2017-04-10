package election;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class Candidate
{
  private Party party;
  private String presCand;
  private String vicePresCand;
  private int votes;
  private int electoralVotes;

  public Candidate(Party party, String presCand, String vicePresCand)
  {
    this.party = party;
    this.presCand = presCand;
    this.vicePresCand = vicePresCand;

    votes = 0;
    electoralVotes = 0;
  }

  public void addElectoralVotes(int votes)
  {
    electoralVotes += votes;
  }

  /**
   * @return the number of electoral votes
   */
  public int getElectoralVotes()
  {
    return electoralVotes;
  }

  /**
   * @return the candidate's party
   */
  public Party getParty()
  {
    return party;
  }

  /**
   * @return the presidential candidate's name
   */
  public String getPresCand()
  {
    return presCand;
  }

  /**
   * @return the vice presidential candidate's name
   */
  public String getVicePresCand()
  {
    return vicePresCand;
  }

  /**
   * @return the votes
   */
  public int getVotes()
  {
    return votes;
  }

  public static Candidate parseCandidate(String line)
  {
    StringTokenizer st;

    st = new StringTokenizer(line, ",");

    try
    {
      return new Candidate(Party.fromAbbreviation(st.nextToken()),
          st.nextToken(), st.nextToken());
    }
    catch (NoSuchElementException e)
    {
      throw new IllegalArgumentException("malformed candidate entry");
    }
  }

  /**
   * @param votes the number of votes to set
   */
  public void setVotes(int votes)
  {
    this.votes = votes;
  }

  @Override
  public String toString()
  {
    return party.toString() + ": " + presCand + " / " + vicePresCand + " - votes: "
        + votes + " electoral votes: " + electoralVotes;
  }

}
