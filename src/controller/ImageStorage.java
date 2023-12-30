package controller;

import java.util.HashMap;
import java.util.Map;
import model.Image;

/**
 * This class represents a storage system for images. It provides methods to associate image names
 * with image objects and retrieve images with their names.
 */
public class ImageStorage {

  private static Map<String, Image> imageMap = new HashMap<>();

  /**
   * Retrieves an Image object associated with the given image name.
   *
   * @param imageName The name of the image
   * @return image object, or null if not found
   */
  public static Image getImageByName(String imageName) {
    return imageMap.get(imageName);
  }

  /**
   * Associates an image name with an Image object.
   *
   * @param imageName The name
   * @param image     The image associated with the name
   */
  public static void associateImageWithName(String imageName, Image image) {
    imageMap.put(imageName, image);
  }
}

