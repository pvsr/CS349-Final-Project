package election;

/**
 * A presidential election candidate.
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

  /**
   * Explicit value constructor.
   * 
   * @param party The candidate's party (independent counts as a party)
   * @param presCand The presidential candidate's name
   * @param vicePresCand The vice-presidential candidate's name
   */
  public Candidate(Party party, String presCand, String vicePresCand)
  {
    this.party = party;
    this.presCand = presCand;
    this.vicePresCand = vicePresCand;

    votes = 0;
    electoralVotes = 0;
  }

  /**
   * Add electoral votes to the candidate's total.
   * 
   * @param newVotes The number of new votes
   */
  public void addElectoralVotes(int newVotes)
  {
    electoralVotes += newVotes;
  }

  /**
   * Getter for electoralVotes.
   * 
   * @return The number of electoral votes
   */
  public int getElectoralVotes()
  {
    return electoralVotes;
  }

  /**
   * Getter for party.
   * 
   * @return The candidate's party
   */
  public Party getParty()
  {
    return party;
  }

  /**
   * Getter for presCand.
   * 
   * @return The presidential candidate's name
   */
  public String getPresCand()
  {
    return presCand;
  }

  /**
   * Getter for vicePresCand.
   * 
   * @return The vice presidential candidate's name
   */
  public String getVicePresCand()
  {
    return vicePresCand;
  }

  /**
   * Getter for votes.
   * 
   * @return The number of votes
   */
  public int getVotes()
  {
    return votes;
  }

  /**
   * Parse a line from the input csv into a Candidate.
   * 
   * @param line The input line
   * @return The result
   */
  public static Candidate parseCandidate(String line)
  {
    String[] split;
    int i = 0;

    split = line.split(",");

    // expected width of the row
    if (split.length != 3)
      throw new IllegalArgumentException("malformed candidate entry");

    return new Candidate(Party.fromAbbreviation(split[i++]), split[i++],
        split[i++]);
  }

  /**
   * Setter for votes.
   * 
   * @param votes The new number of votes
   */
  public void setVotes(int votes)
  {
    this.votes = votes;
  }

  @Override
  public String toString()
  {
    return "<html><center>" + presCand + "<br>" + vicePresCand + "<br>"
        + party.getName() + "</center></html>";
  }

}
