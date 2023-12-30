package model;

/**
 * Utility class for image compression operations.
 */
public class CompressUtils {

  /**
   * Separates the color channels of a Pixel[][] array into three double[][] arrays.
   *
   * @param pixels The Pixel[][] array representing the image.
   * @return An array of three double[][] arrays representing the red, green, and blue color
   *     channels.
   */
  public static double[][][] separateColorChannels(Pixel[][] pixels) {
    int size = pixels.length;
    double[][] redChannel = new double[size][size];
    double[][] greenChannel = new double[size][size];
    double[][] blueChannel = new double[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Pixel pixel = pixels[i][j];
        redChannel[i][j] = pixel.getRed();
        greenChannel[i][j] = pixel.getGreen();
        blueChannel[i][j] = pixel.getBlue();
      }
    }
    return new double[][][]{redChannel, greenChannel, blueChannel};
  }

  /**
   * Combines three color channel arrays into a Pixel[][] array.
   *
   * @param redChannel   The red color channel represented as a double[][] array.
   * @param greenChannel The green color channel represented as a double[][] array.
   * @param blueChannel  The blue color channel represented as a double[][] array.
   * @return A Pixel[][] array representing the image.
   */
  public static Pixel[][] combineColorChannels(double[][] redChannel, double[][] greenChannel,
      double[][] blueChannel) {
    int size = redChannel.length;
    Pixel[][] pixels = new Pixel[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int red = clamp((int) Math.round(redChannel[i][j]));
        int green = clamp((int) Math.round(greenChannel[i][j]));
        int blue = clamp((int) Math.round(blueChannel[i][j]));
        pixels[i][j] = new Pixel(red, green, blue);
      }
    }
    return pixels;
  }

  /**
   * Clamps the given value within the range [0, 255].
   *
   * @param value The input value to be clamped.
   * @return The clamped value within the range [0, 255].
   */
  public static int clamp(int value) {
    return Math.max(0, Math.min(value, 255));
  }
}
