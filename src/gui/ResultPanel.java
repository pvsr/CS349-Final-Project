package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
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
 * Main election result panel.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ResultPanel extends JPanel implements ActionListener
{
  private Clip quote;
  private FederalElection election;
  private File dataDir;
  private ContentFactory cf;
  private Visualization visualization;

  /**
   * Explicit value constructor.
   * 
   * @param election The election to show
   * @param dataDir The directory that contains images and audio
   * @param rf A ResourceFinder
   * @param quote An audio quote
   * @throws IOException
   */
  public ResultPanel(FederalElection election, File dataDir, ResourceFinder rf,
      Clip quote) throws IOException
  {
    super(null);

    this.election = election;
    this.dataDir = dataDir;
    this.cf = new ContentFactory(rf);
    this.quote = quote;

    int numCands;
    Dimension size;
    JButton playButton;
    VisualizationView view;

    numCands = election.getCandidates().size();
    cf = new ContentFactory(rf);
    visualization = new Visualization();

    view = visualization.getView();
    view.setBounds(0, 0, 800, 600);

    // only place play button if there's a quote to play
    if (quote != null)
    {
      playButton = new JButton("Replay quote");
      add(playButton);
      size = playButton.getPreferredSize();
      playButton.setBounds(400 - size.width / 2, 10, size.width, size.height);
      playButton.addActionListener(this);
    }

    for (int i = 0; i < numCands; i++)
    {
      // slightly less than 800 for breathing room
      int sectionWidth = 795 / (numCands);
      // center candidate in their section, assuming image width is 180
      // works well for 2-4 candidates
      addCandidate(i, (sectionWidth / 2 - 90) + sectionWidth * i);
    }

    add(view);
    repaint();
  }

  /**
   * Add a candidate to the panel.
   * 
   * @param i The candidate's index
   * @param x The x-value of the candidate's section
   * @throws IOException
   */
  private void addCandidate(int i, int x) throws IOException
  {
    Candidate cand;
    Content c;
    Dimension size;
    int y;
    JLabel label;
    JProgressBar popular;
    JProgressBar electoral;

    y = 100;

    cand = election.getCandidates().get(i);

    label = new JLabel(cand.toString());
    add(label);
    size = label.getPreferredSize();
    label.setBounds(x + 90 - size.width / 2, y - 50, size.width, size.height);
    label.setHorizontalAlignment(JLabel.CENTER);

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

    // handle external directories separately from those in the .jar
    if (dataDir.isAbsolute())
    {
      c = cf.createContent(ImageIO.read(new File(dataDir, i + ".jpg")));
    }
    else
    {
      c = cf.createContent(dataDir.getPath() + File.separator + i + ".jpg");
    }

    c.setLocation(x, y);
    visualization.add(c);
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
    quote.setFramePosition(0);
    quote.start();
  }
}
