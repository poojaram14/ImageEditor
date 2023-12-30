package model;

import model.enums.Filter;

/**
 * This abstract class serves as a base class for filter-based kernel operations in the model.
 */
public abstract class AbstractFilter implements ImageOperation {

  /**
   * The kernel representing the filter's convolution matrix.
   */
  protected double[][] kernel;

  /**
   * Creates an instance of the AbstractFilter class with the specified filter type.
   *
   * @param filter The filter type.
   */
  public AbstractFilter(Filter filter) {
    // Constructor logic.
  }

  /**
   * Retrieves the kernel associated with the filter.
   *
   * @return The convolution kernel.
   */
  protected abstract double[][] getKernel();

  /**
   * Retrieves the split percentage used to divide the image for processing.
   *
   * @return The split percentage.
   */
  protected abstract int getSplit();

  @Override
  public Image apply(Image... inputs) {
    int splitPercentage;
    Image input = inputs[0];
    int width = input.getWidth();
    int height = input.getHeight();
    Pixel[][] imagePixels = input.getPixels();
    Pixel[][] filterPixels = new Pixel[height][width];

    kernel = getKernel();
    splitPercentage = this.getSplit();
    int splitPos = (int) ((double) width * (splitPercentage / 100.0));

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j < splitPos) {
          filterPixels[i][j] = applyFilterToPixel(i, j, imagePixels);
        } else {
          filterPixels[i][j] = imagePixels[i][j];
        }
        // Add a vertical line at the split position
        if (j == splitPos) {
          filterPixels[i][j] = new Pixel(0, 0, 0);
        }
      }
    }
    Image filterImage = new Image(width, height);
    filterImage.setPixels(filterPixels);
    return filterImage;
  }

  private Pixel applyFilterToPixel(int i, int j, Pixel[][] imagePixels) {
    int kernelSize = kernel.length;
    int offset = kernelSize / 2;

    double r = 0;
    double g = 0;
    double b = 0;

    for (int ki = 0; ki < kernelSize; ki++) {
      for (int kj = 0; kj < kernelSize; kj++) {
        int pixelX = j - offset + kj;
        int pixelY = i - offset + ki;

        if (pixelY >= 0 && pixelY < imagePixels.length && pixelX >= 0
            && pixelX < imagePixels[0].length) {
          Pixel originalPixel = imagePixels[pixelY][pixelX];
          r += originalPixel.getRed() * kernel[ki][kj];
          g += originalPixel.getGreen() * kernel[ki][kj];
          b += originalPixel.getBlue() * kernel[ki][kj];
        }
      }
    }

    // Clamp color values to the range 0-255
    int red = Math.min(255, Math.max(0, (int) r));
    int green = Math.min(255, Math.max(0, (int) g));
    int blue = Math.min(255, Math.max(0, (int) b));

    return new Pixel(red, green, blue);
  }
}
