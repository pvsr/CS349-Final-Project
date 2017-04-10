package gui;

import app.MultimediaApp;
import app.MultimediaApplet;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class FinalProjectApplet extends MultimediaApplet
{
  static private FinalProjectApp decorated;
  
  public FinalProjectApplet()
  {
    super(decorated = new FinalProjectApp());
    setJMenuBar(decorated.getJMenuBar());
  }
}
