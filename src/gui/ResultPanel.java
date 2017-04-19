package gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import election.Candidate;
import election.FederalElection;
import io.ResourceFinder;
import visual.Visualization;
import visual.VisualizationView;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ResultPanel extends JPanel
{
  FederalElection election;
  File dir;
  ContentFactory cf;
  Visualization visualization;

  public ResultPanel(FederalElection election, File dir, ResourceFinder rf)
      throws IOException
  {
    super(null);

    this.election = election;
    this.dir = dir;
    this.cf = new ContentFactory(rf);

    int numCands;
    VisualizationView view;

    numCands = election.getCandidates().size();
    cf = new ContentFactory(rf);
    visualization = new Visualization();

    view = visualization.getView();
    view.setBounds(0, 0, 700, 500);

    for (int i = 0; i < numCands; i++)
    {
      addCandidate(i);
    }

    add(view);
    repaint();
  }

  private void addCandidate(int i) throws IOException
  {
    Candidate cand;
    Content c;
    Dimension size;
    int x, y;
    JLabel label;
    JProgressBar popular;
    JProgressBar electoral;

    x = i % 2 == 0 ? 130 : 490;
    // TODO deal with more than two cands
    y = 100;

    cand = election.getCandidates().get(i);

    label = new JLabel(cand.toString());
    add(label);
    size = label.getPreferredSize();
    label.setBounds(x + 90 - size.width / 2, y - 35, size.width, size.height);
    label.setHorizontalAlignment(JLabel.CENTER);

    // popular vote bar
    popular = new JProgressBar(0, election.getTotalVotes());
    popular.setForeground(cand.getParty().getColor());
    popular.setValue(cand.getVotes());
    popular.setString(cand.getVotes() + " votes");
    popular.setStringPainted(true);
    add(popular);
    size = popular.getPreferredSize();
    popular.setBounds(x + 90 - size.width / 2, y + 260, size.width,
        size.height);

    // electoral vote bar
    electoral = new JProgressBar(0, election.getTotalElectoralVotes());
    electoral.setForeground(cand.getParty().getColor());
    electoral.setValue(cand.getElectoralVotes());
    electoral.setString(cand.getElectoralVotes() + " electoral votes");
    electoral.setStringPainted(true);
    add(electoral);
    size = electoral.getPreferredSize();
    electoral.setBounds(x + 90 - size.width / 2, y + 280, size.width,
        size.height);

    // TODO placeholder images
    if (dir.isAbsolute())
    {
      c = cf.createContent(ImageIO.read(new File(dir, i + ".jpg")));
    }
    else
    {
      c = cf.createContent(
          File.separator + dir.toString() + File.separator + i + ".jpg");
    }

    c.setLocation(x, 100);
    visualization.add(c);
  }
}
