package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import model.Image;
import model.Pixel;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static Image readPPM(String filename) throws FileNotFoundException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      throw e;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        if (r < 0 || r > maxValue ||
            g < 0 || g > maxValue ||
            b < 0 || b > maxValue) {
          throw new IllegalArgumentException("Invalid pixel value");
        }
        pixels[i][j] = new Pixel(r, g, b);
      }
    }
    Image img = new Image(width, height);
    img.setPixels(pixels);
    return img;
  }

  /**
   * Writes the Image object contents to a PPM format file.
   *
   * @param img      Image object containing pixel data.
   * @param filePath Destination file path.
   * @throws IOException if there's an issue writing to the file.
   */
  public static void writePPM(Image img, String filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write("P3\n");
      writer.write(img.getWidth() + " " + img.getHeight() + "\n");
      writer.write("255\n");  // Assuming max color value is 255

      for (int i = 0; i < img.getHeight(); i++) {
        for (int j = 0; j < img.getWidth(); j++) {
          Pixel pixel = img.getPixel(j, i);
          writer.write(pixel.getRed() + " ");
          writer.write(pixel.getGreen() + " ");
          writer.write(pixel.getBlue() + "  ");
        }
        writer.newLine();
      }
    }
  }

  /**
   * Reads an image file in the JPG or PNG format and returns its contents as an Image object.
   *
   * @param filePath Path of the JPG or PNG file.
   * @return Image object containing pixel data from the file.
   * @throws IOException if there's an issue reading the file.
   */
  public static Image readJPGorPNG(String filePath) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(filePath));
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = bufferedImage.getRGB(j, i);
        Color color = new Color(rgb);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        pixels[i][j] = new Pixel(r, g, b);
      }
    }
    Image img = new Image(width, height);
    img.setPixels(pixels);
    return img;
  }

  /**
   * Writes the Image object contents to a file in the specified format (JPG or PNG).
   *
   * @param img      Image object containing pixel data.
   * @param filePath Destination file path.
   * @param format   Image format - "jpg" or "png".
   * @throws IOException if there's an issue writing to the file.
   */
  public static void writeJPGorPNG(Image img, String filePath, String format) throws IOException {
    int width = img.getWidth();
    int height = img.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = img.getPixel(j, i);
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        Color color = new Color(r, g, b);
        int rgb = color.getRGB();
        bufferedImage.setRGB(j, i, rgb);
      }
    }

    ImageIO.write(bufferedImage, format, new File(filePath));
  }
}

