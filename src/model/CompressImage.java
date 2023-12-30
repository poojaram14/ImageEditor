package model;

import static model.CompressUtils.combineColorChannels;
import static model.CompressUtils.separateColorChannels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents an image compression operation using the Haar wavelet transform and
 * thresholding.
 */
public class CompressImage extends AbstractImageOperation<Image> {

  private double percentage;

  /**
   * Constructs a CompressImage operation with the specified compression percentage.
   *
   * @param percentage The percentage of coefficients to retain during compression.
   * @throws IllegalArgumentException If the provided percentage is invalid.
   */
  public CompressImage(double percentage) throws IllegalArgumentException {
    this.percentage = percentage;
  }

  @Override
  public Image apply(Image... inputs) {
    if (inputs == null) {
      throw new IllegalArgumentException("Null image");
    }
    if (inputs.length != 1) {
      throw new IllegalArgumentException(
          "compressImage operation requires exactly one input image.");
    }
    return compress(inputs[0]);
  }

  /**
   * Compresses an input image using the Haar wavelet transform and thresholding.
   *
   * @param image The input image to be compressed.
   * @return The compressed image.
   */
  public Image compress(Image image) {
    int imgWidth = image.getWidth();
    int imgHeight = image.getHeight();
    Pixel[][] paddedPixels = padPixels(image);
    paddedPixels = transformImage(paddedPixels, percentage);
    Pixel[][] originalImagePixels = unPadPixels(paddedPixels, imgHeight, imgWidth);
    Image compressedImage = new Image(image.getWidth(), image.getHeight());
    compressedImage.setPixels(originalImagePixels);
    return compressedImage;
  }

  /**
   * haar transform on the 2D image pixels.
   *
   * @param values pixels
   * @return haar transform
   */
  private static double[][] haarTransform(double[][] values) {
    int size = values.length;
    while (size > 1) {
      for (int i = 0; i < size; i++) {
        double[] temp = new double[size];
        System.arraycopy(values[i], 0, temp, 0, size);
        temp = haarTransformOneD(temp, size);
        System.arraycopy(temp, 0, values[i], 0, size);

      }
      for (int i = 0; i < size; i++) {
        double[] tempColumn = new double[size];
        for (int j = 0; j < size; j++) {
          tempColumn[j] = values[j][i];
        }
        // Transform column
        tempColumn = haarTransformOneD(tempColumn, size);
        for (int j = 0; j < size; j++) {
          values[j][i] = tempColumn[j];
        }
      }
      size /= 2;
    }
    return values;
  }

  //  public static void haarTransform(Pixel[][] pixels) {
  //    int n = pixels.length;
  //
  //    while (n > 1) {
  //      // Transform one row, then one column
  //      for (int i = 0; i < n; i++) {
  //        // Transform row
  //        pixels[i] = haarTransformOneD(pixels[i], n);
  //
  //        // Transpose the matrix to prepare for column transform
  //        pixels = transposePixelMatrix(pixels, n);
  //
  //        // Transform the now-top row, which is actually a column
  //        pixels[i] = haarTransformOneD(pixels[i], n);
  //
  //        // Transpose the matrix back to its original orientation
  //        pixels = transposePixelMatrix(pixels, n);
  //      }
  //      n = n / 2;
  //    }
  //  }

  /**
   * haar transform on one dimension of a 2D image.
   *
   * @param data data
   * @return haar transform
   */
  private static double[] haarTransformOneD(double[] data, int n) {
    double[] transformed = new double[n];
    for (int i = 0; i < n / 2; i++) {
      transformed[i] = (data[2 * i] + data[2 * i + 1]) / (2 * Math.sqrt(2));
      transformed[n / 2 + i] = (data[2 * i] - data[2 * i + 1]) / (2 * Math.sqrt(2));
    }
    return transformed;
  }

  /**
   * Perform haar and inverse transform on the image with the threshold.
   *
   * @param originalPixels      original Pixels.
   * @param thresholdPercentage threshold percentage
   * @return transformed pixels
   */
  private static Pixel[][] transformImage(Pixel[][] originalPixels, double thresholdPercentage) {
    double[][][] channels = separateColorChannels(originalPixels);

    double[][] redChannel = haarTransform(channels[0]);
    double[][] greenChannel = haarTransform(channels[1]);
    double[][] blueChannel = haarTransform(channels[2]);

    List<Double> allCoefficients = getAllAbsoluteValues(redChannel, greenChannel, blueChannel);
    Collections.sort(allCoefficients);
    double threshold = determineThreshold(allCoefficients, thresholdPercentage);

    applyThreshold(redChannel, threshold);
    applyThreshold(greenChannel, threshold);
    applyThreshold(blueChannel, threshold);

    double[][] redChannelInv = inverseHaarTransform(redChannel);
    double[][] greenChannelInv = inverseHaarTransform(greenChannel);
    double[][] blueChannelInv = inverseHaarTransform(blueChannel);

    // Combine the color channels back into a Pixel[][] array
    return combineColorChannels(redChannelInv, greenChannelInv, blueChannelInv);
  }

