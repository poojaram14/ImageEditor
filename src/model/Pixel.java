package model;

import java.util.Objects;

/**
 * This class represents a single pixel in an image, characterized by the red, green, and blue color
 * components.
 */
public class Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a pixel object with the specified red, green, and blue color components.
   *
   * @param red   The red color component (0-255).
   * @param green The green color component (0-255).
   * @param blue  The blue color component (0-255).
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Gets the blue color component of the pixel.
   *
   * @return The blue color component.
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Gets the green color component of the pixel.
   *
   * @return The blue color component (0-255).
   */

  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the red color component of the pixel.
   *
   * @return The blue color component (0-255).
   */

  public int getRed() {
    return this.red;
  }


  /**
   * Sets the blue color component of the pixel.
   *
   * @param blue The blue color component to set (0-255).
   */
  public void setBlue(int blue) {
    if (blue < 0 || blue > 255) {
      throw new IllegalArgumentException("Blue color component must be in the range 0-255");
    }
    this.blue = blue;
  }

  /**
   * Sets the green color component of the pixel.
   *
   * @param green The green color component to set (0-255).
   */
  public void setGreen(int green) {
    if (green < 0 || green > 255) {
      throw new IllegalArgumentException("Green color component must be in the range 0-255");
    }
    this.green = green;
  }

  /**
   * Sets the red color component of the pixel.
   *
   * @param red The red color component to set (0-255).
   */
  public void setRed(int red) {
    if (red < 0 || red > 255) {
      throw new IllegalArgumentException("Red color component must be in the range 0-255");
    }
    this.red = red;
  }

  /**
   * Checks whether this pixel is equal to another pixel object. Two Pixel objects are considered
   * equal if their red, green, and blue color components are equal.
   *
   * @param o The object to compare.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pixel pixel = (Pixel) o;
    return red == pixel.red &&
        green == pixel.green &&
        blue == pixel.blue;
  }

  /**
   * Generates a hash code for Pixel based on its red, green, and blue color components.
   *
   * @return The hash code for the Pixel object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }

  /**
   * Gets the RGB representation of the pixel.
   *
   * @return The RGB representation of the pixel as an integer.
   */
  public int toRGB() {
    return (red << 16) | (green << 8) | blue;
  }
}
