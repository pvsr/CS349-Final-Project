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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
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
{
  private static final String DATA_PATH = File.separator + "data"
      + File.separator;

  private FederalElection election;
  private ResourceFinder rf;

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

  private JPanel buildResultTab()
  {
    Candidate cand;
    Content c;
    ContentFactory cf;
    Dimension size;
    Insets insets;
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
      popular.setBounds(x + 90 - size.width / 2, y + 260, size.width, size.height);


      electoral = new JProgressBar(0, election.getTotalElectoralVotes());
      electoral.setForeground(cand.getParty().getColor());
      electoral.setValue(cand.getElectoralVotes());
      electoral.setString(cand.getElectoralVotes() + " electoral votes");
      electoral.setStringPainted(true);
      result.add(electoral);
      size = electoral.getPreferredSize();
      electoral.setBounds(x + 90 - size.width / 2, y + 280, size.width, size.height);

      c = cf.createContent(DATA_PATH + "2008" + File.separator + i + ".jpg");
      c.setLocation(i % 2 == 0 ? 130 : 490, 100);
      visualization.add(c);
    }

    result.add(view);
    insets = result.getInsets();
    result.setSize(795, 552);
    result.repaint();
    return result;
  }

  @Override
  public void init()
  {
    JPanel contentPane;
    JTabbedPane tabbedPane;

    contentPane = (JPanel) rootPaneContainer.getContentPane();
    contentPane.setLayout(null);

    tabbedPane = new JTabbedPane();
    contentPane.add(tabbedPane);

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
}
