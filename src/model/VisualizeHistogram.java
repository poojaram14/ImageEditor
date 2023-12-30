package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Implements the visualization of the color histogram of an image. The histogram is a graphical
 * representation of the distribution of color intensities.
 */
public class VisualizeHistogram implements ImageOperation {

  private final int splitPercentage;

  /**
   * Constructs a VisualizeHistogram object.
   */
  public VisualizeHistogram() {
    this.splitPercentage = 100;
  }

  /**
   * Constructs a VisualizeHistogram object with a split.
   */
  public VisualizeHistogram(int splitPercentage) {
    this.splitPercentage = splitPercentage;
  }

  @Override
  public Image apply(Image... inputs) {
    if (inputs.length != 1) {
      throw new IllegalArgumentException(
          "VisualizeHistogram operation requires exactly one input image.");
    }
    return generateHistogram(inputs[0]);
  }

  /**
   * Generates a histogram for the given image.
   *
   * @param inputImage The image for which the histogram is to be generated.
   * @return An image representing the histogram of the input image.
   */
  public static Image generateHistogram(Image inputImage) {
    int width = 256;
    int height = 256;
    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = histogramImage.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
    int[] redHistogram = calculateHistogram(inputImage, 'r');
    int[] greenHistogram = calculateHistogram(inputImage, 'g');
    int[] blueHistogram = calculateHistogram(inputImage, 'b');
    drawHistogram(graphics, redHistogram, greenHistogram, blueHistogram);

    graphics.dispose();
    return convertBufferedImageToImage(histogramImage);
  }

  /**
   * Calculates the histogram for a specific color channel of an image.
   *
   * @param inputImage The image from which to calculate the histogram.
   * @param channel    The color channel ('r', 'g', or 'b') for which to calculate the histogram.
   * @return An array representing the histogram of the specified color channel.
   */
  static int[] calculateHistogram(Image inputImage, char channel) {
    int[] histogram = new int[256];
    Pixel[][] pixels = inputImage.getPixels();
    for (int i = 0; i < inputImage.getHeight(); i++) {
      for (int j = 0; j < inputImage.getWidth(); j++) {
        int intensity = getIntensityForChannel(pixels[i][j], channel);
        histogram[intensity]++;
      }
    }

    return histogram;
  }

  /**
   * Retrieves the intensity of a specific color channel from a pixel.
   *
   * @param pixel   The pixel from which to get the color channel intensity.
   * @param channel The color channel ('r', 'g', or 'b').
   * @return The intensity of the specified color channel.
   * @throws IllegalArgumentException If the specified channel is invalid.
   */
  private static int getIntensityForChannel(Pixel pixel, char channel) {
    switch (channel) {
      case 'r':
        return pixel.getRed();
      case 'g':
        return pixel.getGreen();
      case 'b':
        return pixel.getBlue();
      default:
        throw new IllegalArgumentException("Invalid channel: " + channel);
    }
  }

  /**
   * Draws the histogram for each color channel on the given graphics context.
   *
   * @param graphics       The graphics context on which to draw the histogram.
   * @param redHistogram   The histogram data for the red channel.
   * @param greenHistogram The histogram data for the green channel.
   * @param blueHistogram  The histogram data for the blue channel.
   */
  private static void drawHistogram(Graphics graphics, int[] redHistogram, int[] greenHistogram,
      int[] blueHistogram) {
    int maxCount = getMaxCountCumulative(redHistogram, greenHistogram, blueHistogram);

    drawSingleChannelHistogram(graphics, redHistogram, Color.RED, maxCount);
    drawSingleChannelHistogram(graphics, greenHistogram, Color.GREEN, maxCount);
    drawSingleChannelHistogram(graphics, blueHistogram, Color.BLUE, maxCount);
  }

  /**
   * Draws a single color channel histogram on the given graphics context.
   *
   * @param graphics  The graphics context on which to draw the histogram.
   * @param histogram The histogram data for the color channel.
   * @param color     The color to use for drawing the histogram.
   * @param maxCount  The maximum count value in the histogram data for scaling purposes.
   */
  private static void drawSingleChannelHistogram(Graphics graphics, int[] histogram, Color color,
      int maxCount) {
    graphics.setColor(color);

    for (int i = 0; i < histogram.length - 1; i++) {
      int barHeight1 = (int) (((double) histogram[i] / maxCount) * 255);
      int barHeight2 = (int) (((double) histogram[i + 1] / maxCount) * 255);

      graphics.drawLine(i, 255 - barHeight1, i + 1, 255 - barHeight2);
    }
  }

  /**
   * Calculates the maximum count across all color channel histograms. This is used to normalize the
   * histogram values for display.
   *
   * @param redHistogram   Array representing the red channel histogram.
   * @param greenHistogram Array representing the green channel histogram.
   * @param blueHistogram  Array representing the blue channel histogram.
   * @return The maximum cumulative count across all histograms.
   */
  private static int getMaxCountCumulative(int[] redHistogram, int[] greenHistogram,
      int[] blueHistogram) {
    int maxCount = 0;
    for (int i = 0; i < redHistogram.length; i++) {
      int cumulativeCount = redHistogram[i] + greenHistogram[i] + blueHistogram[i];
      if (cumulativeCount > maxCount) {
        maxCount = cumulativeCount;
      }
    }
    return maxCount;
  }

  /**
   * Converts a BufferedImage to an Image object by iterating over each pixel and creating
   * corresponding Pixel objects with the RGB values.
   *
   * @param bufferedImage The BufferedImage to convert.
   * @return The converted Image object.
   */
  static Image convertBufferedImageToImage(BufferedImage bufferedImage) {
    Image image = new Image(bufferedImage.getWidth(), bufferedImage.getHeight());
    for (int x = 0; x < bufferedImage.getWidth(); x++) {
      for (int y = 0; y < bufferedImage.getHeight(); y++) {
        int rgb = bufferedImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        image.getPixels()[y][x] = new Pixel(red, green, blue);
      }
    }
    return image;
  }
}
