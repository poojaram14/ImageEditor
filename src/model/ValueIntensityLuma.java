package model;

import model.enums.Channel;

/**
 * Represents the operation to find the value, intensity and luma components of an image.
 */
public class ValueIntensityLuma extends AbstractColor {

  private final Channel channel;
  private int splitPercentage = 100;  // New field for split percentage

  /**
   * Constructs an instance with the provided channel.
   *
   * @param channel channel (value, intensity or luma)
   */
  public ValueIntensityLuma(Channel channel) {
    this.channel = channel;
  }

  /**
   * Constructs an instance with the provided channel and split percentage.
   *
   * @param channel channel (value, intensity or luma)
   */
  public ValueIntensityLuma(Channel channel, int splitPercentage) {
    this.channel = channel;
    this.splitPercentage = splitPercentage;
  }

  /**
   * Creates images containing the value, intensity and luma of the image.
   *
   * @param originalPixel The original pixel from the input image.
   * @param resultPixels  2D array to store the modified pixels.
   * @param i             Row index of the pixel.
   * @param j             Column index of the pixel.
   */
  @Override
  protected void processPixel(Pixel originalPixel, Pixel[][] resultPixels, int i, int j) {
    int width = resultPixels[0].length;
    int r = originalPixel.getRed();
    int g = originalPixel.getGreen();
    int b = originalPixel.getBlue();
    int splitPos = (int) ((double) width * (splitPercentage / 100.0));
    if (j < splitPos) {
      switch (channel) {
        case VALUE:
          int value = Math.max(r, Math.max(g, b));
          resultPixels[i][j] = new Pixel(value, value, value);
          break;
        case INTENSITY:
          int intensity = (r + g + b) / 3;
          resultPixels[i][j] = new Pixel(intensity, intensity, intensity);
          break;
        case LUMA:
          int luma = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
          resultPixels[i][j] = new Pixel(luma, luma, luma);
          break;
        default:
          break;
      }
    } else {
      resultPixels[i][j] = originalPixel;
    }
    if (j == splitPos) {
      resultPixels[i][j] = new Pixel(0, 0, 0); // Black color for the line
    }
  }
}
