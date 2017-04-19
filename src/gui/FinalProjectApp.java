package gui;

import app.AbstractMultimediaApp;
import election.Candidate;
import election.ElectionFactory;
import election.FederalElection;
import io.ResourceFinder;
import visual.Visualization;
import visual.VisualizationView;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class FinalProjectApp extends AbstractMultimediaApp
    implements ActionListener
{
  private static final String DATA_PATH = "data" + File.separator;
  private static final String RESOURCE_PATH = "resources" + File.separator;
  private static JFileChooser fc = null;

  private ArrayList<FederalElection> elections;
  private ResourceFinder rf;
  private JTabbedPane tabbedPane;

  private void addNewElection(InputStream in, File dir) throws IOException
  {
    FederalElection election;

    election = ElectionFactory.createFederalElection(in);
    election.countVotes();
    tabbedPane.addTab(election.getTitle(), new ResultPanel(election, dir, rf));
    elections.add(election);
  }

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
    MapPanel map;

    contentPane = (JPanel) rootPaneContainer.getContentPane();
    contentPane.setLayout(new BorderLayout());

    tabbedPane = new JTabbedPane();
    contentPane.add(tabbedPane, BorderLayout.CENTER);

    rf = ResourceFinder.createInstance();
    map = new MapPanel(rf, RESOURCE_PATH);

    elections = new ArrayList<FederalElection>();

    // terrible hack to add a menubar
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
      addNewElection(
          rf.findInputStream(File.separator + DATA_PATH + "2008"
              + File.separator + "president.csv"),
          new File(DATA_PATH + "2008"));
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(contentPane, e.toString(), "Error!",
          JOptionPane.ERROR_MESSAGE);
      destroy();
    }

    tabbedPane.add("Map", new MapPanel(rf, RESOURCE_PATH));

    tabbedPane.setSize(tabbedPane.getPreferredSize());
    contentPane.setVisible(true);
  }

  @Override
  public void destroy()
  {
    System.exit(1);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (fc == null) fc = new JFileChooser();
    JPanel contentPane = (JPanel) rootPaneContainer.getContentPane();
    if (e.getActionCommand().equals("Load File"))
    {
      File file;
      int returnValue = fc.showOpenDialog(contentPane);
      if (returnValue == JFileChooser.APPROVE_OPTION)
      {
        file = fc.getSelectedFile();

        try
        {
          addNewElection(new FileInputStream(file), file.getParentFile());
        }
        catch (IOException exception)
        {
          // TODO execute this on the event dispatch thread
          JOptionPane.showMessageDialog(contentPane, exception.toString(),
              "Error!", JOptionPane.ERROR_MESSAGE);
        }
      }
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
}
