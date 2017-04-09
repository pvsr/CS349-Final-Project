package gui;

import app.MultimediaApplication;

import javax.swing.SwingUtilities;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class FinalProjectApplication extends MultimediaApplication
{
  public FinalProjectApplication(String[] args, int width, int height)
  {
    super(args, new FinalProjectApp(), width, height);
  }

  public static void main(String[] args) throws Exception
  {
    SwingUtilities.invokeAndWait(new FinalProjectApplication(args, 600, 400));
  }
}
