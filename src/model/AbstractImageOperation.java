package model;

/**
 * This abstract class provides utility methods for image operations.
 *
 * @param <R> The result type of the image operation.
 */
public abstract class AbstractImageOperation<R> implements ImageOperation<R> {

  /**
   * Pads an image's pixels to a power of 2 dimensions.
   *
   * @param image The input image to pad.
   * @return A 2D array of pixels with padded dimensions.
   */
  protected static Pixel[][] padPixels(Image image) {
    int imgWidth = image.getWidth();
    int imgHeight = image.getHeight();
    int maxDim = Math.max(imgWidth, imgHeight);
    int newDim = 1;
    while (newDim < maxDim) {
      newDim *= 2;
    }
    Pixel[][] newPixels = new Pixel[newDim][newDim];
    Pixel padPixel = new Pixel(0, 0, 0);
    for (int i = 0; i < newDim; i++) {
      for (int j = 0; j < newDim; j++) {
        if (i < imgHeight && j < imgWidth) {
          newPixels[i][j] = image.getPixel(j, i);
        } else {
          newPixels[i][j] = padPixel;
        }
      }
    }
    return newPixels;
  }

  /**
   * Removes padding from an array of pixels and returns the original dimensions.
   *
   * @param pixels    The padded pixels array.
   * @param imgHeight The original image height.
   * @param imgWidth  The original image width.
   * @return A 2D array of pixels with original dimensions.
   */
  protected static Pixel[][] unPadPixels(Pixel[][] pixels, int imgHeight, int imgWidth) {
    Pixel[][] originalPixels = new Pixel[imgHeight][imgWidth];
    for (int i = 0; i < imgHeight; i++) {
      System.arraycopy(pixels[i], 0, originalPixels[i], 0, imgWidth);
    }
    return originalPixels;
  }
}
