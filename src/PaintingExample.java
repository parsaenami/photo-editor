import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class PaintingExample {
    private JFrame      j;
    private JMenu       jmenu;
    private JMenuBar    jbar;
    private JMenuItem   jmi, jexit;
    private JPanel      jpanel, jpanelbar;
    private JButton     jpre, jnext;
    JLabel              image;
    ImageIcon           ic;
    Image               img;

    PaintingExample() {
        j = new JFrame("Image Viewer 2");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        j.revalidate();
        j.setSize(700, 800);
//        System.out.println(j.getSize());
//         j.setExtendedState(Frame.MAXIMIZED_BOTH);
//         j.setLocationRelativeTo(null);
        j.setLocationByPlatform(true);
        j.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.setSize(200, 200);
        image = new JLabel(" ", JLabel.CENTER);
        jpanel.add(image, BorderLayout.CENTER);

        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        c.ipady = 100;
        // c.weightx=0.1;
        c.weighty = 0.1;
//        c.ipady = 50;

        c.insets = new Insets(5, 5, 10, 5);
         jpanel.setBackground(Color.BLACK);
        j.add(jpanel, c);

        jpanelbar = new JPanel();
        jpanelbar.setLayout(new GridBagLayout());
        jpanelbar.setBackground(Color.red);

        GridBagConstraints x = new GridBagConstraints();
        jpre = new JButton("Previous");
        x.gridx = 0;
        x.gridy = 0;
        x.gridwidth = 1;
        x.weightx = 0.1;
        // x.insets=new Insets(5,5,5,5);
        // x.fill=GridBagConstraints.NONE;
        jpanelbar.add(jpre, x);

        jnext = new JButton("Next");
        x.gridx = 1;
        jpanelbar.add(jnext, x);

        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        c.ipady = 150;
        j.add(jpanelbar, c);

        // Creating MainFrame
        jbar = new JMenuBar();
        jmenu = new JMenu("File");
        jmi = new JMenuItem("Open");
        jmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println(file);


                    BufferedImage img1 = null;
                    try {
                        img1 = ImageIO.read(new File(file.getPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(jpanel.getWidth());
                    assert img1 != null;
                    Image dimg = img1.getScaledInstance(jpanel.getWidth(), jpanel.getHeight(),
                            Image.SCALE_SMOOTH);
//                    c.ipadx = jpanel.getWidth();
//                    c.ipady = jpanel.getHeight();
//                    j.add(jpanel, c);
                    ImageIcon imageIcon = new ImageIcon(dimg);


                    image.setIcon(imageIcon);
//                        image.setIcon(new ImageIcon(ImageIO.read(file)));
//                        image = img.getScaledInstance(jPanel.getWidth(),jPanel.getHeight(),Image.SCALE_SMOOTH);
                }
            }
        });
        jexit = new JMenuItem("Exit");
        jexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        jmenu.add(jmi);
        jmenu.add(jexit);
        jbar.add(jmenu);
        j.setJMenuBar(jbar);

      j.setSize(800, 600);
        j.pack();
        j.setResizable(true);
        j.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PaintingExample();
            }
        });
    }
}