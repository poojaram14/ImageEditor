package model;

/**
 * An interface for various image processing operations which takes in one or more input images and
 * produce the result of a generic type based on the specific operation carried out.
 *
 * @param <R> The type of result produced by the image processing operation.
 */
public interface ImageOperation<R> {

  /**
   * Applies the image processing operation to one or more input images and returns the result of
   * that operation.
   *
   * @param inputs One or more input images on which the operation will be applied.
   * @return The result of the image operation.
   */
  R apply(Image... inputs);
}

