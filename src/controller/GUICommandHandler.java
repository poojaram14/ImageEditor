package controller;

import model.AdjustLevel;
import model.Blur;
import model.ColorCorrect;
import model.CompressImage;
import model.Flip;
import model.Image;
import model.Sepia;
import model.Sharpen;
import model.ValueIntensityLuma;
import model.VisualizeComponents;
import model.VisualizeHistogram;
import model.enums.Channel;
import model.enums.Color;
import model.enums.Direction;

/**
 * This class handles various image processing operations and provides methods to apply those
 * operations to images.
 */
public class GUICommandHandler {

  /**
   * Apply a blur operation to the given image.
   *
   * @param image The input image.
   * @param split The split value for the blur operation.
   * @return The blurred image.
   */
  public Image applyBlur(Image image, int split) {
    Blur blurOperation;
    if (split == 0) {
      blurOperation = new Blur();
    } else {
      blurOperation = new Blur(split);
    }
    return blurOperation.apply(image);
  }

  /**
   * Apply a sharpen operation to the given image.
   *
   * @param image The input image.
   * @param split The split value for the sharpen operation.
   * @return The sharpened image.
   */
  public Image applySharpen(Image image, int split) {
    Sharpen sharpenOperation;
    if (split == 0) {
      sharpenOperation = new Sharpen();
    } else {
      sharpenOperation = new Sharpen(split);
    }
    return sharpenOperation.apply(image);
  }

  /**
   * Apply grayscale conversion to the given image.
   *
   * @param image The input image.
   * @param split The split value for the grayscale conversion.
   * @return The grayscale image.
   */
  public Image applyGreyScale(Image image, int split) {
    ValueIntensityLuma vil;
    if (split == 0) {
      vil = new ValueIntensityLuma(Channel.LUMA);
    } else {
      vil = new ValueIntensityLuma(Channel.LUMA, split);
    }
    return vil.apply(image);
  }

  /**
   * Apply sepia tone effect to the given image.
   *
   * @param image The input image.
   * @param split The split value for the sepia effect.
   * @return The sepia-toned image.
   */
  public Image applySepia(Image image, int split) {
    Sepia sepiaOperation;
    if (split == 0) {
      sepiaOperation = new Sepia();
    } else {
      sepiaOperation = new Sepia(split);
    }
    return sepiaOperation.apply(image);
  }

  /**
   * Visualize the red component of the given image.
   *
   * @param currentImageModel The input image.
   * @param split             The split value for visualization.
   * @return The image with only the red component.
   */
  public Image visualizeRed(Image currentImageModel, Integer split) {
    VisualizeComponents vis;
    vis = new VisualizeComponents(Color.RED);
    return vis.apply(currentImageModel);
  }

  /**
   * Visualize the green component of the given image.
   *
   * @param currentImageModel The input image.
   * @param split             The split value for visualization.
   * @return The image with only the green component.
   */
  public Image visualizeGreen(Image currentImageModel, Integer split) {
    VisualizeComponents vis;
    vis = new VisualizeComponents(Color.GREEN);
    return vis.apply(currentImageModel);
  }

  /**
   * Visualize the blue component of the given image.
   *
   * @param currentImageModel The input image.
   * @param split             The split value for visualization.
   * @return The image with only the blue component.
   */
  public Image visualizeBlue(Image currentImageModel, Integer split) {
    VisualizeComponents vis;
    vis = new VisualizeComponents(Color.BLUE);
    return vis.apply(currentImageModel);
  }

  /**
   * Apply a flip operation to the given image.
   *
   * @param image     The input image.
   * @param direction The direction of the flip.
   * @return The flipped image.
   */
  public Image applyFlip(Image image, Direction direction) {
    Flip flipOperation = new Flip(direction);
    return flipOperation.apply(image);
  }

  /**
   * Apply color correction to the given image.
   *
   * @param image           The input image.
   * @param splitPercentage The split percentage for color correction.
   * @return The color-corrected image.
   */
  public Image applyColorCorrection(Image image, int splitPercentage) {
    ColorCorrect colorCorrectOperation = new ColorCorrect(splitPercentage);
    return colorCorrectOperation.apply(image);
  }

  /**
   * Apply histogram visualization to the given image.
   *
   * @param image           The input image.
   * @param splitPercentage The split percentage for histogram visualization.
   * @return The image with a visualized histogram.
   */
  public Image applyHistogram(Image image, int splitPercentage) {
    VisualizeHistogram visualizeHistogramOperation = new VisualizeHistogram(splitPercentage);
    return visualizeHistogramOperation.apply(image);
  }

  /**
   * Apply level adjustment to the given image.
   *
   * @param image           The input image.
   * @param splitPercentage The split percentage for level adjustment.
   * @param black           The black level for adjustment.
   * @param mid             The mid-level for adjustment.
   * @param white           The white level for adjustment.
   * @return The image with adjusted levels.
   */
  public Image applyLevel(Image image, int splitPercentage, double black, double mid,
      double white) {
    AdjustLevel adjustLevelOperation;
    if (splitPercentage == 0) {
      adjustLevelOperation = new AdjustLevel(black, mid, white);
    } else {
      adjustLevelOperation = new AdjustLevel(black, mid, white, splitPercentage);
    }
    return adjustLevelOperation.apply(image);
  }

  /**
   * Apply image compression to the given image.
   *
   * @param image      The input image.
   * @param percentage The compression percentage.
   * @return The compressed image.
   */
  public Image applyCompress(Image image, int percentage) {
    CompressImage compress = new CompressImage(percentage);
    return compress.apply(image);
  }
}
