package gui;

import app.AbstractMultimediaApp;
import election.ElectionFactory;
import election.FederalElection;
import io.ResourceFinder;

import java.awt.FlowLayout;
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

  @Override
  public void init()
  {
    FederalElection election;
    JPanel contentPane;
    JLabel[] labels;
    ResourceFinder rf;

    contentPane = (JPanel)rootPaneContainer.getContentPane();
    contentPane.setLayout(new FlowLayout());

    rf = ResourceFinder.createInstance();

    try
    {
      election = ElectionFactory.createFederalElection(rf.findInputStream(
          DATA_PATH + "2008" + File.separator + "president.csv"));
      election.countVotes();

      labels = new JLabel[election.getCandidates().size()];

      for (int i = 0; i < election.getCandidates().size(); i++)
      {
        labels[i] = new JLabel(election.getCandidates().get(i).toString());
        contentPane.add(labels[i]);
      }

      contentPane.setVisible(true);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
