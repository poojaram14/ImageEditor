package model;

/**
 * This abstract class serves as a base class for the colour transformation operations in the
 * model.
 */
public abstract class AbstractColor implements ImageOperation {

  /**
   * Applies the color transformation operation to the input image pixel by pixel.
   *
   * @param inputs The input image to transform.
   * @return A new image with modified colors based on the transformation.
   */
  @Override
  public Image apply(Image... inputs) {
    Image input = inputs[0];
    int width = input.getWidth();
    int height = input.getHeight();
    Pixel[][] imagePixels = input.getPixels();
    Pixel[][] resultPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel originalPixel = imagePixels[i][j];
        processPixel(originalPixel, resultPixels, i, j);
      }
    }

    Image resultImage = new Image(width, height);
    resultImage.setPixels(resultPixels);
    return resultImage;
  }

  /**
   * Processes a single pixel in the input image to apply the color transformation.
   *
   * @param originalPixel The original pixel from the input image.
   * @param resultPixels  2D array to store the modified pixels.
   * @param i             Row index of the pixel.
   * @param j             Column index of the pixel.
   */
  protected abstract void processPixel(Pixel originalPixel, Pixel[][] resultPixels, int i, int j);

}
