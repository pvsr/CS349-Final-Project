package election;

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
	String[] split;
	int i = 0;

    split = line.split(",");

    if (split.length != 3)
      throw new IllegalArgumentException("malformed candidate entry");

    return new Candidate(Party.fromAbbreviation(split[i++]),
	    split[i++], split[i++]);
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
