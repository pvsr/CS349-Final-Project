package gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
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
  File dataDir;
  ContentFactory cf;
  Visualization visualization;

  public ResultPanel(FederalElection election, File dataDir, ResourceFinder rf)
      throws IOException
  {
    super(null);

    this.election = election;
    this.dataDir = dataDir;
    this.cf = new ContentFactory(rf);

    int numCands;
    VisualizationView view;

    numCands = election.getCandidates().size();
    cf = new ContentFactory(rf);
    visualization = new Visualization();

    view = visualization.getView();
    view.setBounds(0, 0, 800, 600);

    for (int i = 0; i < numCands; i++)
    {
      int sectionWidth = 780 / (numCands);
      // assuming img width is 180, center candidate in their section
      // works well for 2-4 candidates
      addCandidate(i, (sectionWidth / 2 - 90) + sectionWidth * i);
    }

    add(view);
    repaint();
  }

  private void addCandidate(int i, int x) throws IOException
  {
    Candidate cand;
    Content c;
    Dimension size;
    int y;
    JButton playButton;
    JLabel label;
    JProgressBar popular;
    JProgressBar electoral;
    
    y = 100;

    cand = election.getCandidates().get(i);

    label = new JLabel(cand.toString());
    add(label);
    size = label.getPreferredSize();
    label.setBounds(x + 90 - size.width / 2, y - 35, size.width, size.height);
    label.setHorizontalAlignment(JLabel.CENTER);
    
    // play button
    playButton = new JButton("Replay quote");
    add(playButton);
    size = playButton.getPreferredSize();
    playButton.setBounds(400 - size.width / 2, y - 100, size.width, size.height);

    // popular vote bar
    popular = new JProgressBar(0, election.getTotalVotes());
    popular.setForeground(cand.getParty().getColor());
    popular.setValue(cand.getVotes());
    popular.setString(cand.getVotes() + " votes");
    popular.setStringPainted(true);
    add(popular);
    size = popular.getPreferredSize();
    size.setSize(size.width + 25, size.height + 15);
    popular.setBounds(x + 90 - size.width / 2, y + 270, size.width,
        size.height);

    // electoral vote bar
    electoral = new JProgressBar(0, election.getTotalElectoralVotes());
    electoral.setForeground(cand.getParty().getColor());
    electoral.setValue(cand.getElectoralVotes());
    electoral.setString(cand.getElectoralVotes() + " electoral votes");
    electoral.setStringPainted(true);
    add(electoral);
    size = electoral.getPreferredSize();
    size.setSize(size.width + 25, size.height + 15);
    electoral.setBounds(x + 90 - size.width / 2, y + 310, size.width,
        size.height);

    // TODO placeholder images
    if (dataDir.isAbsolute())
    {
      c = cf.createContent(ImageIO.read(new File(dataDir, i + ".jpg")));
    }
    else
    {
      c = cf.createContent(
          dataDir.getPath() + File.separator + i + ".jpg");
    }

    c.setLocation(x, y);
    visualization.add(c);
  }
}
