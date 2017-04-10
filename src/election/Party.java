package election;

import java.awt.Color;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public enum Party
{
  Democratic("D", "Democratic", "Democratic Party", Color.BLUE),
  Republican("R", "Republican", "Republican Party", Color.RED);

  private String abbreviation;
  private String adjective;
  private String name;
  private Color color;

  /**
   * @param abbreviation
   * @param adjective
   * @param name
   * @param color
   */
  private Party(String abbreviation, String adjective, String name, Color color)
  {
    this.abbreviation = abbreviation;
    this.adjective = adjective;
    this.name = name;
    this.color = color;
  }

  public static Party fromAbbreviation(String abbreviation)
  {
    for (Party party : Party.values()) {
      if (abbreviation.equals(party.abbreviation)) {
        return party;
      }
    }

    throw new IllegalArgumentException("unknown party abbreviation " + abbreviation);
  }

  /**
   * @return the abbreviation
   */
  public String getAbbreviation()
  {
    return abbreviation;
  }

  /**
   * @return the adjective
   */
  public String getAdjective()
  {
    return adjective;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @return the color
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
