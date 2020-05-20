package com.company;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CropImage {

    //    private JFrame frame;
    private JPanel selectedAreaPanel;
    private ImagePanel imagePanel;
    private String imagePath = "C:\\Users\\admin\\Desktop\\akkasi-yasgroup.ir-5.jpg";
    private BufferedImage selectedRect;

    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
////                CropImage window = new CropImage();
////                window.frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
    public CropImage(JFrame frame) throws IOException {
        System.out.println("hello!");
        initialize(frame);
        frame.setVisible(true);
    }


    private void initialize(JFrame frame) throws IOException {
        frame = new JFrame();
        frame.setTitle("Select Area In Image");
        frame.setBounds(200, 200, 708, 370);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Image Panel display selected area of the image
        // selected area
        selectedAreaPanel = new JPanel();
        frame.getContentPane().add(selectedAreaPanel);
        selectedAreaPanel.setBounds(469, 36, 221, 289);

        // Image Panel display image with graphics
//        imagePanel = new ImagePanel(imagePath);
        imagePanel.setOnCroppedListener(this::updateSelectedRegion);
        imagePanel.setBounds(10, 11, 449, 314);
        frame.getContentPane().add(imagePanel);
        JLabel lblSelectedArea = new JLabel("Selected Area");
        lblSelectedArea.setBounds(469, 11, 221, 14);
        frame.getContentPane().add(lblSelectedArea);


        JButton cropButton = new JButton("Crop");
        cropButton.setBounds(560, 11, 80, 30);
        cropButton.addActionListener(e -> {

            //todo: make sure to clean this piece of code
            if (selectedRect != null) {
                try {
                    ImageIO.write(selectedRect, "png", new File("C:\\Users\\admin\\Desktop\\edited.jpg"));
                    imagePanel.updateImagePanel(selectedRect);
                    selectedAreaPanel.getGraphics().clearRect(0, 0, 221, 289);
                    selectedRect = null;

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(cropButton);
    }

    public void updateSelectedRegion(BufferedImage bufferedImage) {
        selectedRect = bufferedImage;
    }
}