  /**
   * Get absolute values of all three channels of an image.
   *
   * @param redChannel   red channel
   * @param greenChannel green channel
   * @param blueChannel  blue channel
   * @return list of coefficients
   */
  private static List<Double> getAllAbsoluteValues(double[][] redChannel, double[][] greenChannel,
      double[][] blueChannel) {
    List<Double> allCoefficients = new ArrayList<>();
    int dim = redChannel.length;

    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        allCoefficients.add(Math.abs(redChannel[i][j]));
        allCoefficients.add(Math.abs(greenChannel[i][j]));
        allCoefficients.add(Math.abs(blueChannel[i][j]));
      }
    }

    return allCoefficients;
  }

  /**
   * Determine the threshold value from the coefficients.
   *
   * @param allCoefficients coefficients list
   * @param percentage      compress percentage
   * @return threshold value
   */
  private static double determineThreshold(List<Double> allCoefficients, double percentage) {
    int numCoefficientsToZero = (int) (allCoefficients.size() * percentage / 100.0);
    return allCoefficients.get(Math.min(numCoefficientsToZero, allCoefficients.size() - 1));
  }

  /**
   * Apply the threshold value to the pixels.
   *
   * @param values    pixel values
   * @param threshold threshold value
   */
  private static void applyThreshold(double[][] values, double threshold) {
    int dim = values.length;

    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (Math.abs(values[i][j]) <= threshold) {
          values[i][j] = 0;
        }
      }
    }
  }

  //  public static void inverseHaarTransform(Pixel[][] pixels) {
  //    int n = 1;
  //
  //    while (n < pixels.length) {
  //      // Increase the size of the transformed area
  //      n = n * 2;
  //
  //      // Inverse transform one column (which is a row after transpose), then one row
  //      for (int i = 0; i < n; i++) {
  //        // Transpose the matrix to inverse transform the column (which becomes a row)
  //        pixels = transposePixelMatrix(pixels, n);
  //
  //        // Inverse transform the now-top row, which is actually a column
  //        pixels[i] = inverseHaarTransformOneD(pixels[i], n);
  //
  //        // Transpose the matrix back to its original orientation
  //        pixels = transposePixelMatrix(pixels, n);
  //
  //        // Inverse transform row
  //        pixels[i] = inverseHaarTransformOneD(pixels[i], n);
  //      }
  //    }
  //  }

  /**
   * Perform inverse haar transform on the transformed image.
   *
   * @param values pixel values
   * @return inverse transformed values
   */
  private static double[][] inverseHaarTransform(double[][] values) {
    int size = 2;

    while (size <= values.length) {

      // Inverse transform on columns
      for (int j = 0; j < size; j++) {
        double[] temp = new double[size];
        for (int i = 0; i < size; i++) {
          temp[i] = values[i][j];
        }
        temp = inverseHaarTransformOneD(temp);
        // Inverse transform on rows
        for (int i = 0; i < size; i++) {
          values[i][j] = temp[i];
        }
      }

      for (int i = 0; i < size; i++) {
        double[] temp = new double[size];
        System.arraycopy(values[i], 0, temp, 0, size);
        temp = inverseHaarTransformOneD(temp);
        System.arraycopy(temp, 0, values[i], 0, size);
      }

      size *= 2;
    }
    return values;
  }

  /**
   * inverse transform on one dimension of a 2D image.
   *
   * @param data data
   * @return inverse transform
   */
  private static double[] inverseHaarTransformOneD(double[] data) {
    double[] temp = new double[data.length];
    int halfLength = data.length / 2;
    for (int i = 0; i < halfLength; i++) {
      temp[2 * i] = (data[i] + data[i + halfLength]) * Math.sqrt(2);
      temp[2 * i + 1] = (data[i] - data[i + halfLength]) * Math.sqrt(2);
    }
    System.arraycopy(temp, 0, data, 0, data.length);
    return data;
  }
}