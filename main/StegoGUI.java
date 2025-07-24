package main;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class StegoGUI extends JFrame {
    private JTextField imagePathField, filePathField, outputPathField;
    private LSBEncoder encoder;
    private LSBDecoder decoder;
    
    public StegoGUI() {
        // Initialize encoder and decoder
        encoder = new LSBEncoder();
        decoder = new LSBDecoder();
        
        setTitle("Steganography File Hider");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Panel for selecting image
        JPanel imagePanel = new JPanel();
        imagePathField = new JTextField(30);
        JButton imageButton = new JButton("Select Image");
        imagePanel.add(new JLabel("Image: "));
        imagePanel.add(imagePathField);
        imagePanel.add(imageButton);

        // Panel for selecting file to hide
        JPanel filePanel = new JPanel();
        filePathField = new JTextField(30);
        JButton fileButton = new JButton("Select File");
        filePanel.add(new JLabel("File: "));
        filePanel.add(filePathField);
        filePanel.add(fileButton);

        // Panel for output path
        JPanel outputPanel = new JPanel();
        outputPathField = new JTextField(30);
        JButton outputButton = new JButton("Save As");
        outputPanel.add(new JLabel("Output: "));
        outputPanel.add(outputPathField);
        outputPanel.add(outputButton);

        // Buttons for hiding/extracting
        JButton hideButton = new JButton("Hide File");
        JButton extractButton = new JButton("Extract File");

        // Add panels to frame
        add(imagePanel);
        add(filePanel);
        add(outputPanel);
        add(hideButton);
        add(extractButton);

        // Select Image
        imageButton.addActionListener(e -> selectFile(imagePathField));

        // Select File to Hide
        fileButton.addActionListener(e -> selectFile(filePathField));

        // Save Output Image
        outputButton.addActionListener(e -> selectSaveFile(outputPathField));

        // Hide File in Image
        hideButton.addActionListener(e -> {
            String imagePath = imagePathField.getText();
            String filePath = filePathField.getText();
            String outputPath = outputPathField.getText();
            if (!imagePath.isEmpty() && !filePath.isEmpty() && !outputPath.isEmpty()) {
                encoder.hideFileInImage(imagePath, filePath, outputPath);
                JOptionPane.showMessageDialog(this, "File hidden successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select all files!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Extract File from Image
        extractButton.addActionListener(e -> {
            String imagePath = imagePathField.getText();
            String outputPath = filePathField.getText(); // Extracted file path
            if (!imagePath.isEmpty() && !outputPath.isEmpty()) {
                decoder.extractFileFromImage(imagePath, outputPath);
                JOptionPane.showMessageDialog(this, "File extracted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select all files!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void selectFile(JTextField field) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            field.setText(selectedFile.getAbsolutePath());
        }
    }

    private void selectSaveFile(JTextField field) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            field.setText(selectedFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StegoGUI gui = new StegoGUI();
            gui.setVisible(true);
        });
    }
}