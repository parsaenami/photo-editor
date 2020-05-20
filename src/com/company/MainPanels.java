package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by admin on 2017/05/25 ,0025.
 */
public class MainPanels extends JPanel {


    public MainPanels(PanelType type) {
        switch (type) {
            case MAIN_PANEL:
                setLayout(new GridBagLayout());
                setSize(900, 400);
                add(new JLabel());
                setBackground(Color.lightGray);
                setBorder(new EtchedBorder(Color.gray, Color.gray));
                setVisible(true);
            case BOTTOM_PANEL:
//                setLayout(new BorderLayout());
//                new JPanel();
                setSize(900, 100);
                add(new JLabel());
                setBackground(Color.black);
            case FILTER_PANEL:
//                new JPanel();
                setSize(600, 120);
                setLocation(200, 470);
                setBorder(new EtchedBorder(Color.black, Color.black));
                add(new JLabel());
                setBackground(Color.gray);
            case COLOR_PANEL:
//                new JPanel();
                setSize(600, 120);
                setLocation(200, 470);
                setBorder(new EtchedBorder(Color.black, Color.black));
                add(new JLabel());
                setBackground(Color.gray);
            case ROTATE_PANEL:
//                new JPanel();
                setSize(600, 50);
                setLocation(200, 540);
                setBorder(new EtchedBorder(Color.black, Color.black));
                add(new JLabel());
                setBackground(Color.gray);

        }
    }
}
