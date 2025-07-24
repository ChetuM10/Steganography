package main;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * Abstract class handling common image operations.
 * This promotes code reuse by ensuring all image-processing classes inherit
 * this functionality.
 */
public abstract class ImageProcessor {
    /**z
     * Loads an image from the given file path.
     * 
     * @param path The path of the image file.
     * @return BufferedImage object representing the loaded image.
     * @throws IOException if the file is invalid or cannot be read.
     */
    protected BufferedImage loadImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        if (image == null) {
            throw new IOException("Invalid image file!");
        }
        return image;
    }
    
    /**
     * Saves an image to the specified output path.
     * 
     * @param image The BufferedImage to save.
     * @param outputPath The path where the image will be saved.
     * @throws IOException if the file cannot be written.
     */
    protected void saveImage(BufferedImage image, String outputPath) throws IOException {
        // Ensure the output path has a .png extension
        if (!outputPath.toLowerCase().endsWith(".png")) {
            outputPath += ".png";
        }
        
        File outputFile = new File(outputPath);
        // Create directories if they don't exist
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        ImageIO.write(image, "png", outputFile);
    }
}