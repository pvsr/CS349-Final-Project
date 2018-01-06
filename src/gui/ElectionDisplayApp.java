package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import app.AbstractMultimediaApp;
import io.ResourceFinder;

/**
 * The main program logic.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ElectionDisplayApp extends AbstractMultimediaApp
    implements ActionListener
{
  private JFileChooser fc;
  private ResourceFinder rf;
  private JTabbedPane tabbedPane;

  /**
   * Construct a JMenuBar.
   * 
   * @return The menu bar
   */
  public JMenuBar buildJMenuBar()
  {
    JMenuBar menuBar;
    JMenu file, help;
    JMenuItem load, copyright;

    menuBar = new JMenuBar();

    file = new JMenu("File");
    menuBar.add(file);
    load = new JMenuItem("Load File");
    load.addActionListener(this);
    file.add(load);

    help = new JMenu("Help");
    menuBar.add(help);
    copyright = new JMenuItem("Copyright Information");
    copyright.addActionListener(this);
    help.add(copyright);

    return menuBar;
  }

  @Override
  public void init()
  {
    Container parent;
    JPanel contentPane;

    contentPane = (JPanel) rootPaneContainer.getContentPane();
    contentPane.setLayout(new BorderLayout());

    tabbedPane = new JTabbedPane();
    contentPane.add(tabbedPane, BorderLayout.CENTER);

    rf = ResourceFinder.createInstance(this);

    // hack to add a menubar. The JFrame or JApplet isn't conveniently
    // accessible through the multimedia.jar framework, so I instead go up the
    // component hierarchy until I find a place to add the menu bar
    parent = contentPane;
    do
    {
      parent = parent.getParent();
    }
    while (!(parent instanceof JApplet || parent instanceof JFrame
        || parent == null));

    if (parent instanceof JApplet)
    {
      ((JApplet) parent).setJMenuBar(buildJMenuBar());
    }
    else if (parent instanceof JFrame)
    {
      ((JFrame) parent).setJMenuBar(buildJMenuBar());
    }

    try
    {
      tabbedPane.add("2008", new TabbedElectionPane(rf, null));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(parent, e.toString(), "Error!",
          JOptionPane.ERROR_MESSAGE);
    }

    tabbedPane.setSize(tabbedPane.getPreferredSize());
    contentPane.setVisible(true);
  }

  @Override
  public void destroy()
  {
    System.exit(1);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    JPanel contentPane = (JPanel) rootPaneContainer.getContentPane();
    if (e.getActionCommand().equals("Load File"))
    {
      addElection(contentPane);
    }
    else if (e.getActionCommand().equals("Copyright Information"))
    {
      JOptionPane.showMessageDialog(contentPane,
          "<html><body><p style='width: 375px;'>All datasets provided with "
              + "this application are derived from Creative Commons "
              + "Attribution-Share-Alike Licensed content, including content "
              + "from Wikipedia and the National Archives and Records "
              + "Administration. For more information, see data"
              + File.separator + "copyright.md in your .jar file or on "
              + "the project's website at https://github.com/pvsr/"
              + "CS349-Final-Project.</p></body></html>",
          "Copyright Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /**
   * Get a file from the user and add it to the pane.
   *
   * @param parent Parent component of dialogs
   */
  private void addElection(JPanel parent)
  {
    if (fc == null)
      fc = new JFileChooser();
    File file;
    int returnValue = fc.showOpenDialog(parent);
    if (returnValue == JFileChooser.APPROVE_OPTION)
    {
      file = fc.getSelectedFile();

      try
      {
        tabbedPane.add(file.getParentFile().getName(),
            new TabbedElectionPane(rf, file.getParentFile()));
      }
      catch (IOException exception)
      {
        JOptionPane.showMessageDialog(parent, exception.toString(), "Error!",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
