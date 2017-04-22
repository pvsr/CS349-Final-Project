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
 *
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

  public TabbedElectionPane(ResourceFinder rf, File dataDir) throws IOException
  {
    this.dataDir = dataDir;
    this.rf = rf;
    election = ElectionFactory
        .createFederalElection(findResource("president.csv"));

    addTab("Results", new ResultPanel(election, dataDir, rf));
    add("Map", new MapPanel(election, RESOURCE_PATH, rf));
    findQuote();
    if (clip != null)
      clip.start();
  }

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
          dataDir + File.separator + name + "not found");
    }
    else
      return result;
  }

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
   * @return the election
   */
  public FederalElection getElection()
  {
    return election;
  }
}
