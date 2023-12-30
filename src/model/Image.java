package model;

/**
 * This class represents a two-dimensional image with a width, height, and an array of pixels.
 */
public class Image {

  private int width;
  private int height;
  private Pixel[][] pixels;

  /**
   * Constructs an image object with the specified width and height.
   *
   * @param width  The width of the image.
   * @param height The height of the image.
   */
  public Image(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[height][width];
  }

  /**
   * Gets the height of the image.
   *
   * @return The height of the image.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the width of the image.
   *
   * @return The width of the image.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the two-dimensional array of pixels representing the image.
   *
   * @return The array of pixels.
   */
  public Pixel[][] getPixels() {
    return pixels;
  }

  /**
   * Sets the array of pixels for the image.
   *
   * @param pixels The array of pixels to set.
   */
  public void setPixels(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  /**
   * Gets the pixel at the specified coordinates (x, y) in the image.
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @return The pixel at the specified coordinates.
   */
  public Pixel getPixel(int x, int y) {
    return this.pixels[y][x];
  }
}
