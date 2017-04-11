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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
  private static final String DATA_PATH = File.separator + "data"
      + File.separator;

  private FederalElection election;
  private ResourceFinder rf;

  public JMenuBar getJMenuBar()
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

  private JPanel buildResultTab()
  {
    Candidate cand;
    Content c;
    ContentFactory cf;
    Dimension size;
    int numCands, x, y;
    JPanel result;
    Visualization visualization;
    VisualizationView view;

    numCands = election.getCandidates().size();
    cf = new ContentFactory(rf);
    visualization = new Visualization();
    result = new JPanel(null);

    view = visualization.getView();
    view.setBounds(0, 0, 700, 500);

    for (int i = 0; i < numCands; i++)
    {
      JLabel label;
      JProgressBar popular;
      JProgressBar electoral;
      x = i % 2 == 0 ? 130 : 490;
      // TODO deal with more than two cands
      y = 100;

      cand = election.getCandidates().get(i);

      label = new JLabel(cand.toString());
      result.add(label);
      size = label.getPreferredSize();
      label.setBounds(x + 90 - size.width / 2, y - 35, size.width, size.height);
      label.setHorizontalAlignment(JLabel.CENTER);

      popular = new JProgressBar(0, election.getTotalVotes());
      popular.setForeground(cand.getParty().getColor());
      popular.setValue(cand.getVotes());
      popular.setString(cand.getVotes() + " votes");
      popular.setStringPainted(true);
      result.add(popular);
      size = popular.getPreferredSize();
      popular.setBounds(x + 90 - size.width / 2, y + 260, size.width,
          size.height);

      electoral = new JProgressBar(0, election.getTotalElectoralVotes());
      electoral.setForeground(cand.getParty().getColor());
      electoral.setValue(cand.getElectoralVotes());
      electoral.setString(cand.getElectoralVotes() + " electoral votes");
      electoral.setStringPainted(true);
      result.add(electoral);
      size = electoral.getPreferredSize();
      electoral.setBounds(x + 90 - size.width / 2, y + 280, size.width,
          size.height);

      c = cf.createContent(DATA_PATH + "2008" + File.separator + i + ".jpg");
      c.setLocation(i % 2 == 0 ? 130 : 490, 100);
      visualization.add(c);
    }

    result.add(view);
    result.repaint();
    return result;
  }

  @Override
  public void init()
  {
    JPanel contentPane;
    JTabbedPane tabbedPane;

    contentPane = (JPanel) rootPaneContainer.getContentPane();
    contentPane.setLayout(new BorderLayout());

    tabbedPane = new JTabbedPane();
    contentPane.add(tabbedPane, BorderLayout.CENTER);

    rf = ResourceFinder.createInstance();

    try
    {
      election = ElectionFactory.createFederalElection(rf.findInputStream(
          DATA_PATH + "2008" + File.separator + "president.csv"));
    }
    catch (IOException e)
    {
      // silence variable initialization warning
      election = null;
      e.printStackTrace();
      JOptionPane.showMessageDialog(contentPane, e.toString(), "Error!",
          JOptionPane.ERROR_MESSAGE);
      destroy();
    }

    election.countVotes();

    tabbedPane.addTab("Result Overview", buildResultTab());
    JPanel a2 = buildResultTab();
    tabbedPane.addTab("Result Overview2", a2);
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
    if (e.getActionCommand().equals("Load File"))
    {
    }
    else if (e.getActionCommand().equals("Copyright Information"))
    {
      JOptionPane.showMessageDialog(rootPaneContainer.getContentPane(),
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
