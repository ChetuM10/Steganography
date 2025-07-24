package main;

public class FileRetriever {
    public static void main(String[] args) {
        String imagePath = "C:\Users\cheta\OneDrive\Desktop\PROJECTS\Steganography\Steganography\INPUT\COVER_IMAGES";
        String outputFilePath = "C:\Users\cheta\OneDrive\Desktop\PROJECTS\Steganography\Steganography\OUTPUT\RECOVERED_FILES\recoveredfiles.txt";

        // Create an instance of LSBDecoder
        LSBDecoder decoder = new LSBDecoder();
        
        try {
            // Use the instance method
            decoder.extractFileFromImage(imagePath, outputFilePath);
            System.out.println("File extracted successfully");
        } catch (Exception e) {
            System.err.println("Error extracting file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}