package model;

import static model.VisualizeHistogram.calculateHistogram;

/**
 * This class represents an image operation for color correction.
 */
public class ColorCorrect implements ImageOperation<Image> {

  private int splitPercentage = 100;

  /**
   * Constructs a ColorCorrect operation without any split percentage specified.
   */
  public ColorCorrect() {
    this.splitPercentage = 100;
  }

  /**
   * Constructs a ColorCorrect operation with the specified split percentage.
   *
   * @param splitPercentage The percentage of the image to apply color correction to.
   */
  public ColorCorrect(int splitPercentage) {
    this.splitPercentage = splitPercentage;
  }

  @Override
  public Image apply(Image... inputs) {
    if (inputs.length != 1) {
      throw new IllegalArgumentException(
          "ColorCorrection operation requires exactly one input image.");
    }

    return correctColor(inputs[0]);
  }

  /**
   * Corrects the color of an input image using color histograms and peak values.
   *
   * @param inputImage The input image to be color-corrected.
   * @return The color-corrected image.
   */
  public Image correctColor(Image inputImage) {
    int[] redHistogram = calculateHistogram(inputImage, 'r');
    int[] greenHistogram = calculateHistogram(inputImage, 'g');
    int[] blueHistogram = calculateHistogram(inputImage, 'b');

    int averagePeakPosition =
        (findPeak(redHistogram) + findPeak(greenHistogram) + findPeak(blueHistogram)) / 3;
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    Pixel[][] correctedPixels = new Pixel[height][width];

    int splitPoint = (splitPercentage == 0) ? width : (int) (width * splitPercentage / 100.0);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel originalPixel = inputImage.getPixel(j, i);
        correctedPixels[i][j] = applyColorCorrection(originalPixel, averagePeakPosition,
            j < splitPoint, redHistogram, greenHistogram, blueHistogram);
      }

      // Draw a vertical line at the split position
      if (splitPercentage > 0 && splitPoint < width) {
        correctedPixels[i][splitPoint] = new Pixel(0, 0, 0);
      }
    }

    Image correctedImage = new Image(width, height);
    correctedImage.setPixels(correctedPixels);
    return correctedImage;
  }


  private Pixel applyColorCorrection(Pixel pixel, int averagePeakPosition, boolean applyCorrection,
      int[] redHistogram, int[] greenHistogram, int[] blueHistogram) {
    int redOffset = averagePeakPosition - findPeak(redHistogram);
    int greenOffset = averagePeakPosition - findPeak(greenHistogram);
    int blueOffset = averagePeakPosition - findPeak(blueHistogram);

    int red = applyCorrection ? clamp(pixel.getRed() + redOffset) : pixel.getRed();
    int green = applyCorrection ? clamp(pixel.getGreen() + greenOffset) : pixel.getGreen();
    int blue = applyCorrection ? clamp(pixel.getBlue() + blueOffset) : pixel.getBlue();

    return new Pixel(red, green, blue);
  }

  private int clamp(int value) {
    return Math.min(255, Math.max(0, value));
  }

  private static int findPeak(int[] histogram) {
    int peak = 0;
    int maxCount = 0;
    for (int i = 0; i < histogram.length; i++) {
      if (histogram[i] > maxCount) {
        maxCount = histogram[i];
        peak = i;
      }
    }
    return peak;
  }
}