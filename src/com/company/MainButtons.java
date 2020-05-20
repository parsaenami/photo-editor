package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainButtons extends JButton{
    String path;
    String tool;

    public MainButtons(String path, String tool) {
        this.path = path;
        this.tool = tool;
        try {
            setIcon(new ImageIcon(ImageIO.read(new File(path))));
            setVerticalTextPosition(SwingConstants.BOTTOM);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setBackground(Color.white);
            setPreferredSize(new Dimension(65, 65));
            setToolTipText(tool);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
