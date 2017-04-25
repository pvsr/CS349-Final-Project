package gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JTabbedPane;

import election.ElectionFactory;
import election.FederalElection;
import io.ResourceFinder;

/**
 * A tabbed pane containing other panels related to the same election.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class TabbedElectionPane extends JTabbedPane
{
  private FederalElection election;
  private File dataDir;
  private ResourceFinder rf;

  /**
   * Explicit value constructor.
   * 
   * @param rf A ResourceFinder
   * @param dataDir The directory that contains images and audio
   * @throws IOException
   */
  public TabbedElectionPane(ResourceFinder rf, File dataDir) throws IOException
  {
    this.dataDir = dataDir;
    this.rf = rf;
    election = ElectionFactory
        .createFederalElection(findResource("president.csv"));

    Clip clip = findQuote();
    addTab("Outcome", new ResultPanel(election, dataDir, rf, clip));
    addTab("Map", new MapPanel(election, rf));
    addTab("Detailed results", new TablePanel(election));
    if (clip != null)
    {
      clip.setFramePosition(0);
      clip.start();
    }
  }

  /**
   * Find a resource, handling external and internal files.
   * 
   * @param name The filename
   * @return An InputStream of the resource
   * @throws FileNotFoundException
   */
  private InputStream findResource(String name) throws FileNotFoundException
  {
    InputStream result;
    if (dataDir.isAbsolute())
    {
      result = new FileInputStream(new File(dataDir, name));
    }
    else
    {
      result = rf.findInputStream(dataDir + File.separator + name);
    }

    if (result == null)
    {
      throw new FileNotFoundException(
          dataDir + File.separator + name + " not found");
    }
    else
      return result;
  }

  /**
   * Find a quote audio file, if there is one.
   * 
   * @return The clip, or null
   * @throws IOException
   */
  private Clip findQuote() throws IOException
  {
    Clip result;
    InputStream is;
    BufferedInputStream bis;
    AudioInputStream stream;

    try
    {
      is = findResource("quote.wav");
    }
    catch (FileNotFoundException e)
    {
      return null;
    }

    bis = new BufferedInputStream(is);
    try
    {
      stream = AudioSystem.getAudioInputStream(bis);
      result = AudioSystem.getClip();
      result.open(stream);
    }
    catch (UnsupportedAudioFileException | LineUnavailableException e)
    {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }

    return result;
  }

  /**
   * Getter for election.
   * 
   * @return The election
   */
  public FederalElection getElection()
  {
    return election;
  }
}
