package gui;

import app.MultimediaApplication;

import javax.swing.*;

/**
 * Application version of the program.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ElectionDisplayApplication extends MultimediaApplication
{
  public ElectionDisplayApplication(String[] args, int width, int height)
  {
    super(args, new FinalProjectApp(), width, height);
  }

  public static void main(String[] args) throws Exception
  {
    SwingUtilities.invokeAndWait(new ElectionDisplayApplication(args, 800, 600));
  }
}
