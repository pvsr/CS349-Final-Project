package gui;

import app.AbstractMultimediaApp;
import election.ElectionFactory;
import election.FederalElection;
import io.ResourceFinder;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class FinalProjectApp extends AbstractMultimediaApp
{
  private static final String DATA_PATH = File.separator + "data"
      + File.separator;
  
  public JMenuBar getJMenuBar()
  {
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    menuBar = new JMenuBar();
    menu = new JMenu("test");
    menuBar.add(menu);
    menuItem = new JMenuItem("test item");
    menu.add(menuItem);
    return menuBar;
  }

  @Override
  public void init()
  {
    FederalElection election;
    JPanel contentPane;
    JTextArea textArea;
    ResourceFinder rf;

    contentPane = (JPanel)rootPaneContainer.getContentPane();

    rf = ResourceFinder.createInstance();

    try
    {
      election = ElectionFactory.createFederalElection(rf.findInputStream(
          DATA_PATH + "2008" + File.separator + "president.csv"));
    }
    catch (IOException e)
    {
      // silence variable initialization error
      election = null;
      e.printStackTrace();
      JOptionPane.showMessageDialog(contentPane, e.toString(), "Error!", JOptionPane.ERROR_MESSAGE);
      destroy();
    }

    election.countVotes();

    textArea = new JTextArea(election.toString());
    contentPane.add(textArea);
    contentPane.setVisible(true);
  }
  
  @Override
  public void destroy()
  {
    
  }
}
