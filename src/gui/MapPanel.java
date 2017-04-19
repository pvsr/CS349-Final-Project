package gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;

import election.FederalElection;
import election.StateElection;
import io.ResourceFinder;
import visual.Visualization;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class MapPanel extends JPanel
{

  public MapPanel(FederalElection election, String statesDir, ResourceFinder rf)
  {
    super();

    // setBackground(Color.WHITE);
    Content map, c;
    ContentFactory cf = new ContentFactory(rf);
    Visualization v;

    map = cf.createContent(
        File.separator + statesDir + File.separator + "states.png", 4);
    v = new Visualization();
    v.add(map);

    for (StateElection state : election.getStates())
    {
      c = cf.createContent(File.separator + statesDir + "states"
          + File.separator + state.getState().getAbbreviation() + ".png", 4);
      c.setBufferedImageOp(
          new ColorFilterOp(state.getWinner().getParty().getColor()));
      v.add(c);
    }

    setLayout(new BorderLayout());
    add(v.getView(), BorderLayout.CENTER);
  }
}
