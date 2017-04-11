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
  static private FinalProjectApp app;
  static private FinalProjectApplication instance;

  public FinalProjectApplication(String[] args, int width, int height)
  {
    super(args, app = new FinalProjectApp(), width, height);
  }

  public static void main(String[] args) throws Exception
  {
    SwingUtilities.invokeAndWait(instance = new FinalProjectApplication(args, 800, 600));
    instance.mainWindow.setJMenuBar(app.getJMenuBar());
  }
}
