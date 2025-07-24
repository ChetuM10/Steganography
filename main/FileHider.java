package main;

public class FileHider {
    public static void main(String[] args) {
        String imagePath = "C:\Users\cheta\OneDrive\Desktop\PROJECTS\Steganography\Steganography\INPUT\COVER_IMAGES";
        String filePath = "C:\Users\cheta\OneDrive\Desktop\PROJECTS\Steganography\Steganography\INPUT\FILES\secret.txt"; // File to hide
        String outputImagePath = "C:\Users\cheta\OneDrive\Desktop\PROJECTS\Steganography\Steganography\OUTPUT\HIDDEN_FILES"; // Output image

        LSBEncoder encoder = new LSBEncoder();
        
        try {
            encoder.hideFileInImage(imagePath, filePath, outputImagePath);
            System.out.println("File hidden successfully");
        } catch (Exception e) {
            System.err.println("Error hiding file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}