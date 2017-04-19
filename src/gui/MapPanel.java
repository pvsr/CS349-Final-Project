package gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;

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

  public MapPanel(ResourceFinder rf, String dir)
  {
    super();

    // setBackground(Color.WHITE);
    Content map;
    ContentFactory cf = new ContentFactory(rf);
    Visualization v;

    map = cf.createContent(File.separator + dir + File.separator + "states.png", 4);
    v = new Visualization();
    v.add(map);

    setLayout(new BorderLayout());
    add(v.getView(), BorderLayout.CENTER);
  }
}
