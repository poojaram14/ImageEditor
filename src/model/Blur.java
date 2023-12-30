package model;

import model.enums.Filter;

/**
 * Class that represents a filter operation for applying a blur effect to an image. It extends the
 * abstract filter class and provides the kernel for blurring an image.
 */
public class Blur extends AbstractFilter {

  private int splitPercentage = 100;


  /**
   * Constructs a `Blur` filter operation with the kernel specified by the getKernel method.
   */
  public Blur() {
    super(Filter.BLUR);
  }

  /**
   * Constructs a `Blur` filter operation with the kernel specified by the getKernel method with the
   * split percentage.
   */
  public Blur(int splitPercentage) {
    super(Filter.BLUR);
    this.splitPercentage = splitPercentage;
  }


  @Override
  protected double[][] getKernel() {
    return new double[][]{
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 4, 1.0 / 8}
    };
  }

  protected int getSplit() {
    return this.splitPercentage;
  }
}
