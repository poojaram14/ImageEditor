package model;

import model.enums.Filter;

/**
 * Represents the sharpen filter transformation applied to an image.
 */
public class Sharpen extends AbstractFilter {

  private int splitPercentage = 100; // New field for split percentage

  /**
   * Constructs the sharpen transformation. Initializes the filter type to SHARPEN.
   */
  public Sharpen() {
    super(Filter.SHARPEN);
  }

  /**
   * Constructs the sharpen transformation. Initializes the filter type to SHARPEN and assigns split
   * percentage.
   */
  public Sharpen(int splitPercentage) {
    super(Filter.SHARPEN);
    this.splitPercentage = splitPercentage;
  }

  /**
   * Provided the kernel matrix for the Sharpen transformation.
   *
   * @return A 5x5 matrix
   */
  @Override
  protected double[][] getKernel() {
    return new double[][]{
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
  }

  @Override
  protected int getSplit() {
    return this.splitPercentage;
  }
}
