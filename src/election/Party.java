package election;

import java.awt.Color;

/**
 * An enum of US political parties, including independents as their own party.
 * Only notable vote-getters in federal elections are included for now.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public enum Party
{
  Democratic("D", "Democratic", "Democratic Party", Color.BLUE),
  Republican("R", "Republican", "Republican Party", Color.RED),
  // Roosevelt's Bull-Moose Party and the post-WWII party of La Follette are
  // technically not the same party, but they share this entry
  Progressive("P", "Progressive", "Progressive Party", Color.GREEN),
  Socialist("S", "Socialist", "Socialist Party", new Color(0x79, 0, 0)),
  Independent("I", "Independent", "Independent", Color.LIGHT_GRAY);

  private String abbreviation;
  private String adjective;
  private String name;
  private Color color;

  /**
   * Explicit value constructor.
   * 
   * @param abbreviation The party's abbreviation
   * @param adjective The adjectival form of the party's name
   * @param name The party's name
   * @param color The party's color
   */
  private Party(String abbreviation, String adjective, String name, Color color)
  {
    this.abbreviation = abbreviation;
    this.adjective = adjective;
    this.name = name;
    this.color = color;
  }

  /**
   * Parse a Party from an abbreviation.
   * 
   * @param abbreviation The input abbreviation
   * @return The corresponding party
   */
  public static Party fromAbbreviation(String abbreviation)
  {
    for (Party party : Party.values())
    {
      if (abbreviation.equals(party.abbreviation))
      {
        return party;
      }
    }

    throw new IllegalArgumentException(
        "unknown party abbreviation " + abbreviation);
  }

  /**
   * Getter for abbreviation.
   * 
   * @return The abbreviation
   */
  public String getAbbreviation()
  {
    return abbreviation;
  }

  /**
   * Getter for adjective.
   * 
   * @return The adjective
   */
  public String getAdjective()
  {
    return adjective;
  }

  /**
   * Getter for name.
   * 
   * @return The name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Getter for color.
   * 
   * @return The color
   */
  public Color getColor()
  {
    return color;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
