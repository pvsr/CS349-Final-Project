package gui;

import app.MultimediaApplication;

import javax.swing.*;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class FinalProjectApplication extends MultimediaApplication
{
  static private FinalProjectApp decorated;
  static private FinalProjectApplication instance;

  public FinalProjectApplication(String[] args, int width, int height)
  {
    super(args, decorated = new FinalProjectApp(), width, height);
  }

  public static void main(String[] args) throws Exception
  {
    SwingUtilities.invokeAndWait(instance = new FinalProjectApplication(args, 600, 400));
    instance.mainWindow.setJMenuBar(decorated.getJMenuBar());
  }
}
