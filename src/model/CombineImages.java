package model;

/**
 * Class that represents an image operation that combines three color component images: a red
 * component image, a green component image, and a blue component image to form a single color
 * image.
 */
public class CombineImages implements ImageOperation {

  /**
   * Combines the red, green, and blue color component images to create a single color image.
   *
   * @param inputs An array of three input images: red, green, and blue components.
   * @return The combined color image formed by merging the three color component images.
   * @throws IllegalArgumentException If the number of input images is not exactly three or if the
   *                                  input images have different dimensions.
   */
  @Override
  public Image apply(Image... inputs) {
    if (inputs.length != 3) {
      throw new IllegalArgumentException("Three input images are required");
    }

    Image redComponent = inputs[0];
    Image greenComponent = inputs[1];
    Image blueComponent = inputs[2];

    if (!(redComponent.getWidth() == greenComponent.getWidth()
        && redComponent.getWidth() == blueComponent.getWidth())
        || !(redComponent.getHeight() == greenComponent.getHeight()
        && redComponent.getHeight() == blueComponent.getHeight())) {
      throw new IllegalArgumentException("All images must have the same dimensions to be combined");
    }

    int width = redComponent.getWidth();
    int height = redComponent.getHeight();

    Pixel[][] redPixels = redComponent.getPixels();
    Pixel[][] greenPixels = greenComponent.getPixels();
    Pixel[][] bluePixels = blueComponent.getPixels();

    Pixel[][] combinedPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = Math.min(255, Math.max(0, redPixels[i][j].getRed()));
        int g = Math.min(255, Math.max(0, greenPixels[i][j].getGreen()));
        int b = Math.min(255, Math.max(0, bluePixels[i][j].getBlue()));
        combinedPixels[i][j] = new Pixel(r, g, b);
      }
    }

    Image combinedImage = new Image(width, height);
    combinedImage.setPixels(combinedPixels);
    return combinedImage;
  }
}
