package com.company;

import com.jhlabs.image.BlurFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by admin on 2017/05/26 ,0026.
 */
public class Rotate {

    BufferedImage bufferedImage;
    public void foo(){
        BlurFilter blurFilter = new BlurFilter();
    }


    public Rotate(JFrame frame, Image image, JPanel jPanel, JPanel jPanel1) {
        JSlider slider = new JSlider();
        slider.setValue(0);
        slider.setMaximum(360);
        slider.setMinimum(0);
        slider.setMinorTickSpacing(10);
        jPanel.add(slider);
        slider.setVisible(true);


        JLabel label = new JLabel() {

            public double Angle(int degree) {
                frame.repaint();
                return Math.toRadians(degree) * 3.6;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.translate(this.getSize().width / 2, this.getSize().height / 2);
                g2.rotate(Angle(slider.getValue()));
                g2.drawImage(image, -image.getWidth(null) / 2, -image.getHeight(null) / 2, null);
                slider.setToolTipText(String.valueOf(slider.getValue() * 3.6));
                setVisible(true);
            }

        };
        jPanel1.add(label);
        jPanel1.add(jPanel);
        frame.revalidate();
        frame.add(jPanel1, BorderLayout.CENTER, 0);
        frame.setVisible(true);
        System.out.println(label.isVisible());
    }
}
