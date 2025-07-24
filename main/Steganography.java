package main;

/**
 * Interface defining steganography operations.
 * This ensures any class implementing it will provide methods for hiding and extracting data.
 */
public interface Steganography {
    /**
     * Hides a file within an image using steganography.
     *
     * @param imagePath Path to the cover image.
     * @param filePath Path to the file to be hidden.
     * @param outputImagePath Path where the resulting image will be saved.
     * @throws java.io.IOException if any file operations fail or if the file is too large.
     */
    void hideFileInImage(String imagePath, String filePath, String outputImagePath);
    
    /**
     * Extracts a hidden file from a steganographic image.
     *
     * @param imagePath Path to the image containing the hidden file.
     * @param outputFilePath Path where the extracted file will be saved.
     * @throws java.io.IOException if any file operations fail.
     */
    void extractFileFromImage(String imagePath, String outputFilePath);
}