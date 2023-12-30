package model;

import model.enums.Color;

/**
 * This class represents an image operation to visualize specific color components (red, green, and
 * blue) of the image.
 */
public class VisualizeComponents extends AbstractColor {

  private final Color color;

  /**
   * Constructs the object with the specified color to visualize.
   *
   * @param color the color component (red, blue, green)
   */
  public VisualizeComponents(Color color) {
    this.color = color;
  }

  /**
   * Processes a single pixel in the input image to apply the color visualization transformation.
   *
   * @param originalPixel The original pixel from the input image.
   * @param resultPixels  2D array to store the modified pixels.
   * @param i             Row index of the pixel.
   * @param j             Column index of the pixel.
   */
  @Override
  protected void processPixel(Pixel originalPixel, Pixel[][] resultPixels, int i, int j) {
    int red = originalPixel.getRed();
    int green = originalPixel.getGreen();
    int blue = originalPixel.getBlue();

    switch (color) {
      case RED:
        resultPixels[i][j] = new Pixel(red, 0, 0);
        break;
      case GREEN:
        resultPixels[i][j] = new Pixel(0, green, 0);
        break;
      case BLUE:
        resultPixels[i][j] = new Pixel(0, 0, blue);
        break;
      default:
        break;
    }
  }
}
