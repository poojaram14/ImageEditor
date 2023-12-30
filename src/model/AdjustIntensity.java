package model;

import model.enums.Intensity;

/**
 * Class represents an image operation that adjusts the intensity of an image. Intensity adjustment
 * can brighten or darken an image by changing the intensity of the pixel colors.
 */
public class AdjustIntensity implements ImageOperation {

  private final int adjustment;


  /**
   * Constructs an `AdjustIntensity` operation with the specified adjustment and intensity.
   *
   * @param adjustment The adjustment value for the intensity adjustment.
   * @param intensity  The type of intensity adjustment, which can be either be BRIGHTEN or DARKEN.
   */
  public AdjustIntensity(int adjustment, Intensity intensity) {
    this.adjustment = adjustment;
  }

  /**
   * Adjust the intensity of the given image.
   *
   * @param inputs the input image
   * @return an image with intensity adjusted according to the specified adjustment and intensity
   *     type.
   * @throws IllegalArgumentException if the number of input images is not  one.
   */
  @Override
  public Image apply(Image... inputs) {
    if (inputs.length != 1) {
      throw new IllegalArgumentException("AdjustIntensity requires one input image.");
    }

    Image image = inputs[0];

    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] imagePixels = image.getPixels();
    Pixel[][] intensityPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel originalPixel = imagePixels[i][j];
        int red = originalPixel.getRed();
        int green = originalPixel.getGreen();
        int blue = originalPixel.getBlue();

        int r;
        int g;
        int b;

        r = Math.max(0, Math.min(red + this.adjustment, 255));
        g = Math.max(0, Math.min(green + this.adjustment, 255));
        b = Math.max(0, Math.min(blue + this.adjustment, 255));

        intensityPixels[i][j] = new Pixel(r, g, b);
      }
    }
    Image newImage = new Image(width, height);
    newImage.setPixels(intensityPixels);
    return newImage;
  }
}