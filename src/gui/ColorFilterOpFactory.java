package gui;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ColorFilterOpFactory
{
  private static HashMap<Color, ColorFilterOp> colorOps = new HashMap<Color, ColorFilterOp>();
  
  public static ColorFilterOp getOp(Color color)
  {
    ColorFilterOp result;
    if (colorOps.containsKey(color))
    {
      result = colorOps.get(color);
    }
    else
    {
      result = new ColorFilterOp(color);
      colorOps.put(color, result);
    }
    
    return result;
  }

}
