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
  private static final String RESOURCE_PATH = ".." + File.separator
      + "resources" + File.separator;

  private Clip clip;
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

    findQuote();
    addTab("Results", new ResultPanel(election, dataDir, rf, clip != null));
    add("Map", new MapPanel(election, RESOURCE_PATH, rf));
    if (clip != null)
      clip.start();
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
   * @throws IOException
   */
  private void findQuote() throws IOException
  {
    InputStream is;
    BufferedInputStream bis;
    AudioInputStream stream;

    try
    {
      is = findResource("quote.wav");
    }
    catch (FileNotFoundException e)
    {
      clip = null;
      return;
    }

    bis = new BufferedInputStream(is);
    try
    {
      stream = AudioSystem.getAudioInputStream(bis);
      clip = AudioSystem.getClip();
      clip.open(stream);
    }
    catch (UnsupportedAudioFileException | LineUnavailableException e)
    {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }
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
