package gui;

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
  static private FinalProjectApp app;

  public FinalProjectApplet()
  {
    super(app = new FinalProjectApp());
    setJMenuBar(app.getJMenuBar());
  }
}
