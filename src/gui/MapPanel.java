package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import election.FederalElection;
import election.StateElection;
import io.ResourceFinder;
import visual.Visualization;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 * A panel that contains a map with color-coded results.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class MapPanel extends JPanel
{

  /**
   * Explicit value constructor.
   * 
   * @param election The election to show
   * @param rf A ResourceFinder
   */
  public MapPanel(FederalElection election, ResourceFinder rf)
  {
    super();

    Content c;
    ContentFactory cf = new ContentFactory(rf);
    Visualization v;

    v = new Visualization();

    // each state is stored as a separate image. It's inefficient, but simple
    // and allows states that didn't exist at the time be excluded
    for (StateElection state : election.getStates())
    {
      c = cf.createContent(state.getState().getAbbreviation() + ".png", 4);
      c.setBufferedImageOp(
          ColorFilterOpFactory.getOp(state.getWinner().getParty().getColor()));
      v.add(c);
    }

    setLayout(new BorderLayout());
    add(v.getView(), BorderLayout.CENTER);
  }
}
