package model;


/**
 * Represents the Sepia transformation applied to an image.
 */
public class Sepia extends AbstractColor {

  private final int splitPercentage;

  /**
   * Constructs a Sepia object with full effect applied to the image (100%).
   */
  public Sepia() {
    this.splitPercentage = 100;
  }

  /**
   * Constructs a Sepia object with a specified split percentage.
   *
   * @param splitPercentage The percentage of the image width from the left that will be affected by
   *                        the Sepia effect.
   */
  public Sepia(int splitPercentage) {
    this.splitPercentage = splitPercentage;
  }

  /**
   * Processes the image pixels to apply the Sepia effect. The pixel color values are transformed
   * based on the Sepia filter kernel.
   *
   * @param originalPixel The pixel from the original image.
   * @param resultPixels  The resulting pixel array after transformation.
   * @param i             The row index of the pixel.
   * @param j             The column index of the pixel.
   */
  @Override
  protected void processPixel(Pixel originalPixel, Pixel[][] resultPixels, int i, int j) {
    int width = resultPixels[0].length;
    int r = originalPixel.getRed();
    int g = originalPixel.getGreen();
    int b = originalPixel.getBlue();
    int r1 = (int) Math.min(255, 0.393 * r + 0.769 * g + 0.189 * b);
    int g1 = (int) Math.min(255, 0.349 * r + 0.686 * g + 0.168 * b);
    int b1 = (int) Math.min(255, 0.272 * r + 0.534 * g + 0.131 * b);
    r1 = Math.min(255, Math.max(0, r1));
    g1 = Math.min(255, Math.max(0, g1));
    b1 = Math.min(255, Math.max(0, b1));
    int splitPos = (int) ((double) width * (splitPercentage / 100.0));

    if (j < splitPos) {
      resultPixels[i][j] = new Pixel(r1, g1, b1);
    } else {
      resultPixels[i][j] = originalPixel;
    }
    if (j == splitPos) {
      resultPixels[i][j] = new Pixel(0, 0, 0);
    }
  }

}
