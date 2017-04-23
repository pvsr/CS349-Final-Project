package gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import visual.statik.sampled.IdentityOp;

/**
 * An operation that sets the color of a Content while preserving alpha.
 *
 * @author Peter Rice (ricepv)
 * This work complies with the JMU Honor Code.
 *
 */
public class ColorFilterOp extends IdentityOp
{
  private Color destColor;

  /**
   * Explicit value constructor.
   * 
   * @param destColor The desired color
   */
  public ColorFilterOp(Color destColor)
  {
    this.destColor = destColor;
  }

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest)
  {
    int srcRGB, srcHeight, srcWidth;

    srcWidth = src.getWidth();
    srcHeight = src.getHeight();

    if (dest == null)
      dest = createCompatibleDestImage(src, src.getColorModel());

    for (int x = 0; x < srcWidth; x++)
    {
      for (int y = 0; y < srcHeight; y++)
      {
        srcRGB = src.getRGB(x, y);

        // keep src's alpha but change RGB to new color
        dest.setRGB(x, y,
            (destColor.getRGB() & 0x00ffffff) | (srcRGB & 0xff000000));
      }
    }
    return dest;
  }

}
